package view;

import javax.swing.JOptionPane;

public class SuppresionUtilisateur {
	private String sup;
	public SuppresionUtilisateur(){
		//plut�t faire un menu d�roulant
		sup = JOptionPane.showInputDialog("Identifiant de l'utilisateur � supprimer");
	}

}
