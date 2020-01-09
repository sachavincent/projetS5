package view;

import java.io.File;
import java.util.Scanner;

import main.Encryption;
import main.LectureFichier;
import view.client.VueFenetreClient;
import view.server.VueAjoutUtilisateur;
import view.server.VueAjoutUtilisateurGroupe;
import view.server.VueFenetreServeur;
import view.server.VueSuppressionGroupe;

public class main {

	public static void main(String[] args) {

		String password = "123456";
		System.out.println(password);
		System.out.println("MD5 : "+ Encryption.SHA1(password));
	}

}