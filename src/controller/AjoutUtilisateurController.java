package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import database.DBConnection;
import model.Utilisateur;
import model.Utilisateur.TypeUtilisateur;

public class AjoutUtilisateurController implements ActionListener {
	private JTextField id;
	private JTextField mdp;
	private JTextField nom;
	private JTextField prenom;
	private String type;

	public AjoutUtilisateurController(JTextField id, JTextField mdp, JTextField nom, JTextField prenom) {
		this.id = id;
		this.mdp = mdp;
		this.nom = nom;
		this.prenom = prenom;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			String type = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());
			

			if (type != null) {
				this.type = type;
			} else if (e.getSource() instanceof JButton) {
				if (this.type != null && !id.getText().isEmpty() && !mdp.getText().isEmpty() && !nom.getText().isEmpty()
						&& !prenom.getText().isEmpty()) {
					Utilisateur u = new Utilisateur(id.getText(), mdp.getText(), nom.getText(), prenom.getText(),
							type, false);
					
				}
			}
		}
	}
}
