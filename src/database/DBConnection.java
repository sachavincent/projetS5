package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import model.AssociationGroupeUtilisateur;
import model.AssociationMessageUtilisateur;
import model.AssociationMessageUtilisateur.EtatMessage;
import model.GroupeUtilisateurs;
import model.Message;
import model.Ticket;
import model.Utilisateur;

public class DBConnection {

	// Type de connexion
	public static Type type;

	// Instance unique de cette classe
	private static DBConnection instance;

	// Connexion à la base de donnée
	private Connection connection;

	// Liste des groupes d'utilisateurs
	private List<GroupeUtilisateurs> listeGroupes = new ArrayList<>();

	// Liste des messages
	private List<Message> listeMessages = new ArrayList<>();

	// Liste des utilisateurs
	private List<Utilisateur> listeUtilisateurs = new ArrayList<>();

	// Liste des tickets
	private List<Ticket> listeTickets = new ArrayList<>();

	// Liste des associations entre les utilisateurs et les messages
	private List<AssociationGroupeUtilisateur> listeAGU = new ArrayList<>();

	// Liste des associations entre les utilisateurs et les groupes
	private List<AssociationMessageUtilisateur> listeAMU = new ArrayList<>();

	/**
	 * Constructeur privé
	 */
	private DBConnection() {
		if (type == Type.SERVEUR) {
			this.connection = createNewConnection();

			// populate
			populate();
		}
	}

	/**
	 * Permet de créer les objets models à partir de la base de données
	 */
	private void populate() {
		// TODO
		System.out.println("Populating database...");
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			// Sélection des groupes d'utilisateurs
			st = this.connection.prepareStatement("SELECT * FROM GroupeUtilisateurs");

			rs = st.executeQuery();

			while (rs.next())
				listeGroupes.add(new GroupeUtilisateurs(rs.getInt(1), rs.getString(2)));

			st.close();
			rs.close();

			// Sélection des tickets
			st = this.connection.prepareStatement("SELECT * FROM Ticket");

			rs = st.executeQuery();

			while (rs.next()) {
				int idGroupe = rs.getInt(5);
				GroupeUtilisateurs groupe = listeGroupes.stream().filter(g -> g.getIdGroupe() == idGroupe).findFirst()
						.orElseThrow(IllegalStateException::new); // TODO Change?
				listeTickets.add(new Ticket(rs.getInt(1), rs.getString(2), rs.getDate(3), groupe));
			}

			st.close();
			rs.close();

			// Sélection des utilisateurs
			st = this.connection.prepareStatement("SELECT * FROM Utilisateur");

			rs = st.executeQuery();

			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6) == 1);

				// Sélection des messages dont l'utilisateur donné est le créateur
				st2 = this.connection.prepareStatement(
						"SELECT m.idmessage, m.contenu, m.created_at FROM Message m INNER JOIN Utilisateur u WHERE m.iduser = ?");

				st2.setString(1, utilisateur.getIdentifiant());

				rs2 = st2.executeQuery();

				// Ajouts des messages
				while (rs2.next())
					utilisateur.getMessages().add(new Message(rs2.getInt(1), rs2.getString(2), rs2.getDate(3)));

				st2.close();
				rs2.close();

				// Sélection des tickets dont l'utilisateur donné est le créateur
				st2 = this.connection.prepareStatement(
						"SELECT t.idticket FROM Ticket t INNER JOIN Utilisateur u WHERE t.iduser = ?");

				st2.setString(1, utilisateur.getIdentifiant());

				rs2 = st2.executeQuery();

				// Ajouts des tickets
				while (rs2.next()) {
					int idTicket = rs2.getInt(1);
					Ticket ticket = listeTickets.stream().filter(t -> t.getIdTicket() == idTicket).findFirst()
							.orElseThrow(IllegalStateException::new);
					utilisateur.getTickets().add(ticket);
				}
				listeUtilisateurs.add(utilisateur);
			}

			st.close();
			rs.close();

			// Sélection des messages
			st = this.connection.prepareStatement("SELECT * FROM Message");

			rs = st.executeQuery();

			while (rs.next()) {
				Message message = new Message(rs.getInt(1), rs.getString(2), rs.getDate(3));
				int idTicket = rs.getInt(5);
				Ticket ticket = listeTickets.stream().filter(t -> t.getIdTicket() == idTicket).findFirst()
						.orElseThrow(IllegalStateException::new);
				ticket.getMessages().add(message);
				listeMessages.add(message);
			}

			st.close();
			rs.close();

			// Sélection des associations
			st = this.connection.prepareStatement("SELECT * FROM AssociationMessageUtilisateur");

			rs = st.executeQuery();

			while (rs.next()) {
				int idMessage = rs.getInt(1);
				Message message = listeMessages.stream().filter(m -> m.getIdMessage() == idMessage).findFirst()
						.orElseThrow(IllegalStateException::new);

				String identUtilisateur = rs.getString(2);
				Utilisateur utilisateur = listeUtilisateurs.stream()
						.filter(u -> u.getIdentifiant().equals(identUtilisateur)).findFirst()
						.orElseThrow(IllegalStateException::new);

				AssociationMessageUtilisateur amu = new AssociationMessageUtilisateur(message, utilisateur,
						rs.getString(3));

				listeAMU.add(amu);
			}

			st.close();
			rs.close();

			// Sélection des associations
			st = this.connection.prepareStatement("SELECT * FROM AssociationGroupeUtilisateur");

			rs = st.executeQuery();

			while (rs.next()) {
				int idGroupe = rs.getInt(1);
				GroupeUtilisateurs groupe = listeGroupes.stream().filter(g -> g.getIdGroupe() == idGroupe).findFirst()
						.orElseThrow(IllegalStateException::new);

				String identUtilisateur = rs.getString(2);
				Utilisateur utilisateur = listeUtilisateurs.stream()
						.filter(u -> u.getIdentifiant().equals(identUtilisateur)).findFirst()
						.orElseThrow(IllegalStateException::new);

				AssociationGroupeUtilisateur agu = new AssociationGroupeUtilisateur(groupe, utilisateur);
				listeAGU.add(agu);
			}
		} catch (SQLException | IllegalStateException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (rs2 != null)
					rs2.close();
				if (st != null)
					st.close();
				if (st2 != null)
					st2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de connecter l'utilisateur
	 * 
	 * @param identifiantU idenfiant de l'utilisateur
	 * @param password     mot de passe de l'utilisateur
	 * @return reussite de la connexion
	 */
	public boolean connecter(String identifiantU, String password) {
		Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
				.filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiantU)).findFirst().orElse(null);

		if (utilisateur == null) // Identifiant incorrect
			return false;

		// On vérifie que les mots de passe correspondent
		boolean reussite = utilisateur.memeMotDePasse(password);

		if (reussite) {
			// Connexion réussie

			utilisateur.setConnecte(true);

			PreparedStatement st = null;
			try {
				// UPDATE dans la base de données
				st = this.connection.prepareStatement("UPDATE Utilisateur SET connecte = ? WHERE identifiant = ?");
				st.setInt(1, 1);
				st.setString(2, identifiantU);

				st.execute();

				// Mise à jour des listes
				Utilisateur u = listeUtilisateurs.stream()
						.filter(ut -> ut.getIdentifiant().equalsIgnoreCase(identifiantU)).findFirst().orElse(null);
				if (u != null)
					u.setConnecte(true);

				// Mise à jour des listes
				for (AssociationGroupeUtilisateur agu : listeAGU) {
					Utilisateur uti = agu.getUtilisateur();
					if (uti.getIdentifiant().equalsIgnoreCase(identifiantU))
						uti.setConnecte(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();

				return false;
			} finally {
				try {
					if (st != null)
						st.close();
				} catch (SQLException e) {
					e.printStackTrace();

					return false;
				}
			}

			// Utilisateur connecté dans la base de données
			try {
				// UPDATE Association dans la base de données
				st = this.connection.prepareStatement(
						"UPDATE AssociationMessageUtilisateur SET etat = ? WHERE etat = ? AND iduser = ?");
				st.setString(1, EtatMessage.NON_LU.toString().replace('_', ' '));
				st.setString(2, EtatMessage.EN_ATTENTE.toString().replace('_', ' '));
				st.setString(3, identifiantU);

				st.execute();

				// Mise à jour des listes
				listeAMU.stream()
						.filter(amu -> amu.getUtilisateur().getIdentifiant().equalsIgnoreCase(identifiantU)
								&& amu.getEtat() == EtatMessage.EN_ATTENTE)
						.forEach(amu -> amu.setEtat(EtatMessage.NON_LU));
			} catch (SQLException e) {
				e.printStackTrace();

				return false;
			} finally {
				try {
					if (st != null)
						st.close();
				} catch (SQLException e) {
					e.printStackTrace();

					return false;
				}
			}
		}

		return reussite;

	}

	/**
	 * Permet de déconnecter un utilisateur dans la base de données
	 * 
	 * @param identifiantU l'identifiant de l'utilisateur
	 */
	public void deconnecter(String identifiantU) {
		PreparedStatement st = null;
		try {
			// UPDATE dans la base de données
			st = this.connection.prepareStatement("UPDATE Utilisateur SET connecte = ? WHERE identifiant = ?");
			st.setInt(1, 0);
			st.setString(2, identifiantU);

			st.execute();

			// Mise à jour des listes
			Utilisateur u = listeUtilisateurs.stream().filter(ut -> ut.getIdentifiant().equalsIgnoreCase(identifiantU))
					.findFirst().orElse(null);
			if (u != null)
				u.setConnecte(false);

			// Mise à jour des listes
			for (AssociationGroupeUtilisateur agu : listeAGU) {
				Utilisateur uti = agu.getUtilisateur();
				if (uti.getIdentifiant().equalsIgnoreCase(identifiantU))
					uti.setConnecte(false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de créer un groupe
	 * 
	 * @param nom               le nom du groupe
	 * @param listeUtilisateurs la liste des utilisateurs du groupe
	 * @return true si l'insertion a fonctionné
	 */
	public boolean creerGroupe(String nom, List<Utilisateur> listeUtilisateurs) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// Ajout à la base de donnéesa
			st = this.connection.prepareStatement("INSERT INTO GroupeUtilisateurs (nom) VALUES (?)");
			st.setString(1, nom);

			st.execute();

			rs = st.getGeneratedKeys();

			if (!rs.next())
				return false;

			int idgroupe = rs.getInt(1);

			GroupeUtilisateurs groupeUtilisateurs = new GroupeUtilisateurs(idgroupe, nom);
			// Ajout à la liste
			listeGroupes.add(groupeUtilisateurs);

			st.close();
			rs.close();

			// Retourne true si toutes les insertions ont été effectuées avec succès
			boolean res = true;
			for (Utilisateur utilisateur : listeUtilisateurs) {
				if (!ajouterUtilisateurAGroupe(utilisateur, groupeUtilisateurs) && res)
					res = false;
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet d'ajouter un utilisateur à un groupe
	 * 
	 * @param utilisateur l'utilisateur
	 * @param groupe      le groupe
	 * @return true si l'insertion s'est bien déroulée
	 */
	public boolean ajouterUtilisateurAGroupe(Utilisateur utilisateur, GroupeUtilisateurs groupe) {
		PreparedStatement st = null;
		try {
			// Ajout à la base de données
			st = this.connection
					.prepareStatement("INSERT INTO AssociationGroupeUtilisateur (idgroupe, iduser) VALUES (?, ?)");

			st.setInt(1, groupe.getIdGroupe());
			st.setString(2, utilisateur.getIdentifiant());

			st.execute();

			st.close();

			// Ajout à la liste
			listeAGU.add(new AssociationGroupeUtilisateur(groupe, utilisateur));

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de supprimer un groupe
	 * 
	 * @param groupe le groupe à supprimer
	 * @return true si la suppression a eue lieu
	 */
	public boolean supprimerGroupe(GroupeUtilisateurs groupe) {
		PreparedStatement st = null;
		try {
			// Suppression des tickets concernés par ce groupe
			List<Ticket> lTickets = listeTickets.stream().filter(t -> t.getGroupeDestination().equals(groupe))
					.collect(Collectors.toList());
			for (Ticket ticket : lTickets)
				supprimerTicket(ticket);

			// Suppression des associations avec ce groupe
			for (AssociationGroupeUtilisateur agu : listeAGU) {
				if (agu.getGroupe().equals(groupe))
					supprimerUtilisateurDeGroupe(groupe, agu.getUtilisateur());
			}

			// Suppression dans la base de données
			st = this.connection.prepareStatement("DELETE FROM GroupeUtilisateurs WHERE idgroupe = ?");
			st.setInt(1, groupe.getIdGroupe());

			st.execute();

			// Suppression dans la liste
			listeGroupes.removeIf(g -> g.equals(groupe));

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de supprimer un utilisateur d'un groupe
	 * 
	 * @param utilisateur l'utilisateur
	 * @param groupe      le groupe
	 * @return true si la suppression s'est bien déroulée
	 */
	public boolean supprimerUtilisateurDeGroupe(GroupeUtilisateurs groupe, Utilisateur utilisateur) {
		PreparedStatement st = null;
		try {
			// Suppressions dans la base de données
			st = this.connection
					.prepareStatement("DELETE FROM AssociationGroupeUtilisateur WHERE idgroupe = ? AND iduser = ?");
			st.setInt(1, groupe.getIdGroupe());
			st.setString(2, utilisateur.getIdentifiant());

			st.execute();

			st.close();

			// Suppressions dans la liste
			listeAGU.removeIf(agu -> agu.getGroupe().equals(groupe) && agu.getUtilisateur().equals(utilisateur));

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de supprimer un ticket
	 * 
	 * @param ticket le ticket à supprimer
	 * @return si la suppression a bien été effectuée
	 */
	public boolean supprimerTicket(Ticket ticket) {
		PreparedStatement st = null;
		try {
			// Suppression des messages liés au ticket
			st = this.connection.prepareStatement("DELETE FROM Message WHERE idticket = ?");
			st.setInt(1, ticket.getIdTicket());

			st.execute();

			st.close();

			// Suppression dans la base de données
			st = this.connection.prepareStatement("DELETE FROM Ticket WHERE idticket = ?");
			st.setInt(1, ticket.getIdTicket());

			st.execute();

			st.close();

			// Suppression dans la liste
			listeTickets.removeIf(t -> t.equals(ticket));

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de mettre à jour un GroupeUtilisateurs
	 * 
	 * @param groupe le groupe à mettre à jour
	 */
	public void updateGroupe(GroupeUtilisateurs groupe) {
		PreparedStatement st = null;
		try {
			// UPDATE dans la base de données
			st = this.connection.prepareStatement("UPDATE GroupeUtilisateurs SET nom = ? WHERE idgroupe = ?");
			st.setString(1, groupe.getNom());
			st.setInt(2, groupe.getIdGroupe());

			st.execute();

			GroupeUtilisateurs gr = listeGroupes.stream().filter(g -> g.equals(groupe)).findFirst().orElse(null);
			if (gr != null)
				gr.setNom(groupe.getNom());

			listeTickets.stream().map(t -> t.getGroupeDestination()).filter(g -> g.equals(groupe))
					.forEach(g -> g.setNom(groupe.getNom()));

			listeAGU.stream().map(agu -> agu.getGroupe()).filter(g -> g.equals(groupe))
					.forEach(g -> g.setNom(groupe.getNom()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet d'ajouter un utilisateur à la base de données
	 * 
	 * @param identifiant l'identifiant de l'utilisateur
	 * @param password    le mot de passe de l'utilisateur
	 * @param nom         le nom de l'utilisateur
	 * @param prenom      le prénom de l'utilisateur
	 * @param type        le type de l'utilisateur
	 * 
	 * @return true si l'ajout a fonctionné
	 */
	public boolean creerUtilisateur(String identifiant, String password, String nom, String prenom, String type) {
		Utilisateur utilisateur = new Utilisateur(identifiant, password, nom, prenom, type, false);

		PreparedStatement st = null;
		try {
			// Insertion dans la base de données
			st = this.connection.prepareStatement(
					"INSERT Utilisateur (identifiant, password, nom, prenom, type, connecte) VALUES (?, ?, ?, ?, ?, ?)");
			st.setString(1, identifiant);
			st.setString(2, password);
			st.setString(3, nom);
			st.setString(4, prenom);
			st.setString(5, type);
			st.setInt(6, 0);

			st.execute();

			listeUtilisateurs.add(utilisateur);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de modifier le mot de passe de l'utilisateur
	 * 
	 * @param identifiant l'identifiant de l'utilisateur
	 * @param ancienMDP   l'ancien mot de passe de l'utilisateur
	 * @param nouveauMDP  le nouveau mot de passe de l'utilisateur
	 * 
	 * @return trye si la modification a fonctionné
	 */
	public boolean modifierMotDePasseUtilisateur(String identifiant, String ancienMDP, String nouveauMDP) {
		Utilisateur utilisateur = listeUtilisateurs.stream()
				.filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiant)).findFirst().orElse(null);

		if (utilisateur == null)
			return false;

		boolean memeMDP = utilisateur.modifierMotDePasse(ancienMDP, nouveauMDP);

		if (!memeMDP)
			return false;

		listeAGU.stream().map(agu -> agu.getUtilisateur()).filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiant))
				.forEach(u -> u.modifierMotDePasse(ancienMDP, nouveauMDP));

		PreparedStatement st = null;
		try {
			// Insertion dans la base de données
			st = this.connection.prepareStatement("UPDATE Utilisateur SET password = ? WHERE identifiant = ?");
			st.setString(1, nouveauMDP);
			st.setString(2, identifiant);

			st.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de supprimer un utilisateur de la base de données
	 * 
	 * @param utilisateur l'utilisateur à supprimer
	 * 
	 * @return true si la suppression a fonctionné
	 */
	public boolean supprimerUtilisateur(Utilisateur utilisateur) {
		listeUtilisateurs.remove(utilisateur);

		listeAGU.removeIf(agu -> agu.getUtilisateur().equals(utilisateur));

		PreparedStatement st = null;
		try {
			// Insertion dans la base de données
			st = this.connection.prepareStatement("DELETE FROM AssociationGroupeUtilisateur WHERE iduser = ?");
			st.setString(1, utilisateur.getIdentifiant());

			st.execute();

			st.close();

			st = this.connection.prepareStatement("DELETE FROM AssociationMessageUtilisateur WHERE iduser = ?");
			st.setString(1, utilisateur.getIdentifiant());

			st.execute();

			st.close();

			st = this.connection.prepareStatement("DELETE FROM Utilisateur WHERE identifiant = ?");
			st.setString(1, utilisateur.getIdentifiant());

			st.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Permet de créer un ticket
	 * 
	 * @param titre       le titre du ticket
	 * @param identifiant l'identifiant de l'utilisateur qui crée le ticket
	 * @param idGroupe    l'id du groupe qui reçoit le ticket
	 * 
	 * @return le ticket crée
	 */
	public Ticket creerTicket(String titre, String identifiant, int idGroupe) {
		GroupeUtilisateurs groupe = listeGroupes.stream().filter(g -> g.getIdGroupe() == idGroupe).findFirst()
				.orElse(null);

		if (groupe == null)
			return null;

		Utilisateur utilisateur = listeUtilisateurs.stream()
				.filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiant)).findFirst().orElse(null);

		if (utilisateur == null)
			return null;

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// Insertion dans la base de données
			st = this.connection.prepareStatement("INSERT INTO Ticket (titre, iduser, idgroupe) VALUES (?, ?, ?)");
			st.setString(1, titre);
			st.setString(2, identifiant);
			st.setInt(3, idGroupe);

			st.execute();

			st.close();

			rs = st.getGeneratedKeys();
			if (!rs.next()) // L'insertion n'a pas fonctionné
				return null;

			int idTicket = rs.getInt(1);

			rs.close();

			// Récupération de la date de création
			st = this.connection.prepareStatement("SELECT created_at FROM Ticket WHERE idticket = ?");
			st.setInt(1, idTicket);

			rs = st.executeQuery();

			if (!rs.next()) // L'insertion n'a pas fonctionné
				return null;

			Ticket ticket = new Ticket(idTicket, titre, rs.getDate(1), groupe);
			listeTickets.add(ticket);
			utilisateur.getTickets().add(ticket);

			return ticket;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Permet de créer un message
	 * 
	 * @param contenu     le contenu du message
	 * @param identifiant l'identifiant de l'utilisateur qui crée le message
	 * @param idTicket    l'id du ticket contenant le message
	 * 
	 * @return le message crée
	 */
	public Message creerMessage(String contenu, String identifiant, int idTicket) {
		Ticket ticket = listeTickets.stream().filter(t -> t.getIdTicket() == idTicket).findFirst().orElse(null);

		if (ticket == null)
			return null;

		Utilisateur utilisateur = listeUtilisateurs.stream()
				.filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiant)).findFirst().orElse(null);

		if (utilisateur == null)
			return null;

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// Insertion dans la base de données
			st = this.connection.prepareStatement("INSERT INTO Message (contenu, iduser, idticket) VALUES (?, ?, ?)");
			st.setString(1, contenu);
			st.setString(2, utilisateur.getIdentifiant());
			st.setInt(3, ticket.getIdTicket());

			st.execute();

			st.close();

			rs = st.getGeneratedKeys();
			if (!rs.next()) // L'insertion n'a pas fonctionné
				return null;

			int idMessage = rs.getInt(1);

			rs.close();

			// Récupération de la date de création
			st = this.connection.prepareStatement("SELECT created_at FROM Message WHERE idmessage = ?");
			st.setInt(1, idMessage);

			rs = st.executeQuery();

			if (!rs.next()) // L'insertion n'a pas fonctionné
				return null;

			Message message = new Message(idMessage, contenu, rs.getDate(1));
			listeMessages.add(message);
			ticket.getMessages().add(message);
			utilisateur.getMessages().add(message);

			return message;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Permet de créer la connexion avec la base de données
	 * 
	 * @return l'objet Connection à la base de données
	 */
	private Connection createNewConnection() {
		Properties connectionProps = new Properties();

		String userName = "root";
		String password = "";
		String serverName = "localhost";
		String portNumber = "3306";

		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		String urlDB = "jdbc:mysql://" + serverName + ":";
		urlDB += portNumber + "/" + "NeOCampus";

		try {
			return DriverManager.getConnection(urlDB, connectionProps);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return l'instance unique de cette classe
	 */
	public static DBConnection getInstance() {
		return instance == null ? instance = new DBConnection() : instance;
	}

	/**
	 * @return l'objet de connexion à la base de données
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * @return la liste des groupes
	 */
	public List<GroupeUtilisateurs> getListeGroupes() {
		return this.listeGroupes;
	}

	/**
	 * @return la liste des utilisateurs
	 */
	public List<Utilisateur> getListeUtilisateurs() {
		return listeUtilisateurs;
	}

	/**
	 * @return la liste des tickets
	 */
	public List<Ticket> getListeTickets() {
		return listeTickets;
	}

	/**
	 * @return la liste des messages
	 */
	public List<Message> getListeMessages() {
		return listeMessages;
	}

	/**
	 * @return la liste des associations entre utilisateurs et groupes
	 */
	public List<AssociationGroupeUtilisateur> getListeAssociationsGroupeUtilisateur() {
		return listeAGU;
	}

	/**
	 * @return la liste des associations entre utilisateurs et messages
	 */
	public List<AssociationMessageUtilisateur> getListeAssociationsMessageUtilisateur() {
		return listeAMU;
	}

	public enum Type {
		SERVEUR, CLIENT
	}
}
