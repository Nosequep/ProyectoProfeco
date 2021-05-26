-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: profeco
-- ------------------------------------------------------
-- Server version	8.0.24
USE profeco;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `calificacion`
--

DROP TABLE IF EXISTS `calificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificacion` (
  `comentario` varchar(200) NOT NULL,
  `idUsuario` int NOT NULL,
  `idComercio` int NOT NULL,
  `calificacion` int NOT NULL,
  `idCalificacion` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idCalificacion`),
  KEY `FK_idusuario_idx` (`idUsuario`),
  KEY `FK_idcomercio_calificacion_idx` (`idComercio`),
  CONSTRAINT `FK_idcomercio_calificacion` FOREIGN KEY (`idComercio`) REFERENCES `comercio` (`idcomercio`),
  CONSTRAINT `FK_idusuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificacion`
--

LOCK TABLES `calificacion` WRITE;
/*!40000 ALTER TABLE `calificacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `calificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comercio`
--

DROP TABLE IF EXISTS `comercio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comercio` (
  `idcomercio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `calificacion` int NOT NULL DEFAULT '5',
  `idusuario` int NOT NULL,
  PRIMARY KEY (`idcomercio`),
  KEY `FK_idusuario_idx` (`idusuario`),
  KEY `FK_idusuario_comercio` (`idusuario`),
  CONSTRAINT `FK_idusuario_comercio` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comercio`
--

LOCK TABLES `comercio` WRITE;
/*!40000 ALTER TABLE `comercio` DISABLE KEYS */;
INSERT INTO `comercio` VALUES (1,'Tienda de la esquina',5,0),(2,'Tienda de al lado',5,0);
/*!40000 ALTER TABLE `comercio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inconsistencias`
--

DROP TABLE IF EXISTS `inconsistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inconsistencias` (
  `precioreal` double NOT NULL,
  `idcomercio` int NOT NULL,
  `idusuario` int NOT NULL,
  `preciopublicado` double NOT NULL,
  `idproducto` int NOT NULL,
  `fecha` date NOT NULL,
  `idreporte` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idreporte`),
  KEY `FK_idusuario_inconsistencias_idx` (`idusuario`),
  KEY `FK_idproducto_inconsistencias_idx` (`idproducto`),
  KEY `FK_idcomercio_inconsistencias_idx` (`idcomercio`),
  CONSTRAINT `FK_idcomercio_inconsistencias` FOREIGN KEY (`idcomercio`) REFERENCES `comercio` (`idcomercio`),
  CONSTRAINT `FK_idproducto_inconsistencias` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`idproducto`),
  CONSTRAINT `FK_idusuario_inconsistencias` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inconsistencias`
--

LOCK TABLES `inconsistencias` WRITE;
/*!40000 ALTER TABLE `inconsistencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `inconsistencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idproducto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `precio` double NOT NULL,
  `oferta` double NOT NULL,
  `idcomercio` int NOT NULL,
  PRIMARY KEY (`idproducto`),
  KEY `FK_idcomercio_producto_idx` (`idcomercio`),
  CONSTRAINT `FK_idcomercio_producto` FOREIGN KEY (`idcomercio`) REFERENCES `comercio` (`idcomercio`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'chetis',10,0.1,1),(2,'chetis',11,0,2),(3,'Chetones',1122,0.2,1),(4,'Chetines',350,0.1,1);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sancion`
--

DROP TABLE IF EXISTS `sancion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sancion` (
  `idsancion` int NOT NULL,
  `cabecera` varchar(45) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `castigos` varchar(100) DEFAULT NULL,
  `multa` double NOT NULL,
  `idcomercio` int DEFAULT NULL,
  PRIMARY KEY (`idsancion`),
  KEY `FK_idcomercio_sancion_idx` (`idcomercio`),
  CONSTRAINT `FK_idcomercio_sancion` FOREIGN KEY (`idcomercio`) REFERENCES `comercio` (`idcomercio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sancion`
--

LOCK TABLES `sancion` WRITE;
/*!40000 ALTER TABLE `sancion` DISABLE KEYS */;
/*!40000 ALTER TABLE `sancion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idusuario` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `contrasenia` varchar(45) NOT NULL,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'profeco','profeco','profeco'),(2,'mercado','mercado','mercado'),(3,'consumidor','consumidor','consumidor');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-23 17:23:07
