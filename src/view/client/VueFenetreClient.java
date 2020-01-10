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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import communication.ClientThread;
import controller.client.FenetreClientController;
import database.DBConnection;
import model.Ticket;
import view.VueMessage;

//Interface pour le Client
//contient le fils de discussion, une zone pour envoyer un message ainsi que toute les groupes auxquels appartient l'utilisateur

public class VueFenetreClient extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private ImageIcon plusIcon;
	private FenetreClientController fenetreClientController;
	private JPanel panelTickets = new JPanel();

	private JLabel servicesAdmLabel;
	private JLabel servicesTechLabel;
	private JLabel secretariatLabel;

	private Set<Ticket> tickets;

	private JPanel panelAdm = new JPanel();
	private JPanel panelTech = new JPanel();
	private JPanel panelSecr = new JPanel();
	JPanel messages = new JPanel();
	
	private JPanel rightSidePanel = new JPanel();
	private JTextField fieldMessage = new JTextField();

	public static void main(String[] args) {
		new VueFenetreClient();
	}

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
		tickets.forEach(t -> t.addObserver(this));
		
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


		
		messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS)); 
		

//		SpringLayout layoutMessages = new SpringLayout();

		JPanel panelMessage = new JPanel();
		JScrollPane panelMessages = new JScrollPane();
//		panelMessages.setBorder(BorderFactory.createEmptyBorder());
		

//		layoutMessages.putConstraint(SpringLayout.WEST, panelMessage, 50, SpringLayout.WEST, messages);
//		layoutMessages.putConstraint(SpringLayout.NORTH, panelMessage, 50, SpringLayout.NORTH, messages);
		messages.setBorder(BorderFactory.createLineBorder(Color.RED));
//		messages.setLayout(layoutMessages);7

		


		rightSidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightSidePanel.add(panelMessages, BorderLayout.CENTER);
		JButton envoi = new JButton("Envoyer");
		//rightSidePanel.add(envoi,BorderLayout.EAST);
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

		fenetreClientController = new FenetreClientController(servicesAdmLabel,
				servicesTechLabel, secretariatLabel, panelAdm, panelTech, panelSecr, panelAdm2, panelTech2, plus1,
				plus2, plus3,messages,panelMessages);
		servicesAdmLabel.addMouseListener(fenetreClientController);
		servicesTechLabel.addMouseListener(fenetreClientController);
		secretariatLabel.addMouseListener(fenetreClientController);

		plus1.addMouseListener(fenetreClientController);
		plus2.addMouseListener(fenetreClientController);
		plus3.addMouseListener(fenetreClientController);

		fieldMessage.addKeyListener(fenetreClientController);

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
	public JPanel creationM(String str,String d) {
		VueMessage m = new VueMessage(str,d);
		JPanel mp = m.test();
		JPanel test = new JPanel();
		JPanel test2 = new JPanel();
		test2.setLayout(new GridLayout(1, 1));

		messages.add(mp, BorderLayout.WEST);
		test.setLayout(new GridLayout(1, 1, 12, 12));
		test.add(messages);
		test2.add(test);
		return test2;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		panelAdm.removeAll();
		fenetreClientController.openService(servicesAdmLabel);
		panelSecr.removeAll();
		fenetreClientController.openService(secretariatLabel);
		panelTech.removeAll();
		fenetreClientController.openService(servicesTechLabel);
	}
}
