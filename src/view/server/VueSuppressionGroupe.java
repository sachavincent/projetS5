package view.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.server.SuppressionGroupeController;
import database.DBConnection;
import model.GroupeUtilisateurs;

//Interface permettant de supprimer un groupe via un menu déroulant contenant l'ensemble des groupes
public class VueSuppressionGroupe extends JFrame implements Observer {

	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JComboBox<String> listeGroupesComboBox;
	private JPanel[] panels = new JPanel[5];
	private JLabel titreComboBox = new JLabel("Liste des groupes");
	private JLabel titrePanel = new JLabel("Suppression d'un groupe");

	public VueSuppressionGroupe() {
		// TODO pop up de confirmation quand on clique sur "OK"
		// init
		titrePanel.setFont(titrePanel.getFont().deriveFont(15f));

		Set<GroupeUtilisateurs> listeGroupes = DBConnection.getInstance().getListeGroupes();

		listeGroupes.forEach(g -> g.addObserver(this));

		listeGroupesComboBox = new JComboBox<String>(listeGroupes.stream().map(g -> g.getNom()).toArray(String[]::new));
		SuppressionGroupeController suppressionGroupeController = new SuppressionGroupeController();

		for (int i = 0; i < 5; i++) {
			panels[i] = new JPanel();
		}
		// taille
		listeGroupesComboBox.setPreferredSize(new Dimension(300, 50));
		ok.setPreferredSize(new Dimension(200, 50));
		annuler.setPreferredSize(new Dimension(200, 50));
		// actionListener
		ok.addActionListener(suppressionGroupeController);
		annuler.addActionListener(suppressionGroupeController);
		listeGroupesComboBox.addActionListener(suppressionGroupeController);
		// layout
		panels[0].setLayout(new GridLayout(3, 1));
		panels[1].setLayout(new FlowLayout());
		panels[2].setLayout(new FlowLayout());
		panels[3].setLayout(new BorderLayout());
		// ajout
		panels[4].add(titrePanel);
		panels[1].add(titreComboBox);
		panels[1].add(listeGroupesComboBox);
		panels[2].add(annuler);
		panels[2].add(ok);
		panels[0].add(panels[4]);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[3].add(panels[0], BorderLayout.NORTH);
		
		setTitle("NeoCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panels[3], BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		listeGroupesComboBox.removeItem(listeGroupesComboBox.getSelectedItem());
	}

}
