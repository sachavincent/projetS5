package view.client;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import communication.ClientThread;
import database.DBConnection;
import model.Ticket;
import model.Utilisateur.TypeUtilisateur;

//Interface pour le Client
//contient le fils de discussion, une zone pour envoyer un message ainsi que toute les groupes auquel appartient l'utilisateur

public class VueFenetreClient extends JFrame implements Observer {

	private ImageIcon openedTicketIcon = new ImageIcon("icons/opened_ticket.png", "Ticket ouvert");
	private ImageIcon closedTicketicon = new ImageIcon("icons/closed_ticket.png", "Ticket ouvert");

	private JPanel panelTickets = new JPanel();
	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	public VueFenetreClient() {
		// init
		servicesAdmLabel = new JLabel("Services administratifs");
		servicesAdmLabel.setIcon(closedTicketicon);

		servicesTechLabel = new JLabel("Services techniques");
		servicesTechLabel.setIcon(closedTicketicon);

		secretariatLabel = new JLabel("Secrétariat pédagogique");
		secretariatLabel.setIcon(closedTicketicon);

		if (ClientThread.getUtilisateur() == null) {
			System.out.println("Utilisateur null");
			return;
		}
		
		Set<Ticket> tickets = ClientThread.getUtilisateur().getTickets();

		tickets.stream().forEach(t -> {
			int idGroupe = t.getGroupeDestination().getIdGroupe();
			TypeUtilisateur typeUtilisateur = DBConnection.getInstance().getListeAssociationsGroupeUtilisateur()
					.stream().filter(agu -> agu.getGroupe().getIdGroupe() == idGroupe)
					.map(agu -> agu.getUtilisateur().getType()).findFirst().orElse(null);

			if (typeUtilisateur != null) {
				if (typeUtilisateur == TypeUtilisateur.SERVICE_ADMINISTRATIF
						&& servicesAdmLabel.getIcon().equals(closedTicketicon)) {
					servicesAdmLabel.setIcon(openedTicketIcon);
				} else if (typeUtilisateur == TypeUtilisateur.SERVICE_TECHNIQUE
						&& servicesTechLabel.getIcon().equals(closedTicketicon)) {
					servicesTechLabel.setIcon(openedTicketIcon);
				} else if (typeUtilisateur == TypeUtilisateur.SECRETAIRE_PEDAGOGIQUE
						&& servicesTechLabel.getIcon().equals(closedTicketicon)) {
					servicesTechLabel.setIcon(openedTicketIcon);
				}
			}
		});

		panelTickets.add(servicesAdmLabel);
		panelTickets.add(servicesTechLabel);
		panelTickets.add(secretariatLabel);
		// layout

		// ajout

		// affichage
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
						.getHeight()));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		add(servicesAdmLabel);
		pack();
		setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
