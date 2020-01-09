package view.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.server.SuppressionUtilisateurController;
import database.DBConnection;

//Interface permettant de supprimé un utilisateur via un menu déroulant
public class VueSuppressionUtilisateur extends JPanel implements Observer {

	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JComboBox<String> listeUtilisateur;
	private JPanel[] panel = new JPanel[5];
	private JLabel titrePanel = new JLabel("Suppression d'un utilisateur");
	private JLabel titreComboBox = new JLabel("Sélection de l'utilisateur");

	public VueSuppressionUtilisateur() {
		// TODO pop up de confirmation quand on clique sur "OK"
		// init
		listeUtilisateur = new JComboBox<String>(
				DBConnection.getInstance().getListeUtilisateurs().stream().map(g -> g.getNom()).toArray(String[]::new));
		for (int i = 0; i < 5; i++) {
			panel[i] = new JPanel();
		}
		titrePanel.setFont(titrePanel.getFont().deriveFont(15f));
		listeUtilisateur.setPreferredSize(new Dimension(300, 50));
		
		ok.setPreferredSize(new Dimension(200, 50));
		annuler.setPreferredSize(new Dimension(200, 50));
		// actionListener
		SuppressionUtilisateurController suppressionUtilisateurController = new SuppressionUtilisateurController();
		ok.addActionListener(suppressionUtilisateurController);
		annuler.addActionListener(suppressionUtilisateurController);
		listeUtilisateur.addActionListener(suppressionUtilisateurController);
		// layout
		panel[0].setLayout(new GridLayout(3, 1));
		panel[1].setLayout(new FlowLayout());
		panel[2].setLayout(new FlowLayout());
		panel[3].setLayout(new BorderLayout());
		// ajout
		panel[1].add(titreComboBox);
		panel[1].add(listeUtilisateur);
		panel[2].add(ok);
		panel[2].add(annuler);
		panel[4].add(titrePanel);
		panel[0].add(panel[4]);
		panel[0].add(panel[1]);
		panel[0].add(panel[2]);
		panel[3].add(panel[0], BorderLayout.NORTH);

		add(panel[3], BorderLayout.NORTH);
		
		

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
