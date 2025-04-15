# JavaProject - Gestion du Tri Sélectif

JavaProject est une application console développée en Java, permettant la gestion du tri sélectif des déchets avec une base de données MySQL.

## Installation

Cloner le projet avec la commande suivante :

```bash
git clone git@github.com:Poireaultn/JavaProject.git
```

Importer ensuite le projet dans Eclipse :

``` Fichier > Ouvrir un projet depuis le système de fichiers... Sélectionner le dossier : JavaProject/ ```

Configurer le chemin du connecteur JDBC dans Eclipse :

``` Clic droit sur le projet > Properties > Java Build Path > Libraries > Add JARs... Sélectionner : lib/mysql-connector-j-8.0.xx.jar Appliquer et fermer ```

Vérifier que les dossiers sont correctement configurés :

``` Properties > Java Build Path > Source src/ --> Dossier source bin/ --> Dossier de sortie (classes compilées) ```

## Base de données

Importer le dump SQL de la base de données :

```bash 
mysql -u root -p
```

Puis dans le terminal MySQL :

``` sql 
CREATE DATABASE tri_selectif;
USE tri_selectif;
SOURCE /chemin/vers/le/projet/JavaProject/sql/tri_selectif.sql;
```

Remplacez `/chemin/vers/le/projet/` par le chemin réel vers votre projet.

IL FAUT IMPERATIVEMENT MODIFIER LES L'ID MYSQL ET LE MOT DE PASSE DANS LE FICHIER ConnexionBaseDeDonnees.java

```
private static final String UTILISATEUR = "root"; 
private static final String MOT_DE_PASSE = "cytech0001";
```

## Utilisation

Exécuter le projet dans Eclipse :

``` Ouvrir ApplicationTriSelectif.java Clic droit > Run As > Java Application ```

L'application se lance dans la console Eclipse.

## Fonctionnalités

    Gestion des corbeilles et des poubelles

    Ajout et suppression de déchets

    Historique des dépôts et vidages

    Achat de bons d'achat avec points de fidélité

    Mode visiteur : Découverte du tri sélectif

## Auteurs

    Antoine Metz - iNeKka

    Nathan Poireault - PoireaultN

    Thierry Berard - ThryB



Projet académique ING 1 GM — CY Tech 2025
