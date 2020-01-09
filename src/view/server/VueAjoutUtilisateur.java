package view.server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.server.AjoutUtilisateurController;
import model.Utilisateur.TypeUtilisateur;

//Interface permettant de créer un nouvel utilisateur.
public class VueAjoutUtilisateur extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private final static int TAILLE_TEXT_FIELDS = 30;

	private JTextField[] fields = new JTextField[4];

	private JComboBox<String> typeUtilisateurComboBox = new JComboBox<>();

	private String[] nomsLabels = new String[] { "Identifiant", "Mot de passe", "Nom", "Prenom", "Type Utilisateur" };
	private JLabel[] labels = new JLabel[5];
	private JLabel titre = new JLabel("Ajout d'un Utilisateur", SwingConstants.CENTER);
	private JButton okButton = new JButton("Création");
	private JButton annulerButton = new JButton("Annuler");

	private JPanel[] panels = new JPanel[9];

	public VueAjoutUtilisateur() {
		// init
		for (int i = 0; i < 9; i++)
			panels[i] = new JPanel();

		for (int i = 0; i < 5; i++)
			labels[i] = new JLabel(nomsLabels[i]);

		for (int i = 0; i < 4; i++)
			fields[i] = new JTextField(TAILLE_TEXT_FIELDS);

		okButton.setEnabled(false);

		// action listeners
		AjoutUtilisateurController ajoutUtilisateurController = new AjoutUtilisateurController(fields[0], fields[1],
				fields[2], fields[3], okButton);

		typeUtilisateurComboBox.addItem("Choisir un type d'utilisateur");

		((Stream<TypeUtilisateur>) Arrays.stream(TypeUtilisateur.values()))
				.map(type -> type.toString().replace('_', ' ')).forEach(type -> typeUtilisateurComboBox.addItem(type));

		typeUtilisateurComboBox.addActionListener(ajoutUtilisateurController);
		okButton.addActionListener(ajoutUtilisateurController);
		annulerButton.addActionListener(ajoutUtilisateurController);

		// layout
		panels[0].setLayout(new GridLayout(3, 1));

		for (int i = 1; i < 6; i++)
			panels[1].setLayout(new FlowLayout());

		// ajout
		// panels[1].add(titre);
		panels[1].add(labels[0]);
		panels[1].add(fields[0]);
		panels[2].add(labels[1]);
		panels[2].add(fields[1]);
		panels[3].add(labels[2]);
		panels[3].add(fields[2]);
		panels[4].add(labels[3]);
		panels[4].add(fields[3]);
		panels[5].add(labels[4]);
		panels[5].add(typeUtilisateurComboBox);

		for (int i = 1; i < 6; i++)
			panels[0].add(panels[i]);

		panels[7].add(panels[0]);
		panels[8].add(annulerButton);
		panels[8].add(okButton);

		panels[6].setLayout(new BorderLayout());
		panels[6].add(panels[7], BorderLayout.NORTH);
		panels[6].add(panels[8], BorderLayout.SOUTH);

		setTitle("NeoCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panels[6], BorderLayout.NORTH);
		pack();
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}

}
