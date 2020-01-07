package model;

import static main.Main.DELIMITER;

import java.util.Observable;

public class GroupeUtilisateurs extends Observable {

	// Id du groupe
	private int idGroupe;

	// Nom du groupe
	private String nom;

	public GroupeUtilisateurs(int idGroupe, String nom) {
		this.idGroupe = idGroupe;
		this.nom = nom;
	}

	/**
	 * @return le nom du groupe
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return l'id du groupe
	 */
	public int getIdGroupe() {
		return idGroupe;
	}

	/**
	 * @param nom le nom du groupe
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(idGroupe);
		builder.append(DELIMITER);
		builder.append(nom);

		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGroupe;
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
		GroupeUtilisateurs other = (GroupeUtilisateurs) obj;

		return idGroupe == other.idGroupe;
	}

}
