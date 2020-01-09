package controller.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import communication.ClientThread;
import database.DBConnection;
import main.Encryption;
import view.client.VueFenetreClient;

public class ConnexionController implements ActionListener {

	private JTextField identifiantField;
	private JPasswordField passwordField;

	public ConnexionController(JTextField identifiantField, JPasswordField passwordField) {
		this.identifiantField = identifiantField;
		this.passwordField = passwordField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String identifiant = identifiantField.getText();
		String password = String.valueOf(passwordField.getPassword());
		if (identifiant.isEmpty() || password.isEmpty())
			return;

		System.out.println("Click");
		password = Encryption.SHA1(password);
		System.out.println(password);
		ClientThread client = ClientThread.getClient();
		if (client != null) {
			boolean res = client.connect(identifiant, password);
			if (res) {
				System.out.println("true");
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(identifiantField);
				topFrame.dispose();

				new VueFenetreClient();
			}
			// TODO Message d'erreur
		}
		// TODO Message d'erreur
	}

}
