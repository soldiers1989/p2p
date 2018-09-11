/*
	Source Server         : 标准库-DEVELOPMENT
	Source Server Version : 50621
	Source Host           : 
	Source Database       : S65
	
	Target Server Type    : MYSQL
	Target Server Version : 50621
	File Encoding         : UTF-8
	
	Copyright (c) dimeng.net 2014
	Date  : 2014-10-11 15:02:37
*/

SET FOREIGN_KEY_CHECKS=0;




DROP TABLE IF EXISTS `S65`.`T6501`;
CREATE TABLE `S65`.`T6501` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` enum('DTJ','YTJ','DQR','CG','SB') NOT NULL DEFAULT 'DTJ',
  `F04` datetime NOT NULL,
  `F05` datetime DEFAULT NULL,
  `F06` datetime DEFAULT NULL,
  `F07` enum('XT','YH','HT') NOT NULL,
  `F08` int(10) unsigned DEFAULT NULL,
  `F09` int(10) unsigned DEFAULT NULL,
  `F10` varchar(70) DEFAULT NULL,
  `F11` enum('F','S') NOT NULL DEFAULT 'F',
  `F12` varchar(100) DEFAULT NULL,
  `F13` decimal(20,2) NOT NULL DEFAULT 0.00,
  `F14` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6502`;
CREATE TABLE `S65`.`T6502` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F06` varchar(30) DEFAULT NULL,
  `F07` int(10) unsigned NOT NULL,
  `F08` varchar(50) DEFAULT NULL,
  `F09` char(6) DEFAULT NULL,
  `F10` enum('QY','TY') DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6503`;
CREATE TABLE `S65`.`T6503` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F06` varchar(30) NOT NULL,
  `F07` int(10) unsigned DEFAULT NULL,
  `F08` varchar(50) DEFAULT NULL,
  `F09` int(11) unsigned DEFAULT NULL,
  `F10` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F11` decimal(20,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F09` (`F09`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6504`;
CREATE TABLE `S65`.`T6504` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` int(10) unsigned DEFAULT NULL,
  `F06` enum('S','F') NULL DEFAULT 'F',
  `F07` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6505`;
CREATE TABLE `S65`.`T6505` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6506`;
CREATE TABLE `S65`.`T6506` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` int(11) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6507`;
CREATE TABLE `S65`.`T6507` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6508`;
CREATE TABLE `S65`.`T6508` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6509`;
CREATE TABLE `S65`.`T6509` (
  `F01` int(10) unsigned NOT NULL ,
  `F02` int(10) unsigned NOT NULL ,
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' ,
  `F04` int(10) DEFAULT NULL ,
  `F05` varchar(50) DEFAULT NULL ,
  `F06` int(10) unsigned NOT NULL ,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

DROP TABLE IF EXISTS `S65`.`T6510`;
CREATE TABLE `S65`.`T6510` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6511`;
CREATE TABLE `S65`.`T6511` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6512`;
CREATE TABLE `S65`.`T6512` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` int(11) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6513`;
CREATE TABLE `S65`.`T6513` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) unsigned NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6514`;
CREATE TABLE `S65`.`T6514` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` int(11) NOT NULL,
  `F07` enum('F','S') NOT NULL DEFAULT 'F',
  `F08` int(10) unsigned,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6515`;
CREATE TABLE `S65`.`T6515` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6516`;
CREATE TABLE `S65`.`T6516` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6517`;
CREATE TABLE `S65`.`T6517` (
  `F01` int(10) unsigned NOT NULL,
  `F02` decimal(20,2) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` varchar(200) DEFAULT NULL,
  `F06` int(10),
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6518`;
CREATE TABLE `S65`.`T6518` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` int(10) unsigned DEFAULT NULL,
  `F06` int(10) unsigned DEFAULT 0,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6519`;
CREATE TABLE `S65`.`T6519` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S65`.`T6520`;
CREATE TABLE `S65`.`T6520` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` int(11) unsigned NOT NULL,
  `F07` int(11) NOT NULL,
  `F08` int(11) DEFAULT 0,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6521`;
CREATE TABLE `S65`.`T6521` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` int(11) unsigned NOT NULL,
  `F08` int(11) NOT NULL,
  `F09` int(11) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S65`.`T6522`;
CREATE TABLE `S65`.`T6522` (
`F01`  int(10) unsigned NOT NULL  ,
`F02`  int(10) unsigned NOT NULL ,
`F03`  int(10) unsigned NOT NULL ,
PRIMARY KEY (`F01`),
INDEX `F02` (`F02`),
INDEX `F03` (`F03`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S65`.`T6550`;
CREATE TABLE `S65`.`T6550` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` text NOT NULL,
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS S65.`T6554`;
CREATE TABLE S65.`T6554` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6523`;
CREATE TABLE `S65`.`T6523` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(9,9) unsigned NOT NULL DEFAULT '0.000000000',
  `F05` int(10) unsigned DEFAULT NULL,
  `F06` int(10) DEFAULT '0',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6524`;
CREATE TABLE `S65`.`T6524` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(9,9) unsigned NOT NULL,
  `F06` decimal(20,2) NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `S65`.`T6525`;
CREATE TABLE `S65`.`T6525` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` int(11) unsigned NOT NULL,
  `F07` int(11) NOT NULL,
  `F08` int(11) DEFAULT '0',
  `F09` int(11) DEFAULT '0',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6526`;
CREATE TABLE `S65`.`T6526` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6527`;
CREATE TABLE `S65`.`T6527` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` int(10) unsigned DEFAULT NULL,
  `F06` int(10) DEFAULT '0',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S65`.`T6528`;
CREATE TABLE `S65`.`T6528` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F04` int(10) unsigned NOT NULL ,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S65`.`T6529`;
CREATE TABLE `S65`.`T6529` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
	`F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` int(11) NOT NULL ,
  `F08` enum('S','F') NOT NULL DEFAULT 'F',
  `F09` int(10) unsigned,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS S65.`T6555`;
CREATE TABLE S65.`T6555` (
  `F01` int(10) unsigned NOT NULL ,
  `F02` int(10) unsigned NOT NULL ,
  `F03` int(10) unsigned NOT NULL ,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' ,
  `F05` int(10) unsigned DEFAULT NULL ,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS S65.`T6556`;
CREATE TABLE S65.`T6556` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL ,
  `F03` int(10) unsigned NOT NULL ,
  `F04` int(10) unsigned NOT NULL ,
  `F05` int(10) unsigned DEFAULT NULL ,
  `F06` varchar(11) DEFAULT NULL ,
  `F07` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' ,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;