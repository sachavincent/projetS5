package model;

import static main.Main.DELIMITER;

import java.util.Observable;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupe == null) ? 0 : groupe.hashCode());
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssociationGroupeUtilisateur other = (AssociationGroupeUtilisateur) obj;
		if (groupe == null) {
			if (other.groupe != null)
				return false;
		} else if (!groupe.equals(other.groupe))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}

}