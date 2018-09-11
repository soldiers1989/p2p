/*
	Source Server         : 标准库-DEVELOPMENT
	Source Server Version : 50621
	Source Host           : 
	Source Database       : S51
	
	Target Server Type    : MYSQL
	Target Server Version : 50621
	File Encoding         : UTF-8
	
	Copyright (c) dimeng.net 2014
	Date  : 2014-10-11 15:02:37
*/

SET FOREIGN_KEY_CHECKS=0;




DROP TABLE IF EXISTS `S51`.`T5122`;
CREATE TABLE `S51`.`T5122` (
  `F01` int(10) unsigned NOT NULL,
  `F02` varchar(20) NOT NULL,
  `F03` enum('QY','TY') NOT NULL,
  `F04` enum('yes','no') DEFAULT 'yes',
  `F05` enum('yes','no') DEFAULT 'yes',
  `F06` enum('yes','no') DEFAULT 'yes',
  `F07` enum('yes','no') DEFAULT 'yes',
  `F08` enum('yes','no') DEFAULT 'yes',
  `F09` enum('yes','no') DEFAULT 'yes',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `S51`.`T5123`;
CREATE TABLE `S51`.`T5123` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` enum('S','F') NOT NULL,
  `F04` enum('QY','TY') NOT NULL,
  `F05` int(10) unsigned NOT NULL DEFAULT '0',
  `F06`  enum('GR','QY') NOT NULL DEFAULT 'GR',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S51`.`T5124`;
CREATE TABLE `S51`.`T5124` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` enum('QY','TY') NOT NULL DEFAULT 'QY',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S51`.`T5125`;
CREATE TABLE `S51`.`T5125` (
  `F01` int(11) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` varchar(50) NOT NULL,
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S51`.`T5126`;
CREATE TABLE `S51`.`T5126` (
  `F01` int(11) unsigned NOT NULL,
  `F02` int(10) unsigned NOT NULL,
  `F03` text,
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S51`.`T5127`;
CREATE TABLE `S51`.`T5127` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` enum('TZ','JK') NOT NULL,
  `F03` enum('YJ','EJ','SJ','SIJ','WJ','LJ','QJ') NOT NULL,
  `F04` decimal(20,2) NOT NULL,
  `F05` decimal(20,2) NOT NULL,
  `F06` enum('QY','TY') NOT NULL DEFAULT 'QY',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S51`.`T5129`;
CREATE TABLE `S51`.`T5129` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` date NOT NULL,
  `F03` enum('BDBH','HTBH','GYBDBH') NOT NULL,
  `F04` int(11) NOT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02_UNIQUE` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S51`.`T5131`;
CREATE TABLE `S51`.`T5131` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` enum('N','BJ','BX') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S51`.`T5132`;
CREATE TABLE `S51`.`T5132` (
  `F01` INT(10) UNSIGNED NOT NULL,
  `F02` VARCHAR(20) NOT NULL,
  `F03` ENUM('QY','TY') NOT NULL,
  `F04` ENUM('yes','no') DEFAULT 'yes',
  `F05` ENUM('yes','no') DEFAULT 'yes',
  `F06` ENUM('yes','no') DEFAULT 'yes',
  `F07` ENUM('yes','no') DEFAULT 'yes',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单类型';