package main;

import java.io.File;
import java.util.Scanner;

public class LectureFichier {
	private Scanner x;
	private int port;
	private String ip;

	public LectureFichier() {

	}

	public void fileReader() {
		String txt;
		String eq;
		String nb;

		while (x.hasNext()) {
			txt = x.next();
			eq = x.next();
			nb = x.next();
			if (txt.equals("ip")) {
				ip = nb;
			}
			if (txt.equals("port")) {
				port = Integer.parseInt(nb);
			}
		}

	}

	public int getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	public void openFile() {
		try {
			x = new Scanner(new File("settings.txt"));
		} catch (Exception e) {
			System.out.println("fichier introuvable");
		}

	}

}
