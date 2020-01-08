package model;

import static main.Main.DELIMITER;

import java.util.Date;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

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
	private Set<Message> listeMessages = new HashSet<>();

	public Ticket(int idTicket, String titre, Date dateCreation, GroupeUtilisateurs groupeDestination) {
		this.idTicket = idTicket;
		this.titre = titre;
		this.dateCreation = dateCreation;
		this.groupeDestination = groupeDestination;
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
		result = prime * result + idTicket;
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
		Ticket other = (Ticket) obj;
		return other.getIdTicket() == idTicket;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(idTicket);
		builder.append(DELIMITER);
		builder.append(titre);
		builder.append(DELIMITER);
		builder.append(dateCreation);
		builder.append(DELIMITER);
		builder.append(groupeDestination);
		return builder.toString();
	}

}
