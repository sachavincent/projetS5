package view.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import communication.ClientThread;
import controller.client.FenetreClientController;
import model.Ticket;

//Interface pour le Client
//contient le fils de discussion, une zone pour envoyer un message ainsi que toute les groupes auxquels appartient l'utilisateur

public class VueFenetreClient extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private ImageIcon plusIcon = new ImageIcon("icons/add_ticket.png", "Add ticket");

	private JPanel panelTickets = new JPanel();

	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	private Set<Ticket> tickets;

	private JPanel panelAdm = new JPanel();
	private JPanel panelTech = new JPanel();
	private JPanel panelSecr = new JPanel();

	public VueFenetreClient() {
		setTitle("NeOCampus");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
				.getWidth();
		int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode()
				.getHeight();
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		BorderLayout layout = new BorderLayout();

		setLayout(layout);

		// init
		servicesAdmLabel = new JLabel("Services administratifs");

		servicesTechLabel = new JLabel("Services techniques");

		secretariatLabel = new JLabel("Secrétariat pédagogique");

		if (ClientThread.getUtilisateur() == null) {
			System.out.println("Utilisateur null");

			return;
		}

		tickets = ClientThread.getUtilisateur().getTickets();
		panelTickets.setPreferredSize(new Dimension(screenWidth / 6, screenHeight));
		panelTickets.setBorder(BorderFactory.createLineBorder(Color.BLACK));

//		panelTickets.setLayout(new BoxLayout(panelTickets, BoxLayout.Y_AXIS));
		panelTickets.setLayout(new GridLayout(1, 2));

		panelAdm.setLayout(new BoxLayout(panelAdm, BoxLayout.Y_AXIS));
		panelTech.setLayout(new BoxLayout(panelTech, BoxLayout.Y_AXIS));
		panelSecr.setLayout(new BoxLayout(panelSecr, BoxLayout.Y_AXIS));

		servicesAdmLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
		servicesTechLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
		secretariatLabel.setBorder(new EmptyBorder(10, 10, 0, 0));

		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));

		panelLeft.add(servicesAdmLabel);
		panelLeft.add(panelAdm);
		panelLeft.add(servicesTechLabel);
		panelLeft.add(panelTech);
		panelLeft.add(secretariatLabel);
		panelLeft.add(panelSecr);

		JLabel plus1 = new JLabel(plusIcon);
		plus1.setBorder(new EmptyBorder(10, 10, 0, 0));
		JLabel plus2 = new JLabel(plusIcon);
		plus2.setBorder(new EmptyBorder(10, 10, 0, 0));
		JLabel plus3 = new JLabel(plusIcon);
		plus3.setBorder(new EmptyBorder(10, 10, 0, 0));

		plus1.setAlignmentX(SwingConstants.CENTER);
		plus2.setAlignmentX(SwingConstants.CENTER);
		plus3.setAlignmentX(SwingConstants.CENTER);

		JPanel panelAdm2 = new JPanel();
		JPanel panelTech2 = new JPanel();
		panelAdm2.setLayout(new BoxLayout(panelAdm2, BoxLayout.Y_AXIS));
		panelTech2.setLayout(new BoxLayout(panelTech2, BoxLayout.Y_AXIS));

		panelRight.add(plus1);
		panelRight.add(panelAdm2);
		panelRight.add(plus2);
		panelRight.add(panelTech2);
		panelRight.add(plus3);

		panelTickets.add(panelLeft);
		panelTickets.add(panelRight);

		FenetreClientController fenetreClientController = new FenetreClientController(servicesAdmLabel,
				servicesTechLabel, secretariatLabel, panelAdm, panelTech, panelSecr, panelAdm2, panelTech2, plus1,
				plus2, plus3);
		servicesAdmLabel.addMouseListener(fenetreClientController);
		servicesTechLabel.addMouseListener(fenetreClientController);
		secretariatLabel.addMouseListener(fenetreClientController);
		
		plus1.addMouseListener(fenetreClientController);
		plus2.addMouseListener(fenetreClientController);
		plus3.addMouseListener(fenetreClientController);

		// ajout
		// affichage
		add(panelTickets, BorderLayout.WEST);
		pack();
		setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
