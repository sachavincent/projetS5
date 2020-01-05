package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.AssociationMessageUtilisateur;
import model.GroupeUtilisateurs;
import model.Message;
import model.Ticket;
import model.Utilisateur;

public class DBConnection {

	// Instance unique de cette classe
	private static DBConnection instance;

	// Connexion à la base de donnée
	private Connection connection;

	// Liste des groupes d'utilisateurs
	private List<GroupeUtilisateurs> listeGroupes = new ArrayList<>();

	// Liste des utilisateurs
	private List<Utilisateur> listeUtilisateurs = new ArrayList<>();

	// Liste des tickets
	private List<Ticket> listeTickets = new ArrayList<>();

	// Liste des associations entre les utilisateurs et les messages
	private List<AssociationMessageUtilisateur> listeAssociationsMessageUtilisateur = new ArrayList<>();

	/**
	 * Constructeur privé
	 */
	private DBConnection() {
		this.connection = createNewConnection();

		// populate
		populate();
	}

	/**
	 * Permet de créer les objets models à partir de la base de données
	 */
	private void populate() {
		// TODO

		PreparedStatement st;
		PreparedStatement st2 = null;
		ResultSet rs;
		ResultSet rs2 = null;
		try {
			// Sélection des groupes d'utilisateurs
			st = this.connection.prepareStatement("SELECT * FROM GroupeUtilisateurs");

			rs = st.executeQuery();

			while (rs.next())
				listeGroupes.add(new GroupeUtilisateurs(rs.getInt(0), rs.getString(1)));

			// Sélection des tickets
			st = this.connection.prepareStatement("SELECT * FROM Ticket");

			rs = st.executeQuery();

			while (rs.next()) {
				int idGroupe = rs.getInt(3);
				GroupeUtilisateurs groupe = listeGroupes.stream().filter(g -> g.getIdGroupe() == idGroupe).findFirst()
						.orElseThrow(IllegalStateException::new); // TODO Change?
				listeTickets.add(new Ticket(rs.getInt(0), rs.getString(1), rs.getDate(2), groupe));
			}

			// Sélection des utilisateurs
			st = this.connection.prepareStatement("SELECT * FROM Utilisateur");

			rs = st.executeQuery();

			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getString(0), rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getInt(5) == 1);

				// Sélection des messages dont l'utilisateur donné est le créateur
				st2 = this.connection.prepareStatement(
						"SELECT m.idmessage, m.contenu, m.created_at FROM Message m INNER JOIN Utilisateur u WHERE m.iduser = ?");

				st2.setString(0, utilisateur.getIdentifiant());

				rs2 = st.executeQuery();

				// Ajouts des messages
				while (rs2.next())
					utilisateur.getMessages().add(new Message(rs2.getInt(0), rs2.getString(1), rs2.getDate(2)));

				// Sélection des tickets dont l'utilisateur donné est le créateur
				st2 = this.connection.prepareStatement(
						"SELECT t.idticket FROM Ticket t INNER JOIN Utilisateur u WHERE t.iduser = ?");

				st2.setString(0, utilisateur.getIdentifiant());

				rs2 = st.executeQuery();

				// Ajouts des tickets
				while (rs2.next()) {
					int idTicket = rs2.getInt(0);
					Ticket ticket = listeTickets.stream().filter(t -> t.getIdTicket() == idTicket).findFirst()
							.orElseThrow(IllegalStateException::new);
					utilisateur.getTickets().add(ticket);
				}

				listeUtilisateurs.add(utilisateur);
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
	 * @return la liste des associations entre utilisateurs et messages
	 */
	public List<AssociationMessageUtilisateur> getListeAssociationsMessageUtilisateur() {
		return listeAssociationsMessageUtilisateur;
	}

}
