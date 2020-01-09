package view.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import communication.ClientThread;
import controller.client.FenetreClientController;
import database.DBConnection;
import model.Ticket;

//Interface pour le Client
//contient le fils de discussion, une zone pour envoyer un message ainsi que toute les groupes auxquels appartient l'utilisateur

public class VueFenetreClient extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private ImageIcon plusIcon;

	private JPanel panelTickets = new JPanel();

	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	private Set<Ticket> tickets;

	private JPanel panelAdm = new JPanel();
	private JPanel panelTech = new JPanel();
	private JPanel panelSecr = new JPanel();

	private JPanel rightSidePanel = new JPanel();
	private JTextField fieldMessage = new JTextField();

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

		try {
			plusIcon = new ImageIcon(ImageIO.read(getClass().getResource("/resources/icons/add_ticket.png")));
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		// init
		servicesAdmLabel = new JLabel("Services administratifs");

		servicesTechLabel = new JLabel("Services techniques");

		secretariatLabel = new JLabel("Secrétariat pédagogique");

		if (ClientThread.getUtilisateur() == null) {
			System.out.println("Utilisateur null");

			return;
		}

		tickets = ClientThread.getUtilisateur().getTickets();
		panelTickets.setPreferredSize(new Dimension(screenWidth / 4, screenHeight));
		panelTickets.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panelTickets.setLayout(new GridLayout(1, 2));

		rightSidePanel.setLayout(new BorderLayout());

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

		JPanel panelMessage = new JPanel();
		SpringLayout springLayout = new SpringLayout();
		JLabel nom = new JLabel("Sacha V.");
		JLabel dateMessage = new JLabel("Hier à 23:14");

		JTextArea contenuMessage = new JTextArea(5, 2);
		contenuMessage.setBorder(new LineBorder(Color.BLACK, 2, true));
		contenuMessage.setEditable(false);
//		contenuMessage.setPreferredSize(new Dimension(screenWidth / 8, screenHeight / 15));
//		message.add(contenuMessage, BorderLayout.CENTER);
//		nom.setBorder(new EmptyBorder(0, 50, 0, 0));
//		message.setBorder(new EmptyBorder(100, screenWidth / 3, 0, 0));

		panelMessage.add(nom);
		panelMessage.add(contenuMessage);
		panelMessage.add(dateMessage);

		springLayout.putConstraint(SpringLayout.NORTH, nom, 20, SpringLayout.NORTH, contenuMessage);

		// panelMessage si 1er message sinon précédent
		springLayout.putConstraint(SpringLayout.NORTH, contenuMessage, 100, SpringLayout.NORTH, panelMessage);
		springLayout.putConstraint(SpringLayout.WEST, nom, 8, SpringLayout.EAST, contenuMessage);
		springLayout.putConstraint(SpringLayout.WEST, contenuMessage, screenWidth / 2, SpringLayout.WEST, panelMessage);
		springLayout.putConstraint(SpringLayout.EAST, panelMessage, 20, SpringLayout.EAST, nom);
		springLayout.putConstraint(SpringLayout.NORTH, dateMessage, 5, SpringLayout.SOUTH, contenuMessage);
		springLayout.putConstraint(SpringLayout.WEST, dateMessage, 0, SpringLayout.WEST, contenuMessage);

		panelMessage.setLayout(springLayout);

		JScrollPane panelMessages = new JScrollPane();
		panelMessages.setBorder(BorderFactory.createEmptyBorder());
		panelMessages.getViewport().add(panelMessage);

		rightSidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightSidePanel.add(panelMessages, BorderLayout.CENTER);
		rightSidePanel.add(fieldMessage, BorderLayout.SOUTH);

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

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (ClientThread.getUtilisateur() == null)
					return;

				if (DBConnection.type == DBConnection.Type.CLIENT && ClientThread.getUtilisateur().isConnecte()) {
					ClientThread.getClient().disconnect();

					System.out.println("Déconnexion");
				}
			}
		});

		// ajout
		// affichage
		add(panelTickets, BorderLayout.WEST);
		add(rightSidePanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
