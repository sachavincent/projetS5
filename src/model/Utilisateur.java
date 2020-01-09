package model;

import static main.Main.DELIMITER;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import com.sun.istack.internal.NotNull;

public class Utilisateur extends Observable {

	// Identifiant unique
	private String identifiant;

	// Mot de passe encodé
	private String password;

	// Nom(s) de famille de l'utilisateur
	private String nom;

	// Prénom de l'utilisateur
	private String prenom;

	// Type d'utilisateur
	private TypeUtilisateur type;

	// Booléen indiquant si l'utilisateur est connecté
	private boolean connecte;

	// Liste des tickets de l'utilisateur
	private Set<Ticket> listeTickets = new HashSet<>();

	// Liste des messages de l'utilisateur
	private Set<Message> listeMessages = new HashSet<>();

	public Utilisateur(String identifiant, String password, String nom, String prenom, String type, boolean connecte) {
		this.identifiant = identifiant;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;

		switch (type) {
		case "ETUDIANT":
			this.type = TypeUtilisateur.ETUDIANT;
			break;
		case "ENSEIGNANT":
			this.type = TypeUtilisateur.ENSEIGNANT;
			break;
		case "SERVICE_ADMINISTRATIF":
			this.type = TypeUtilisateur.SERVICE_ADMINISTRATIF;
			break;
		case "SERVICE_TECHNIQUE":
			this.type = TypeUtilisateur.SERVICE_TECHNIQUE;
			break;
		case "SECRETAIRE_PEDAGOGIQUE":
			this.type = TypeUtilisateur.SECRETAIRE_PEDAGOGIQUE;
			break;
		default:
			throw new IllegalArgumentException("TypeUtilisateur invalide : " + type);
		}

		this.connecte = connecte;
	}

	public Utilisateur(String identifiant, String password, String nom, String prenom, TypeUtilisateur type,
			boolean connecte) {
		this.identifiant = identifiant;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.type = type;
		this.connecte = connecte;
	}

	// Enumération donnant le type d'un utilisateur
	public enum TypeUtilisateur {
		ETUDIANT, ENSEIGNANT, SECRETAIRE_PEDAGOGIQUE, SERVICE_ADMINISTRATIF, SERVICE_TECHNIQUE
	}

	/**
	 * Permet de vérifier si le mot de passe donné est le même que celui de
	 * l'utilisateur
	 * 
	 * @param password mot de passe à vérifier
	 * @return true si les deux mots de passe sont similaires
	 */
	public boolean memeMotDePasse(String password) {
		if (this.password == null)
			return true;

		return this.password.equalsIgnoreCase(password);
	}

	/**
	 * Permet de modifier le mot de passe
	 * 
	 * @param ancienMDP  ancien mot de passe
	 * @param nouveauMDP nouveau mot de passe
	 * 
	 * @return true si le mot de passe a changé
	 */
	public boolean modifierMotDePasse(String ancienMDP, @NotNull String nouveauMDP) {
		if (memeMotDePasse(ancienMDP)) {
			password = nouveauMDP;

			return true;
		}

		return false;
	}

	/**
	 * @return l'identifiant de l'utilisateur
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * @return le(s) nom(s) de l'utilisateur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return le prénom de l'utilisateur
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @return le type de l'utilisateur
	 */
	public TypeUtilisateur getType() {
		return type;
	}

	/**
	 * @return si l'utilisateur est connecté
	 */
	public boolean isConnecte() {
		return connecte;
	}

	/**
	 * @return la liste des tickets
	 */
	public Set<Ticket> getTickets() {
		return listeTickets;
	}

	/**
	 * @return la liste des messages
	 */
	public Set<Message> getMessages() {
		return listeMessages;
	}

	/**
	 * @param nom le(s) nom(s) de l'utilisateur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param prenom le prénom de l'utilisateur
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @param connecte si l'utilisateur est connecté
	 */
	public void setConnecte(boolean connecte) {
		this.connecte = connecte;
	}

	/**
	 * @return le hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifiant == null) ? 0 : identifiant.hashCode());

		return result;
	}

	/**
	 * @param obj objet à comparer
	 * 
	 * @return true si les deux objets sont similaires
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;

		return identifiant.equalsIgnoreCase(other.getIdentifiant());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(identifiant);
		builder.append(DELIMITER);
		builder.append(password);
		builder.append(DELIMITER);
		builder.append(nom);
		builder.append(DELIMITER);
		builder.append(prenom);
		builder.append(DELIMITER);
		builder.append(type);
		builder.append(DELIMITER);
		builder.append(connecte);
		return builder.toString();
	}

	public void setModelChanged() {
		setChanged();
	}
	
}
