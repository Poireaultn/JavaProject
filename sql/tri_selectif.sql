-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: tri_selectif
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `achats_bons`
--

DROP TABLE IF EXISTS `achats_bons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achats_bons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_menage` int DEFAULT NULL,
  `id_bon` int DEFAULT NULL,
  `date_achat` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_menage` (`id_menage`),
  KEY `id_bon` (`id_bon`),
  CONSTRAINT `achats_bons_ibfk_1` FOREIGN KEY (`id_menage`) REFERENCES `menage` (`id`),
  CONSTRAINT `achats_bons_ibfk_2` FOREIGN KEY (`id_bon`) REFERENCES `bons_achat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achats_bons`
--

LOCK TABLES `achats_bons` WRITE;
/*!40000 ALTER TABLE `achats_bons` DISABLE KEYS */;
/*!40000 ALTER TABLE `achats_bons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_achat`
--

DROP TABLE IF EXISTS `bon_achat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bon_achat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prix` double DEFAULT NULL,
  `categorie_produit` varchar(100) DEFAULT NULL,
  `date_expiration` date DEFAULT NULL,
  `id_commerce` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_commerce` (`id_commerce`),
  CONSTRAINT `bon_achat_ibfk_1` FOREIGN KEY (`id_commerce`) REFERENCES `commerce` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_achat`
--

LOCK TABLES `bon_achat` WRITE;
/*!40000 ALTER TABLE `bon_achat` DISABLE KEYS */;
/*!40000 ALTER TABLE `bon_achat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bons_achat`
--

DROP TABLE IF EXISTS `bons_achat`;
CREATE TABLE `bons_achat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marque` varchar(100) DEFAULT NULL,
  `valeur` double DEFAULT NULL,
  `cout_points` double DEFAULT NULL,
  `date_expiration` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bons_achat`
--

LOCK TABLES `bons_achat` WRITE;
/*!40000 ALTER TABLE `bons_achat` DISABLE KEYS */;
INSERT INTO `bons_achat` (id, marque, valeur, cout_points, date_expiration) VALUES
(1, 'Amazon', 10, 100, CURDATE() + INTERVAL 1 YEAR),
(2, 'Fnac', 15, 150, CURDATE() + INTERVAL 1 YEAR),
(3, 'Decathlon', 20, 200, CURDATE() + INTERVAL 1 YEAR),
(4, 'Carrefour', 25, 250, CURDATE() + INTERVAL 1 YEAR);
UNLOCK TABLES;

ALTER TABLE bons_achat ADD COLUMN date_expiration DATE DEFAULT (CURRENT_DATE + INTERVAL 1 YEAR);

--
-- Table structure for table `centre_de_tri`
--

DROP TABLE IF EXISTS `centre_de_tri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `centre_de_tri` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `adresse` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centre_de_tri`
--

LOCK TABLES `centre_de_tri` WRITE;
/*!40000 ALTER TABLE `centre_de_tri` DISABLE KEYS */;
/*!40000 ALTER TABLE `centre_de_tri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `centre_tri`
--

DROP TABLE IF EXISTS `centre_tri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `centre_tri` (
  `idCentre` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  PRIMARY KEY (`idCentre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centre_tri`
--

LOCK TABLES `centre_tri` WRITE;
/*!40000 ALTER TABLE `centre_tri` DISABLE KEYS */;
INSERT INTO `centre_tri` VALUES (1,'Centre de Tri de Pau','12 rue des Pyrénées, Pau'),(2,'Centre de Tri de Bordeaux','23 avenue Aquitaine, Bordeaux'),(3,'Centre de Tri de Toulouse','45 boulevard Occitanie, Toulouse');
/*!40000 ALTER TABLE `centre_tri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commerce`
--

DROP TABLE IF EXISTS `commerce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commerce` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commerce`
--

LOCK TABLES `commerce` WRITE;
/*!40000 ALTER TABLE `commerce` DISABLE KEYS */;
/*!40000 ALTER TABLE `commerce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corbeille`
--

DROP TABLE IF EXISTS `corbeille`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corbeille` (
  `idCorbeille` int NOT NULL AUTO_INCREMENT,
  `idMenage` int NOT NULL,
  PRIMARY KEY (`idCorbeille`),
  KEY `idMenage` (`idMenage`),
  CONSTRAINT `corbeille_ibfk_1` FOREIGN KEY (`idMenage`) REFERENCES `menage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corbeille`
--

LOCK TABLES `corbeille` WRITE;
/*!40000 ALTER TABLE `corbeille` DISABLE KEYS */;
INSERT INTO `corbeille` VALUES (1,1),(3,1),(2,2);
/*!40000 ALTER TABLE `corbeille` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dechet`
--

DROP TABLE IF EXISTS `dechet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dechet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_dechet` varchar(50) DEFAULT NULL,
  `poids` double DEFAULT NULL,
  `id_corbeille` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_corbeille` (`id_corbeille`),
  CONSTRAINT `dechet_ibfk_1` FOREIGN KEY (`id_corbeille`) REFERENCES `corbeille` (`idCorbeille`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dechet`
--

LOCK TABLES `dechet` WRITE;
/*!40000 ALTER TABLE `dechet` DISABLE KEYS */;
INSERT INTO `dechet` VALUES (7,'plastique',129,1),(8,'metal',1,1);
/*!40000 ALTER TABLE `dechet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historique_depots`
--

DROP TABLE IF EXISTS `historique_depots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historique_depots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idPoubelle` int NOT NULL,
  `idUtilisateur` int NOT NULL,
  `dateDepot` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `poids_depose` double DEFAULT NULL,
  `date_depot` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historique_depots`
--

LOCK TABLES `historique_depots` WRITE;
/*!40000 ALTER TABLE `historique_depots` DISABLE KEYS */;
INSERT INTO `historique_depots` VALUES (1,1,1,'2025-04-13 14:34:39',1,'2025-04-13 14:34:39'),(2,1,1,'2025-04-14 06:09:34',1,'2025-04-14 06:09:34'),(3,1,1,'2025-04-14 13:30:19',50,'2025-04-14 13:30:19'),(4,6,1,'2025-04-14 13:31:29',1,'2025-04-14 13:31:29');
/*!40000 ALTER TABLE `historique_depots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historique_vidages`
--

DROP TABLE IF EXISTS `historique_vidages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historique_vidages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idPoubelle` int NOT NULL,
  `idCentre` int NOT NULL,
  `idEmploye` int NOT NULL,
  `dateVidage` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `poids_vidange` double DEFAULT NULL,
  `date_vidange` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historique_vidages`
--

LOCK TABLES `historique_vidages` WRITE;
/*!40000 ALTER TABLE `historique_vidages` DISABLE KEYS */;
INSERT INTO `historique_vidages` VALUES (1,1,1,1,'2025-04-13 14:34:56',1,'2025-04-13 14:34:56'),(2,1,1,1,'2025-04-14 06:09:51',1,'2025-04-14 06:09:51'),(3,1,1,1,'2025-04-14 13:30:02',0,'2025-04-14 13:30:02'),(4,6,1,1,'2025-04-14 13:31:56',1,'2025-04-14 13:31:56');
/*!40000 ALTER TABLE `historique_vidages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menage`
--

DROP TABLE IF EXISTS `menage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `mot_de_passe` varchar(100) NOT NULL,
  `points_fidelite` double DEFAULT '0',
  `role` varchar(50) DEFAULT 'utilisateur',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menage`
--

LOCK TABLES `menage` WRITE;
/*!40000 ALTER TABLE `menage` DISABLE KEYS */;
INSERT INTO `menage` VALUES (1,'user1','pass1',-38,'employé'),(2,'user2','pass2',20,'utilisateur');
/*!40000 ALTER TABLE `menage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partenariat`
--

DROP TABLE IF EXISTS `partenariat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partenariat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contrat` varchar(100) DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `id_commerce` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_commerce` (`id_commerce`),
  CONSTRAINT `partenariat_ibfk_1` FOREIGN KEY (`id_commerce`) REFERENCES `commerce` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partenariat`
--

LOCK TABLES `partenariat` WRITE;
/*!40000 ALTER TABLE `partenariat` DISABLE KEYS */;
/*!40000 ALTER TABLE `partenariat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poubelle`
--

DROP TABLE IF EXISTS `poubelle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poubelle` (
  `idPoubelle` int NOT NULL AUTO_INCREMENT,
  `idMenage` int DEFAULT NULL,
  `ville` varchar(50) DEFAULT NULL,
  `quartier` varchar(50) DEFAULT NULL,
  `typePoubelle` varchar(50) DEFAULT NULL,
  `capacite` int DEFAULT NULL,
  `poids_actuel` int DEFAULT '0',
  PRIMARY KEY (`idPoubelle`),
  KEY `idMenage` (`idMenage`),
  CONSTRAINT `poubelle_ibfk_1` FOREIGN KEY (`idMenage`) REFERENCES `menage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poubelle`
--

LOCK TABLES `poubelle` WRITE;
/*!40000 ALTER TABLE `poubelle` DISABLE KEYS */;
INSERT INTO `poubelle` VALUES (1,1,'Paris','Quartier Latin','plastique',50,50),(6,NULL,'Pau','aadadad','metal',150,0);
/*!40000 ALTER TABLE `poubelle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types_dechets`
--

DROP TABLE IF EXISTS `types_dechets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types_dechets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_dechet` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_dechet` (`type_dechet`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types_dechets`
--

LOCK TABLES `types_dechets` WRITE;
/*!40000 ALTER TABLE `types_dechets` DISABLE KEYS */;
INSERT INTO `types_dechets` VALUES (4,'metal'),(3,'papier'),(1,'plastique'),(2,'verre');
/*!40000 ALTER TABLE `types_dechets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vidage`
--

DROP TABLE IF EXISTS `vidage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vidage` (
  `idVidage` int NOT NULL AUTO_INCREMENT,
  `idMenage` int NOT NULL,
  `quantite` double DEFAULT NULL,
  `momentDepot` datetime DEFAULT NULL,
  `typeDechet` varchar(50) DEFAULT NULL,
  `idCorbeille` int DEFAULT NULL,
  `idPoubelle` int DEFAULT NULL,
  PRIMARY KEY (`idVidage`),
  KEY `idMenage` (`idMenage`),
  KEY `idCorbeille` (`idCorbeille`),
  KEY `idPoubelle` (`idPoubelle`),
  CONSTRAINT `vidage_ibfk_1` FOREIGN KEY (`idMenage`) REFERENCES `menage` (`id`),
  CONSTRAINT `vidage_ibfk_2` FOREIGN KEY (`idCorbeille`) REFERENCES `corbeille` (`idCorbeille`),
  CONSTRAINT `vidage_ibfk_3` FOREIGN KEY (`idPoubelle`) REFERENCES `poubelle` (`idPoubelle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vidage`
--

LOCK TABLES `vidage` WRITE;
/*!40000 ALTER TABLE `vidage` DISABLE KEYS */;
/*!40000 ALTER TABLE `vidage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-14 15:50:58
