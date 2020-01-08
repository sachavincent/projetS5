package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AjoutUtilisateurController;

//Interface permettant de créer un nouvel utilisateur.
public class VueAjoutUtilisateur extends JPanel implements Observer {
	private String[] tu = {"ETUDIANT","ENSEIGANT","SECRETAIRE PEDAGOGIQUE", "SERVICES ADMINISTRATIF","SERVICE TECNIQUES"};
	private JPanel[] panel = new JPanel[8];
	private JPanel top = new JPanel();
	private JPanel bot = new JPanel();
	private JTextField identifiant = new JTextField(30);;
	private JTextField password = new JTextField(30);;
	private JTextField nom = new JTextField(30);;
	private JTextField prenom = new JTextField(30);;
	private JComboBox typeUtilisateur = new JComboBox(tu);
	private JLabel id  = new JLabel("Identifiant");;
	private JLabel mdp = new JLabel("Mot de passe");;
	private JLabel firstname = new JLabel("Nom");;
	private JLabel name = new JLabel("Prénom");;
	private JLabel typeU = new JLabel("Type Utilisateur :");;
	private JButton Creation = new JButton("Création");;
	
	public VueAjoutUtilisateur(){
		//init 
		for (int i=0; i<8;i++) {
			panel[i] = new JPanel();
		}
		AjoutUtilisateurController ajoutUtilisateurController = new AjoutUtilisateurController(identifiant, password, nom, prenom);
		
		typeUtilisateur.addActionListener(ajoutUtilisateurController);
		//panel
		panel[0].setLayout(new GridLayout(3,1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new FlowLayout());
		panel[4].setLayout(new FlowLayout());
		panel[5].setLayout(new FlowLayout());

	
		
		
		//ajout
		panel[1].add(id);
		panel[1].add(identifiant);
		panel[2].add(mdp);
		panel[2].add(password);
		panel[3].add(firstname);
		panel[3].add(nom);
		panel[4].add(name);
		panel[4].add(prenom);
		panel[5].add(typeU);
		panel[5].add(typeUtilisateur);
		
		
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[0].add(panel[3]);
		panel[0].add(panel[4]);
		panel[0].add(panel[5]);
		
		
		top.add(panel[0]);
		bot.add(Creation);
		
		panel[6].setLayout(new BorderLayout());
		panel[6].add(top,BorderLayout.NORTH);
		panel[6].add(bot,BorderLayout.SOUTH);
		panel[7].add(panel[6],BorderLayout.NORTH);
		
		add(panel[7]);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
