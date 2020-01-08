package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import database.DBConnection;
import model.GroupeUtilisateurs;

public class SuppressionGroupeController implements ActionListener {
	private GroupeUtilisateurs groupe;

	public SuppressionGroupeController() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			String source = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

			GroupeUtilisateurs groupe = DBConnection.getInstance().getListeGroupes().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);
			if (groupe != null)
				this.groupe = groupe;

		} else if (e.getSource() instanceof JButton) {
			if (this.groupe != null) {
				DBConnection.getInstance().supprimerGroupe(groupe);
			}
			

		}

//	if (source == listeGrp[i]) {
//		nom = listeGrp[i];
//		String s = "confirmation de la supprésion de l'utilisateur :  " + nom;
//		int op = JOptionPane.showConfirmDialog(null, s);

	}

}
