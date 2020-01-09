package view;

import model.Utilisateur.TypeUtilisateur;
import view.client.VueCreationTicket;

public class main {
	public static void main(String[] args) {
		new TestVue(TypeUtilisateur.ETUDIANT);
	}

}
