package communication;

import static main.Main.DELIMITER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import model.AssociationMessageUtilisateur;
import model.GroupeUtilisateurs;
import model.Message;
import model.Ticket;
import model.Utilisateur;

public class Client {

	private static Client client;

	private static Utilisateur utilisateur;

	private PrintWriter pw;
	private BufferedReader br;

	public Client(PrintWriter pw, BufferedReader br) {
		this.pw = pw;
		this.br = br;
	}

	/**
	 * Connecter un utilisateur
	 * 
	 * @param identifiant
	 * @param password
	 * @return
	 */
	public boolean connect(String identifiant, String password) {
		pw.println("connexion");
		pw.println(identifiant + DELIMITER + password);

		try {
			// Attente du booléen de confirmation
			while (!br.ready()) {
			}
			String message = br.readLine();

			// Attente de l'utilisateur
			while (!br.ready()) {
			}
			Client.setUtilisateur(getUtilisateur(br.readLine()));
			Client.getUtilisateur().setConnecte(true);
			return Boolean.parseBoolean(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Déconnecter l'utilisateur
	 */
	public void disconnect() {
		pw.println("deconnexion");
		pw.println(utilisateur.getIdentifiant());
	}

	public static void setUtilisateur(Utilisateur utilisateur) {
		Client.utilisateur = utilisateur;
	}

	public static Utilisateur getUtilisateur() {
		return utilisateur;
	}

	private Utilisateur getUtilisateur(String line) {
		String[] parametres = line.split(DELIMITER);
		String identifiant = parametres[0];
		String password = parametres[1];
		String nom = parametres[2];
		String prenom = parametres[3];
		String type = parametres[4];
		boolean connecte = Boolean.parseBoolean(parametres[5]);

		return new Utilisateur(identifiant, password, nom, prenom, type, connecte);
	}

	private Message getMessage(String line) {
		String[] parametres = line.split(DELIMITER);
		int idMessage = Integer.parseInt(parametres[0]);
		String contenu = parametres[1];
		Date date = null;
		if (!parametres[2].equals("null"))
			date = Date.valueOf(parametres[2]);

		return new Message(idMessage, contenu, date);
	}

	private GroupeUtilisateurs getGroupe(String line) {
		String[] parametres = line.split(DELIMITER);
		int idGroupe = Integer.parseInt(parametres[0]);
		String nom = parametres[1];

		return new GroupeUtilisateurs(idGroupe, nom);
	}

	private Ticket getTicket(String line) {
		String[] parametres = line.split(DELIMITER);
		int idTicket = Integer.parseInt(parametres[0]);
		String titre = parametres[1];
		Date date = null;
		if (!parametres[2].equals("null"))
			date = Date.valueOf(parametres[2]);

		String groupe = parametres[3] + DELIMITER + parametres[4];
		GroupeUtilisateurs groupeUtilisateurs = getGroupe(groupe);
		return new Ticket(idTicket, titre, date, groupeUtilisateurs);
	}

	private AssociationMessageUtilisateur getAMU(String message, String utilisateur, String etat) {
		return new AssociationMessageUtilisateur(getMessage(message), getUtilisateur(utilisateur), etat);
	}

//	private void format(String s) {
//		String[] content = s.split("\t\0\0\t");
//		int nombre = 0;
//
//		int i = 0;
//
//		do {
//			String line = content[i];
//			if (line.isEmpty()) {
//				i++;
//				continue;
//			}
//			String[] split = line.split(":");
//			nombre = Integer.parseInt(split[1]);
//			switch (split[0]) {
//			case "Utilisateurs":
//				while (nombre > 0) {
//					String u = content[++i].substring(1);
//					Utilisateur utilisateur = getUtilisateur(u);
//					split = content[++i].substring(2).split(":");
//					int nbMessages = Integer.parseInt(split[1]);
//					while (nbMessages > 0) {
//						String m = content[++i].substring(3);
//						Message message = getMessage(m);
//						utilisateur.getMessages().add(message);
//						nbMessages--;
//					}
//
//					split = content[++i].substring(2).split(":");
//					int nbTickets = Integer.parseInt(split[1]);
//					while (nbTickets > 0) {
//						String t = content[++i].substring(3);
//						Ticket ticket = getTicket(t);
//						utilisateur.getTickets().add(ticket);
//						nbTickets--;
//					}
//
//					DBConnection.getInstance().getListeUtilisateurs().add(utilisateur);
//					nombre--;
//					i++;
//				}
//				break;
//			case "Groupes":
//				while (nombre > 0) {
//					String g = content[++i].substring(1);
//					GroupeUtilisateurs groupe = getGroupe(g);
//					split = content[++i].substring(2).split(":");
//					int nbUtilisateurs = Integer.parseInt(split[1]);
//					while (nbUtilisateurs > 0) {
//						String u = content[++i].substring(3);
//						Utilisateur utilisateur = getUtilisateur(u);
//						groupe.getUtilisateurs().add(utilisateur);
//						nbUtilisateurs--;
//					}
//
//					DBConnection.getInstance().getListeGroupes().add(groupe);
//					nombre--;
//					i++;
//				}
//				break;
//			case "Tickets":
//				while (nombre > 0) {
//					String t = content[++i].substring(1);
//					Ticket ticket = getTicket(t);
//					split = content[++i].substring(2).split(":");
//					int nbMessages = Integer.parseInt(split[1]);
//					while (nbMessages > 0) {
//						String m = content[++i].substring(3);
//						Message message = getMessage(m);
//						ticket.getMessages().add(message);
//						nbMessages--;
//					}
//
//					DBConnection.getInstance().getListeTickets().add(ticket);
//					nombre--;
//					i++;
//				}
//				break;
//			case "Messages":
//				while (nombre > 0) {
//					String m = content[++i].substring(1);
//					Message message = getMessage(m);
//
//					DBConnection.getInstance().getListeMessages().add(message);
//					nombre--;
//					i++;
//				}
//				break;
//			case "AMU":
//				while (nombre > 0) {
//					String amu = content[++i].substring(1);
//					split = amu.split(DELIMITER + DELIMITER);
//					AssociationGroupeUtilisateur asso = getAMU(split[0], split[1], split[2]);
//
//					DBConnection.getInstance().getListeAssociationsMessageUtilisateur().add(asso);
//					nombre--;
//					i++;
//				}
//				break;
//			}
//
//			i++;
//		} while (i < content.length);
//	}

	/**
	 * Récupère le client où en crée un nouveau à l'aide d'un socket
	 * 
	 * @return le client
	 */
	public static Client getClient() {
		return client == null ? client = TCPCommunication.openClientSocket() : client;
	}

}
