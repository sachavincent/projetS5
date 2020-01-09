package view.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import communication.ClientThread;
import controller.client.FenetreClientController;
import model.Ticket;

//Interface pour le Client
//contient le fils de discussion, une zone pour envoyer un message ainsi que toute les groupes auquel appartient l'utilisateur

public class VueFenetreClient extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private ImageIcon openedTicketIcon = new ImageIcon("icons/opened_ticket.png", "Ticket ouvert");
	private ImageIcon closedTicketIcon = new ImageIcon("icons/closed_ticket.png", "Ticket férmé");

	private JPanel panelTickets = new JPanel();

	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	private Set<Ticket> tickets;

	private JPanel panelAdm = new JPanel();

	public VueFenetreClient() {
		setLayout(new BorderLayout());
		// init
		servicesAdmLabel = new JLabel("Services administratifs");
		servicesAdmLabel.setIcon(closedTicketIcon);

		servicesTechLabel = new JLabel("Services techniques");
		servicesTechLabel.setIcon(closedTicketIcon);

		secretariatLabel = new JLabel("Secrétariat pédagogique");
		secretariatLabel.setIcon(closedTicketIcon);

		if (ClientThread.getUtilisateur() == null) {
			System.out.println("Utilisateur null");

			return;
		}

		tickets = ClientThread.getUtilisateur().getTickets();

		// layout
		panelTickets.setLayout(new BoxLayout(panelTickets, BoxLayout.Y_AXIS));
		panelAdm.setLayout(new BoxLayout(panelAdm, BoxLayout.Y_AXIS));
		
		panelTickets.add(servicesAdmLabel);
		panelTickets.add(panelAdm);
//		panelTickets.add(servicesTechLabel);
//		panelTickets.add(secretariatLabel);

		FenetreClientController fenetreClientController = new FenetreClientController(servicesAdmLabel,
				servicesTechLabel, secretariatLabel, panelAdm);
		servicesAdmLabel.addMouseListener(fenetreClientController);
		servicesTechLabel.addMouseListener(fenetreClientController);
		secretariatLabel.addMouseListener(fenetreClientController);


		// ajout
		// affichage
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
				GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
						.getHeight()));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		add(panelTickets, BorderLayout.CENTER);
		pack();
		setVisible(true);

//		this.panelAdm.add(new JLabel("Help"));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
