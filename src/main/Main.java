package main;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.util.Scanner;

import javax.swing.JFrame;

import view.FenetreServeur;

/**
 * TODO Renommer cette classe parce que bon
 *
 */

public class Main {

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
				choix = sc.nextInt();
			} while (choix != 0 && choix != 1);
			sc.close();

			switch (choix) {
			case 0: // Serveur
				// TODO
				new Runnable() {
					public void run() {
						TCPCommunication.openServerSocket();
					}
				};
				break;
			case 1: // Client
				// TODO
				
						TCPCommunication.openClientSocket();
						TCPCommunication.sendMessage("Hello world!");
				
				break;
			}
		}

//		DBConnection db = DBConnection.getInstance();

		// Frame
		JFrame frame = new JFrame("NeOCampus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
						.getHeight()));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		frame.setPreferredSize(new Dimension(500,500));
//		frame.setLocationRelativeTo(null);

		frame.setContentPane(new FenetreServeur());
		// centrage + affichage
		frame.pack();
		frame.setVisible(true);
	}

}
