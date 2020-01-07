package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//import database.DBConnection;

public class TCPCommunication {

	private final static int PORT = 1978;
	private final static String SERVER_IP = "192.168.1.46";

	private static InetAddress ip;
	private static Socket socket;
	private static ServerSocket serverSocket;
	private static PrintWriter pw;
	private static BufferedReader br;

	public static void main(String[] args) {
//		openClientSocket();
		Client client = new Client(pw, br);
		client.connect();
		
		openServerSocket();
	}

	public static void sendMessage(String m) {
		if (pw != null)
			pw.println(m);
	}

	public static void openClientSocket() {
		try {
			ip = InetAddress.getByName(SERVER_IP);
			socket = new Socket(ip, PORT);
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du socket client");
			e.printStackTrace();

			closeClientSocket();
		}
	}

	public static void closeClientSocket() {
		try {
			if (socket != null)
				socket.close();

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