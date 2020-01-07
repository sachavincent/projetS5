package view;

import javax.swing.JOptionPane;

public class AjoutGroupe {
	private String nom;
	private int idGrp;
	public AjoutGroupe() {
		nom = JOptionPane.showInputDialog("Nom du groupe");
	}

}
