package view.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.client.ConnexionController;

// Interface permettant de se connecter à l'application et au serveur
public class VueConnexion extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JTextField identField = new JTextField(30);
	private JPasswordField passwordField = new JPasswordField(30);

	private JLabel identifiantLabel = new JLabel("Identifiant");
	private JLabel passwordLabel = new JLabel("Mot de passe");

	private JButton buttonConnexion = new JButton("Connexion");

	private JPanel[] panels = new JPanel[7];

	public VueConnexion() {
		// init
		for (int i = 0; i < 7; i++)
			panels[i] = new JPanel();

		// layout pour les panels
		panels[0].setLayout(new GridLayout(2, 1));
		panels[1].setLayout(new FlowLayout());
		panels[2].setLayout(new FlowLayout());
		panels[3].setLayout(new FlowLayout());
		panels[4].setLayout(new FlowLayout());
		panels[5].setLayout(new BorderLayout());
		panels[6].setLayout(new BorderLayout());

		// ajout au panel
		panels[1].add(identifiantLabel);
		panels[1].add(identField);
		panels[2].add(passwordLabel);
		panels[2].add(passwordField);
		panels[0].add(panels[1]);
		panels[0].add(panels[2]);
		panels[3].add(panels[0]);
		panels[4].add(buttonConnexion);

		panels[5].add(panels[3], BorderLayout.NORTH);
		panels[5].add(panels[4], BorderLayout.SOUTH);

		panels[6].add(panels[5], BorderLayout.NORTH);

		// TODO Remove temp
		identField.setText("sacha.vincent");
		passwordField.setText("password");

		buttonConnexion.addActionListener(new ConnexionController(identField, passwordField));

		add(panels[6]);
	}

	@Override
	public void update(Observable o, Object arg) {
	}

}
