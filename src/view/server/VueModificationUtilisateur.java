package view.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.server.ModificationGroupeController;
import controller.server.ModificationUtilisateurController;
import database.DBConnection;

//Affiche une interface permettant de modifier un paramètre d'un utilisateur via 2 menu déroulant.
public class VueModificationUtilisateur extends JFrame implements Observer {
	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JLabel nomPanel = new JLabel("Modification d'un utilisateur",SwingConstants.CENTER);
	private JLabel listeU = new JLabel("liste des utilisateurs");
	private JLabel type = new JLabel("liste des attributs à modifier");
	private JComboBox<String> listeUtilisateur;
	private String[] Attribut = { "Identifiant", "Mot de passe", "Nom", "Prenom", "Type" };
	private JComboBox<String> AttributModif;
	private JPanel[] panel = new JPanel[6];
	private Dimension d = new Dimension(300, 50);

	public VueModificationUtilisateur() {
		// init
		listeUtilisateur = new JComboBox<String>(
				DBConnection.getInstance().getListeUtilisateurs().stream().map(g -> g.getIdentifiant()).toArray(String[]::new));
		AttributModif = new JComboBox<String>(Attribut);
		nomPanel.setFont(nomPanel.getFont().deriveFont(15f));
		for (int i = 0; i < 6; i++)
			panel[i] = new JPanel();
		ok.setEnabled(false);
		// taille
		ok.setPreferredSize(d);
		annuler.setPreferredSize(d);
		listeUtilisateur.setPreferredSize(d);
		AttributModif.setPreferredSize(d);
		//actionListener
		ModificationUtilisateurController modificationUtilisateurController = new ModificationUtilisateurController();
		ok.addActionListener(modificationUtilisateurController);
		annuler.addActionListener(modificationUtilisateurController);
		listeUtilisateur.addActionListener(modificationUtilisateurController);
		AttributModif.addActionListener(modificationUtilisateurController);
		// layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new BorderLayout());

		// ajout
		panel[5].add(nomPanel);
		panel[1].add(listeU);
		panel[1].add(listeUtilisateur);
		panel[2].add(type);
		panel[2].add(AttributModif);
		panel[3].add(annuler);
		panel[3].add(ok);
		//panel[0].add(panel[5]);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0], BorderLayout.NORTH);

		setTitle("NeoCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel[4],BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);
		


		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}

}
