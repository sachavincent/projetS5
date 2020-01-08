package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Utilisateur;

public class SuppressionUtilisateurGroupeController implements ActionListener {
	private GroupeUtilisateurs groupe;
	private Utilisateur utilisateur;

	public SuppressionUtilisateurGroupeController() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			String source = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

			Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);
			GroupeUtilisateurs groupe = DBConnection.getInstance().getListeGroupes().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);

			if (utilisateur != null)
				this.utilisateur = utilisateur;

			if (groupe != null)
				this.groupe = groupe;

		} else if (e.getSource() instanceof JButton) {
			if (this.utilisateur != null) {
				DBConnection.getInstance().supprimerUtilisateurDeGroupe(groupe, utilisateur);
			}

		}
	}

}
