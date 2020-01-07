package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

//Interface permettant de supprimer un groupe via un menu déroulant contenant l'ensemble des groupes
public class SuppresionGroupe {
	private JButton ok = new JButton("OK");
	private String[] listeGrp = {"groupe 1","groupe 2","groupe 3","groupe 4","groupe 5"}; //TODO changer la liste des groupes pour qu'elle corresponde à la vrai liste
	private JComboBox<String> ListeGroupe;
	private JPanel[] panel = new JPanel[4];
	public SuppresionGroupe() {
		//TODO pop up de confirmation quand on clique sur "OK"
				//init
				ListeGroupe = new JComboBox<String>(listeGrp);
				for (int i=0; i<4;i++) {
					panel[i] = new JPanel();
				}
				ListeGroupe.setPreferredSize(new Dimension(300, 50));
				ok.setPreferredSize(new Dimension(300, 50));
				//layout
				panel[0].setLayout(new GridLayout(3,1));
				panel[1].setLayout(new FlowLayout());
				panel[2].setLayout(new FlowLayout());
				panel[3].setLayout(new BorderLayout());
				//ajout
				panel[1].add(ListeGroupe);
				panel[2].add(ok);
				panel[0].add(panel[1]);
				panel[0].add(panel[2]);
				panel[3].add(panel[0],BorderLayout.NORTH);
				
				//affichage
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(300, 600);
				frame.setLocationRelativeTo(null);
				frame.add(panel[3]);
				frame.pack();
				frame.setVisible(true);
	}
	

}
