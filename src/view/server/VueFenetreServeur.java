package view.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.server.FenetreServeurController;

// Interface Serveur avec toutes les options auquel il a accés 

public class VueFenetreServeur extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private String[] nomsButtons = new String[] { "Ajouter un utilisateur", "Modifier un utilisateur",
			"Supprimer un utilisateur", "Ajouter un groupe", "Modifier un groupe", "Supprimer un groupe",
			"Ajouter un utilisateur à un groupe", "Supprimer un utilisateur d'un groupe" };

	private JButton[] buttons = new JButton[8];
	private JPanel[] panels = new JPanel[7];

	private Dimension buttonsDimension = new Dimension(300, 50);

	public VueFenetreServeur() {
		JPanel panel = new JPanel();
		// paramétrage du layout
		GridLayout g = new GridLayout(3, 2);
		g.setVgap(50);
		g.setHgap(50);

		panel.setLayout(g);

		GridBagConstraints t = new GridBagConstraints();
		t.anchor = GridBagConstraints.CENTER;
		t.fill = GridBagConstraints.HORIZONTAL;
		t.gridwidth = GridBagConstraints.REMAINDER;
		t.insets = new Insets(50, 0, 0, 0);
		FenetreServeurController fc = new FenetreServeurController();
		for (int i = 0; i < 8; i++) {
			buttons[i] = new JButton(nomsButtons[i]);

			if (i < 7) {
				panels[i] = new JPanel();
				panels[i].setLayout(new GridBagLayout());
			}

			// taille boutons
			buttons[i].setPreferredSize(buttonsDimension);

			// ActionListener
			
			buttons[i].addActionListener(fc);

			if (i < 6)
				panels[i].add(buttons[i]);
			else if (i == 7) {
				panels[i - 1].add(buttons[6], t);
				panels[i - 1].add(buttons[7], t);
			}

			if (i < 6)
				panel.add(panels[i]);
		}

		setLayout(new BorderLayout());

		add(panel, BorderLayout.NORTH);
		add(panels[6], BorderLayout.CENTER);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
