package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.SuppressionUtilisateurGroupeController;
import database.DBConnection;

// Interface servant à la suppression d'un groupe
// Elle affiche la liste de tous les groupes puis après la sélection du groupe à supprimer, affiche une fenêtre de confirmation.
public class VueSuppressionUtilisateurGroupe extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JButton okButton = new JButton("OK");

	private JLabel listeGLabel = new JLabel("Liste des groupes");
	private JLabel listeULabel = new JLabel("Liste des utilisateurs");

	private JComboBox<String> groupesComboBox;
	private JComboBox<String> utilisateursComboBox;

	private JPanel[] panels = new JPanel[5];

	private Dimension comboBoxDimension = new Dimension(300, 50);
	
	public VueSuppressionUtilisateurGroupe() {
		// init
		okButton.setPreferredSize(new Dimension(300, 50));
		groupesComboBox = new JComboBox<String>(
				DBConnection.getInstance().getListeGroupes().stream().map(g -> g.getNom()).toArray(String[]::new));

		utilisateursComboBox = new JComboBox<String>(
				DBConnection.getInstance().getListeUtilisateurs().stream().map(g -> g.getNom()).toArray(String[]::new));

		for (int i = 0; i < 5; i++)
			panels[i] = new JPanel();

		// taille
		groupesComboBox.setPreferredSize(comboBoxDimension);
		utilisateursComboBox.setPreferredSize(comboBoxDimension);

		// actionListener
		SuppressionUtilisateurGroupeController suppressionUtilisateurGroupeController = new SuppressionUtilisateurGroupeController();
		okButton.addActionListener(suppressionUtilisateurGroupeController);
		groupesComboBox.addActionListener(suppressionUtilisateurGroupeController);
		utilisateursComboBox.addActionListener(suppressionUtilisateurGroupeController);

		// layout
		panels[0].setLayout(new GridLayout(3, 1));
		for (int i = 1; i < 4; i++)
			panels[1].setLayout(new FlowLayout());

		panels[4].setLayout(new BorderLayout());

		// ajout
		panels[1].add(listeULabel);
		panels[1].add(utilisateursComboBox);
		panels[2].add(listeGLabel);
		panels[2].add(groupesComboBox);
		panels[3].add(okButton);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[0].add(panels[3]);
		panels[4].add(panels[0], BorderLayout.NORTH);

		add(panels[4], BorderLayout.NORTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
