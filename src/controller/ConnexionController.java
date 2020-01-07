package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import communication.Client;
import communication.TCPCommunication;

public class ConnexionController implements ActionListener {

	private JLabel labelIdentifiant;
	private JPasswordField passwordField;

	public ConnexionController(JLabel labelIdentifiant, JPasswordField passwordField) {
		this.labelIdentifiant = labelIdentifiant;
		this.passwordField = passwordField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String identifiant = labelIdentifiant.getText();
		String password = String.valueOf(passwordField.getPassword());
		if (identifiant.isEmpty() || password.isEmpty())
			return;

		// TODO Encryption

		Client client = TCPCommunication.openClientSocket();
		if (client != null)
			client.connect(identifiant, password);
	}

}
