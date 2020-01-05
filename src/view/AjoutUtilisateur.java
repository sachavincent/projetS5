package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AjoutUtilisateur {
	private JFrame frame;
	private JPanel panel;
	private JTextField identifiant;
	private JTextField password;
	private JTextField nom;
	private JTextField prenom;
	private JComboBox typeUtilisateur;
	private JLabel id;
	private JLabel mdp;
	private JLabel fname;
	private JLabel name;
	private JLabel typeU;
	private JButton Creation;
	
	public AjoutUtilisateur(){
		//frame
		frame = new JFrame ("Ajout Utilisateur");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		
		//panel
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout());
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout());
		
		//label
		id = new JLabel("Identifiant");
		mdp = new JLabel("Mot de passe");
		fname = new JLabel("Nom");
		name = new JLabel("Prénom");
		typeU = new JLabel("Type Utilisateur :");
		
		//textField
		identifiant = new JTextField(30);
		password = new JTextField(30);
		nom = new JTextField(30);
		prenom = new JTextField(30);
		
		//bouton
		Creation = new JButton("Création");
		//comboBox
		String[] TU = {"ETUDIANT","ENSEIGANT","SECRETAIRE PEDAGOGIQUE", "SERVICES ADMINISTRATIF","SERVICE TECNIQUES"};
		typeUtilisateur = new JComboBox(TU);		
		
		
		//ajout
		p1.add(id);
		p1.add(identifiant);
		p2.add(mdp);
		p2.add(password);
		p3.add(fname);
		p3.add(nom);
		p4.add(name);
		p4.add(prenom);
		p5.add(typeU);
		p5.add(typeUtilisateur);
		
		
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		panel.add(p4);
		panel.add(p5);
		
		JPanel top = new JPanel();
		JPanel bot = new JPanel();
		top.add(panel);
		bot.add(Creation);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(top,BorderLayout.NORTH);
		p.add(bot,BorderLayout.SOUTH);
		JPanel pf = new JPanel();
		pf.add(p,BorderLayout.NORTH);
		
		//centrage + affichage
		frame.add(pf);
		frame.pack();
		frame.setVisible(true);
		
		
	}
	

}
