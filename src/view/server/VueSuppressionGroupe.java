package view.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.*;

import controller.server.SuppressionGroupeController;
import database.DBConnection;
import javafx.beans.Observable;

//Interface permettant de supprimer un groupe via un menu d�roulant contenant l'ensemble des groupes
public class VueSuppressionGroupe extends JPanel implements Observer {

	private JButton ok = new JButton("OK");
	private JButton annuler = new JButton("Annuler");
	private JComboBox<String> ListeGroupe;
	private JPanel[] panels = new JPanel[5];
	private JLabel titreComboBox = new JLabel("Liste des groupes");
	private JLabel titrePanel = new JLabel("Suppression d'un groupe");

	public VueSuppressionGroupe() {
		// TODO pop up de confirmation quand on clique sur "OK"
		// init
		titrePanel.setFont(titrePanel.getFont().deriveFont(15f));
		ListeGroupe = new JComboBox<String>(
				DBConnection.getInstance().getListeGroupes().stream().map(g -> g.getNom()).toArray(String[]::new));
		SuppressionGroupeController suppressionGroupeController = new SuppressionGroupeController();
		
		for (int i = 0; i < 5; i++) {
			panels[i] = new JPanel();
		}
		//taille
		ListeGroupe.setPreferredSize(new Dimension(300, 50));
		ok.setPreferredSize(new Dimension(200, 50));
		annuler.setPreferredSize(new Dimension(200, 50));
		//actionListener
		ok.addActionListener(suppressionGroupeController);
		annuler.addActionListener(suppressionGroupeController);
		ListeGroupe.addActionListener(suppressionGroupeController);
		// layout
		panels[0].setLayout(new GridLayout(3, 1));
		panels[1].setLayout(new FlowLayout());
		panels[2].setLayout(new FlowLayout());
		panels[3].setLayout(new BorderLayout());
		// ajout
		panels[4].add(titrePanel);
		panels[1].add(titreComboBox);
		panels[1].add(ListeGroupe);
		panels[2].add(ok);
		panels[2].add(annuler);
		panels[0].add(panels[4]);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[3].add(panels[0], BorderLayout.NORTH);
		
		add(panels[3], BorderLayout.NORTH);
		
		


	}


	@Override
	public void update(java.util.Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}