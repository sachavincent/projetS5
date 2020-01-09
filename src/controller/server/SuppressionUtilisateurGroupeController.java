package controller.server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Utilisateur;

public class SuppressionUtilisateurGroupeController implements ActionListener {
	private GroupeUtilisateurs groupe;
	private Utilisateur utilisateur;

	private JComboBox<String> groupesComboBox;
	private JComboBox<String> utilisateursComboBox;
	private JButton boutonOk;

	public SuppressionUtilisateurGroupeController(JComboBox<String> groupesComboBox,
			JComboBox<String> utilisateursComboBox, JButton boutonOk) {
		this.groupesComboBox = groupesComboBox;
		this.utilisateursComboBox = utilisateursComboBox;
		this.boutonOk = boutonOk;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {

			JComboBox<String> sourceComboBox = (JComboBox<String>) e.getSource();
			int index = sourceComboBox.getSelectedIndex();
			String source = sourceComboBox.getItemAt(index);

			if (e.getSource().equals(utilisateursComboBox)) {
				if (index == 0) {
					this.boutonOk.setEnabled(false);

					this.utilisateur = null;
					this.groupe = null;
					
					groupesComboBox.setSelectedItem(groupesComboBox.getItemAt(0));
				} else
					setSelectedUser(source);
			} else if (e.getSource().equals(groupesComboBox)) {
				if (index == 0) {
					this.boutonOk.setEnabled(false);

					this.groupe = null;
				} else {
					this.groupe = DBConnection.getInstance().getListeGroupes().stream()
							.filter(g -> g.getNom().equals(source)).findFirst().orElse(null);

					if (this.utilisateur != null && !this.boutonOk.isEnabled() && this.groupe != null)
						this.boutonOk.setEnabled(true);
				}
			}

		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();

			if (nomB.equals("OK")) {
				if (this.utilisateur != null && this.groupe != null) {
					boolean res = DBConnection.getInstance().supprimerUtilisateurDeGroupe(groupe, utilisateur);
					System.out.println(res);

					if (res) {
						// Utilisateur supprim�
						utilisateur.setModelChanged();

						utilisateur.notifyObservers();

						// Afficher r�ussite
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Suppression r�ussi");
						topFrame.setVisible(false);
						topFrame.dispose();

					} else {
						// Afficher erreur
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Erreur lors de la suppr�ssion");

					}
				} else {
					// Afficher erreur
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
					JOptionPane.showMessageDialog(topFrame, "Merci de remplir tout les champs");
				}
			} else if (nomB.equals("Annuler")) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}
		}
	}

	public void setSelectedUser(String identifiant) {
		Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
				.filter(u -> u.getIdentifiant().equals(identifiant)).findFirst().orElse(null);

		this.utilisateur = utilisateur;

		if (utilisateur != null) {

			groupesComboBox.removeAllItems();
			groupesComboBox.addItem("Choisir un groupe");

			DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().stream()
					.filter(agu -> agu.getUtilisateur().equals(utilisateur)).map(agu -> agu.getGroupe())
					.forEach(g -> groupesComboBox.addItem(g.getNom()));
		}
	}

}
