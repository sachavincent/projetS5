package main;

import java.util.Scanner;

/**
 * TODO Renommer cette classe parce que bon
 *
 */
public class Main {

	/**
	 * M�thode principale de l'application
	 */
	public static void main(String[] args) {

		// Solution temporaire, faudra 2 applications s�par�s � la fin
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
	}

}
