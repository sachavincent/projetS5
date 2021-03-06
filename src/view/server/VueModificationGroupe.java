package view.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.server.ModificationGroupeController;
import database.DBConnection;

//Interface permettant la modification du nom d'un grouoe
public class VueModificationGroupe extends JFrame implements Observer {

	private JButton okButton = new JButton("OK");
	private JButton annulerButton = new JButton("Annuler");
	private JComboBox<String> groupesComboBox;

	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel titrePanel = new JLabel("Modification d'un groupe");
	private JLabel titreComboBox = new JLabel("liste des groupes");

	private JPanel[] panels = new JPanel[5];

	private Dimension buttonsDimension = new Dimension(200, 50);

	public VueModificationGroupe() {
		// init
		nameLabel = new JLabel("Nouveau nom: ");
		nameField = new JTextField(30);
		titrePanel.setFont(titrePanel.getFont().deriveFont(15f));

		ModificationGroupeController modificationGroupeController = new ModificationGroupeController(nameField,okButton);
		groupesComboBox = new JComboBox<String>(
				DBConnection.getInstance().getListeGroupes().stream().map(g -> g.getNom()).toArray(String[]::new));

		groupesComboBox.addActionListener(modificationGroupeController);
		okButton.setEnabled(false);
		for (int i = 0; i < 5; i++)
			panels[i] = new JPanel();

		okButton.addActionListener(modificationGroupeController);
		annulerButton.addActionListener(modificationGroupeController);
		// taille

		okButton.setPreferredSize(buttonsDimension);
		annulerButton.setPreferredSize(buttonsDimension);
		groupesComboBox.setPreferredSize(buttonsDimension);

		// layout
		panels[0].setLayout(new GridLayout(3, 1));
		for (int i = 1; i < 4; i++)
			panels[1].setLayout(new FlowLayout());

		setLayout(new BorderLayout());

		// ajout
		panels[4].add(titrePanel);
		panels[1].add(titreComboBox);
		panels[1].add(groupesComboBox);
		panels[2].add(nameLabel);
		panels[2].add(nameField);
		panels[3].add(annulerButton);
		panels[3].add(okButton);
		
		//panels[0].add(panels[4]);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[0].add(panels[3]);

		setTitle("NeoCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panels[0], BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);
		
	
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

}