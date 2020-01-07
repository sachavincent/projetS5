package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Interface permettant la modification du nom d'un grouoe
public class ModificationGroupe implements ActionListener{
	private String[] listeGrp = {"groupe 1","groupe 2","groupe 3","groupe 4","groupe 5"};//TODO modfi pour avoir la bonne liste
	private JComboBox<String> ListeGroupe;
	private JLabel newName = new JLabel("Nouveau nom: ");
	private JTextField nName = new JTextField(30);
	private JPanel[] panel = new JPanel[4];
	private JFrame frame = new JFrame();
	public ModificationGroupe() {
		//init
		ListeGroupe = new JComboBox<String>(listeGrp);
		for (int i = 0; i<4; i++)
			panel[i] = new JPanel();
		ListeGroupe.addActionListener(this);
		//taille
		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		//layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new BorderLayout());
		//ajout 
		panel[1].add(ListeGroupe);
		panel[2].add(newName);
		panel[2].add(nName);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[3].add(panel[0],BorderLayout.NORTH);
		
		//
		//affichage
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 600);
		frame.setLocationRelativeTo(null);
		frame.add(panel[3]);
		frame.pack();
		frame.setVisible(true);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String groupe;
		String nom;
		if (e.getSource() == ListeGroupe) {
			JComboBox action = (JComboBox) e.getSource();
			String source = (String) action.getSelectedItem();
			for (int i = 0; i < listeGrp.length; i++) {
				if (source == listeGrp[i]) {
					groupe = listeGrp[i];
					nom = nName.getText();
					int op = JOptionPane.showConfirmDialog(null, "confirmation du nouveau nom du groupe ?");
					if(op == 0 ) {
						frame.setVisible(false);
						//TODO listeGrp[i].setname(groupe);
						break;
					}
				}
			}
		}
	}
		


	

}
