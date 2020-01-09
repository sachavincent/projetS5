package model;

import static main.Main.*;
import java.util.Observable;

/**
 * Classe permettant l'association d'un utilisateur et d'un message
 */
public class AssociationMessageUtilisateur extends Observable {

	// Message
	private Message message;

	// Utilisateur
	private Utilisateur utilisateur;

	// Etat du message
	private EtatMessage etat;

	public AssociationMessageUtilisateur(Message message, Utilisateur utilisateur, String etat) {
		this.message = message;
		this.utilisateur = utilisateur;

		switch (etat) {
		case "EN ATTENTE":
			this.etat = EtatMessage.EN_ATTENTE;

			break;
		case "NON_LU":
			this.etat = EtatMessage.NON_LU;

			break;
		case "LU":
			this.etat = EtatMessage.LU;

			break;
		}
	}

	public AssociationMessageUtilisateur(Message message, Utilisateur utilisateur, EtatMessage etat) {
		this.message = message;
		this.utilisateur = utilisateur;
		this.etat = etat;
	}

	// Enumération donnant l'état d'un message
	public enum EtatMessage {
		EN_ATTENTE, NON_LU, LU;
	}

	/**
	 * @return le message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @return l'utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @return l'état
	 */
	public EtatMessage getEtat() {
		return etat;
	}

	/**
	 * @param l'état du message
	 */
	public void setEtat(EtatMessage etat) {
		this.etat = etat;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		builder.append(DELIMITER);
		builder.append(DELIMITER);
		builder.append(utilisateur);
		builder.append(DELIMITER);
		builder.append(DELIMITER);
		builder.append(etat);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		AssociationMessageUtilisateur other = (AssociationMessageUtilisateur) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}

	public void setModelChanged() {
		setChanged();
	}
	
}