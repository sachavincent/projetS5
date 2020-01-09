package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Utilisateur;

public class SuppressionUtilisateurController implements ActionListener {
	Utilisateur utilisateur;

	public SuppressionUtilisateurController() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			String source = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

			Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);

			if (utilisateur != null)
				this.utilisateur = utilisateur;

		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB == "OK") {
				if (this.utilisateur != null) {
					DBConnection.getInstance().supprimerUtilisateur(utilisateur);
				}
			} else if (nomB == "Annuler") {
				// TODO go back
			}

		}
	}
}
