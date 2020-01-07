package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static main.Main.*;
import database.DBConnection;

public class ServerThread extends Thread {

	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;

	private boolean connexion = true;

	public ServerThread(Socket socket) {
		this.socket = socket;
		System.out.println("new thread");
	}

	@Override
	public void run() {
		try {
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
			executor.scheduleAtFixedRate(() -> {
				try {
					if (br.ready())
						readAndReply(socket, br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}, 0, 333, TimeUnit.MILLISECONDS);
		} catch (IOException e) {
			e.printStackTrace();

			closeServerSocket();
		}
	}

	private void readAndReply(Socket socket, String instruction) {
		if (pw == null)
			throw new IllegalStateException();

		StringBuilder stringBuilder = new StringBuilder();

		System.out.println("Instruction reçue: " + instruction);
//		pw.println("Message received: " + instruction);

		switch (instruction) {
		case "connexion":
			connexion = true;

			break;
		case "Y": // Connexion de l'utilisateur, récupération des données du serveur
			stringBuilder.append("Utilisateurs:");
			stringBuilder.append(DBConnection.getInstance().getListeUtilisateurs().size() + "\t\0\0\t");
			DBConnection.getInstance().getListeUtilisateurs().forEach(o -> {
				appendLn(stringBuilder, o.toString());

				stringBuilder.append("\t\tMessages:");
				stringBuilder.append(o.getMessages().size() + "\t\0\0\t");
				o.getMessages().forEach(m -> {
					stringBuilder.append("\t\t");
					appendLn(stringBuilder, m.toString());
				});

				stringBuilder.append("\t\tTickets:");
				stringBuilder.append(o.getTickets().size() + "\t\0\0\t");
				o.getTickets().forEach(m -> {
					stringBuilder.append("\t\t");
					appendLn(stringBuilder, m.toString());
				});
				stringBuilder.append("\t\0\0\t");
			});

			stringBuilder.append("Groupes:");
			stringBuilder.append(DBConnection.getInstance().getListeGroupes().size() + "\t\0\0\t");
			DBConnection.getInstance().getListeGroupes().forEach(o -> {
				appendLn(stringBuilder, o.toString());

				stringBuilder.append("\t\tUtilisateurs:");
				stringBuilder.append(o.getUtilisateurs().size() + "\t\0\0\t");
				o.getUtilisateurs().forEach(m -> {
					stringBuilder.append("\t\t");
					appendLn(stringBuilder, m.toString());
				});
				stringBuilder.append("\t\0\0\t");
			});

			stringBuilder.append("Tickets:");
			stringBuilder.append(DBConnection.getInstance().getListeTickets().size() + "\t\0\0\t");
			DBConnection.getInstance().getListeTickets().forEach(o -> {
				appendLn(stringBuilder, o.toString());

				stringBuilder.append("\t\tMessages:");
				stringBuilder.append(o.getMessages().size() + "\t\0\0\t");
				o.getMessages().forEach(m -> {
					stringBuilder.append("\t\t");
					appendLn(stringBuilder, m.toString());
				});
				stringBuilder.append("\t\0\0\t");
			});

			stringBuilder.append("Messages:");
			stringBuilder.append(DBConnection.getInstance().getListeMessages().size() + "\t\0\0\t");
			DBConnection.getInstance().getListeMessages().forEach(o -> {
				appendLn(stringBuilder, o.toString());

				stringBuilder.append("\t\0\0\t");
			});

			stringBuilder.append("AMU:");
			stringBuilder
					.append(DBConnection.getInstance().getListeAssociationsMessageUtilisateur().size() + "\t\0\0\t");
			DBConnection.getInstance().getListeAssociationsMessageUtilisateur()
					.forEach(o -> appendLn(stringBuilder, o.toString()));

			System.out.println(stringBuilder.toString());
			pw.println(stringBuilder.toString());
			break;

		default:
			if (connexion) {
				String[] logins = instruction.split(DELIMITER);
				pw.println(DBConnection.getInstance().connecter(logins[0], logins[1]));

				connexion = false;
			}

			break;
		}
	}

	private StringBuilder appendLn(StringBuilder stringBuilder, String m) {
		stringBuilder.append("\t");
		stringBuilder.append(m);
		stringBuilder.append("\t\0\0\t");

		return stringBuilder;
	}

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
}
