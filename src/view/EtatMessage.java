package view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.AssociationMessageUtilisateur;
import model.GroupeUtilisateurs;
import model.Utilisateur;

public class EtatMessage extends JFrame {
	JPanel pf = new JPanel();
	GroupeUtilisateurs groupe;// = new GroupeUtilisateurs(null, "test");
	Utilisateur[] dansLeGroupe;// = new Utilisateur[];
	//AssociationMessageUtilisateur uXgrp = new AssociationMessageUtilisateur(message, utilisateur, etat)
	public EtatMessage()  {
		//TODO
		//Recup liste Utilisateur dans le groupe
		//Association Message Utilisateur pour savoir qui à lu ou non le messages
		// affichage dans une fenetre du type : Utilisateur X = "Message Lu/Non Lu"
		pf.setLayout(new GridLayout(3,1));
		
		
		
		for (Utilisateur utilisateur : dansLeGroupe) {
			//affichage
			JLabel nom = new JLabel((utilisateur.getNom()));
			JLabel deuxPoint = new JLabel((":"));
			JLabel EtatM = new JLabel((utilisateur.getNom()));
			pf.add(nom);
			pf.add(deuxPoint);
			pf.add(EtatM);
			
			
		}
	}

}
