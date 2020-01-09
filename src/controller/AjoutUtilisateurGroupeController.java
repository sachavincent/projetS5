package controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Utilisateur;

public class AjoutUtilisateurGroupeController implements ActionListener {

	private Utilisateur utilisateur;
	private GroupeUtilisateurs groupe;

	public AjoutUtilisateurGroupeController() {

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
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB == "OK") {
				if (this.utilisateur != null && this.groupe != null)
					DBConnection.getInstance().ajouterUtilisateurAGroupe(utilisateur, groupe);
			}
			else if(nomB == "Annuler") {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}

		}
	}

}
