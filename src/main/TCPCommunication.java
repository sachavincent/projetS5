package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPCommunication {

	private final static int PORT = 5557;
	private final static String SERVER_IP = "90.55.189.128" + 
			"";

	private static InetAddress ip;
	private static Socket socket;
	private static ServerSocket serverSocket;
	private static PrintWriter pw;
	private static BufferedReader br;

	public static void sendMessage(String m) {
		if (pw != null)
			pw.println(m);
	}

	public static void openClientSocket() {
		try {
			ip = InetAddress.getByName(SERVER_IP);
			socket = new Socket(ip, PORT);
			pw = new PrintWriter(socket.getOutputStream(), true);
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
		} catch (IOException e) {
			System.out.println("Erreur lors de la fermeture du socket client");
		}
	}

	public static void openServerSocket() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				socket = serverSocket.accept();
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (!br.ready()) {
				}

				System.out.println(br.readLine());
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

			if (socket != null)
				socket.close();

			if (br != null)
				br.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de la fermeture du socket server");
			e.printStackTrace();
		}
	}
}