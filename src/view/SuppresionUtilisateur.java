package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

//Interface permettant de supprim� un utilisateur via un menu d�roulant
public class SuppresionUtilisateur {
	private JButton ok = new JButton("OK");
	private String[] listeU = {"�tudiant 1","�tudiant 2","�tudiant 3","�tudiant 4"}; //TODO � modif pour avoir la vrai liste
	private JComboBox<String> listeUtilisateur;
	private JPanel[] panel = new JPanel[4];
	
	public SuppresionUtilisateur(){
		//TODO pop up de confirmation quand on clique sur "OK"
		//init
		listeUtilisateur = new JComboBox<String>(listeU);
		for (int i=0; i<4;i++) {
			panel[i] = new JPanel();
		}
		listeUtilisateur.setPreferredSize(new Dimension(300, 50));
		ok.setPreferredSize(new Dimension(300, 50));
		//layout
		panel[0].setLayout(new GridLayout(3,1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new BorderLayout());
		//ajout
		panel[1].add(listeUtilisateur);
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
