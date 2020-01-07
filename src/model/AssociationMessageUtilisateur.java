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
		EN_ATTENTE("EN ATTENTE"), NON_LU("NON LU"), LU("LU");

		EtatMessage(String s) {
			this.name = s;
		}

		private String name;

		public String getName() {
			return name;
		}

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

}