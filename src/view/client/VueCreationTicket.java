package view.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.client.ConnexionController;
import database.DBConnection;
import model.Utilisateur.TypeUtilisateur;

// Interface permettant de se connecter à l'application et au serveur
public class VueCreationTicket extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private JTextField titreField = new JTextField(30);

	private JComboBox<String> groupesComboBox = new JComboBox<>(new String[] { "Choisir un groupe" });

	private JPanel[] panels = new JPanel[7];

	public VueCreationTicket(TypeUtilisateur typeUtilisateur) {
		// init
		for (int i = 0; i < 7; i++)
			panels[i] = new JPanel();

		DBConnection.getInstance().getListeGroupes().stream().filter(groupe -> {
			return DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().stream()
					.filter(g -> g.equals(groupe)).allMatch(agu -> agu.getUtilisateur().getType() == typeUtilisateur);
		}).forEach(g -> groupesComboBox.addItem(g.getNom()));

		// layout pour les panels
		panels[0].setLayout(new GridLayout(2, 1));
		panels[1].setLayout(new FlowLayout());
		panels[2].setLayout(new FlowLayout());
		panels[3].setLayout(new FlowLayout());
		panels[4].setLayout(new FlowLayout());
		panels[5].setLayout(new BorderLayout());
		panels[6].setLayout(new BorderLayout());

		// ajout au panel
		panels[1].add(titreField);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[3].add(panels[0]);

		panels[5].add(panels[3], BorderLayout.NORTH);
		panels[5].add(panels[4], BorderLayout.SOUTH);

		panels[6].add(panels[5], BorderLayout.NORTH);

		add(panels[6]);
	}

	@Override
	public void update(Observable o, Object arg) {
	}

}
