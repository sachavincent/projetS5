package view;

import javax.swing.*;

//Interface pour le Client
//contient le fils de discussion, une zone pour envoyer un message ainsi que toute les groupes auquel appartient l'utilisateur

public class FenetreClient {
	private JTextField message = new JTextField(20);
	private JPanel[] panel = new JPanel[3];
	
	public FenetreClient() {
		//init
		for (int i = 0; i< 3; i++)
			panel[i] = new JPanel();
		//layout
		
		//ajout

		
		
		
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
