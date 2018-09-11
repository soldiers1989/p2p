﻿/*
	Source Server         : 标准库-DEVELOPMENT
	Source Server Version : 50621
	Source Host           : 
	Source Database       : S62
	
	Target Server Type    : MYSQL
	Target Server Version : 50621
	File Encoding         : UTF-8
	
	Copyright (c) dimeng.net 2014
	Date  : 2014-10-11 15:02:37
*/

SET FOREIGN_KEY_CHECKS=0;




DROP TABLE IF EXISTS `S62`.`T6211`;
CREATE TABLE `S62`.`T6211` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` enum('QY','TY') NOT NULL,
  `F04` text,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6212`;
CREATE TABLE `S62`.`T6212` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` enum('QY','TY') NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6213`;
CREATE TABLE `S62`.`T6213` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(60) NOT NULL,
  `F03` enum('QY','TY') NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6214`;
CREATE TABLE `S62`.`T6214` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` varchar(60) NOT NULL,
  `F04` enum('TY','QY') NOT NULL DEFAULT 'QY',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6215`;
CREATE TABLE `S62`.`T6215` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(45) NOT NULL,
  `F03` enum('S','F') DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6216`;
CREATE TABLE `S62`.`T6216` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(100) NOT NULL,
  `F03` varchar(100) NOT NULL,
  `F04` enum('QY','TY') NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` int(10) unsigned NOT NULL,
  `F08` int(10) unsigned NOT NULL,
  `F09` decimal(20,2) unsigned NOT NULL,
  `F10` decimal(20,2) unsigned NOT NULL,
  `F11` varchar(100) DEFAULT NULL,
  `F12` decimal(20,3) DEFAULT NULL,
  `F13` decimal(20,3) DEFAULT NULL,
  `F14` decimal(20,3) DEFAULT NULL,
  `F15` decimal(20,2) DEFAULT NULL,
  `F16` int(10) unsigned NULL,
  `F17` int(10) unsigned NULL,
  `F18` enum('BSX','JSX','WJX','JQX','JJX') NOT NULL DEFAULT 'BSX',
   PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6230`;
CREATE TABLE `S62`.`T6230` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` varchar(200) NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(9,9) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` int(10) unsigned NOT NULL DEFAULT '7',
  `F09` int(10) unsigned NOT NULL,
  `F10` enum('DEBX','MYFX','YCFQ','DEBJ') NOT NULL,
  `F11` enum('S','F') NOT NULL DEFAULT 'F',
  `F12` enum('BXQEDB','BJQEDB') NOT NULL DEFAULT 'BJQEDB',
  `F13` enum('S','F') NOT NULL DEFAULT 'F',
  `F14` enum('S','F') NOT NULL DEFAULT 'F',
  `F15` enum('S','F') NOT NULL DEFAULT 'F',
  `F16` enum('S','F') NOT NULL DEFAULT 'F',
  `F17` enum('ZRY','GDR') NOT NULL,
  `F18` int(10) unsigned NOT NULL,
  `F19` int(10) unsigned NOT NULL DEFAULT '1',
  `F20` enum('SQZ','DSH','DFB','YFB','TBZ','DFK','HKZ','YDF','YZR','YJQ','YLB','YZF') NOT NULL DEFAULT 'SQZ',
  `F21` varchar(20) DEFAULT NULL,
  `F22` datetime DEFAULT NULL,
  `F23` int(10) unsigned DEFAULT NULL,
  `F24` datetime NOT NULL,
  `F25` varchar(20) NOT NULL,
  `F26` decimal(20,2) unsigned NOT NULL,
  `F27` enum('S','F') NOT NULL DEFAULT 'F',
  `F28`  enum('F','S') NOT NULL DEFAULT 'F',
  `F29`  enum('S','F') NOT NULL DEFAULT 'S',
  `F32`  int(10) NULL DEFAULT 0,
  `F33` enum('S','F') NOT NULL DEFAULT 'F',
  `F34` varchar(20) DEFAULT NULL ,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F25_UNIQUE` (`F25`),
  KEY `F02` (`F02`),
  KEY `F04` (`F04`),
  KEY `F23` (`F23`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;






DROP TABLE IF EXISTS S62.`T6230_EXT`;
CREATE TABLE S62.`T6230_EXT` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` int(11) NOT NULL,
  `F04` enum('S','F') NOT NULL DEFAULT 'F',
  `F05` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `Unique_F01_F03` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;






DROP TABLE IF EXISTS `S62`.`T6231`;
CREATE TABLE `S62`.`T6231` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(9,9) unsigned NOT NULL,
  `F05` decimal(9,9) unsigned NOT NULL,
  `F06` date DEFAULT NULL,
  `F07` mediumint(8) unsigned DEFAULT NULL,
  `F08` varchar(100) DEFAULT NULL,
  `F09` text,
  `F10` datetime DEFAULT NULL,
  `F11` datetime DEFAULT NULL,
  `F12` datetime DEFAULT NULL,
  `F13` datetime DEFAULT NULL,
  `F14` datetime DEFAULT NULL,
  `F15` datetime DEFAULT NULL,
  `F16` varchar(100) DEFAULT NULL,
  `F17` date DEFAULT NULL,
  `F18` date DEFAULT NULL,
  `F19` enum('YZYQ','S','F') NOT NULL DEFAULT 'F',
  `F20` int(10) unsigned NOT NULL DEFAULT '15',
  `F21` enum('S','F') NOT NULL DEFAULT 'F',
  `F22` int(11) unsigned zerofill DEFAULT NULL,
  `F25` decimal(20,2) NOT NULL,
  `F26` decimal(20,2) NOT NULL,
  `F27` enum('S','F') NOT NULL DEFAULT 'F',
  `F28` decimal(9,9) unsigned DEFAULT NULL,
  `F29` enum('S','F') NOT NULL DEFAULT 'F',
  `F30` datetime NULL,
  `F31` char(6) DEFAULT NULL,
  `F32` enum('QY','TY') DEFAULT 'QY',
  `F33` enum('PC_APP','PC','APP') NOT NULL DEFAULT 'PC_APP',
  `F34` datetime DEFAULT NULL,
  `F35` enum('XYD','DBD','HTXZ') NOT NULL DEFAULT 'HTXZ' ,
  `F36`  enum('ALL','SINGLE','NONE') DEFAULT 'ALL' ,
  PRIMARY KEY (`F01`),
  KEY `F07` (`F07`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6232`;
CREATE TABLE `S62`.`T6232` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` varchar(20) NOT NULL,
  `F05` varchar(60) NOT NULL,
  `F06` int(10) unsigned NOT NULL,
  `F07` varchar(20) DEFAULT NULL,
  `F08` datetime NOT NULL,
  `F09` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F09_idx` (`F09`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6233`;
CREATE TABLE `S62`.`T6233` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` varchar(60) NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` varchar(20) NOT NULL,
  `F07` varchar(20) DEFAULT NULL,
  `F08` datetime NOT NULL,
  `F09` int(10) unsigned DEFAULT NULL,
  `F10` enum('F','S') NOT NULL DEFAULT 'F',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F09` (`F09`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6234`;
CREATE TABLE `S62`.`T6234` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` varchar(60) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6235`;
CREATE TABLE `S62`.`T6235` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) DEFAULT NULL,
  `F03` int(10) DEFAULT NULL,
  `F04` text,
  `F05` int(10) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6236`;
CREATE TABLE `S62`.`T6236` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` enum('S','F') NOT NULL,
  `F05` text,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6237`;
CREATE TABLE `S62`.`T6237` (
  `F01` int(10) NOT NULL,
  `F02` text,
  `F03` text,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6238`;
CREATE TABLE `S62`.`T6238` (
  `F01` int(10) unsigned NOT NULL,
  `F02` decimal(9,9) NOT NULL,
  `F03` decimal(9,9) NOT NULL,
  `F04` decimal(9,9) NOT NULL,
  `F05` decimal(9,9) NOT NULL DEFAULT '0.000000000',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6239`;
CREATE TABLE `S62`.`T6239` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(11) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6240`;
CREATE TABLE `S62`.`T6240` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) NOT NULL,
  `F05` varchar(200) NOT NULL,
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F07` decimal(20,2) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6241`;
CREATE TABLE `S62`.`T6241` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` datetime NOT NULL,
  `F05` enum('YCL','WCL','YZF') NOT NULL DEFAULT 'WCL',
  `F06` text,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6242`;
CREATE TABLE `S62`.`T6242` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` varchar(200) NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` int(10) unsigned NOT NULL DEFAULT 0 DEFAULT '7',
  `F09` int(10) unsigned NOT NULL DEFAULT 0,
  `F10` enum('S','F') NOT NULL DEFAULT 'F',
  `F11` enum('SQZ','DSH','DFB','JKZ','YJZ','YZF') NOT NULL DEFAULT 'SQZ',
  `F12` varchar(20) DEFAULT NULL,
  `F13` datetime DEFAULT NULL,
  `F14` int(10) unsigned DEFAULT NULL,
  `F15` datetime NOT NULL,
  `F16` datetime DEFAULT NULL,
  `F17` datetime DEFAULT NULL,
  `F18` datetime DEFAULT NULL,
  `F19` datetime DEFAULT NULL,
  `F20` datetime DEFAULT NULL,
  `F21` varchar(20) NOT NULL,
  `F22` varchar(20) NOT NULL,
  `F23` varchar(20) NOT NULL,
  `F24` varchar(500)  NULL,
  `F25` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6242.F21_UNIQUE` (`F21`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6243`;
CREATE TABLE `S62`.`T6243` (
  `F01` int(10) unsigned NOT NULL,
  `F02` longtext,
  `F03` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6244`;
CREATE TABLE `S62`.`T6244` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(11) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6245`;
CREATE TABLE `S62`.`T6245` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` varchar(50) DEFAULT NULL,
  `F05` enum('S','F') NOT NULL DEFAULT 'F',
  `F06` varchar(600) DEFAULT NULL,
  `F07` datetime DEFAULT NULL,
  `F08` datetime NOT NULL,
  `F09` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6246`;
CREATE TABLE `S62`.`T6246` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` datetime NOT NULL,
  `F07` enum('F','S') NOT NULL DEFAULT 'F',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6247`;
CREATE TABLE `S62`.`T6247` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` datetime NOT NULL,
  `F05` enum('YCL','WCL') NOT NULL DEFAULT 'WCL',
  `F06` text,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6248`;
CREATE TABLE `S62`.`T6248` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT ,
  `F02` int(10) unsigned NOT NULL ,
  `F03` int(10) unsigned NOT NULL ,
  `F04` varchar(50) DEFAULT NULL ,
  `F05` enum('YFB','WFB') NOT NULL DEFAULT 'YFB' ,
  `F06` varchar(600) DEFAULT NULL ,
  `F07` datetime DEFAULT NULL ,
  `F08` datetime NOT NULL ,
  `F09` varchar(100) DEFAULT NULL ,
  PRIMARY KEY (`F01`),
  KEY `F03_idx` (`F03`),
  KEY `F04_idx` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ;


DROP TABLE IF EXISTS `S62`.`T6250`;
CREATE TABLE `S62`.`T6250` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` datetime NOT NULL,
  `F07` enum('F','S') NOT NULL DEFAULT 'F',
  `F08` enum('F','S') NOT NULL DEFAULT 'F',
  `F09` enum('F','S') NOT NULL DEFAULT 'F',
  `F10` varchar(30) DEFAULT NULL,
  `F11` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC',
  `F12` char(6) DEFAULT NULL,
  `F13` enum('QY','TY') DEFAULT 'QY',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6251`;
CREATE TABLE `S62`.`T6251` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` enum('S','F') NOT NULL DEFAULT 'F',
  `F09` date NOT NULL,
  `F10` date NOT NULL,
  `F11` int(11) unsigned NOT NULL,
  `F12` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02_UNIQUE` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F04_idx` (`F04`),
  KEY `F11_idx` (`F11`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6252`;
CREATE TABLE `S62`.`T6252` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` int(10) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` date NOT NULL,
  `F09` enum('WH','HKZ','YH', 'TQH', 'DF') NOT NULL,
  `F10` datetime DEFAULT NULL,
  `F11` int(11) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F05_2` (`F05`,`F06`,`F11`),
  KEY `F02_idx` (`F02`),
  KEY `F05_idx` (`F05`),
  KEY `F04_idx` (`F04`),
  KEY `F03_idx` (`F03`) USING BTREE,
  KEY `F11_idx` (`F11`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS S62.`T6252_EXT`;
CREATE TABLE S62.`T6252_EXT` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL ,
  `F03` varchar(200) DEFAULT NULL,
  `F04` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6253`;
CREATE TABLE `S62`.`T6253` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(11) DEFAULT NULL,
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F06` decimal(20,2) unsigned DEFAULT '0.00',
  `F07` datetime NOT NULL,
  `F08` INT NOT NULL DEFAULT '0',
  `F09`  VARCHAR(20) DEFAULT NULL,
  `F10` VARCHAR(20) DEFAULT NULL,
  `F11` int(11) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F04_idx` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6255`;
CREATE TABLE `S62`.`T6255` (
  `F01` int(10) NOT NULL AUTO_INCREMENT ,
  `F02` int(10) NOT NULL ,
  `F03` decimal(10,2) NOT NULL ,
  `F04` int(10) NOT NULL ,
  `F05` int(10) NOT NULL ,
  `F06` int(10) NOT NULL ,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6256`;
CREATE TABLE `S62`.`T6256` (
`F01`  int(11) UNSIGNED NOT NULL  ,
`F02`  int(11) NOT NULL ,
PRIMARY KEY (`F01`)
)
ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;


DROP TABLE IF EXISTS `S62`.`T6260`;
CREATE TABLE `S62`.`T6260` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` datetime NOT NULL,
  `F06` date DEFAULT NULL,
  `F07` enum('DSH','ZRZ','YJS','YXJ','YQX') NOT NULL,
  `F08` decimal(9,9) NOT NULL DEFAULT '0.000000000',
  `F09` INT(10) DEFAULT NULL ,
  `F10` DECIMAL(20,2) DEFAULT NULL,
  `F11` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6261`;
CREATE TABLE `S62`.`T6261` (
  `F01` int(11) unsigned NOT NULL,
  `F02` int(11) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6262`;
CREATE TABLE `S62`.`T6262` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` datetime NOT NULL,
  `F08` decimal(20,2) NOT NULL,
  `F09` decimal(20,2) NOT NULL,
  `F10` int(11) DEFAULT '0',
  `F11` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6263`;
CREATE TABLE `S62`.`T6263` (
  `F01` int(10) NOT NULL,
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F05` int(10) NOT NULL DEFAULT '0',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F08` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6264`;
CREATE TABLE `S62`.`T6264` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` enum('DSH','ZRZ','YZR','YXJ','ZRSB') NOT NULL,
  `F05` int(10) unsigned DEFAULT NULL,
  `F06` int(10) unsigned NOT NULL,
  `F07` datetime NOT NULL,
  `F08` datetime NOT NULL,
  `F09` decimal(20,2) DEFAULT NULL,
  `F10` decimal(20,2) DEFAULT NULL,
  `F11` varchar(60) DEFAULT NULL,
  `F12` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6265`;
CREATE TABLE `S62`.`T6265` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
	`F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` datetime NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6266`;
CREATE TABLE `S62`.`T6266` (
  `F01` int(10) NOT NULL AUTO_INCREMENT,
  `F02` int(10) NOT NULL,
  `F03` decimal(10,2) NOT NULL,
  `F04` int(10) NOT NULL,
  `F05` int(10) NOT NULL,
  `F06` int(10) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6267`;
CREATE TABLE `S62`.`T6267` (
  `F01` int(11) unsigned NOT NULL,
  `F02` int(11) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6270`;
CREATE TABLE `S62`.`T6270` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` datetime NOT NULL,
  `F05` enum('DSH','YHK','BTG') NOT NULL,
  `F06` int(10) unsigned DEFAULT NULL,
  `F07` datetime DEFAULT NULL,
  `F08` varchar(100) DEFAULT NULL,
  `F09` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S62`.`T6271`;
CREATE TABLE `S62`.`T6271` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) DEFAULT NULL,
  `F03` int(11) DEFAULT NULL,
  `F04` varchar(32) DEFAULT NULL,
  `F05` varchar(32) DEFAULT NULL,
  `F06` datetime DEFAULT NULL,
  `F07` enum('WBQ','YBQ') DEFAULT 'WBQ',
  `F08` enum('BLZQZRHT','DFHT','ZQZRHT','JKHT') DEFAULT 'JKHT',
  `F09` varchar(128) DEFAULT NULL ,
  `F10` enum('BDFR','DFR','SRR','ZCR','TZR','JKR') DEFAULT NULL ,
  `F11` int(11) DEFAULT NULL ,
  `F12` int(11) DEFAULT NULL,
  `F13` int(11) DEFAULT NULL,
  `F14` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ;

DROP TABLE IF EXISTS `S62`.`T6272`;
CREATE TABLE `S62`.`T6272` (
`F01`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`F02`  int(10) UNSIGNED NOT NULL,
`F03`  int(10) UNSIGNED NOT NULL,
`F04`  varchar(10) DEFAULT NULL,
`F05`  datetime NULL DEFAULT NULL,
`F06`  enum('WBQ','YBQ') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`F07`  varchar(128) DEFAULT NULL,
`F08` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`F01`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


DROP TABLE IF EXISTS `S62`.`T6280`;
CREATE TABLE `S62`.`T6280` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` decimal(20,2) unsigned DEFAULT '0.00',
  `F03` decimal(20,4) unsigned DEFAULT '0.00',
  `F04` decimal(20,4) unsigned DEFAULT '0.00',
  `F05` int(11) unsigned DEFAULT '0',
  `F06` int(11) unsigned DEFAULT '0',
  `F07` int(11) unsigned DEFAULT NULL,
  `F08` int(11) unsigned DEFAULT NULL,
  `F09` decimal(20,2) unsigned DEFAULT NULL,
  `F10` enum('QY','TY') DEFAULT 'TY',
  `F11` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `F12` int(10) NOT NULL,
  `F13` int(2) NULL DEFAULT 1,
  `F14` varchar(20) NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6281`;
CREATE TABLE `S62`.`T6281` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) unsigned DEFAULT NULL,
  `F03` varchar(100) NOT NULL,
  `F04` varchar(20) NOT NULL,
  `F05` varchar(45) NOT NULL,
  `F06` varchar(20) NOT NULL,
  `F07` char(18) DEFAULT NULL,
  `F08` varchar(200) DEFAULT NULL,
  `F09` decimal(20,2) unsigned NOT NULL,
  `F10` int(11) unsigned DEFAULT NULL,
  `F11` mediumint(8) unsigned DEFAULT NULL,
  `F12` varchar(50) DEFAULT NULL,
  `F13` varchar(500) DEFAULT NULL,
  `F14` enum('WCL','YCL') DEFAULT 'WCL',
  `F15` int(11) unsigned DEFAULT NULL,
  `F16` datetime NOT NULL,
  `F17` datetime DEFAULT NULL,
  `F18` enum('S','F') NOT NULL DEFAULT 'F',
  `F19` enum('S','F') NOT NULL DEFAULT 'F',
  `F20` enum('S','F') NOT NULL DEFAULT 'F',
  `F21` varchar(255) DEFAULT NULL,
  `F22` int(10) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F10_idx` (`F10`),
  KEY `F11_idx` (`F11`),
  KEY `F15_idx` (`F15`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6282`;
CREATE TABLE `S62`.`T6282` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) unsigned DEFAULT '0',
  `F03` varchar(45) NOT NULL,
  `F04` varchar(20) NOT NULL,
  `F05` char(18) DEFAULT NULL,
  `F06` decimal(20,2) NOT NULL,
  `F07` int(11) unsigned DEFAULT NULL,
  `F08` mediumint(8) unsigned DEFAULT NULL,
  `F09` varchar(50) DEFAULT NULL,
  `F10` varchar(500) DEFAULT NULL,
  `F11` enum('WCL','YCL') DEFAULT 'WCL',
  `F12` int(11) unsigned DEFAULT NULL,
  `F13` datetime NOT NULL,
  `F14` datetime DEFAULT NULL,
  `F15` enum('S','F') NOT NULL DEFAULT 'F',
  `F16` enum('S','F') NOT NULL DEFAULT 'F',
  `F17` enum('S','F') NOT NULL DEFAULT 'F',
  `F18` varchar(225) DEFAULT NULL,
  `F19` int(10) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F07_idx` (`F07`),
  KEY `F08_idx` (`F08`),
  KEY `F12_idx` (`F12`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6283`;
CREATE TABLE `S62`.`T6283` (
  `F01` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned zerofill DEFAULT NULL,
  `F03` longblob NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6284`;
CREATE TABLE `S62`.`T6284` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(45) NOT NULL,
  `F03` varchar(20) DEFAULT NULL,
  `F04` varchar(20) DEFAULT NULL,
  `F05` varchar(100) DEFAULT NULL,
  `F06` int(11) NOT NULL,
  `F07` varchar(500) DEFAULT NULL,
  `F08` enum('WCL','YCL') DEFAULT 'WCL',
  `F09` int(11) DEFAULT NULL,
  `F10` datetime NOT NULL,
  `F11` datetime DEFAULT NULL,
  `F12` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S62`.`T6285`;
CREATE TABLE `S62`.`T6285` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` int(10) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F11` decimal(20,2) unsigned NOT NULL,
  `F08` date NOT NULL,
  `F09` enum('WFH','YFH','FHZ') NOT NULL,
  `F10` datetime DEFAULT NULL,
  `F12` int(11) DEFAULT 0,
  PRIMARY KEY (`F01`),
  KEY `F02_UNIQUE` (`F02`,`F04`,`F06`),
  KEY `F05` (`F05`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6286`;
CREATE TABLE `S62`.`T6286` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` datetime NOT NULL,
  `F06` enum('F','S') NOT NULL DEFAULT 'F',
  `F07` enum('F','S') NOT NULL DEFAULT 'F',
  `F08` enum('F','S') NOT NULL DEFAULT 'F',
  `F09` int(10) DEFAULT NULL ,
  `F10` int(10) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6288`;
CREATE TABLE `S62`.`T6288` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(9,9) unsigned NOT NULL,
  `F05` datetime NOT NULL,
  `F06` enum('F','S') NOT NULL DEFAULT 'F',
  `F07` enum('F','S') NOT NULL DEFAULT 'F',
  `F08` enum('F','S') NOT NULL DEFAULT 'F',
  `F09` int(10) DEFAULT NULL,
  `F10` int(10) DEFAULT NULL,
  `F11` decimal(20,2) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6289`;
CREATE TABLE `S62`.`T6289` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` int(10) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` date NOT NULL,
  `F09` enum('WFH','YFH','YSX') NOT NULL,
  `F10` datetime DEFAULT NULL,
  `F11` decimal(20,2) DEFAULT NULL,
  `F12` int(11) DEFAULT '0',
  `F13` int(11) DEFAULT '0',
  PRIMARY KEY (`F01`),
  KEY `F05` (`F05`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE,
  KEY `F02_UNIQUE` (`F02`,`F04`,`F06`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6290`;
CREATE TABLE `S62`.`T6290` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `F02` VARCHAR(30) NOT NULL,
  `F03` INT(4) NOT NULL,
  `F04` ENUM('HKTX','YQTX') NOT NULL,
  `F05` DATETIME NOT NULL,
  `F06` ENUM('QY','TY') NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS S62.`T6291`;
CREATE TABLE S62.`T6291` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` datetime NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S62`.`T6292`;
CREATE TABLE `S62`.`T6292` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` datetime NOT NULL,
  `F06` enum('F','S') NOT NULL DEFAULT 'F',
  `F07` enum('F','S') NOT NULL DEFAULT 'F',
  `F08` enum('F','S') NOT NULL DEFAULT 'F',
  `F09` int(10) DEFAULT NULL,
  `F10` int(10) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `S62`.`T6299` (
`F01`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
`F02`  int(11) NOT NULL,
`F03`  int(11) NOT NULL,
`F04`  enum('JKQX','RZJD','NHSY') CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`F05`  timestamp NULL DEFAULT NULL,
PRIMARY KEY (`F01`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT;


DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_DFK`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_DFK`()
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE 		_F01     INT     DEFAULT 0; -- 鏍嘔D
		DECLARE 		_stop_credit							   	INT   				 DEFAULT 0;				-- 鏄惁杈惧埌璁板綍鐨勬湯灏炬帶鍒跺彉閲�
		DECLARE 		_error 					            	INT 					 DEFAULT 0;
		DECLARE _cur_credit CURSOR FOR SELECT F01 FROM S62.T6230 WHERE F20 = 'TBZ' AND F22 IS NOT NULL AND DATE_ADD(F22,INTERVAL F08 DAY) < CURRENT_TIMESTAMP() AND F05 != F07;
		
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN _cur_credit;
			REPEAT
				FETCH _cur_credit INTO _F01;
				IF P_ID IS NULL THEN
					SET _stop_credit = 1;
				END IF;
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S62.T6230 SET F20 = 'DFK' WHERE F01=_F01;
						UPDATE S62.T6231 SET F11=CURRENT_TIMESTAMP(),F29 = 'F',F30=null WHERE F01=_F01;
						IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE _cur_credit;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;




DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YFB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YFB`()
   
BEGIN
UPDATE S62.T6230 SET F20 = 'TBZ' WHERE F20 = 'YFB' AND F22 IS NOT NULL AND F22 <= CURRENT_TIMESTAMP();
END
;;
DELIMITER ;




DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YQ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YQ`()
   
BEGIN
	DECLARE P_ID INT;
	DECLARE _loanId INT;
	DECLARE _qh INT;
	DECLARE _yh_time DATE;
	DECLARE _yq_ts INT DEFAULT 0;
	DECLARE _stop_loan INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _loan_yq CURSOR FOR SELECT F02,F06,F08 FROM S62.T6252 WHERE T6252.F08 < CURDATE() AND T6252.F09 = 'WH' GROUP BY F02,F06;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_loan = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
	OPEN _loan_yq;
	REPEAT
		FETCH _loan_yq INTO _loanId, _qh,_yh_time;
		IF P_ID IS NULL THEN
				SET _stop_loan = 1;
		END IF;
		IF _stop_loan = 0 THEN
				START TRANSACTION;
				SET _yq_ts = TO_DAYS(CURDATE())-TO_DAYS(T6252.F08);
				
				IF _yq_ts > 30 THEN
					UPDATE S62.S6231 SET F19 = 'YZYQ' WHERE F01 = _loanId;
				ELSE 
					UPDATE S62.S6231 SET F19 = 'S' WHERE F01 = _loanId;
				END IF;
				
				
				
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
   UNTIL _stop_loan END REPEAT;
		CLOSE _loan_yq;
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YLB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YLB`()
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE 		_F01     INT     DEFAULT 0;
		DECLARE 		_F02     decimal(20,2)     DEFAULT 0.00;
		DECLARE 		_F03     char(1)     DEFAULT 0;
		DECLARE 		_F04     INT     DEFAULT 0; 
		DECLARE 		_F05     INT     DEFAULT 0;
		DECLARE 		_F06     decimal(20,2)     DEFAULT 0.00; 
		DECLARE 		_F07     decimal(20,2)     DEFAULT 0.00;
		DECLARE 		_stop_credit							   	INT   				 DEFAULT 0;
		DECLARE 		_error 					            	INT 					 DEFAULT 0;
		DECLARE _cur_credit CURSOR FOR SELECT F01, F02, F05, F11 FROM S62.T6230 WHERE F20 = 'TBZ' AND F22 IS NOT NULL AND DATE_ADD(F22,INTERVAL F08 DAY) < CURRENT_TIMESTAMP() AND F05 = F07;
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN _cur_credit;
			REPEAT
				FETCH _cur_credit INTO _F01,_F04,_F02,_F03;
				IF P_ID IS NULL THEN
					SET _stop_credit = 1;
				END IF;
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S62.T6230 SET F20 = 'YLB' WHERE F01=_F01 ;
						UPDATE S62.T6231 SET F11=CURRENT_TIMESTAMP(),F29 = 'F',F30=null WHERE F01=_F01;
							SELECT F03 INTO _F07 FROM S61.T6116 WHERE F01 = _F04;
							UPDATE S61.T6116 SET F03 = _F07+_F02 WHERE F01 = _F04;
							INSERT INTO S61.T6117 SET F02 = _F04, F03 = '1103', F04 = CURRENT_TIMESTAMP(), F05 = _F02, F07 = _F07+_F02, F08 = '自动流标信用额度返还';
						
						IF _F03 = 'S' THEN
							SELECT F03 INTO _F05 FROM S62.T6236 WHERE F02 = _F01;
							SELECT F04 INTO _F06 FROM S61.T6125 WHERE F02 = _F05;
							UPDATE S61.T6125 SET F04 = _F06+_F02 WHERE F02 = _F05;
							INSERT INTO S61.T6126 SET F02 = _F05, F03 = '1302', F04 = CURRENT_TIMESTAMP(), F05 = _F02, F07 = _F06+_F02, F08 = '自动流标担保额度返还';
						ELSE
							IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE _cur_credit;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `S62`.`SP_T6242_YJZ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6242_YJZ`()

BEGIN
	DECLARE P_ID INT DEFAULT 0;
	DECLARE _actId INT DEFAULT 0;
	DECLARE _stop_act INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _t6242s CURSOR FOR SELECT F01 FROM S62.T6242 WHERE F19 IS NULL AND F13 IS NOT NULL AND DATE_ADD(F13,INTERVAL F08 - 1 DAY) < CURRENT_DATE();
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_act = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	OPEN _t6242s;
	read_loop:LOOP
		FETCH _t6242s INTO _actId;
    SELECT _actId,_stop_act,P_ID,_error;
    IF P_ID IS NULL THEN
		  SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		IF _stop_act = 0 THEN
				START TRANSACTION;
					UPDATE S62.T6242 SET F11 = 'YJZ',F19 = NOW() WHERE F01 = _actId;
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
  END LOOP;
	CLOSE _t6242s;
END
;;
DELIMITER ;


DROP EVENT IF EXISTS `S62`.`EVT_T6230_DFK`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6230_DFK` ON SCHEDULE EVERY 10 SECOND STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230_DFK()
;;
DELIMITER ;




DROP EVENT IF EXISTS `S62`.`EVT_T6230_YFB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6230_YFB` ON SCHEDULE EVERY 1 SECOND STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230_YFB()
;;
DELIMITER ;

DROP EVENT IF EXISTS `S62`.`EVT_T6230_YLB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6230_YLB` ON SCHEDULE EVERY 5 SECOND STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230_YLB()
;;
DELIMITER ;

DROP EVENT IF EXISTS `S62`.`EVT_T6242_YJZ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6242_YJZ` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-08 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6242_YJZ()
;;
DELIMITER ;