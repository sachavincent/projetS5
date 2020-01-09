package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.LectureFichier;

public class TCPCommunication {

	public static int PORT = 1978;
	public static String SERVER_IP = "192.168.1.23";

	public final static List<Socket> CLIENTS = new ArrayList<>();

	private static InetAddress ip;
	private static Socket socket;
	private static ServerSocket serverSocket;
	private static PrintWriter pw;
	private static BufferedReader br;

	public void majAdresse() {
		// modification IP et port
		LectureFichier file = new LectureFichier();
		file.openFile();
		file.fileReader();
		PORT = file.getPort();
		SERVER_IP = file.getIp();
	}

	public static ClientThread openClientSocket() {

		if (socket != null)
			return null;

		try {
			ip = InetAddress.getByName(SERVER_IP);
			socket = new Socket(ip, PORT);
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			ClientThread clientThread = new ClientThread(pw, br);
			clientThread.start();

			return clientThread;
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du socket client");
			e.printStackTrace();

			closeClientSocket();
		}
		return null;
	}

	public static void closeClientSocket() {
		try {
			if (socket != null) {
				// Suppression du socket de la liste des clients
				CLIENTS.remove(socket);

				socket.close();
			}
			if (pw != null)
				pw.close();

			if (br != null)
				br.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de la fermeture du socket client");
			e.printStackTrace();
		}
	}

	public static void openServerSocket() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) { // TODO : Condition
				socket = serverSocket.accept();

				CLIENTS.add(socket);

				new ServerThread(socket).start();
			}
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du socket server");
			e.printStackTrace();

			closeServerSocket();
		}
	}

	public static void closeServerSocket() {
		try {
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de la fermeture du socket server");
			e.printStackTrace();
		}
	}
}