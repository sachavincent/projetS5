package view;

import javax.swing.JOptionPane;

public class SuppresionUtilisateur {
	private String sup;
	public SuppresionUtilisateur(){
		//TODO plutôt faire un menu déroulant
		sup = JOptionPane.showInputDialog("Identifiant de l'utilisateur à supprimer");
	}

}
