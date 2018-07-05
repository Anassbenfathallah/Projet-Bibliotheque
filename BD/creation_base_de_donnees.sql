-- Titre             : Script SQL (PostgreSQL) de création de la base de données du projet bibliothèque
-- Version           : 1.0
-- Date création     : 07 mars 2006
-- Date modification : 9 avril 2017
-- Auteur            : Philippe TANGUY
-- Description       : Ce script est une ébauche, à compléter, qui permet la création de la table
--                     "livre" pour la réalisation de la fonctionnalité "liste de tous les livres".

-- +----------------------------------------------------------------------------------------------+
-- | Suppression des tables                                                                       |
-- +----------------------------------------------------------------------------------------------+

drop table if exists "livre";

-- +----------------------------------------------------------------------------------------------+
-- | Création des tables                                                                          |
-- +----------------------------------------------------------------------------------------------+

create table livre
(
  id     serial primary key,
  isbn10 varchar(25) unique,
  isbn13 varchar(25) unique,
  titre  varchar(50) not null,
  auteur varchar(30)
);

create table exemplaire
(
  idExemplaire serial primary key,
  id serial references livre	
 );

create table emprunt
	(
	idExemplaire serial references exemplaire,
	id serial references livre,
	idAbonne serial references abonne,

	date_emprunt date not null,
	daterDeRetour date,

	);

create table abonne
	(
	idAbonne serial primary key,
  	nom varchar(25),
  	prenom varchar(255),
  	statut varchar(255),
  	check (statut in ('Etudiant','Enseignant')) ,
	adresse varchar(50) not null
);
-- +----------------------------------------------------------------------------------------------+
-- | Insertion de quelques données de pour les tests                                              |
-- +----------------------------------------------------------------------------------------------+

insert into livre values(nextval('livre_id_seq'), '2-84177-042-7', NULL,                'JDBC et JAVA',                            'George REESE');    -- id = 1
insert into livre values(nextval('livre_id_seq'), NULL,            '978-2-7440-7222-2', 'Sociologie des organisations',            'Michel FOUDRIAT'); -- id = 2
insert into livre values(nextval('livre_id_seq'), '2-212-11600-4', '978-2-212-11600-7', 'Le data warehouse',                       'Ralph KIMBALL');   -- id = 3
insert into livre values(nextval('livre_id_seq'), '2-7117-4811-1', NULL,                'Entrepots de données',                    'Ralph KIMBALL');   -- id = 4
insert into livre values(nextval('livre_id_seq'), '2012250564',    '978-2012250567',    'Oui-Oui et le nouveau taxi',              'Enid BLYTON');     -- id = 5
insert into livre values(nextval('livre_id_seq'), '2203001011',    '978-2203001015',    'Tintin au Congo',                         'HERGÉ');           -- id = 6
insert into livre values(nextval('livre_id_seq'), '2012011373',    '978-2012011373',    'Le Club des Cinq et le trésor de l''île', 'Enid BLYTON');     -- id = 7
