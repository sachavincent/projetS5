package controller.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import database.DBConnection;
import model.Utilisateur;

public class AjoutUtilisateurController implements ActionListener {
	private JTextField identifiantField;
	private JTextField passwordField;
	private JTextField nomField;
	private JTextField prenomField;

	private String typeUtilisateur;

	public AjoutUtilisateurController(JTextField id, JTextField mdp, JTextField nom, JTextField prenom) {
		this.identifiantField = id;
		this.passwordField = mdp;
		this.nomField = nom;
		this.prenomField = prenom;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			String type = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

			if (type != null)
				this.typeUtilisateur = type;
		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB.equals("Création")) {
				if (this.typeUtilisateur != null && !identifiantField.getText().isEmpty()
						&& !passwordField.getText().isEmpty() && !nomField.getText().isEmpty()
						&& !prenomField.getText().isEmpty())
					DBConnection.getInstance().creerUtilisateur(identifiantField.getText(), passwordField.getText(),
							nomField.getText(), prenomField.getText(), typeUtilisateur);
				
			} else if (nomB.equals("Annuler")) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}
		}
	}
}
