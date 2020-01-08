package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.SuppressionUtilisateurGroupeController;
import database.DBConnection;

//Interface servant à la supprésion d'un groupe
// elle affiche la liste de tout les groupes puis aprés la sélection du groupe à supprimé affiche une fenètre de confirmation.
public class VueSuppressionUtilisateurGroupe extends JPanel implements Observer {

	private JButton ok = new JButton("OK");
	private JComboBox<String> ListeGroupe;
	private JLabel listeG = new JLabel("ListeGroupe");
	private JLabel listeU = new JLabel("Liste utilisateur");
	private JComboBox<String> listeUtilisateur;
	private JPanel[] panel = new JPanel[5];

	public VueSuppressionUtilisateurGroupe() {
		// init
		ok.setPreferredSize(new Dimension(300, 50));
		ListeGroupe = new JComboBox<String>(
				DBConnection.getInstance().getListeGroupes().stream().map(g -> g.getNom()).toArray(String[]::new));
		
		listeUtilisateur = new JComboBox<String>(
				DBConnection.getInstance().getListeUtilisateurs().stream().map(g -> g.getNom()).toArray(String[]::new));
		
	
		for (int i = 0; i < 5; i++)
			panel[i] = new JPanel();
		//taille
		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		listeUtilisateur.setPreferredSize(new Dimension(300, 50));
		//actionListener
		SuppressionUtilisateurGroupeController suppressionUtilisateurGroupeController = new SuppressionUtilisateurGroupeController();
		ok.addActionListener(suppressionUtilisateurGroupeController);
		ListeGroupe.addActionListener(suppressionUtilisateurGroupeController);
		listeUtilisateur.addActionListener(suppressionUtilisateurGroupeController);
		// layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new BorderLayout());

		// ajout
		panel[1].add(listeU);
		panel[1].add(listeUtilisateur);
		panel[2].add(listeG);
		panel[2].add(ListeGroupe);
		panel[3].add(ok);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0], BorderLayout.NORTH);

		add(panel[4],BorderLayout.NORTH);
		

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
