package controller.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import communication.ClientThread;
import database.DBConnection;
import model.Ticket;
import model.Utilisateur.TypeUtilisateur;
import view.client.VueCreationTicket;

public class FenetreClientController implements MouseListener, KeyListener {

	private ImageIcon openedTicketIcon;
	private ImageIcon closedTicketIcon;
	private ImageIcon ticketIcon;
	private ImageIcon invisibleIcon;

	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	private JPanel panelAdm;
	private JPanel panelTech;
	private JPanel panelSecr;

	private Set<Ticket> tickets;

	private JPanel panelAdm2;
	private JPanel panelTech2;

	private JLabel plusAdm;
	private JLabel plusTech;
	private JLabel plusSecr;

	private Ticket ticket;

	public FenetreClientController(JLabel servicesAdmLabel, JLabel servicesTechLabel, JLabel secretariatLabel,
			JPanel panelAdm, JPanel panelTech, JPanel panelSecr, JPanel panelAdm2, JPanel panelTech2, JLabel plusAdm,
			JLabel plusTech, JLabel plusSecr) {
		try {
			closedTicketIcon = new ImageIcon(
					ImageIO.read(getClass().getResource("/resources/icons/closed_ticket.png")));
			openedTicketIcon = new ImageIcon(
					ImageIO.read(getClass().getResource("/resources/icons/opened_ticket.png")));
			invisibleIcon = new ImageIcon(
					ImageIO.read(getClass().getResource("/resources/icons/invisible_ticket.png")));
			ticketIcon = new ImageIcon(ImageIO.read(getClass().getResource("/resources/icons/ticket.png")));
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		servicesAdmLabel.setIcon(closedTicketIcon);
		servicesTechLabel.setIcon(closedTicketIcon);
		secretariatLabel.setIcon(closedTicketIcon);

		this.servicesAdmLabel = servicesAdmLabel;
		this.servicesTechLabel = servicesTechLabel;
		this.secretariatLabel = secretariatLabel;

		this.tickets = DBConnection.getInstance().getListeTickets();

		this.panelAdm = panelAdm;
		this.panelTech = panelTech;
		this.panelSecr = panelSecr;

		this.panelAdm2 = panelAdm2;
		this.panelTech2 = panelTech2;

		this.plusAdm = plusAdm;
		this.plusTech = plusTech;
		this.plusSecr = plusSecr;
	}

	private void closeService(JLabel label) {
		label.setIcon(closedTicketIcon);

		if (label.equals(servicesAdmLabel)) {
			panelAdm.removeAll();
			panelAdm2.removeAll();

			panelAdm.revalidate();
			panelAdm2.revalidate();
		} else if (label.equals(servicesTechLabel)) {
			panelTech.removeAll();
			panelTech2.removeAll();

			panelTech.revalidate();
			panelTech2.revalidate();
		} else if (label.equals(secretariatLabel)) {
			panelSecr.removeAll();
			panelSecr.revalidate();
		}
		System.out.println("closing");
	}

	private void openService(JLabel label) {
		label.setIcon(openedTicketIcon);

		TypeUtilisateur type;
		if (label.equals(servicesAdmLabel))
			type = TypeUtilisateur.SERVICE_ADMINISTRATIF;
		else if (label.equals(servicesTechLabel))
			type = TypeUtilisateur.SERVICE_TECHNIQUE;
		else if (label.equals(secretariatLabel))
			type = TypeUtilisateur.SECRETAIRE_PEDAGOGIQUE;
		else
			throw new IllegalArgumentException("Wrong label");

		tickets.stream().forEach(t -> {
			int idGroupe = t.getGroupeDestination().getIdGroupe();

			TypeUtilisateur typeUtilisateur = DBConnection.getInstance().getListeAssociationsGroupeUtilisateur()
					.stream().filter(agu -> agu.getGroupe().getIdGroupe() == idGroupe)
					.map(agu -> agu.getUtilisateur().getType()).findFirst().orElse(null);

			if (typeUtilisateur != null) {
				if (typeUtilisateur == type) {
					System.out.println(typeUtilisateur);
					JLabel jlabel = new JLabel("Ticket n°" + t.getIdTicket() + " - " + t.getTitre());
					jlabel.addMouseListener(this);

					jlabel.setBorder(new EmptyBorder(5, 20, 2, 0));
					jlabel.setIcon(ticketIcon);

					JLabel jlabel2 = new JLabel("");
					jlabel2.setBorder(new EmptyBorder(5, 20, 2, 0));
					jlabel2.setIcon(invisibleIcon);
					if (label.equals(servicesAdmLabel)) {
						panelAdm.add(jlabel);
						panelAdm2.add(jlabel2);

						panelAdm.revalidate();
						panelAdm2.revalidate();
					} else if (label.equals(servicesTechLabel)) {
						panelTech.add(jlabel);
						panelTech2.add(jlabel2);

						panelTech.revalidate();
						panelTech2.revalidate();
					} else if (label.equals(secretariatLabel)) {
						panelSecr.add(jlabel);
						panelSecr.revalidate();
					} else {
						throw new IllegalArgumentException("Wrong label");
					}
				}

			}
		});

		System.out.println("opening");
	}

	private boolean isServiceOpened(JLabel label) {
		return label.getIcon().equals(openedTicketIcon);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(servicesAdmLabel)) {
			if (e.getClickCount() == 2) {
				if (isServiceOpened(servicesAdmLabel))
					closeService(servicesAdmLabel);
				else
					openService(servicesAdmLabel);
			}
		} else if (e.getSource().equals(servicesTechLabel)) {
			if (e.getClickCount() == 2) {
				if (isServiceOpened(servicesTechLabel))
					closeService(servicesTechLabel);
				else
					openService(servicesTechLabel);
			}
		} else if (e.getSource().equals(secretariatLabel)) {
			if (e.getClickCount() == 2) {
				if (isServiceOpened(secretariatLabel))
					closeService(secretariatLabel);
				else
					openService(secretariatLabel);
			}
		} else if (e.getSource().equals(plusAdm)) {
			new VueCreationTicket(TypeUtilisateur.SERVICE_ADMINISTRATIF);

//			JLabel l = (JLabel) e.getSource();
//			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(l);
//			topFrame.setVisible(false);
//			topFrame.setVisible(true);
		} else if (e.getSource().equals(plusTech)) {
			new VueCreationTicket(TypeUtilisateur.SERVICE_TECHNIQUE);
		} else if (e.getSource().equals(plusSecr)) {
			new VueCreationTicket(TypeUtilisateur.SECRETAIRE_PEDAGOGIQUE);
		} else if (e.getSource() instanceof JLabel) { // Tickets
			JLabel labelTicket = (JLabel) e.getSource();
			System.out.println("Label:" + labelTicket.getText());
			int idTicket = Integer.parseInt(labelTicket.getText().split("°")[1].split(" - ")[0]);
			Ticket ticket = DBConnection.getInstance().getListeTickets().stream()
					.filter(t -> t.getIdTicket() == idTicket).findFirst().orElse(null);

			if (ticket != null && !ticket.equals(this.ticket)) {
				this.ticket = ticket;

				boolean res = ClientThread.getClient().ouvrirTicket(this.ticket.getIdTicket());
				System.out.println("ouverture : " + res);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() instanceof JTextField && e.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextField textField = (JTextField) e.getSource();
			String message = textField.getText();

			if (!message.isEmpty() && ticket != null) {
				ClientThread.getClient().envoyerMessage(message, ticket.getIdTicket());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
