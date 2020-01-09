package view.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.client.CreationTicketController;
import database.DBConnection;
import model.Utilisateur.TypeUtilisateur;

// Interface permettant de se connecter à l'application et au serveur
public class VueCreationTicket extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private JTextField titreField = new JTextField(30);

	private JComboBox<String> groupesComboBox = new JComboBox<>(new String[] { "Choisir un groupe" });

	private JLabel titreLabel = new JLabel("Titre du ticket");

	private JPanel[] panels = new JPanel[7];

	private JButton okButton = new JButton("OK");
	private JButton annulerButton = new JButton("Annuler");

	public VueCreationTicket(TypeUtilisateur typeUtilisateur) {
		setLayout(new BorderLayout());

		// init
		for (int i = 0; i < 7; i++)
			panels[i] = new JPanel();

		DBConnection.getInstance().getListeGroupes().stream().filter(groupe -> {
			return DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().stream()
					.filter(agu -> agu.getGroupe().equals(groupe))
					.allMatch(agu -> agu.getUtilisateur().getType() == typeUtilisateur);
		}).forEach(g -> groupesComboBox.addItem(g.getNom()));

		// layout pour les panels
		panels[0].setLayout(new GridLayout(2, 1));

		CreationTicketController controller = new CreationTicketController(titreLabel, groupesComboBox);

		okButton.addActionListener(controller);
		annulerButton.addActionListener(controller);

		groupesComboBox.addActionListener(controller);

		// ajout au panel
		panels[1].add(titreLabel);
		panels[1].add(titreField);
		panels[0].add(panels[1]);

		panels[0].add(groupesComboBox);

		panels[1].add(annulerButton);
		panels[1].add(okButton);

		add(panels[0], BorderLayout.NORTH);
		add(panels[1], BorderLayout.SOUTH);

		setTitle("NeOCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
	}

}
