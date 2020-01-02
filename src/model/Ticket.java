package model;

import java.util.Date;
import java.util.Observable;
import java.util.Set;

import com.sun.istack.internal.NotNull;

public class Ticket extends Observable {

	// Id unique du ticket
	private int idTicket;

	// Titre du ticket
	private String titre;

	// Date de création du ticket
	private Date dateCreation;

	// Groupe de destination du ticket
	private GroupeUtilisateurs groupeDestination;

	// Liste des messages de ce ticket
	private Set<Message> listeMessages;

	
	public Ticket(int idTicket, String titre, Date dateCreation, GroupeUtilisateurs groupeDestination) {
		this.idTicket = idTicket;
		this.titre = titre;
		this.dateCreation = dateCreation;
		this.groupeDestination = groupeDestination;
	}

	/**
	 * Permet d'envoyer un message dans le ticket
	 * 
	 * @param message
	 *            le message à envoyer
	 * @return true si le message a bien été envoyé
	 */
	public boolean envoyerMessage(@NotNull Message message) {
		return false;
		// TODO: Voir diagramme de séquence
	}

	/**
	 * @return l'id du ticket
	 */
	public int getIdTicket() {
		return idTicket;
	}

	/**
	 * @return le titre du ticket
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @return la date de création du ticket
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @return le groupe de destination du ticket
	 */
	public GroupeUtilisateurs getGroupeDestination() {
		return groupeDestination;
	}

	/**
	 * @return la liste des messages tu ticket
	 */
	public Set<Message> getMessages() {
		return listeMessages;
	}

	/**
	 * @return le hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + idTicket;
		result = prime * result + ((listeMessages == null) ? 0 : listeMessages.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
		return result;
	}

	/**
	 * @param obj
	 *            objet à comparer
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
		Ticket other = (Ticket) obj;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (idTicket != other.idTicket)
			return false;
		if (listeMessages == null) {
			if (other.listeMessages != null)
				return false;
		} else if (!listeMessages.equals(other.listeMessages))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}

}
