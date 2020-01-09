package communication;

import static main.Main.DELIMITER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import database.DBConnection;
import model.AssociationGroupeUtilisateur;
import model.AssociationMessageUtilisateur;
import model.AssociationMessageUtilisateur.EtatMessage;
import model.GroupeUtilisateurs;
import model.Message;
import model.Ticket;
import model.Utilisateur;

public class ClientThread extends Thread {

	private static ClientThread client;

	private static Utilisateur utilisateur;

	private PrintWriter pw;
	private BufferedReader br;

	// Booléen indiquant si le client peut attendre un message quelconque du serveur
	private boolean canReceive;

	private Requete requete;

	private ScheduledExecutorService executor;

	public ClientThread(PrintWriter pw, BufferedReader br) {
		this.pw = pw;
		this.br = br;

		this.requete = Requete.NONE;
	}

	enum Requete {
		MESSAGE, CONNEXION, NONE, CHANGEMENT_ETAT_MESSAGE;
	}

	@Override
	public void run() {
		executor = Executors.newScheduledThreadPool(10);
		executor.scheduleAtFixedRate(() -> {
			try {
				if (canReceive) {
					// Check l'envoi d'information par le serveur sans requête de la part du client
					if (br.ready()) {
						String line = br.readLine();

						switch (requete) {
						case NONE:

							switch (line) {
							case "MESSAGE":
								// Un message a été envoyé dans un ticket auquel cet utilisateur a accès

								requete = Requete.MESSAGE;
								break;
							case "CONNEXION":
								// Un utilisateur s'est connecté

								requete = Requete.CONNEXION;

								System.out.println("Connexion d'un utilisateur");
							}
							break;

						case MESSAGE:
							Message message = getMessage(line);

							DBConnection.getInstance().getListeMessages().add(message);

							while (!br.ready()) {
							}

							String[] split = line.split(DELIMITER);

							String identifiantU = split[0];
							int idTicket = Integer.parseInt(split[1]);

							Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
									.filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiantU)).findFirst()
									.orElse(null);

							if (utilisateur == null)
								return;

							Ticket ticket = DBConnection.getInstance().getListeTickets().stream()
									.filter(t -> t.getIdTicket() == idTicket).findFirst().orElse(null);

							if (ticket == null)
								return;

							DBConnection.getInstance().getListeTickets().add(ticket);

							DBConnection.getInstance().getListeAssociationsMessageUtilisateur()
									.add(new AssociationMessageUtilisateur(message, utilisateur, EtatMessage.LU));

							// Lecture des associations
							do {
								while (!br.ready()) {
								}

								line = br.readLine();

								// Ajout des associations
								DBConnection.getInstance().getListeAssociationsMessageUtilisateur().add(getAMU(line));

							} while (!line.equals(DELIMITER + DELIMITER + DELIMITER)); // TODO: Handle timeout

							requete = Requete.NONE;
							break;
						case CHANGEMENT_ETAT_MESSAGE:
							// TODO
							requete = Requete.NONE;
							break;
						case CONNEXION:
							Utilisateur util = getUtilisateur(line);
							DBConnection.getInstance().getListeUtilisateurs().stream().filter(u -> u.equals(util))
									.forEach(u -> u.setConnecte(true));

							DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().stream()
									.filter(agu -> agu.getUtilisateur().equals(util))
									.forEach(agu -> agu.getUtilisateur().setConnecte(true));

							// Les messages en attente passent en non lus
							// Envoi des messages
							DBConnection.getInstance().getListeAssociationsMessageUtilisateur().stream().filter(
									amu -> amu.getUtilisateur().equals(util) && amu.getEtat() == EtatMessage.EN_ATTENTE)
									.forEach(amu -> {
										amu.getUtilisateur().setConnecte(true);
										DBConnection.getInstance().changerEtatMessage(amu.getMessage(),
												amu.getUtilisateur(), amu.getEtat());
									});

							System.out.println("Utilisateur connecté : " + util);
							
							requete = Requete.NONE;
							break;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();

				TCPCommunication.closeClientSocket();

				this.interrupt();
			}
		}, 0, 333, TimeUnit.MILLISECONDS);
	}

	/**
	 * Connecter un utilisateur
	 * 
	 * @param identifiant
	 * @param password
	 * @return
	 */
	public boolean connect(String identifiant, String password) {
		canReceive = false;

		pw.println("connexion");
		pw.println(identifiant + DELIMITER + password);

		boolean res = false;

		try {
			// Attente du booléen de confirmation
			while (!br.ready()) {
			}

			String line = br.readLine();
			res = Boolean.parseBoolean(line);
			System.out.println("bool: " + res);

			if (!res)
				return false;

			// Attente de l'utilisateur
			while (!br.ready()) {
			}

			// L'utilisateur est trouvé
			// Connexion réussie

			line = br.readLine();
			ClientThread.setUtilisateur(getUtilisateur(line));
			// Réception des données
			while (!br.ready()) {
			}

			line = br.readLine();
			System.out.println("User: " + line);
			while (!line.equals(DELIMITER + DELIMITER + DELIMITER)) { // TODO: Handle timeout
				Utilisateur utilisateur = getUtilisateur(line);
				if (utilisateur.equals(ClientThread.getUtilisateur()))
					utilisateur.setConnecte(true);

				// Messages de l'utilisateur
				while (!br.ready()) {
				}

				line = br.readLine();
				while (!line.equals(DELIMITER + DELIMITER)) { // TODO: Handle timeout
					System.out.println("Message: " + line);
					Message message = getMessage(line);

					DBConnection.getInstance().getListeMessages().add(message);
					utilisateur.getMessages().add(message);

					while (!br.ready()) {
					}

					line = br.readLine();
				}

				while (!br.ready()) {
				}

				line = br.readLine();
				while (!line.equals(DELIMITER + DELIMITER)) { // TODO: Handle timeout
					System.out.println("Ticket: " + line);
					Ticket ticket = getTicket(line);
					GroupeUtilisateurs groupeUtilisateurs = ticket.getGroupeDestination();

					DBConnection.getInstance().getListeGroupes().add(groupeUtilisateurs);

					// Messages du ticket
					while (!br.ready()) {
					}

					line = br.readLine();
					while (!line.equals(DELIMITER + DELIMITER)) { // TODO: Handle timeout
						System.out.println("Message: " + line);

						Message message = getMessage(line);

						DBConnection.getInstance().getListeMessages().add(message);
						ticket.getMessages().add(message);

						while (!br.ready()) {
						}

						line = br.readLine();
					}

					utilisateur.getTickets().add(ticket);
					DBConnection.getInstance().getListeTickets().add(ticket);
					DBConnection.getInstance().getListeUtilisateurs().add(utilisateur);

					while (!br.ready()) {
					}

					line = br.readLine();
				}

				// Liste des groupes
				while (!br.ready()) {
				}

				line = br.readLine();
				while (!line.equals(DELIMITER + DELIMITER)) { // TODO: Handle timeout
					System.out.println("Groupe: " + line);
					GroupeUtilisateurs groupeUtilisateurs = getGroupe(line);

					DBConnection.getInstance().getListeGroupes().add(groupeUtilisateurs);

					while (!br.ready()) {
					}

					line = br.readLine();
				}

				// AssociationsMessageUtilisateur
				while (!br.ready()) {
				}

				line = br.readLine();
				while (!line.equals(DELIMITER + DELIMITER)) { // TODO: Handle timeout
					System.out.println("AMU: " + line);
					AssociationMessageUtilisateur amu = getAMU(line);

					DBConnection.getInstance().getListeAssociationsMessageUtilisateur().add(amu);

					while (!br.ready()) {
					}

					line = br.readLine();
				}

				// AssociationsGroupeUtilisateur
				while (!br.ready()) {
				}

				line = br.readLine();
				while (!line.equals(DELIMITER + DELIMITER)) { // TODO: Handle timeout
					System.out.println("AGU: " + line);
					AssociationGroupeUtilisateur agu = getAGU(line);

					DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().add(agu);

					while (!br.ready()) {
					}

					line = br.readLine();
				}
				while (!br.ready()) {
				}

				line = br.readLine();
			}

			ClientThread.getUtilisateur().setConnecte(true);

			System.out.println("Groupes: " + DBConnection.getInstance().getListeGroupes());
			System.out.println("Tickets: " + DBConnection.getInstance().getListeTickets());
			System.out.println("Users: " + DBConnection.getInstance().getListeUtilisateurs());
			System.out.println("Msg: " + DBConnection.getInstance().getListeMessages());
			System.out.println("AMU: " + DBConnection.getInstance().getListeAssociationsMessageUtilisateur());
			System.out.println("AGU: " + DBConnection.getInstance().getListeAssociationsGroupeUtilisateur());
		} catch (

		IOException e) {
			e.printStackTrace();
		} finally {
			canReceive = true;
		}

		return res;
	}

	/**
	 * Permet d'envoyer une demande de création de ticket au serveur
	 * 
	 * @param titre                  le titre du ticket
	 * @param identifiantUtilisateur l'identifiant de l'utilisateur qui fait la
	 *                               demande
	 * @param idGroupe               l'id du groupe qui reçoit le ticket
	 * @return le ticket crée
	 */
	public Ticket creerTicket(String titre, int idGroupe) {
		canReceive = false;

		pw.println("creerTicket");
		pw.println(titre + DELIMITER + ClientThread.getUtilisateur().getIdentifiant() + DELIMITER + idGroupe);

		try {
			// Attente du booléen de confirmation
			while (!br.ready()) {
			}
			String message = br.readLine();

			// Erreur
			if (message.equals("false"))
				return null;

			Ticket ticket = getTicket(message);

			DBConnection.getInstance().getListeTickets().add(ticket);

			return ticket;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			canReceive = true;
		}

		return null;
	}

	/**
	 * Permet d'envoyer une demande d'envoi de message au serveur
	 * 
	 * @param contenu  le contenu du message
	 * @param idTicket l'id du ticket dans lequel envoyer le message
	 * @return le message crée
	 */
	public Message envoyerMessage(String contenu, int idTicket) {
		canReceive = false;

		pw.println("envoyerMessage");
		pw.println(contenu + DELIMITER + ClientThread.getUtilisateur().getIdentifiant() + DELIMITER + idTicket);

		try {
			// Attente du booléen de confirmation
			while (!br.ready()) {
			}
			String message = br.readLine();

			// Erreur
			if (message.equals("false"))
				return null;

			return getMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			canReceive = true;
		}

		return null;
	}

	/**
	 * Déconnecter l'utilisateur
	 */
	public void disconnect() {
		canReceive = false;

		pw.println("deconnexion");
		pw.println(utilisateur.getIdentifiant());

		// Fermeture du socket
		TCPCommunication.closeClientSocket();

		canReceive = true;
	}

	public static void setUtilisateur(Utilisateur utilisateur) {
		ClientThread.utilisateur = utilisateur;
	}

	public static Utilisateur getUtilisateur() {
		return utilisateur;
	}

	private Utilisateur getUtilisateur(String line) {
		if (!line.contains(DELIMITER) || line.equals(DELIMITER + DELIMITER))
			throw new IllegalArgumentException("Line does not contain delimiter " + DELIMITER + ":\n" + line);

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
		if (!line.contains(DELIMITER) || line.equals(DELIMITER + DELIMITER))
			throw new IllegalArgumentException("Line does not contain delimiter " + DELIMITER + ":\n" + line);

		String[] parametres = line.split(DELIMITER);
		int idMessage = Integer.parseInt(parametres[0]);
		String contenu = parametres[1];
		Date date = null;
		if (!parametres[2].equals("null"))
			date = Date.valueOf(parametres[2]);

		return new Message(idMessage, contenu, date);
	}

	private GroupeUtilisateurs getGroupe(String line) {
		if (!line.contains(DELIMITER) || line.equals(DELIMITER + DELIMITER))
			throw new IllegalArgumentException("Line does not contain delimiter " + DELIMITER + ":\n" + line);

		String[] parametres = line.split(DELIMITER);
		int idGroupe = Integer.parseInt(parametres[0]);
		String nom = parametres[1];

		return new GroupeUtilisateurs(idGroupe, nom);
	}

	private Ticket getTicket(String line) {
		if (!line.contains(DELIMITER) || line.equals(DELIMITER + DELIMITER))
			throw new IllegalArgumentException("Line does not contain delimiter " + DELIMITER + ":\n" + line);

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

	private AssociationMessageUtilisateur getAMU(String line) {
		if (!line.contains(DELIMITER) || line.equals(DELIMITER + DELIMITER))
			throw new IllegalArgumentException(
					"Line does not contain delimiter " + (DELIMITER + DELIMITER) + ":\n" + line);

		String[] parametres = line.split(DELIMITER + DELIMITER);

		return new AssociationMessageUtilisateur(getMessage(parametres[0]), getUtilisateur(parametres[1]),
				parametres[2]);
	}

	private AssociationGroupeUtilisateur getAGU(String line) {
		if (!line.contains(DELIMITER) || line.equals(DELIMITER + DELIMITER))
			throw new IllegalArgumentException(
					"Line does not contain delimiter " + (DELIMITER + DELIMITER) + ":\n" + line);
		String[] parametres = line.split(DELIMITER + DELIMITER);

		return new AssociationGroupeUtilisateur(getGroupe(parametres[0]), getUtilisateur(parametres[1]));
	}

	// private void format(String s) {
	// String[] content = s.split("\t\0\0\t");
	// int nombre = 0;
	//
	// int i = 0;
	//
	// do {
	// String line = content[i];
	// if (line.isEmpty()) {
	// i++;
	// continue;
	// }
	// String[] split = line.split(":");
	// nombre = Integer.parseInt(split[1]);
	// switch (split[0]) {
	// case "Utilisateurs":
	// while (nombre > 0) {
	// String u = content[++i].substring(1);
	// Utilisateur utilisateur = getUtilisateur(u);
	// split = content[++i].substring(2).split(":");
	// int nbMessages = Integer.parseInt(split[1]);
	// while (nbMessages > 0) {
	// String m = content[++i].substring(3);
	// Message message = getMessage(m);
	// utilisateur.getMessages().add(message);
	// nbMessages--;
	// }
	//
	// split = content[++i].substring(2).split(":");
	// int nbTickets = Integer.parseInt(split[1]);
	// while (nbTickets > 0) {
	// String t = content[++i].substring(3);
	// Ticket ticket = getTicket(t);
	// utilisateur.getTickets().add(ticket);
	// nbTickets--;
	// }
	//
	// DBConnection.getInstance().getListeUtilisateurs().add(utilisateur);
	// nombre--;
	// i++;
	// }
	// break;
	// case "Groupes":
	// while (nombre > 0) {
	// String g = content[++i].substring(1);
	// GroupeUtilisateurs groupe = getGroupe(g);
	// split = content[++i].substring(2).split(":");
	// int nbUtilisateurs = Integer.parseInt(split[1]);
	// while (nbUtilisateurs > 0) {
	// String u = content[++i].substring(3);
	// Utilisateur utilisateur = getUtilisateur(u);
	// groupe.getUtilisateurs().add(utilisateur);
	// nbUtilisateurs--;
	// }
	//
	// DBConnection.getInstance().getListeGroupes().add(groupe);
	// nombre--;
	// i++;
	// }
	// break;
	// case "Tickets":
	// while (nombre > 0) {
	// String t = content[++i].substring(1);
	// Ticket ticket = getTicket(t);
	// split = content[++i].substring(2).split(":");
	// int nbMessages = Integer.parseInt(split[1]);
	// while (nbMessages > 0) {
	// String m = content[++i].substring(3);
	// Message message = getMessage(m);
	// ticket.getMessages().add(message);
	// nbMessages--;
	// }
	//
	// DBConnection.getInstance().getListeTickets().add(ticket);
	// nombre--;
	// i++;
	// }
	// break;
	// case "Messages":
	// while (nombre > 0) {
	// String m = content[++i].substring(1);
	// Message message = getMessage(m);
	//
	// DBConnection.getInstance().getListeMessages().add(message);
	// nombre--;
	// i++;
	// }
	// break;
	// case "AMU":
	// while (nombre > 0) {
	// String amu = content[++i].substring(1);
	// split = amu.split(DELIMITER + DELIMITER);
	// AssociationGroupeUtilisateur asso = getAMU(split[0], split[1], split[2]);
	//
	// DBConnection.getInstance().getListeAssociationsMessageUtilisateur().add(asso);
	// nombre--;
	// i++;
	// }
	// break;
	// }
	//
	// i++;
	// } while (i < content.length);
	// }

	/**
	 * Récupère le client où en crée un nouveau à l'aide d'un socket
	 * 
	 * @return le client
	 */
	public static ClientThread getClient() {
		return client == null ? client = TCPCommunication.openClientSocket() : client;
	}

}
