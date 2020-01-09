package view.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.server.SuppressionUtilisateurGroupeController;
import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Utilisateur;

// Interface servant à la suppression d'un groupe
// Elle affiche la liste de tous les groupes puis après la sélection du groupe à supprimer, affiche une fenêtre de confirmation.
public class VueSuppressionUtilisateurGroupe extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private Set<GroupeUtilisateurs> listeGroupes = new HashSet<>();
	private Set<Utilisateur> listeUtilisateurs = new HashSet<>();

	private JButton okButton = new JButton("OK");
	private JButton annulerButton = new JButton("Annuler");

	private JLabel listeULabel = new JLabel("Choisir l'utilisateur à supprimer");
	private JLabel listeGLabel = new JLabel("Choisir le groupe dans lequel supprimer l'utilisateur");
	private JLabel titreLabel = new JLabel("Suppression d'un utilisateur à un groupe", SwingConstants.CENTER);
	private JLabel erreurLabel = new JLabel("", SwingConstants.CENTER);

	private JComboBox<String> groupesComboBox;
	private JComboBox<String> utilisateursComboBox;

	private JPanel[] panels = new JPanel[5];

	private Dimension dimension = new Dimension(300, 50);

	private DefaultListCellRenderer listRenderer;

	private SuppressionUtilisateurGroupeController controller;

	public VueSuppressionUtilisateurGroupe() {
		// init
		okButton.setPreferredSize(dimension);
		annulerButton.setPreferredSize(dimension);

		okButton.setEnabled(false);

		titreLabel.setFont(titreLabel.getFont().deriveFont(20f));

		erreurLabel.setFont(titreLabel.getFont().deriveFont(20f));
		erreurLabel.setForeground(Color.GREEN);
		erreurLabel.setText("test");
		erreurLabel.setOpaque(true);
		erreurLabel.setVisible(false);

		listeGroupes = DBConnection.getInstance().getListeGroupes();
		listeUtilisateurs = DBConnection.getInstance().getListeUtilisateurs();

		listeGroupes.forEach(g -> g.addObserver(this));
		listeUtilisateurs.forEach(u -> u.addObserver(this));

		listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items

		groupesComboBox = new JComboBox<String>(new String[] { "Choisir un groupe" });

		String[] array = listeUtilisateurs.stream().map(g -> g.getIdentifiant()).toArray(String[]::new);
		utilisateursComboBox = new JComboBox<String>();
		utilisateursComboBox.addItem("Choisir un utilisateur");
		for (String s : array)
			utilisateursComboBox.addItem(s);

		// actionListener
		controller = new SuppressionUtilisateurGroupeController(groupesComboBox, utilisateursComboBox, okButton, erreurLabel);

		String identifiant = utilisateursComboBox.getItemAt(utilisateursComboBox.getSelectedIndex());
		controller.setSelectedUser(identifiant);

		utilisateursComboBox.setRenderer(listRenderer);
		groupesComboBox.setRenderer(listRenderer);

		for (int i = 0; i < 5; i++)
			panels[i] = new JPanel();

		// taille
		groupesComboBox.setPreferredSize(dimension);
		utilisateursComboBox.setPreferredSize(dimension);

		okButton.addActionListener(controller);
		annulerButton.addActionListener(controller);

		groupesComboBox.addActionListener(controller);
		utilisateursComboBox.addActionListener(controller);

		// layout
		panels[0].setLayout(new GridLayout(4, 1));
		for (int i = 1; i < 4; i++)
			panels[1].setLayout(new FlowLayout());

		panels[4].setLayout(new BorderLayout());
		// ajout
		panels[1].add(listeULabel);
		panels[1].add(utilisateursComboBox);
		panels[2].add(listeGLabel);
		panels[2].add(groupesComboBox);
		panels[3].add(annulerButton);
		panels[3].add(okButton);
		// panels[0].add(panels[5]);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[0].add(erreurLabel);
		panels[0].add(panels[3]);
//		panels[4].add(panels[0], BorderLayout.NORTH);
		panels[4].add(titreLabel, BorderLayout.NORTH);
		panels[0].setBorder(new EmptyBorder(40, 10, 10, 10));		
		panels[4].add(panels[0], BorderLayout.CENTER);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panels[4], BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object obj) {
		controller.setSelectedUser(utilisateursComboBox.getItemAt(utilisateursComboBox.getSelectedIndex()));
	}

}
