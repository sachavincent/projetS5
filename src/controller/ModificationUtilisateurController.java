package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import database.DBConnection;
import model.*;
import model.Utilisateur.TypeUtilisateur;

public class ModificationUtilisateurController implements ActionListener {
	private Utilisateur utilisateur;
	private String identifiant, password, nom, prenom;
	private TypeUtilisateur type;
	public ModificationUtilisateurController() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> utilisateursComboBox = (JComboBox<String>) e.getSource();
			String source = utilisateursComboBox.getItemAt(utilisateursComboBox.getSelectedIndex());
			
			Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);

			switch (source) {
			case "Identifiant":
				String id = JOptionPane.showInputDialog("Nouvel identifiant");
				identifiant = id;
			case "Mot de passe":
				String mdp = JOptionPane.showInputDialog("Nouveau Mot de Passe");
				password = mdp;
			case "Nom":
				String Nom = JOptionPane.showInputDialog("Nouveau Nom");
				nom = Nom;
			case "Prenom":
				String Prenom = JOptionPane.showInputDialog("Nouveau Prenom");
				prenom = Prenom;
				
			default:
				;
			}

			if (utilisateur != null)
				this.utilisateur = new Utilisateur(identifiant, password, nom, prenom, utilisateur.getType(), true);
		} else if (e.getSource() instanceof JButton) {
			if (this.utilisateur != null) {

				// Mets à jour la base de données
				DBConnection.getInstance().modifierUtilisateur(utilisateur);

			}

		}
	}

}
