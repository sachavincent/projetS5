package controller.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import database.DBConnection;
import model.GroupeUtilisateurs;

public class SuppressionGroupeController implements ActionListener {
	private GroupeUtilisateurs groupe;
	private JButton ok;

	public SuppressionGroupeController(JButton ok) {
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
			if (groupe != null)
				this.groupe = groupe;
			if (index != 0) {
				ok.setEnabled(true);
			}
		} else if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB.equals("OK")) {
				if (this.groupe != null) {
					boolean res = DBConnection.getInstance().supprimerGroupe(groupe);

					if (res) {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Suppression réussi");
						groupe.setModelChanged();
						groupe.notifyObservers();
					} else {
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
						JOptionPane.showMessageDialog(topFrame, "Erreur suppréssion");
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
