package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.client.CreationTicketController;
import database.DBConnection;
import model.Utilisateur.TypeUtilisateur;

public class TestVue extends JPanel{
	private static final long serialVersionUID = 1L;

	private JTextField titreField = new JTextField(20);

	private JComboBox<String> groupesComboBox = new JComboBox<>(new String[] { "Choisir un groupe" });

	private JLabel titreLabel = new JLabel("Titre du ticket");
	private JLabel titreVue = new JLabel("Création d'un ticket");
	private JPanel[] panels = new JPanel[7];

	private JButton okButton = new JButton("OK");
	private JButton annulerButton = new JButton("Annuler");
	
	public TestVue(TypeUtilisateur typeUtilisateur)  {
		for (int i = 0; i < 7; i++)
			panels[i] = new JPanel();
			

		DBConnection.getInstance().getListeGroupes().stream().filter(groupe -> {
			return DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().stream()
					.filter(agu -> agu.getGroupe().equals(groupe))
					.allMatch(agu -> agu.getUtilisateur().getType() == typeUtilisateur);
		}).forEach(g -> groupesComboBox.addItem(g.getNom()));

		// layout pour les panels
		panels[0].setLayout(new GridLayout(2, 1));
		
		CreationTicketController controller = new CreationTicketController(titreField, groupesComboBox);

		okButton.addActionListener(controller);
		annulerButton.addActionListener(controller);
		groupesComboBox.addActionListener(controller);

		// ajout au panel
		panels[1].setLayout(new FlowLayout());
		panels[0].setLayout(new FlowLayout());
		panels[2].setLayout(new FlowLayout());
		panels[4].setLayout(new FlowLayout());
		panels[4].add(titreVue);
		
		panels[1].add(titreLabel);
		panels[1].add(titreField);
		
		panels[0].add(groupesComboBox);
		
		panels[2].add(annulerButton);
		panels[2].add(okButton);
		panels[3].setLayout(new GridLayout(4,1));
		panels[3].add(panels[4]);
		panels[3].add(panels[0]);
		panels[3].add(panels[1]);
		panels[3].add(panels[2]);
		
		
//		panels[1].add(annulerButton);
//		panels[1].add(okButton);
		
		JPanel pf = new JPanel();
		pf.setLayout(new BorderLayout());
		pf.add(panels[3],BorderLayout.NORTH);
		add(pf,BorderLayout.NORTH);
		
		
	}

}
