package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;

import database.DBConnection;

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
				//TODO
				break;
			case 1: // Client
				//TODO
				break;
			}
		}
		
		DBConnection db = DBConnection.getInstance();
		
		JFrame frame = new JFrame("NeOCampus");
		frame.setPreferredSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
