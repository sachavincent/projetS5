package view;

import javax.swing.JOptionPane;
//Interface permettant de cr�er un nouveau groupe
public class AjoutGroupe {
	private String nom;
	private int idGrp;
	public AjoutGroupe() {
		nom = JOptionPane.showInputDialog("Nom du groupe");
		//TODO Groupe g = new Groupe(nom,id); + ajout � la liste des grp
	}

}
