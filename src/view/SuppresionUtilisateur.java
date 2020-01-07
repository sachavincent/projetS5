package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Interface permettant de supprimé un utilisateur via un menu déroulant
public class SuppresionUtilisateur implements ActionListener{
	private JButton ok = new JButton("OK");
	private String[] listeU = {"étudiant 1","étudiant 2","étudiant 3","étudiant 4"}; //TODO à modif pour avoir la vrai liste
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
		ok.addActionListener(this);
		listeUtilisateur.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String nom = listeU[0];
		if(e.getSource() == listeUtilisateur) {
			JComboBox action = (JComboBox) e.getSource();
			String source = (String) action.getSelectedItem();
			for(int i=0; i< listeU.length; i++) {
				if(source == listeU[i]) {
					nom = listeU[i];
					//break;
				}
			}
		}
		if (e.getSource() == ok) {
			//TODO close
			System.out.println(nom);
			
		}
		
	}

}
