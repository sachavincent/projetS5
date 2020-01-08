package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.VueAjoutUtilisateur;
import view.VueModificationGroupe;
import view.VueSuppressionGroupe;
import view.VueSuppressionUtilisateur;
import view.VueSuppressionUtilisateurGroupe;

public class FenetreServeurController implements ActionListener {

	public FenetreServeurController() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			String nom = button.getName();
			switch (nom) {
			case "Ajouter un utilisateur":
				new VueAjoutUtilisateur();
				break;
			case "Modifier un utilisateur":

				break;
			case "Supprimer un utilisateur":
				new VueSuppressionUtilisateur();
				break;
			case "Ajouter un groupe":

				break;
			case "Modifier un groupe":
				new VueModificationGroupe();
				break;
			case "Supprimer un groupe":
				new VueSuppressionGroupe();
				break;
			case "Ajouter un utilisateur � un groupe":

				break;
			case "Supprimer un utilisateur d'un groupe":
				new VueSuppressionUtilisateurGroupe();
				break;
			default:
				;
			}

		}

	}

}
