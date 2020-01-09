package controller.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB == "OK") {
				if (this.groupe != null) {
					boolean res = DBConnection.getInstance().supprimerGroupe(groupe);

					System.out.println(res);
					
					if (res) {
						// TODO Afficher succès
						groupe.setModelChanged();
						groupe.notifyObservers();
					} else {
						// TODO Afficher erreur
					}
				}
			} else if (nomB == "Annuler") {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
				topFrame.setVisible(false);
				topFrame.dispose();
			}

		}

	}

}
