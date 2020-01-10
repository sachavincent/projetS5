package view.server;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import database.DBConnection;

//Interface permettant de créer un nouveau groupe
public class VueAjoutGroupe {
	private String nom;

	public VueAjoutGroupe() {
		nom = JOptionPane.showInputDialog("Nom du groupe");
		if (nom == null || nom.isEmpty())
			return;

		boolean res = DBConnection.getInstance().creerGroupe(nom, new ArrayList<>());
		if (res)
			JOptionPane.showMessageDialog(null, "Ajout réussi");
	}

}
