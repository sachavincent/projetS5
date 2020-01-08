package model;

import static main.Main.*;
import java.util.Date;
import java.util.Observable;

public class Message extends Observable {

	// Id du message
	private int idMessage;

	// Contenu du message
	private String contenu;

	// Date d'envoi du message;
	private Date date;

	public Message(int idMessage, String contenu, Date date) {
		this.idMessage = idMessage;
		this.contenu = contenu;
		this.date = date;
	}

	/**
	 * @return le contenu du message
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * @param contenu le contenu du message
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * @return l'id du message
	 */
	public int getIdMessage() {
		return idMessage;
	}

	/**
	 * @return la date d'envoi du message
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return le hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime * idMessage;

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
		Message other = (Message) obj;

		return idMessage == other.idMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(idMessage);
		builder.append(DELIMITER);
		builder.append(contenu);
		builder.append(DELIMITER);
		builder.append(date);
		return builder.toString();
	}

}
