package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ModificationGroupe {
	private JButton ok = new JButton("OK");
	private String[] listeGrp = {"groupe 1","groupe 2","groupe 3","groupe 4","groupe 5"};
	private JComboBox<String> ListeGroupe;
	private JLabel newName = new JLabel("Nouveau nom: ");
	private JTextField nName = new JTextField(30);
	private JPanel[] panel = new JPanel[5];
	
	public ModificationGroupe() {
		//TODO menu d�roulant + autre fenetre
		//init
		ListeGroupe = new JComboBox<String>(listeGrp);
		for (int i = 0; i<5; i++)
			panel[i] = new JPanel();
		//taille
		ok.setPreferredSize(new Dimension(300, 50));
		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		//layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new BorderLayout());
		//ajout 
		panel[1].add(ListeGroupe);
		panel[2].add(newName);
		panel[2].add(nName);
		panel[3].add(ok);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0],BorderLayout.NORTH);
		
		//affichage
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		frame.add(panel[4]);
		frame.pack();
		frame.setVisible(true);
				
	}
	

}
