-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: XL_db
-- ------------------------------------------------------
-- Server version	5.6.27-0ubuntu0.15.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BackupLocalContacts`
--

DROP TABLE IF EXISTS `BackupLocalContacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BackupLocalContacts` (
  `bid` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL DEFAULT '0',
  `name` varchar(20) NOT NULL DEFAULT '',
  `contact` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BackupLocalContacts`
--

LOCK TABLES `BackupLocalContacts` WRITE;
/*!40000 ALTER TABLE `BackupLocalContacts` DISABLE KEYS */;
INSERT INTO `BackupLocalContacts` VALUES (1,1,'1','1'),(2,2,'2','2'),(3,3,'3','3'),(4,5,'wang','18829292929'),(5,5,'wei','17728282828'),(6,5,'wang','18829292929'),(7,5,'wei','17728282828');
/*!40000 ALTER TABLE `BackupLocalContacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRcode`
--

DROP TABLE IF EXISTS `QRcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRcode` (
  `qid` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `expir_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `authority` int(16) DEFAULT '7',
  `time_out` int(8) NOT NULL,
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRcode`
--

LOCK TABLES `QRcode` WRITE;
/*!40000 ALTER TABLE `QRcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRcode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserContact`
--

DROP TABLE IF EXISTS `UserContact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserContact` (
  `uid` int(10) NOT NULL,
  `type` int(20) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `cid` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `uid` (`uid`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserContact`
--

LOCK TABLES `UserContact` WRITE;
/*!40000 ALTER TABLE `UserContact` DISABLE KEYS */;
INSERT INTO `UserContact` VALUES (5,1,'123123123',1);
/*!40000 ALTER TABLE `UserContact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserFriend`
--

DROP TABLE IF EXISTS `UserFriend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserFriend` (
  `uid` int(10) NOT NULL,
  `friendId` int(10) NOT NULL,
  `isUpdate` int(5) DEFAULT '0',
  `remark` varchar(20) DEFAULT '',
  `fid` int(10) NOT NULL AUTO_INCREMENT,
  `authority` int(10) DEFAULT '0',
  PRIMARY KEY (`fid`),
  UNIQUE KEY `uid` (`uid`,`friendId`),
  UNIQUE KEY `uid_2` (`uid`,`friendId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserFriend`
--

LOCK TABLES `UserFriend` WRITE;
/*!40000 ALTER TABLE `UserFriend` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserFriend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserInfo`
--

DROP TABLE IF EXISTS `UserInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserInfo` (
  `account` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT '',
  `head` int(8) DEFAULT '0',
  `uid` int(10) NOT NULL AUTO_INCREMENT,
  `question` varchar(80) DEFAULT '',
  `answer` varchar(80) DEFAULT '',
  `addquestion` varchar(80) DEFAULT '',
  `addanswer` varchar(80) DEFAULT '',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UQ_account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserInfo`
--

LOCK TABLES `UserInfo` WRITE;
/*!40000 ALTER TABLE `UserInfo` DISABLE KEYS */;
INSERT INTO `UserInfo` VALUES ('wangweihao','123456789','',0,1,'','','who','me'),('zhuchenguang','123456789','',0,2,'','','who','me'),('zhuchen','123456789','hahahahaha',1,3,'','','who','me'),('1','1','',0,4,'1','1','who','me'),('zhu','123','',0,5,'you phone','5029054897','who','e'),('2','1','',0,6,'1','1','','');
/*!40000 ALTER TABLE `UserInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-20 19:45:54
