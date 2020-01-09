package controller.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import communication.ClientThread;
import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Ticket;

public class CreationTicketController implements ActionListener {

	private JTextField titreField;

	private JComboBox<String> groupesComboBox;

	public CreationTicketController(JTextField titreField, JComboBox<String> groupesComboBox) {
		this.titreField = titreField;
		this.groupesComboBox = groupesComboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			// TODO Embrun

			JButton b = (JButton) e.getSource();
			String nomB = b.getText();
			if (nomB.equals("OK")) {
				if (!titreField.getText().equals("") && groupesComboBox.getSelectedIndex() != 0) {

					String nomGroupe = groupesComboBox.getItemAt(groupesComboBox.getSelectedIndex());

					GroupeUtilisateurs groupeUtilisateurs = DBConnection.getInstance().getListeGroupes().stream()
							.filter(g -> g.getNom().equals(nomGroupe)).findFirst().orElse(null);

					if (groupeUtilisateurs != null) {

						Ticket ticket = ClientThread.getClient().creerTicket(titreField.getText(),
								groupeUtilisateurs.getIdGroupe());
						if (ticket == null) {
							// TODO Afficher erreur
						} else {
							// TODO Afficher succès
							
						}
					} else {
						// TODO Afficher erreur
					}
				}
			}
		}

	}
}
