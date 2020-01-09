package view.server;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.*;
//Interface permettant d'ajouter un utilisateur à un groupe via deux menu déroulant, l'un contenant les différents utilisateurs et l'autres les groupes.

import com.sun.javafx.geom.AreaOp.AddOp;

import controller.server.AjoutUtilisateurGroupeController;
import database.DBConnection;
import model.GroupeUtilisateurs;
import model.Utilisateur;

public class VueAjoutUtilisateurGroupe extends JFrame implements Observer {

	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JLabel nomPanel = new JLabel("Ajout d'un utilisateur à un groupe");
	private JLabel listeGrp = new JLabel("Choisir un groupe");
	private JLabel listeU = new JLabel("Choisir un utilisateurs");
	private JComboBox<String> listeGroupeComboBox = new JComboBox<String>();;
	private JComboBox<String> listeUtilisateurComboBox = new JComboBox<String>();;
	private JPanel[] panel = new JPanel[6];
	private DefaultListCellRenderer listRenderer;

	public VueAjoutUtilisateurGroupe() {
		// init
		nomPanel.setFont(nomPanel.getFont().deriveFont(15f));
		ok.setPreferredSize(new Dimension(300, 50));
		annuler.setPreferredSize(new Dimension(300, 50));

		listeGroupeComboBox.addItem("Choisir un groupe");
		
		Set<GroupeUtilisateurs> listeGroupes = DBConnection.getInstance().getListeGroupes();
		Set<Utilisateur> listeUtilisateurs = DBConnection.getInstance().getListeUtilisateurs();
		
		listeGroupes.forEach(g -> g.addObserver(this));
		listeUtilisateurs.forEach(u -> u.addObserver(this));
		
		
		listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
		String[] array = listeUtilisateurs.stream().map(u -> u.getIdentifiant()).toArray(String[]::new);
		listeUtilisateurComboBox = new JComboBox<String>();
		listeUtilisateurComboBox.addItem("Choisir un utilisateur");
		
		for (String s : array)
			listeUtilisateurComboBox.addItem(s);
		
		listeGroupeComboBox.setPreferredSize(new Dimension(300, 50));
		listeUtilisateurComboBox.setPreferredSize(new Dimension(300, 50));

		for (int i = 0; i < 6; i++)
			panel[i] = new JPanel();
		ok.setEnabled(false);
		// actionListener
		AjoutUtilisateurGroupeController ajoutUtilisateurGroupeController = new AjoutUtilisateurGroupeController(ok,listeGroupeComboBox,listeUtilisateurComboBox);
		listeGroupeComboBox.addActionListener(ajoutUtilisateurGroupeController);
		listeUtilisateurComboBox.addActionListener(ajoutUtilisateurGroupeController);
		ok.addActionListener(ajoutUtilisateurGroupeController);
		annuler.addActionListener(ajoutUtilisateurGroupeController);
		// layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new BorderLayout());

		// ajout
		panel[5].add(nomPanel);
		panel[1].add(listeU);
		panel[1].add(listeUtilisateurComboBox);
		panel[2].add(listeGrp);
		panel[2].add(listeGroupeComboBox);
		panel[3].add(annuler);
		panel[3].add(ok);
		// panel[0].add(panel[5]);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0], BorderLayout.NORTH);

		setTitle("NeoCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel[4], BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
