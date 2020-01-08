package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Affiche une interface permettant de modifier un param�tre d'un utilisateur via 2 menu d�roulant.
public class ModificationUtilisateur {
	//TODO fenetre de confirmation
	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("annuler");
	private String[] listeU = {"�tudiant 1","�tudiant 2","�tudiant 3","�tudiant 4"}; //TODO modif pour avoir la bonne liste
	private JComboBox<String> listeUtilisateur;
	private String[] Attribut = {"identifiant","password","nom","prenom","type"};
	private JComboBox<String> AttributModif;
	private JPanel[] panel = new JPanel[5];
	private Dimension d = new Dimension(300, 50);
	
	
	public ModificationUtilisateur() {
		//init 
		listeUtilisateur = new JComboBox<String>(listeU);
		AttributModif = new JComboBox<String>(Attribut);
		
		for(int i=0;i<5;i++)
			panel[i] = new JPanel();
		
		//taille
		ok.setPreferredSize(d);
		annuler.setPreferredSize(d);
		listeUtilisateur.setPreferredSize(d);
		AttributModif.setPreferredSize(d);
		
		//layout
		panel[0].setLayout(new GridLayout(3,1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new BorderLayout());
		
		//ajout
		panel[1].add(listeUtilisateur);
		panel[2].add(AttributModif);
		panel[3].add(ok);
		panel[3].add(annuler);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[4].add(panel[0],BorderLayout.NORTH);
		
		
		
		
		//affichage
		JFrame frame = new JFrame("ModificationUtilsateurGrp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		frame.add(panel[4]);
		frame.pack();
		frame.setVisible(true);
	}

}
