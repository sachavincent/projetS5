package view;

import javax.swing.JFrame;

import database.DBConnection;
import database.DBConnection.Type;

public class main {
	public static void main(String[] args) {
		DBConnection.type = Type.SERVEUR;
		DBConnection.getInstance();
		
		JFrame frame = new JFrame("NeOCampus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new VueSuppressionUtilisateurGroupe();
		frame.setContentPane(new VueSuppressionUtilisateurGroupe());
		
		frame.pack();
		frame.setVisible(true);
	}

}
