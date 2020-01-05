package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Connexion extends JFrame {
	private JTextField id ;
	private JPasswordField mdp;
	private JFrame frame;
	private JPanel panel;
	private JLabel identifiant;
	private JLabel motDePasse;
	private JButton connexion;
	
	public Connexion() {
		//frame
		frame = new JFrame ("Connexion");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		//Panel
		panel = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		//label
		identifiant = new JLabel("Identifiant");
		motDePasse = new JLabel("Mot de passe");
		//layout
		panel.setLayout(new GridLayout(2,1));
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		JPanel bot = new JPanel();
		bot.setLayout(new FlowLayout());
		//texteField
		id = new JTextField(30);
		mdp = new JPasswordField(30);
		//bouton
		connexion = new JButton("Connexion");
		//ajout
		top.add(identifiant);
		top.add(id);
		bot.add(motDePasse);
		bot.add(mdp);
		panel.add(top);
		panel.add(bot);
		p1.add(panel);
		p2.add(connexion);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(p1,BorderLayout.NORTH);
		p.add(p2,BorderLayout.SOUTH);
		JPanel pf = new JPanel();
		pf.setLayout(new BorderLayout());
		pf.add(p,BorderLayout.NORTH);
		//centrage + affichage
		frame.add(pf);
		frame.pack();
		frame.setVisible(true);
		
	}
	

}
