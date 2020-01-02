package model;

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

	// Enum�ration donnant le type d'un utilisateur
	public enum TypeUtilisateur {
		ETUDIANT, ENSEIGNANT, SECRETAIRE_PEDAGOGIQUE, SERVICE_ADMINISTRATIF, SERVICE_TECHNIQUE
	}

	/**
	 * @return l'identifiant de l'utilisateur
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * @return le mot de passe de l'utilisateur
	 */
	public String getPassword() {
		return password;
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
	 * @param nom
	 *            le(s) nom(s) de l'utilisateur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param prenom
	 *            le pr�nom de l'utilisateur
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @param connecte
	 *            si l'utilisateur est connect�
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
	 * @param obj
	 *            objet � comparer
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

}
