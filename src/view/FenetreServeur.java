 package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreServeur extends JFrame implements ActionListener{
	private JFrame frame;
	private JPanel panel;
	private JButton bouton1,bouton2,bouton3,bouton4,bouton5,bouton6,bouton7,bouton8;
	
	public FenetreServeur() {
		//Frame
		frame = new JFrame ("Interface Serveur");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		//Panel
		panel = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();
		
		JPanel p7 = new JPanel();
		//boutons
		// utilisateur
		bouton1 = new JButton("Ajouter un utilisateur");
		bouton2 = new JButton("Modifier un utilisateur");
		bouton3 = new JButton("Supprimer un utilisateur");
		//groupe 
		bouton4 = new JButton("Ajouter un groupe");
		bouton5 = new JButton("Modifier un groupe");
		bouton6 = new JButton("Supprimer un groupe");
		// user X grp
		bouton7 = new JButton("Ajouter un utilisateur à un groupe");
		bouton8 = new JButton("Supprimer un utilisateur d'un groupe");
		// taille bouton
		bouton1.setPreferredSize(new Dimension(300, 50));
		bouton2.setPreferredSize(new Dimension(300, 50));
		bouton3.setPreferredSize(new Dimension(300, 50));
		bouton4.setPreferredSize(new Dimension(300, 50));
		bouton5.setPreferredSize(new Dimension(300, 50));
		bouton6.setPreferredSize(new Dimension(300, 50));
		bouton7.setPreferredSize(new Dimension(300, 50));
		bouton8.setPreferredSize(new Dimension(300, 50));
		//Layout
		p1.setLayout(new GridBagLayout());
		p2.setLayout(new GridBagLayout());
		p3.setLayout(new GridBagLayout());
		p4.setLayout(new GridBagLayout());
		p5.setLayout(new GridBagLayout());
		p6.setLayout(new GridBagLayout());
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints t = new GridBagConstraints();
		t.anchor = GridBagConstraints.CENTER;
		t.fill = GridBagConstraints.HORIZONTAL;
		t.gridwidth = GridBagConstraints.REMAINDER;
		t.anchor = GridBagConstraints.NORTH;
		t.insets = new Insets(50, 0,0,0);
		p7.setLayout(gbl);
		
		GridLayout g = new GridLayout(3,2);
		g.setVgap(50);
		g.setHgap(50);
		//ActionListener
		
		bouton1.addActionListener(this);
		bouton2.addActionListener(this);
		bouton3.addActionListener(this);
		bouton4.addActionListener(this);
		bouton5.addActionListener(this);
		bouton6.addActionListener(this);
		bouton7.addActionListener(this);
		bouton8.addActionListener(this);
		//Ajout
		p1.add(bouton1);
		p2.add(bouton4);
		p3.add(bouton3);
		p4.add(bouton6);
		p5.add(bouton2);
		p6.add(bouton5);
		p7.add(bouton7,t);
		p7.add(bouton8,t);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		panel.add(p4);
		panel.add(p5);
		panel.add(p6);
		
		p.add(panel,BorderLayout.NORTH);
		p.add(p7,BorderLayout.CENTER);
		panel.setLayout(g);
		frame.add(p);
		
		//centrage + affichage
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	public void actionPerformed(ActionEvent event) {
		if( event.getSource() == bouton1) {
			frame.setVisible(false);
			new AjoutUtilisateur();
		}
		if(event.getSource() == bouton3) {
			new SuppresionUtilisateur();
		}
		}
}
	
