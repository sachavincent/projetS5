package view;

import javax.swing.JOptionPane;

import database.DBConnection;
//Interface permettant de cr�er un nouveau groupe
public class VueAjoutGroupe {
	private String nom;
	public VueAjoutGroupe() {
		nom = JOptionPane.showInputDialog("Nom du groupe");
		DBConnection.getInstance().creerGroupe(nom, null);
	}

}
