package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import communication.Client;
import communication.TCPCommunication;
import model.AssociationMessageUtilisateur;
import model.GroupeUtilisateurs;
import model.Message;
import model.Ticket;
import model.Utilisateur;

public class DBConnection {

	// Instance unique de cette classe
	private static DBConnection instance;

	// Connexion � la base de donn�e
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
	private List<AssociationMessageUtilisateur> listeAMU = new ArrayList<>();

	/**
	 * Constructeur priv�
	 */
	private DBConnection() {
		this.connection = createNewConnection();

		// populate
		populate();
	}

	/**
	 * Permet de cr�er les objets models � partir de la base de donn�es
	 */
	private void populate() {
		// TODO

		PreparedStatement st;
		PreparedStatement st2 = null;
		ResultSet rs;
		ResultSet rs2 = null;
		try {
			// S�lection des groupes d'utilisateurs
			st = this.connection.prepareStatement("SELECT * FROM GroupeUtilisateurs");

			rs = st.executeQuery();

			while (rs.next())
				listeGroupes.add(new GroupeUtilisateurs(rs.getInt(1), rs.getString(2)));

			// S�lection des tickets
			st = this.connection.prepareStatement("SELECT * FROM Ticket");

			rs = st.executeQuery();

			while (rs.next()) {
				int idGroupe = rs.getInt(5);
				GroupeUtilisateurs groupe = listeGroupes.stream().filter(g -> g.getIdGroupe() == idGroupe).findFirst()
						.orElseThrow(IllegalStateException::new); // TODO Change?
				listeTickets.add(new Ticket(rs.getInt(1), rs.getString(2), rs.getDate(3), groupe));
			}

			// S�lection des utilisateurs
			st = this.connection.prepareStatement("SELECT * FROM Utilisateur");

			rs = st.executeQuery();

			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6) == 1);

				// S�lection des messages dont l'utilisateur donn� est le cr�ateur
				st2 = this.connection.prepareStatement(
						"SELECT m.idmessage, m.contenu, m.created_at FROM Message m INNER JOIN Utilisateur u WHERE m.iduser = ?");

				st2.setString(1, utilisateur.getIdentifiant());

				rs2 = st2.executeQuery();

				// Ajouts des messages
				while (rs2.next())
					utilisateur.getMessages().add(new Message(rs2.getInt(1), rs2.getString(2), rs2.getDate(3)));

				// S�lection des tickets dont l'utilisateur donn� est le cr�ateur
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

				int idGroupe = rs.getInt(7);
				GroupeUtilisateurs groupe = listeGroupes.stream().filter(g -> g.getIdGroupe() == idGroupe).findFirst()
						.orElseThrow(IllegalStateException::new);
				groupe.getUtilisateurs().add(utilisateur);
				listeUtilisateurs.add(utilisateur);
			}

			// S�lection des messages
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

			// S�lection des associations
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

			rs.close();
			if (rs2 != null)
				rs2.close();
			st.close();
			if (st2 != null)
				st2.close();
		} catch (SQLException | IllegalStateException e) {
			e.printStackTrace();
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
		// Dans le listener
//		Client client = TCPCommunication.openClientSocket();
//		client.connect(identifiantU, password);

		Utilisateur utilisateur = DBConnection.getInstance().getListeUtilisateurs().stream()
				.filter(u -> u.getIdentifiant().equalsIgnoreCase(identifiantU)).findFirst().orElse(null);

		if (utilisateur == null) // Identifiant incorrect
			return false;

		// On v�rifie que les mots de passe correspondent
		boolean reussite = utilisateur.memeMotDePasse(password);

		if (reussite) {
			// Connexion r�ussie

			utilisateur.setConnecte(true);

			PreparedStatement st;
			ResultSet rs;
			try {
				// S�lection des groupes d'utilisateurs
				st = this.connection.prepareStatement("UPDATE * FROM GroupeUtilisateurs");

				rs = st.executeQuery();

				while (rs.next())
					listeGroupes.add(new GroupeUtilisateurs(rs.getInt(1), rs.getString(2)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return reussite;
	}

	/**
	 * Permet de cr�er la connexion avec la base de donn�es
	 * 
	 * @return l'objet Connection � la base de donn�es
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
	 * @return l'objet de connexion � la base de donn�es
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
	 * @return la liste des associations entre utilisateurs et messages
	 */
	public List<AssociationMessageUtilisateur> getListeAssociationsMessageUtilisateur() {
		return listeAMU;
	}

}
