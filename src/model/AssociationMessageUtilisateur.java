package model;

import java.util.Observable;

/**
 * Classe permettant l'association d'un utilisateur et d'un message. Cette
 * classe est caract�ris� par l'id de l'utilisateur, l'id du message, et l'�tat
 * du message.
 */
public class AssociationMessageUtilisateur extends Observable {

	// Id du message
	private int idMessage;

	// Id de l'utilisateur
	private int idUtilisateur;

	// Etat du message
	private EtatMessage etat;

	// Enum�ration donnant l'�tat d'un message
	public enum EtatMessage {
		EN_ATTENTE, NON_LU, LU
	}
}
