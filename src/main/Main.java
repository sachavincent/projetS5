package main;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JFrame;

import communication.ClientThread;
import communication.TCPCommunication;
import database.DBConnection;
import database.DBConnection.Type;
import view.client.VueConnexion;

/**
 * TODO Renommer cette classe parce que bon
 *
 */

public class Main {

	public final static String DELIMITER = "\0";

	/**
	 * Méthode principale de l'application
	 */
	public static void main(String[] args) {

		// Solution temporaire, faudra 2 applications séparées à la fin
		{
			Scanner sc = new Scanner(System.in);
			int choix;
			do {
				System.out.println("Mode Serveur (0) ou mode Client (1) ?");
				String next = sc.next();
				try {
					choix = Integer.parseInt(next);
				} catch (NumberFormatException e) {
					choix = -1;
				}
			} while (choix != 0 && choix != 1);
			sc.close();

			switch (choix) {
			case 0: // Serveur
				// TODO
				DBConnection.type = Type.SERVEUR;

				new Thread(new Runnable() {
					public void run() {
						TCPCommunication.openServerSocket();
					}
				}).start();

				break;
			case 1: // Client
				// TODO
				DBConnection.type = Type.CLIENT;

//				ClientThread clientThread = TCPCommunication.openClientSocket();
//				clientThread.start();

				break;
			}
		}

		DBConnection db = DBConnection.getInstance();

		
		// Frame
		JFrame frame = new JFrame("NeOCampus");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
//		frame.setPreferredSize(new Dimension(
//				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
//				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
//						.getHeight()));
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setPreferredSize(new Dimension(500,500));
		// frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (ClientThread.getUtilisateur() == null)
					return;

				if (DBConnection.type == Type.CLIENT && ClientThread.getUtilisateur().isConnecte()) {
					ClientThread.getUtilisateur().setConnecte(false);

					ClientThread.getClient().disconnect();
				}
			}
		});
		frame.setContentPane(new VueConnexion());
		// centrage + affichage
		frame.pack();
		frame.toFront();
		frame.requestFocus();
		frame.setVisible(true);
	}

}
