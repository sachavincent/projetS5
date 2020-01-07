package view;

import java.awt.*;

import javax.swing.*;
//Interface permettant d'ajouter un utilisateur à un groupe via deux menu déroulant, l'un contenant les différents utilisateurs et l'autres les groupes.

public class AjoutUtilisateurGroupe {
	
	//TODO fenetre de confirmation
	private JButton ok = new JButton("OK");
	private String[] listeGrp = {"groupe 1","groupe 2","groupe 3","groupe 4","groupe 5"};
	private JComboBox<String> ListeGroupe;
	private String[] listeU = {"étudiant 1","étudiant 2","étudiant 3","étudiant 4"};
	private JComboBox<String> listeUtilisateur;
	private JPanel[] panel = new JPanel[5];
	public AjoutUtilisateurGroupe() {
		//init 
		ok.setPreferredSize(new Dimension(300,50));
		ListeGroupe = new JComboBox<String>(listeGrp);
		listeUtilisateur = new JComboBox<String>(listeU);
		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		listeUtilisateur.setPreferredSize(new Dimension(300, 50));
		for (int i =0; i<5;i++)
			panel[i] = new JPanel();
		
		//layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new BorderLayout());
		
		//ajout
		panel[1].add(listeUtilisateur);
		panel[2].add(ListeGroupe);
		panel[3].add(ok);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0],BorderLayout.NORTH);
		
		//affichage
		JFrame frame = new JFrame("Ajout utilisateur groupe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		frame.add(panel[4]);
		frame.pack();
		frame.setVisible(true);
	}

}
