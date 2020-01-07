package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import communication.Client;
import communication.TCPCommunication;

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

		// TODO Encryption

		Client client = Client.getClient();
		if (client != null)
			System.out.println("Res: " + client.connect(identifiant, password));
	}

}
