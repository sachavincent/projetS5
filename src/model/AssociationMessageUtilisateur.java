package model;

import static main.Main.*;
import java.util.Observable;

/**
 * Classe permettant l'association d'un utilisateur et d'un message. Cette
 * classe est caractérisé par l'id de l'utilisateur, l'id du message, et l'état
 * du message.
 */
public class AssociationMessageUtilisateur extends Observable {

	// Version 1 du diagramme de classe : les idMessage et idUtilisateur
	// Version 2 suggérée : Directement les objets pour pas s'emmerder
	// TODO: Réfléchir dessus

	// Message
	private Message message;

	// Utilisateur
	private Utilisateur utilisateur;

	// Etat du message
	private EtatMessage etat;

	// Enumération donnant l'état d'un message
	public enum EtatMessage {
		EN_ATTENTE, NON_LU, LU
	}

	public AssociationMessageUtilisateur(Message message, Utilisateur utilisateur, String etat) {
		this.message = message;
		this.utilisateur = utilisateur;
		
		switch(etat) {
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