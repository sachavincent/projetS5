package view.server;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
//Interface permettant d'ajouter un utilisateur � un groupe via deux menu d�roulant, l'un contenant les diff�rents utilisateurs et l'autres les groupes.

import com.sun.javafx.geom.AreaOp.AddOp;

import controller.server.AjoutUtilisateurGroupeController;
import database.DBConnection;

public class VueAjoutUtilisateurGroupe extends JFrame implements Observer {

	// TODO fenetre de confirmation
	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JLabel nomPanel = new JLabel("Ajout d'un utilisateur � un groupe");
	private JLabel listeGrp = new JLabel("liste des groupes");
	private JLabel listeU = new JLabel("liste des utilisateurs");
	private JComboBox<String> ListeGroupe;
	private JComboBox<String> listeUtilisateur;
	private JPanel[] panel = new JPanel[6];

	public VueAjoutUtilisateurGroupe() {
		// init
		nomPanel.setFont(nomPanel.getFont().deriveFont(15f));
		ok.setPreferredSize(new Dimension(300, 50));
		annuler.setPreferredSize(new Dimension(300, 50));

		ListeGroupe = new JComboBox<String>(
				DBConnection.getInstance().getListeGroupes().stream().map(g -> g.getNom()).toArray(String[]::new));
		listeUtilisateur = new JComboBox<String>(
				DBConnection.getInstance().getListeUtilisateurs().stream().map(g -> g.getIdentifiant()).toArray(String[]::new));

		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		listeUtilisateur.setPreferredSize(new Dimension(300, 50));
		for (int i = 0; i < 6; i++)
			panel[i] = new JPanel();
		// actionListener
		AjoutUtilisateurGroupeController ajoutUtilisateurGroupeController = new AjoutUtilisateurGroupeController();
		ListeGroupe.addActionListener(ajoutUtilisateurGroupeController);
		listeUtilisateur.addActionListener(ajoutUtilisateurGroupeController);
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
		panel[1].add(listeUtilisateur);
		panel[2].add(listeGrp);
		panel[2].add(ListeGroupe);
		panel[3].add(annuler);
		panel[3].add(ok);
		//panel[0].add(panel[5]);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0], BorderLayout.NORTH);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel[4],BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
