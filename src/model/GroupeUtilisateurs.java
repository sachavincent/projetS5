package model;

import static main.Main.*;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import com.sun.istack.internal.NotNull;

public class GroupeUtilisateurs extends Observable {

	// Id du groupe
	private int idGroupe;

	// Nom du groupe
	private String nom;

	// Liste des utilisateurs du groupe
	private Set<Utilisateur> listeUtilisateurs = new HashSet<>();

	public GroupeUtilisateurs(int idGroupe, String nom) {
		this.idGroupe = idGroupe;
		this.nom = nom;
	}

	/**
	 * Permet d'ajouter un utilisateur � un groupe
	 * 
	 * @param utilisateur l'utilisateur � ajouter
	 * @return true si l'utilisateur a bien �t� ajout�
	 */
	public boolean ajouterUtilisateur(@NotNull Utilisateur utilisateur) {
		return false;
		// TODO Voir diagramme de s�quence
	}

	/**
	 * Permet de supprimer un utilisateur d'n groupe
	 * 
	 * @param utilisateur l'utilisateur � supprimer
	 * @return true si l'utilisateur a bien �t� supprim�
	 */
	public boolean supprimerUtilisateur(@NotNull Utilisateur utilisateur) {
		return false;
		// TODO Voir diagramme de s�quence
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
	 * @return la liste des utilisateurs
	 */
	public Set<Utilisateur> getUtilisateurs() {
		return listeUtilisateurs;
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

}
