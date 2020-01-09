package controller.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import database.DBConnection;
import model.Utilisateur;

public class SuppressionUtilisateurController implements ActionListener {
	Utilisateur utilisateur;
	JButton ok;

	public SuppressionUtilisateurController(JButton ok) {
		this.ok = ok;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> groupesComboBox = (JComboBox<String>) e.getSource();
			int index = groupesComboBox.getSelectedIndex();
			String source = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

			Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
					.filter(g -> g.getIdentifiant().equals(source)).findFirst().orElse(null);

			if (utilisateur != null)
				this.utilisateur = utilisateur;
			if (index != 0)
				ok.setEnabled(true);
		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB.equals("OK")) {
				if (this.utilisateur != null) {
					boolean res = DBConnection.getInstance().supprimerUtilisateur(utilisateur);
					if (res) {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Suppression réussi");
						topFrame.setVisible(false);
						topFrame.dispose();
					} else {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Echec lors de la suppression");
						topFrame.setVisible(false);
						topFrame.dispose();
					}
				}
			} else if (nomB.equals("Annuler")) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}

		}
	}
}
