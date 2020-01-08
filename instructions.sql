DROP TABLE IF EXISTS `AssociationMessageUtilisateur`;
DROP TABLE IF EXISTS `AssociationGroupeUtilisateur`;
DROP TABLE IF EXISTS `Ticket`;
DROP TABLE IF EXISTS `Message`;
DROP TABLE IF EXISTS `Utilisateur`;
DROP TABLE IF EXISTS `GroupeUtilisateurs`;

CREATE TABLE GroupeUtilisateurs (
	idgroupe INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nom VARCHAR(100)
);

CREATE TABLE Utilisateur (
	identifiant  VARCHAR(100) PRIMARY KEY NOT NULL,
	password     VARCHAR(50),
	nom          VARCHAR(100),
	prenom	     VARCHAR(100),
	type		 ENUM('ETUDIANT', 'ENSEIGNANT', 'SECRETAIRE PEDAGOGIQUE', 'SERVICE TECHNIQUE', 'SERVICE ADMINISTRATIF'),
	connecte	 INT(1) DEFAULT 0
);


CREATE TABLE Ticket (
	idticket	 INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	titre 		 VARCHAR(100),
	created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
	iduser		 VARCHAR(100) NOT NULL,
	idgroupe	 INT(4) NOT NULL,
	FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant),
	FOREIGN KEY (idgroupe) REFERENCES `GroupeUtilisateurs` (idgroupe)
);

CREATE TABLE Message (
	idmessage	 INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	contenu 	 VARCHAR(10000) NOT NULL,
	created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
	iduser		 VARCHAR(100) NOT NULL,
	idticket	 INT(4) NOT NULL,
	FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant),
	FOREIGN KEY (idticket) REFERENCES `Ticket` (idticket)
);

CREATE TABLE AssociationMessageUtilisateur (
	idmessage 	 INT(4) NOT NULL,
	iduser 	  	 VARCHAR(100) NOT NULL,
	etat 		 ENUM('EN ATTENTE', 'NON LU', 'LU'),
	PRIMARY KEY(idmessage, iduser)
);

CREATE TABLE AssociationGroupeUtilisateur (
	idgroupe 	 INT(4) NOT NULL,
	iduser	     VARCHAR(100) NOT NULL,
	PRIMARY KEY(idgroupe, iduser)
);
ALTER TABLE `AssociationMessageUtilisateur`
  ADD FOREIGN KEY (idmessage) REFERENCES `Message` (idmessage);

ALTER TABLE `AssociationMessageUtilisateur`
  ADD FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant);
 
ALTER TABLE `AssociationGroupeUtilisateur`
  ADD FOREIGN KEY (idgroupe) REFERENCES `GroupeUtilisateurs` (idgroupe);

ALTER TABLE `AssociationGroupeUtilisateur`
  ADD FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant);