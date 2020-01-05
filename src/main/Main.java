package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * TODO Renommer cette classe parce que bon
 *
 */
public class Main {

	/**
	 * Méthode principale de l'application
	 */
	public static void main(String[] args) {

		// Solution temporaire, faudra 2 applications séparées à la fin
		// {
		// Scanner sc = new Scanner(System.in);
		// int choix;
		// do {
		// System.out.println("Mode Serveur (0) ou mode Client (1) ?");
		// choix = sc.nextInt();
		// } while (choix != 0 && choix != 1);
		// sc.close();
		//
		// switch (choix) {
		// case 0: // Serveur
		// //TODO
		// break;
		// case 1: // Client
		// //TODO
		// break;
		// }
		// }
		//
		// DBConnection db = DBConnection.getInstance();

		JFrame frame = new JFrame("NeOCampus");
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();

		JPanel p7 = new JPanel();
		p1.setLayout(new GridBagLayout());
		p2.setLayout(new GridBagLayout());
		p3.setLayout(new GridBagLayout());
		p4.setLayout(new GridBagLayout());
		p5.setLayout(new GridBagLayout());
		p6.setLayout(new GridBagLayout());
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbcTitle = new GridBagConstraints();
		gbcTitle.gridwidth = GridBagConstraints.RELATIVE;
		gbcTitle.gridheight = GridBagConstraints.RELATIVE;
//		gbcTitle.weightx = 1;
//		gbcTitle.weighty = 0;
//		gbcTitle.gridheight = 1;
//
		JButton b1 = new JButton("1");
		JButton b2 = new JButton("2");
		JButton b3 = new JButton("3");
		JButton b4 = new JButton("4");
		JButton b5 = new JButton("5");
		JButton b6 = new JButton("6");
		JButton b7 = new JButton("7");
		JButton b8 = new JButton("8");

//		b1.setPreferredSize(new Dimension(200, 50));
//		b2.setPreferredSize(new Dimension(200, 50));
//		b3.setPreferredSize(new Dimension(200, 50));
//		b4.setPreferredSize(new Dimension(200, 50));
//		b5.setPreferredSize(new Dimension(200, 50));
//		b6.setPreferredSize(new Dimension(200, 50));
//		b7.setPreferredSize(new Dimension(200, 50));
//		b8.setPreferredSize(new Dimension(200, 50));
		
		b1.setMinimumSize(new Dimension(200, 50));
		b2.setMinimumSize(new Dimension(200, 50));
		b3.setMinimumSize(new Dimension(200, 50));
		b4.setMinimumSize(new Dimension(200, 50));
		b5.setMinimumSize(new Dimension(200, 50));
		b6.setMinimumSize(new Dimension(200, 50));
		b7.setMinimumSize(new Dimension(200, 50));
		b8.setMinimumSize(new Dimension(200, 50));
		GridLayout gridLayout = new GridLayout(3, 2);
		gridLayout.setVgap(50);
		gridLayout.setHgap(50);
		p1.add(b1, gbcTitle);
		p2.add(b2, gbcTitle);
		p3.add(b3, gbcTitle);
		p4.add(b4, gbcTitle);
		p5.add(b5, gbcTitle);
		p6.add(b6, gbcTitle);

		gbcTitle = new GridBagConstraints();
		gbcTitle.anchor = GridBagConstraints.CENTER;
		gbcTitle.fill = GridBagConstraints.HORIZONTAL;
		gbcTitle.gridwidth = GridBagConstraints.REMAINDER;
		gbcTitle.insets = new Insets(50, 0, 0, 0);
		p7.setLayout(gbl);
		p7.add(b7, gbcTitle);
		p7.add(b8, gbcTitle);

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		panel.add(p4);
		panel.add(p5);
		panel.add(p6);

		p.add(panel, BorderLayout.NORTH);
		p.add(p7, BorderLayout.CENTER);
		panel.setLayout(gridLayout);
		frame.setContentPane(p);
		frame.pack();
		frame.setVisible(true);
	}

}
