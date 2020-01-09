package controller.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import communication.ClientThread;

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
		// TODO Encryption

		ClientThread client = ClientThread.getClient();
		if (client != null) {
			boolean res = client.connect(identifiant, password);
			System.out.println("Res: " + res);
//			if(res)
//				Client.getUtilisateur().setConnecte(true);
		}
	}

}
