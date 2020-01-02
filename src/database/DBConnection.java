package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	// Instance unique de cette classe
	private static DBConnection instance;

	// Connexion � la base de donn�e
	private Connection connection;

	/**
	 * Constructeur priv�
	 */
	private DBConnection() {
//		this.connection = this.createNewConnection();
	}

//	private Connection createNewConnection() {
//		Properties connectionProps = new Properties();
//
//		String userName = "root";
//		String password = "";
//		String serverName = "localhost";
//		String portNumber = "3306";
//
//		connectionProps.put("user", userName);
//		connectionProps.put("password", password);
//
//		String urlDB = "jdbc:mysql://" + serverName + ":";
//		urlDB += portNumber + "/" + this.databaseName;
//
//		try {
//			return DriverManager.getConnection(urlDB, connectionProps);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

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

}
