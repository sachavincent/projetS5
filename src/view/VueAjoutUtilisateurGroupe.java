package view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
//Interface permettant d'ajouter un utilisateur à un groupe via deux menu déroulant, l'un contenant les différents utilisateurs et l'autres les groupes.

import com.sun.javafx.geom.AreaOp.AddOp;

import controller.AjoutUtilisateurGroupeController;
import database.DBConnection;

public class VueAjoutUtilisateurGroupe extends JPanel implements Observer {

	// TODO fenetre de confirmation
	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JLabel nomPanel = new JLabel("Ajout d'un utilisateur à un groupe");
	private JLabel listeGrp = new JLabel("liste des groupes");
	private JLabel listeU = new JLabel("liste des utilisateurs");
	private JComboBox<String> ListeGroupe;
	private JComboBox<String> listeUtilisateur;
	private JPanel[] panel = new JPanel[5];

	public VueAjoutUtilisateurGroupe() {
		// init
		ok.setPreferredSize(new Dimension(300, 50));
		annuler.setPreferredSize(new Dimension(300, 50));

		ListeGroupe = new JComboBox<String>(
				DBConnection.getInstance().getListeGroupes().stream().map(g -> g.getNom()).toArray(String[]::new));
		listeUtilisateur = new JComboBox<String>(
				DBConnection.getInstance().getListeUtilisateurs().stream().map(g -> g.getNom()).toArray(String[]::new));

		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		listeUtilisateur.setPreferredSize(new Dimension(300, 50));
		for (int i = 0; i < 5; i++)
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
		panel[1].add(listeUtilisateur);
		panel[2].add(ListeGroupe);
		panel[3].add(ok);
		// panel[3].add(annuler);
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
