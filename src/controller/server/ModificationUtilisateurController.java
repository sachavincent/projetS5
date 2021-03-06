package controller.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import database.DBConnection;
import main.Encryption;
import model.Utilisateur;
import model.Utilisateur.TypeUtilisateur;

public class ModificationUtilisateurController implements ActionListener {
	private Utilisateur utilisateur;
	private String nouveauMDP;
	private boolean passwordSelect = false;
	private JButton ok;

	public ModificationUtilisateurController(JButton ok) {
		this.ok = ok;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JComboBox) {
			passwordSelect = false;
			JComboBox<String> utilisateursComboBox = (JComboBox<String>) e.getSource();
			String source = utilisateursComboBox.getItemAt(utilisateursComboBox.getSelectedIndex());
			int index = utilisateursComboBox.getSelectedIndex();

			Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
					.filter(g -> g.getIdentifiant().equals(source)).findFirst().orElse(null);
			if (index != 0)
				ok.setEnabled(true);
			if (utilisateur != null)
				this.utilisateur = utilisateur;

			switch (source) {
			case "Identifiant":
				String id = JOptionPane.showInputDialog("Nouvel identifiant");
				this.utilisateur.setIdentifiant(id);
				break;
			case "Mot de passe":
				nouveauMDP = JOptionPane.showInputDialog("Nouveau Mot de Passe");
				// nouveauMDP = this.utilisateur.getPassword();
				// this.utilisateur.modifierMotDePasse(this.utilisateur.getPassword(),
				// Encryption.SHA1(mdp));
				passwordSelect = true;
				break;
			case "Nom":
				String Nom = JOptionPane.showInputDialog("Nouveau Nom");
				this.utilisateur.setNom(Nom);
				break;
			case "Prenom":
				String Prenom = JOptionPane.showInputDialog("Nouveau Prenom");
				this.utilisateur.setPrenom(Prenom);

				break;
			default:
				break;
			}

		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nom = b.getText();
			if (nom.equals("OK")) {
				if (this.utilisateur != null) {
					// Mets � jour la base de donn�es
					boolean res;
					if (passwordSelect) {
						res = DBConnection.getInstance().modifierMotDePasseUtilisateur(
								this.utilisateur.getIdentifiant(), this.utilisateur.getPassword(),
								Encryption.SHA1(nouveauMDP));
					} else {
						res = DBConnection.getInstance().modifierUtilisateur(utilisateur);
					}
					if (res) {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Modification r�ussi");
						topFrame.setVisible(false);
						topFrame.dispose();
					} else {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Echec de la Modification");
						topFrame.setVisible(false);
						topFrame.dispose();
					}
				} else {
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
					JOptionPane.showMessageDialog(topFrame, "Erreur");
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
