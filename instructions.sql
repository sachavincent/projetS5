#DROP TABLE IF EXISTS `AssociationMessageUtilisateur`;
#ALTER TABLE `Message` DROP FOREIGN KEY IF EXISTS message_ibfk_2;
#DROP TABLE IF EXISTS `Ticket`;
#DROP TABLE IF EXISTS `Message`;
#DROP TABLE IF EXISTS `Utilisateur`;
#DROP TABLE IF EXISTS `GroupeUtilisateurs`;

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
	connecte	 INT(1) DEFAULT 0,
	idgroupe	 INT(4) NOT NULL,
	FOREIGN KEY (idgroupe) REFERENCES `GroupeUtilisateurs` (idgroupe)
);

CREATE TABLE Message (
	idmessage	 INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	contenu 	 VARCHAR(10000) NOT NULL,
	created_at   DATETIME,
	iduser		 VARCHAR(100) NOT NULL,
	idticket	 INT(4) NOT NULL,
	FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant)
);

CREATE TABLE Ticket (
	idticket	 INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	titre 		 VARCHAR(100),
	created_at   DATETIME,
	iduser		 VARCHAR(100) NOT NULL,
	idgroupe	 INT(4) NOT NULL,
	idmessage	 INT(4) NOT NULL,
	FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant),
	FOREIGN KEY (idgroupe) REFERENCES `GroupeUtilisateurs` (idgroupe),
	FOREIGN KEY (idmessage) REFERENCES `Message` (idmessage)
);

CREATE TABLE AssociationMessageUtilisateur (
	idmessage 	 INT(4) NOT NULL,
	iduser 	  	 VARCHAR(100) NOT NULL,
	etat 		 ENUM('EN ATTENTE', 'NON LU', 'LU'),
	PRIMARY KEY(idmessage, iduser)
);

ALTER TABLE `AssociationMessageUtilisateur`
  ADD FOREIGN KEY (idmessage) REFERENCES `Message` (idmessage);

ALTER TABLE `AssociationMessageUtilisateur`
  ADD FOREIGN KEY (iduser) REFERENCES `Utilisateur` (identifiant);
 
ALTER TABLE `Message`
  ADD FOREIGN KEY (idticket) REFERENCES `Ticket` (idticket);