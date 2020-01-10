package controller.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import database.DBConnection;
import model.GroupeUtilisateurs;
import view.client.VueFenetreClient;

public class ModificationGroupeController implements ActionListener {

	private GroupeUtilisateurs groupe;
	private JTextField nameField;
	JButton ok;

	public ModificationGroupeController(JTextField nameField, JButton ok) {
		this.nameField = nameField;
		this.ok = ok;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			int index = groupesComboBox.getSelectedIndex();
			String source = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

			GroupeUtilisateurs groupe = DBConnection.getInstance().getListeGroupes().stream()
					.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);
			if (index == 0) {
				this.groupe = null;

				if (this.ok.isEnabled())
					this.ok.setEnabled(false);
			}
			this.ok.setEnabled(true);
			if (groupe != null)
				this.groupe = groupe;
		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nom = b.getText();
			if (nom.equals("OK")) {
				if (this.groupe != null && !nameField.getText().isEmpty()) {
					this.groupe.setNom(nameField.getText());

					// Mets à jour la base de données
					DBConnection.getInstance().updateGroupe(groupe);
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
					JOptionPane.showMessageDialog(topFrame, "Modification réussi");
					topFrame.setVisible(false);
					topFrame.dispose();
				}
			} else if (nom.equals("Annuler")) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}
		}

	}

}
