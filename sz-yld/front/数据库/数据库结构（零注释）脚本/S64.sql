/*
	Source Server         : 标准库-DEVELOPMENT
	Source Server Version : 50621
	Source Host           : 
	Source Database       : S64
	
	Target Server Type    : MYSQL
	Target Server Version : 50621
	File Encoding         : UTF-8
	
	Copyright (c) dimeng.net 2014
	Date  : 2014-10-11 15:02:37
*/

SET FOREIGN_KEY_CHECKS=0;




DROP TABLE IF EXISTS `S64`.`T6410`;
CREATE TABLE `S64`.`T6410` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(45) NOT NULL,
  `F03` decimal(20,2) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` decimal(6,6) NOT NULL,
  `F06` int(11) unsigned NOT NULL,
  `F07` enum('XJ','YFB','YSX','YJZ') NOT NULL DEFAULT 'XJ',
  `F08` int(11) unsigned NOT NULL,
  `F09` datetime NOT NULL,
  `F10` date NOT NULL,
  `F11` int(11) unsigned NOT NULL,
  `F12` datetime DEFAULT NULL,
  `F13` date DEFAULT NULL,
  `F14` enum('DEBX','MYHXDQHB','YCXHBFX') NOT NULL,
  `F15` decimal(6,6) unsigned NOT NULL,
  `F16` decimal(6,6) unsigned NOT NULL,
  `F17` decimal(6,6) unsigned NOT NULL,
  `F18` varchar(200) NOT NULL,
  `F19` int(11) unsigned NOT NULL,
  `F20` datetime NOT NULL,
  `F21` date DEFAULT NULL,
  `F22` decimal(20,2) unsigned NOT NULL,
  `F23` decimal(20,2) unsigned NOT NULL,
  `F24` enum('QEBXBZ') NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F06_idx` (`F06`),
  KEY `F19_idx` (`F19`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S64`.`T6411`;
CREATE TABLE `S64`.`T6411` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F06` datetime NOT NULL,
  `F07` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`,`F03`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S64`.`T6412`;
CREATE TABLE `S64`.`T6412` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` int(10) unsigned NOT NULL,
  `F05` int(10) unsigned NOT NULL,
  `F06` int(10) unsigned NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` date NOT NULL,
  `F09` enum('WH','YH') NOT NULL,
  `F10` datetime DEFAULT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02_UNIQUE` (`F02`,`F03`,`F04`,`F05`,`F06`),
  KEY `F05` (`F05`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S64`.`T6413`;
CREATE TABLE `S64`.`T6413` (
  `F01` int(10) unsigned NOT NULL,
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F04` decimal(3,3) NOT NULL DEFAULT '0.000',
  `F05` int(10) NOT NULL DEFAULT '0',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP PROCEDURE IF EXISTS `S64`.`SP_T6413`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S64`.`SP_T6413`()
BEGIN
			DECLARE _F01 	INT        DEFAULT 0;
			DECLARE _F02	DECIMAL(20,2) DEFAULT 0.00;
			DECLARE _F03	DECIMAL(20,2) DEFAULT 0.00;
			DECLARE _F04 	DECIMAL(20,2) DEFAULT 0.00;
			DECLARE _F05 	INT	UNSIGNED	DEFAULT	0;         
			DECLARE _F06 	DECIMAL(20,2) 	DEFAULT 0.000;
			DECLARE _F07  DECIMAL(20,2) DEFAULT 0.00;
			DECLARE _ACCOUNTID 	INT   DEFAULT 0;
			DECLARE _TOTAL_RATE  DECIMAL(20,2) DEFAULT 0.00;
			DECLARE _DONE								INT UNSIGNED		DEFAULT	0;
			DECLARE _CURSOR CURSOR FOR SELECT DISTINCT F03 FROM S64.T6411;
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET _DONE = 1;

			OPEN _CURSOR;
			REPEAT 
				FETCH _CURSOR INTO _F01;
				IF NOT _DONE THEN	
					BEGIN
							SELECT IFNULL(SUM(F07),0)	 INTO _F02  FROM S64.T6412 WHERE F09 = 'YH' AND F04=_F01 AND F05=7002;
							SELECT IFNULL(SUM(F05),0) INTO _F03 FROM S64.T6411 WHERE F03=_F01;
							SELECT IFNULL(SUM(T6410.F05),0) INTO _TOTAL_RATE FROM T6411 INNER JOIN T6410 ON T6411.F02=T6410.F01 WHERE T6411.F03=_F01 AND (T6410.F07 = 'YSX' OR T6410.F07 = 'YJZ');
							SELECT IFNULL(COUNT(1),0) INTO _F05 FROM S64.T6411 WHERE F03=_F01;
							SET _F04 =IFNULL(_TOTAL_RATE_F05,0);
							SELECT F01 			INTO _ACCOUNTID FROM S61.T6101 WHERE F02=_F01 AND F03='WLZH';
							SELECT IFNULL(SUM(F07),0) INTO _F06 FROM S61.T6102 WHERE F02=_ACCOUNTID AND F03=1302;
							SELECT IFNULL(SUM(F04),0) INTO _F07 FROM S64.T6411 WHERE F03=_F01;
							UPDATE S64.T6413 SET F02 = _F02, F03 = _F03, F04 = _F04, F05 = _F05,F06=_F06 ,F07=_F07 WHERE F01=_F01;
					END;
				END IF;
			UNTIL _DONE	END REPEAT;
			CLOSE _CURSOR;
END
;;
DELIMITER ;


DROP EVENT IF EXISTS `S64`.`EVT_T6413`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S64`.`EVT_T6413` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 12:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6413()
;;
DELIMITER ;
