package communication;

import static main.Main.DELIMITER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import database.DBConnection;
import model.AssociationMessageUtilisateur.EtatMessage;
import model.Message;
import model.Ticket;
import model.Utilisateur;

public class ServerThread extends Thread {

	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;

	private Requete requeteActuelle;

	public ServerThread(Socket socket) {
		this.socket = socket;
		System.out.println("new thread");

		requeteActuelle = Requete.NONE;
	}

	@Override
	public void run() {
		try {
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			TCPCommunication.CLIENTS.add(pw);

			ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
			executor.scheduleAtFixedRate(() -> {
				try {
					if (Thread.currentThread().isInterrupted()) {
						TCPCommunication.CLIENTS.remove(pw);

						TCPCommunication.closeServerSocket();

						executor.shutdown();
					}

					if (br.ready())
						readAndReply(socket, br.readLine());
				} catch (IOException e) {
					e.printStackTrace();

					executor.shutdown();
				}
			}, 0, 333, TimeUnit.MILLISECONDS);
		} catch (IOException e) {
			e.printStackTrace();

			closeServerSocket();

			this.interrupt();
		}
	}

	private void readAndReply(Socket socket, String instruction) {
		if (pw == null)
			throw new IllegalStateException();

		StringBuilder stringBuilder = new StringBuilder();

		System.out.println("Instruction reçue: " + instruction);
		// pw.println("Message received: " + instruction);

		switch (instruction) {
		case "connexion":
			requeteActuelle = Requete.CONNEXION;
			break;
		case "deconnexion":
			requeteActuelle = Requete.DECONNEXION;
			break;
		case "creerTicket":
			requeteActuelle = Requete.CREATION_TICKET;
			break;
		case "envoyerMessage":
			requeteActuelle = Requete.ENVOI_MESSAGE;
			break;
		case "Y": // Connexion de l'utilisateur, récupération des données du serveur
			// stringBuilder.append("Utilisateurs:");
			// stringBuilder.append(DBConnection.getInstance().getListeUtilisateurs().size()
			// + "\t\0\0\t");
			// DBConnection.getInstance().getListeUtilisateurs().forEach(o -> {
			// appendLn(stringBuilder, o.toString());
			//
			// stringBuilder.append("\t\tMessages:");
			// stringBuilder.append(o.getMessages().size() + "\t\0\0\t");
			// o.getMessages().forEach(m -> {
			// stringBuilder.append("\t\t");
			// appendLn(stringBuilder, m.toString());
			// });
			//
			// stringBuilder.append("\t\tTickets:");
			// stringBuilder.append(o.getTickets().size() + "\t\0\0\t");
			// o.getTickets().forEach(m -> {
			// stringBuilder.append("\t\t");
			// appendLn(stringBuilder, m.toString());
			// });
			// stringBuilder.append("\t\0\0\t");
			// });
			//
			// stringBuilder.append("Groupes:");
			// stringBuilder.append(DBConnection.getInstance().getListeGroupes().size() +
			// "\t\0\0\t");
			// DBConnection.getInstance().getListeGroupes().forEach(o -> {
			// appendLn(stringBuilder, o.toString());
			//
			// stringBuilder.append("\t\tUtilisateurs:");
			// stringBuilder.append(o.getUtilisateurs().size() + "\t\0\0\t");
			// o.getUtilisateurs().forEach(m -> {
			// stringBuilder.append("\t\t");
			// appendLn(stringBuilder, m.toString());
			// });
			// stringBuilder.append("\t\0\0\t");
			// });
			//
			// stringBuilder.append("Tickets:");
			// stringBuilder.append(DBConnection.getInstance().getListeTickets().size() +
			// "\t\0\0\t");
			// DBConnection.getInstance().getListeTickets().forEach(o -> {
			// appendLn(stringBuilder, o.toString());
			//
			// stringBuilder.append("\t\tMessages:");
			// stringBuilder.append(o.getMessages().size() + "\t\0\0\t");
			// o.getMessages().forEach(m -> {
			// stringBuilder.append("\t\t");
			// appendLn(stringBuilder, m.toString());
			// });
			// stringBuilder.append("\t\0\0\t");
			// });
			//
			// stringBuilder.append("Messages:");
			// stringBuilder.append(DBConnection.getInstance().getListeMessages().size() +
			// "\t\0\0\t");
			// DBConnection.getInstance().getListeMessages().forEach(o -> {
			// appendLn(stringBuilder, o.toString());
			//
			// stringBuilder.append("\t\0\0\t");
			// });
			//
			// stringBuilder.append("AMU:");
			// stringBuilder
			// .append(DBConnection.getInstance().getListeAssociationsMessageUtilisateur().size()
			// + "\t\0\0\t");
			// DBConnection.getInstance().getListeAssociationsMessageUtilisateur()
			// .forEach(o -> appendLn(stringBuilder, o.toString()));
			//
			// System.out.println(stringBuilder.toString());
			// pw.println(stringBuilder.toString());
			break;

		default:
			String[] split = instruction.split(DELIMITER);
			switch (requeteActuelle) {
			case CONNEXION:
				boolean res = DBConnection.getInstance().connecter(split[0], split[1]);
				pw.println(res); // Identifiants de l'utilisateurs à connecter donnés

				if (res) { // Connexion réussie
					Utilisateur u = DBConnection.getInstance().getListeUtilisateurs().stream()
							.filter(ut -> ut.getIdentifiant().equalsIgnoreCase(split[0])).findFirst().orElse(null);

					// Envoi des données
					TCPCommunication.CLIENTS.forEach(writer -> {
						if (!writer.equals(pw)) {
							writer.println("CONNEXION");
						}
						pw.println(u.toString()); // Envoi de l'utilisateur
					});

					// Les messages en attente passent en non lus
					// Envoi des messages
					DBConnection.getInstance().getListeAssociationsMessageUtilisateur().stream()
							.filter(amu -> amu.getUtilisateur().equals(u) && amu.getEtat() == EtatMessage.EN_ATTENTE)
							.forEach(amu -> {
								amu.getUtilisateur().setConnecte(true);

								DBConnection.getInstance().changerEtatMessage(amu.getMessage(), amu.getUtilisateur(),
										amu.getEtat());
							});
					try {
						// TODO: Envoyer tout ce qui le concerne
						// Envoi des messages qu'il a envoyé et ceux dans les tickets auxquels il
						// appartient
						// Envoi des tickets qu'il a crée et ceux auxquels il appartient
						// Envoi des utilisateurs qui sont dans le même ticket que lui
						// Envoi des groupes (groupes auxquels il appartient et groupes à destination
						// des tickets)

						List<Ticket> listeTickets = new ArrayList<>();

						// Utilisateurs concernés et leurs tickets et les groupes, les messages et les
						// associations AMU
						DBConnection.getInstance().getListeUtilisateurs().forEach(ut -> {
							pw.println(ut.toString());

							ut.getMessages().stream().forEach(m -> pw.println(m.toString()));

							pw.println(DELIMITER + DELIMITER);

							Set<Ticket> set = u.getTickets().stream().filter(t -> !listeTickets.contains(t))
									.collect(Collectors.toSet());
							if (set.isEmpty())
								pw.println(DELIMITER + DELIMITER);
							else {
								set.forEach(t -> {
									listeTickets.add(t);
									System.out.println("Sent Ticket: " + t.toString());
									pw.println(t.toString());

									t.getMessages().stream().forEach(m -> pw.println(m.toString()));

									pw.println(DELIMITER + DELIMITER);
								});
								pw.println(DELIMITER + DELIMITER);
							}

							DBConnection.getInstance().getListeGroupes().stream()
									.forEach(g -> pw.println(g.toString()));

							pw.println(DELIMITER + DELIMITER);

							DBConnection.getInstance().getListeAssociationsMessageUtilisateur().stream()
									.filter(amu -> amu.getUtilisateur().equals(ut))
									.forEach(amu -> pw.println(amu.toString()));

							pw.println(DELIMITER + DELIMITER);

							DBConnection.getInstance().getListeAssociationsGroupeUtilisateur().stream()
									.filter(agu -> agu.getUtilisateur().equals(ut)).forEach(agu -> {
										System.out.println("AGU sent: " + agu.toString());
										pw.println(agu.toString());
									});

							pw.println(DELIMITER + DELIMITER);
						});

						// Aucun ticket, rien n'a été envoyé
						if (listeTickets.isEmpty()) {
							// TODO
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {

						// Marquage de fin
						pw.println(DELIMITER + DELIMITER + DELIMITER);
					}
				}

				requeteActuelle = Requete.NONE;

				break;
			case DECONNEXION:
				DBConnection.getInstance().deconnecter(instruction); // Identifiant de l'utilisateur à déconnecter donné

				requeteActuelle = Requete.NONE;

				break;
			case CREATION_TICKET:
				Ticket ticket = DBConnection.getInstance().creerTicket(split[0], split[1], Integer.parseInt(split[2]));

				pw.println(ticket == null ? false : ticket.toString()); // Envoi du ticket ou de false si la création
																		// n'a pas fonctionné
				requeteActuelle = Requete.NONE;

				break;
			case ENVOI_MESSAGE:
				Message message = DBConnection.getInstance().creerMessage(split[0], split[1],
						Integer.parseInt(split[2]));

				pw.println(message == null ? false : message.toString()); // Envoi du message ou de false si la création
																			// n'a pas fonctionné

				if (message != null)
					pw.println(split[1] + DELIMITER + split[2]); // Envoi de l'identifiant de l'utilisateur qui l'a
																	// envoyé et de l'id du ticket
				try {
					// Envoi des associations
					DBConnection.getInstance().getListeAssociationsMessageUtilisateur().stream()
							.filter(amu -> amu.getMessage().equals(message)).forEach(amu -> pw.println(amu.toString()));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					// Marquage de fin
					pw.println(DELIMITER + DELIMITER + DELIMITER);
				}
				requeteActuelle = Requete.NONE;

				break;
			case NONE:
				break;
			}

			break;
		}

	}

	// private StringBuilder appendLn(StringBuilder stringBuilder, String m) {
	// stringBuilder.append("\t");
	// stringBuilder.append(m);
	// stringBuilder.append("\t\0\0\t");
	//
	// return stringBuilder;
	// }

	private void closeServerSocket() {
		try {
			if (socket != null)
				socket.close();

			if (br != null)
				br.close();

			if (pw != null)
				pw.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de la fermeture du socket server");
			e.printStackTrace();
		}
	}

	enum Requete {
		ENVOI_MESSAGE, CREATION_TICKET, CONNEXION, DECONNEXION, NONE;
	}
}
