package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ConnexionController;

// Interface permettant de se connecter à l'application et au serveur
public class VueConnexion extends JFrame implements Observer {
	private JTextField id = new JTextField(30);;
	private JPasswordField mdp = new JPasswordField(30);;
	private JLabel identifiant = new JLabel("Identifiant");;
	private JLabel motDePasse = new JLabel("Mot de passe");;
	private JButton connexion = new JButton("Connexion");;
	private JPanel[] panel = new JPanel[7];
	
	public VueConnexion() {
		//init
		for(int i = 0; i< 7; i++)
			panel[i] = new JPanel();
		
		//layout pour les panels
		panel[0].setLayout(new GridLayout(2,1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new FlowLayout());
		panel[5].setLayout(new BorderLayout());
		panel[6].setLayout(new BorderLayout());
		//ajout au panel
		// ajout
		panel[1].add(identifiant);
		panel[1].add(id);
		panel[2].add(motDePasse);
		panel[2].add(mdp);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[3].add(panel[0]);
		panel[4].add(connexion);

		panel[5].add(panel[3], BorderLayout.NORTH);
		panel[5].add(panel[4], BorderLayout.SOUTH);

		panel[6].add(panel[5], BorderLayout.NORTH);

		connexion.addActionListener(new ConnexionController(id, mdp));

		add(panel[6]);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
