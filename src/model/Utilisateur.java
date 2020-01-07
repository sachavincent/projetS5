package model;

import static main.Main.*;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class Utilisateur extends Observable {

	// Identifiant unique
	private String identifiant;

	// Mot de passe encod�
	private String password;

	// Nom(s) de famille de l'utilisateur
	private String nom;

	// Pr�nom de l'utilisateur
	private String prenom;

	// Type d'utilisateur
	private TypeUtilisateur type;

	// Bool�en indiquant si l'utilisateur est connect�
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
		case "SERVICE ADMINISTRATIF":
			this.type = TypeUtilisateur.SERVICE_ADMINISTRATIF;
			break;
		case "SERVICE TECHNIQUE":
			this.type = TypeUtilisateur.SERVICE_TECHNIQUE;
			break;
		case "SECRETAIRE PEDAGOGIQUE":
			this.type = TypeUtilisateur.SECRETAIRE_PEDAGOGIQUE;
			break;
		default:
			throw new IllegalArgumentException("TypeUtilisateur invalide");
		}

		this.connecte = connecte;
	}

	// Enum�ration donnant le type d'un utilisateur
	public enum TypeUtilisateur {
		ETUDIANT, ENSEIGNANT, SECRETAIRE_PEDAGOGIQUE, SERVICE_ADMINISTRATIF, SERVICE_TECHNIQUE
	}

	/**
	 * Permet de v�rifier si le mot de passe donn� est le m�me que celui de
	 * l'utilisateur
	 * 
	 * @param password mot de passe � v�rifier
	 * @return true si les deux mots de passe sont similaires
	 */
	public boolean memeMotDePasse(String password) {
		return this.password.equalsIgnoreCase(password);
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
	 * @return le pr�nom de l'utilisateur
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
	 * @return si l'utilisateur est connect�
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
	 * @param prenom le pr�nom de l'utilisateur
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @param connecte si l'utilisateur est connect�
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
		result = prime * result + (connecte ? 1231 : 1237);
		result = prime * result + ((identifiant == null) ? 0 : identifiant.hashCode());
		result = prime * result + ((listeMessages == null) ? 0 : listeMessages.hashCode());
		result = prime * result + ((listeTickets == null) ? 0 : listeTickets.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());

		return result;
	}

	/**
	 * @param obj objet � comparer
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
		if (connecte != other.connecte)
			return false;
		if (identifiant == null) {
			if (other.identifiant != null)
				return false;
		} else if (!identifiant.equals(other.identifiant))
			return false;
		if (listeMessages == null) {
			if (other.listeMessages != null)
				return false;
		} else if (!listeMessages.equals(other.listeMessages))
			return false;
		if (listeTickets == null) {
			if (other.listeTickets != null)
				return false;
		} else if (!listeTickets.equals(other.listeTickets))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (type != other.type)
			return false;
		return true;
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

}
