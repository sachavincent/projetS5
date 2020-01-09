package view;

import java.io.File;
import java.util.Scanner;

import main.FileReader;
import view.client.VueFenetreClient;
import view.server.VueAjoutUtilisateur;
import view.server.VueAjoutUtilisateurGroupe;
import view.server.VueFenetreServeur;
import view.server.VueSuppressionGroupe;

public class main {
	
	
	

	public static void main(String[] args) {
		
		FileReader t = new FileReader();
		t.openFile();
		t.fileReader();
		System.out.println(t.getIp());
		System.out.println(t.getPort());
	}

}