package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import database.DBConnection;
import model.*;

public class ModificationUtilisateurController implements ActionListener {
	Utilisateur utilisateur;
	String Identifiant,password,nom,prenom;
	

	public ModificationUtilisateurController() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> utilisateursComboBox = (JComboBox<String>) e.getSource(); 
			
			String source = utilisateursComboBox.getItemAt(utilisateursComboBox.getSelectedIndex());
			switch(source) {
			case "Identifiant":
			case "Mot de passe":
			case "Nom":
			case "Prenom":
			case "Type Utilisateur":
			default:;
			}

			Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);

			if (utilisateur != null)
				this.utilisateur = utilisateur;
		} else if (e.getSource() instanceof JButton) {
			if (this.utilisateur != null ) {

				// Mets à jour la base de données
				//DBConnection.getInstance().m

			}

	}
	}

}
