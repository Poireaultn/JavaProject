# JavaProject - Application de Gestion du Tri Sélectif ♻️

Ce projet Java est une **application JavaFX** connectée à une base de données **MySQL**, permettant la gestion du tri sélectif des déchets. Il s'agit d'un projet académique complet avec **interface graphique**, **import/export CSV**, **génération de bilans**, **statistiques dynamiques**, et gestion des **utilisateurs, corbeilles, poubelles, déchets, bons d'achat** et bien plus.

---

## 🔧 Installation et Configuration

### 1. Cloner le projet

```bash
git clone git@github.com:Poireaultn/JavaProject.git
```

### 2. Importer le projet dans Eclipse

```menu
Fichier > Ouvrir un projet depuis le système de fichiers...
Sélectionner le dossier : JavaProject/
```

---

## ⚙️ Configuration JavaFX dans Eclipse

### 1. Télécharger JavaFX

Téléchargez le SDK JavaFX depuis :  
👉 [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)

### 2. Ajouter les `.jar` JavaFX

Dans Eclipse :

```menu
Clic droit sur le projet > Properties > Java Build Path > Libraries > Add External JARs...
Sélectionner tous les fichiers .jar dans le dossier : javafx-sdk-XX/lib/
```

### 3. Ajouter les options VM

Pour exécuter une classe JavaFX, ajoutez dans l'onglet **Run Configurations > Arguments > VM arguments** :

```bash
--module-path /chemin/vers/javafx-sdk-XX/lib --add-modules javafx.controls,javafx.fxml
```

> Remplacez `/chemin/vers/...` par le chemin absolu vers le dossier lib de JavaFX.

---

## 🔌 Configuration du connecteur JDBC

### 1. Ajouter `mysql-connector-java`

```menu
Clic droit sur le projet > Properties > Java Build Path > Libraries > Add External JARs...
Sélectionner : lib/mysql-connector-j-8.0.xx.jar
```

### 2. Vérifier les chemins du projet

Dans `Properties > Java Build Path` :

- `src/` → **dossier source**
- `bin/` → **dossier de sortie** (compilation)

---

## 🗄️ Configuration de la Base de Données

### 1. Création de la base `tri_selectif`

```bash
mysql -u root -p
```

```sql
CREATE DATABASE tri_selectif;
USE tri_selectif;
SOURCE /chemin/vers/JavaProject/sql/tri_selectif.sql;
```

Remplacez le chemin par le chemin réel vers le fichier `.sql`.

### 2. Modifier l'accès dans le projet

Dans `ConnexionBaseDeDonnees.java` :

```java
private static final String UTILISATEUR = "root";
private static final String MOT_DE_PASSE = "votre_mot_de_passe";
```

---

## 🚀 Lancer l'application

Dans Eclipse :

```menu
MainApp.java > clic droit > Run As > Java Application > MainApp - IHM
```

Assurez-vous d’avoir bien configuré JavaFX et JDBC.

Comptes disponibles :
- Employé :
  - login : user1
  - mdp : pass1
- Utilisateur :
  - login : user2
  - mdp : pass2

---

## ✅ Fonctionnalités disponibles

- 🔐 Connexion utilisateur (rôles : employé / utilisateur)
- 🧺 Gestion des corbeilles
- 🗑️ Dépôt dans les poubelles compatibles
- ♻️ Ajout de déchets et vidage des corbeilles
- 📊 Visualisation des statistiques globales
- 📊 Possibilité d'importer des fichiers CSV pour visualiser les stats
- 🏆 Attribution de points de fidélité
- 🎫 Achat de bons d'achat avec les points
- 📤 Export et 📥 import de la base au format CSV
- 📄 Génération de bilan d'activité au format texte (.txt)
- 📅 Suivi des dépôts et vidages par date et par utilisateur
- 🧾 Gestion complète de l’historique et des données

---

## 📁 Arborescence du projet

```
JavaProject/
├── src/
│   ├── modele/           # Classes de gestion et services liés à MySQL
│   └── IHM/              # Interface graphique JavaFX
├── lib/                  # JAR JavaFX et JDBC
├── sql/                  # Fichier de dump SQL
└── README.md
```

---

## 👥 Auteurs

- **Antoine Metz** — _iNeKka_
- **Nathan Poireault** — _PoireaultN_
- **Thierry Berard** — _ThryB_
- **Imrane Boumedine** — 

Projet académique — **ING 1 GM — CY Tech 2025**
