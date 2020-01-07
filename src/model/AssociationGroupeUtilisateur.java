package model;

import static main.Main.DELIMITER;

import java.util.Observable;

import com.sun.istack.internal.NotNull;

/**
 * Classe permettant l'association d'un utilisateur et d'un groupe
 */
public class AssociationGroupeUtilisateur extends Observable {

	// Groupe
	private GroupeUtilisateurs groupe;

	// Utilisateur
	private Utilisateur utilisateur;

	public AssociationGroupeUtilisateur(GroupeUtilisateurs groupe, Utilisateur utilisateur) {
		this.groupe = groupe;
		this.utilisateur = utilisateur;
	}
	
	/**
	 * @return le groupe
	 */
	public GroupeUtilisateurs getGroupe() {
		return groupe;
	}

	/**
	 * @return l'utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(groupe);
		builder.append(DELIMITER);
		builder.append(DELIMITER);
		builder.append(utilisateur);
		return builder.toString();
	}

}