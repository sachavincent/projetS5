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

public class AjoutUtilisateurController implements ActionListener {
	private JTextField identifiantField;
	private JTextField passwordField;
	private JTextField nomField;
	private JTextField prenomField;

	private JButton okButton;

	private String typeUtilisateur;

	public AjoutUtilisateurController(JTextField id, JTextField mdp, JTextField nom, JTextField prenom,
			JButton okButton) {
		this.identifiantField = id;
		this.passwordField = mdp;
		this.nomField = nom;
		this.prenomField = prenom;
		this.okButton = okButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			int index = groupesComboBox.getSelectedIndex();
			String type = groupesComboBox.getItemAt(index);

			if (index == 0) {
				this.typeUtilisateur = null;

				if (this.okButton.isEnabled())
					this.okButton.setEnabled(false);
			} else {
				this.typeUtilisateur = type;

				if (!this.okButton.isEnabled())
					this.okButton.setEnabled(true);
			}
		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB.equals("Création")) {
				if (this.typeUtilisateur != null && !identifiantField.getText().isEmpty()
						&& !passwordField.getText().isEmpty() && !nomField.getText().isEmpty()
						&& !prenomField.getText().isEmpty()) {

					boolean res = DBConnection.getInstance().creerUtilisateur(identifiantField.getText(),
							passwordField.getText(), nomField.getText(), prenomField.getText(), typeUtilisateur);

					if (res) {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Ajout réussi");
						topFrame.setVisible(false);
						topFrame.dispose();
					} else {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Erreur lors de l'ajout");
						topFrame.setVisible(false);
						topFrame.dispose();

					}
				} else {
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
					JOptionPane.showMessageDialog(topFrame, "Erreur");

				}
			} else if (nomB.equals("Annuler")) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}
		}
	}
}
