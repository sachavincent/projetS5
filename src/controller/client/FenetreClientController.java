package controller.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import database.DBConnection;
import model.Ticket;
import model.Utilisateur.TypeUtilisateur;

public class FenetreClientController implements ActionListener, MouseListener {

	private ImageIcon openedTicketIcon = new ImageIcon("icons/opened_ticket.png", "Ticket ouvert");
	private ImageIcon closedTicketIcon = new ImageIcon("icons/closed_ticket.png", "Ticket fermé");
	private ImageIcon ticketIcon = new ImageIcon("icons/ticket.png", "Ticket");

	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	private JPanel panelAdm;
	private JPanel panelTech;
	private JPanel panelSecr;

	private Set<Ticket> tickets;

	public FenetreClientController(JLabel servicesAdmLabel, JLabel servicesTechLabel, JLabel secretariatLabel,
			JPanel panelAdm, JPanel panelTech, JPanel panelSecr) {
		this.servicesAdmLabel = servicesAdmLabel;
		this.servicesTechLabel = servicesTechLabel;
		this.secretariatLabel = secretariatLabel;

		this.tickets = DBConnection.getInstance().getListeTickets();

		this.panelAdm = panelAdm;
		this.panelTech = panelAdm;
		this.panelSecr = panelAdm;
	}

	private void closeService(JLabel label) {
		label.setIcon(closedTicketIcon);

		if (label.equals(servicesAdmLabel)) {
			panelAdm.removeAll();
			panelAdm.revalidate();
		}
		System.out.println("closing");
	}

	private void openService(JLabel label) {
		label.setIcon(openedTicketIcon);

		JPanel panel;
		TypeUtilisateur type;
		if (label.equals(servicesAdmLabel)) {
			panel = panelAdm;
			type = TypeUtilisateur.SERVICE_ADMINISTRATIF;
		} else if (label.equals(servicesTechLabel)) {
			panel = panelTech;
			type = TypeUtilisateur.SERVICE_TECHNIQUE;
		} else if (label.equals(secretariatLabel)) {
			panel = panelSecr;
			type = TypeUtilisateur.SECRETAIRE_PEDAGOGIQUE;
		} else {
			throw new IllegalArgumentException("Wrong label");
		}

		System.out.println(tickets);
		tickets.stream().forEach(t -> {
			int idGroupe = t.getGroupeDestination().getIdGroupe();

			TypeUtilisateur typeUtilisateur = DBConnection.getInstance().getListeAssociationsGroupeUtilisateur()
					.stream().filter(agu -> agu.getGroupe().getIdGroupe() == idGroupe)
					.map(agu -> agu.getUtilisateur().getType()).findFirst().orElse(null);

			if (typeUtilisateur != null) {
				System.out.println(typeUtilisateur);
				if (typeUtilisateur == type) {
					JLabel jlabel = new JLabel(t.getTitre());
					jlabel.setBorder(new EmptyBorder(5, 20, 2, 0));
					jlabel.setIcon(ticketIcon);
					panel.add(jlabel);
					panel.revalidate();
				}

			}
		});

		System.out.println("opening");
	}

	private boolean isServiceOpened(JLabel label) {
		return label.getIcon().equals(openedTicketIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (e.getSource().equals(servicesAdmLabel)) {
				if (isServiceOpened(servicesAdmLabel))
					closeService(servicesAdmLabel);
				else
					openService(servicesAdmLabel);
			} else if (e.getSource().equals(servicesTechLabel)) {
				if (isServiceOpened(servicesTechLabel))
					closeService(servicesTechLabel);
				else
					openService(servicesTechLabel);
			} else if (e.getSource().equals(secretariatLabel)) {
				if (isServiceOpened(secretariatLabel))
					closeService(secretariatLabel);
				else
					openService(secretariatLabel);
//			tickets.stream().forEach(t -> {
//				int idGroupe = t.getGroupeDestination().getIdGroupe();
//				TypeUtilisateur typeUtilisateur = DBConnection.getInstance().getListeAssociationsGroupeUtilisateur()
//						.stream().filter(agu -> agu.getGroupe().getIdGroupe() == idGroupe)
//						.map(agu -> agu.getUtilisateur().getType()).findFirst().orElse(null);
//
//				if (typeUtilisateur != null) {
//					if (typeUtilisateur == TypeUtilisateur.SERVICE_ADMINISTRATIF) {
//
//					}
//
//				}
//			});
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
