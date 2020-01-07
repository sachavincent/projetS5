package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import database.DBConnection;
import model.GroupeUtilisateurs;

public class ModificationGroupeController implements ActionListener {

	private GroupeUtilisateurs groupe;
	private JTextField nameField;

	public ModificationGroupeController(JTextField nameField) {
		this.nameField = nameField;
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
			if (this.groupe != null && !nameField.getText().isEmpty()) {
				this.groupe.setNom(nameField.getText());

				// Mets � jour la base de donn�es
				DBConnection.getInstance().updateGroupe(groupe);
				// TODO Go back
			}
		}

	}

}
