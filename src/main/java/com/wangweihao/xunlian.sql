-- MySQL dump 10.13  Distrib 5.6.25, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: XL_db
-- ------------------------------------------------------
-- Server version	5.6.25-0ubuntu0.15.04.1

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRcode`
--

LOCK TABLES `QRcode` WRITE;
/*!40000 ALTER TABLE `QRcode` DISABLE KEYS */;
INSERT INTO `QRcode` VALUES (1,1,'2015-09-13 03:04:52',4,30),(3,2,'2015-09-13 03:21:15',20,30),(7,2,'2015-10-27 05:50:47',127,30),(8,6,'2015-10-27 05:52:07',127,30),(9,2,'2015-10-27 06:03:19',0,0),(10,2,'2015-10-27 06:03:51',0,0),(11,2,'2015-10-27 06:03:55',0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserContact`
--

LOCK TABLES `UserContact` WRITE;
/*!40000 ALTER TABLE `UserContact` DISABLE KEYS */;
INSERT INTO `UserContact` VALUES (1,1,'188292929',1),(1,2,'188292929',2),(1,4,'188292929',3),(1,8,'5788@qq.com',4),(1,16,'123123@qq.com',5),(1,32,'12312123@qq.com',6),(1,64,'1123123',7),(1,128,'112312312weibo',8),(2,1,'123123123',9),(2,2,'123123123',10),(2,4,'123123123',11),(2,8,'123123123',12),(2,16,'123123123',13),(2,32,'123123123',14),(2,64,'123123123',15),(2,128,'123123123',16),(3,1,'123123123',17),(3,2,'123123123',18),(3,4,'18829292929',19),(3,8,'123123123',20),(3,16,'123123123',21),(3,32,'123123123',22),(3,64,'123123123',23),(3,128,'123123123',24),(4,1,'123123123',25),(4,2,'123123123',26),(4,4,'123123123',27),(4,8,'123123123',28),(4,16,'123123123',29),(4,32,'123123123',30),(4,64,'123123123',31),(4,128,'123123123',32),(5,1,'123123123',33),(5,2,'123123123',34),(5,4,'123123123',35),(5,8,'123123123',36),(5,16,'123123123',37),(5,32,'123123123',38),(5,64,'123123123',39),(5,128,'123123123',40),(3,256,'18829292929',41);
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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserFriend`
--

LOCK TABLES `UserFriend` WRITE;
/*!40000 ALTER TABLE `UserFriend` DISABLE KEYS */;
INSERT INTO `UserFriend` VALUES (1,2,16,'',1,0),(1,4,0,'',3,0),(2,4,0,'',6,0),(3,2,16,'',7,0),(3,4,16,'',8,0),(4,2,0,'',9,0),(2,5,0,'',38,0),(4,5,0,'',41,0),(4,6,0,'',47,127),(1,5,0,'',51,30);
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
  `head` blob,
  `uid` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UQ_account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserInfo`
--

LOCK TABLES `UserInfo` WRITE;
/*!40000 ALTER TABLE `UserInfo` DISABLE KEYS */;
INSERT INTO `UserInfo` VALUES ('1111','weihao','1111','1111',1),('2222','2222','2222','2222',2),('3333','3333','3333','3333',3),('4444','4444','4444','4444',4),('5555','5555','5555','5555',5),('6666','6666','6666','6666',6),('7777','7777','7777','7777',7),('8888','8888','8888','8888',8),('9999','9999','9999','9999',9),('10101010','123123123','',NULL,11);
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

-- Dump completed on 2015-10-27 15:48:42