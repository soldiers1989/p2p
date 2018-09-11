/*
	Source Server         : 标准库-DEVELOPMENT
	Source Server Version : 50621
	Source Host           : 
	Source Database       : S63
	
	Target Server Type    : MYSQL
	Target Server Version : 50621
	File Encoding         : UTF-8
	
	Copyright (c) dimeng.net 2014
	Date  : 2014-10-11 15:02:37
*/

SET FOREIGN_KEY_CHECKS=0;




DROP TABLE IF EXISTS `S63`.`T6310`;
CREATE TABLE `S63`.`T6310` (
  `F01` int(10) unsigned NOT NULL,
  `F02` int(10) unsigned DEFAULT '0',
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`F01`,`F04`,`F03`,`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6311`;
CREATE TABLE `S63`.`T6311` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL DEFAULT '0',
  `F03` int(10) unsigned NOT NULL DEFAULT '0',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F06` datetime DEFAULT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03` (`F03`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6312`;
CREATE TABLE `S63`.`T6312` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL DEFAULT '0',
  `F03` int(10) unsigned NOT NULL DEFAULT '0',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F06` datetime NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;




DROP TABLE IF EXISTS `S63`.`T6320`;
CREATE TABLE `S63`.`T6320` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(120) NOT NULL,
  `F03` datetime NOT NULL,
  `F04` datetime NOT NULL,
  `F05` date NOT NULL,
  `F06` date NOT NULL,
  `F07` int(10) unsigned NOT NULL,
  `F08` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F09` enum('ZJFF','YHLQ','TG','CZ') NOT NULL DEFAULT 'ZJFF',
  `F10` enum('F','S') NOT NULL DEFAULT 'S',
  `F11` decimal(20,0) DEFAULT NULL,
  `F12` enum('F','S') NOT NULL DEFAULT 'S',
  `F13` decimal(20,2) DEFAULT NULL,
  `F14` enum('YJS','SX','XJ') NOT NULL DEFAULT 'SX',
  `F15` decimal(20,2) DEFAULT '0.00',
  `F16` int(10) unsigned DEFAULT '0',
  `F17` int(10) unsigned NOT NULL,
  `F18` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6321`;
CREATE TABLE `S63`.`T6321` (
  `F01` int(10) NOT NULL,
  `F02` text NOT NULL,
  `F03` text NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6322`;
CREATE TABLE `S63`.`T6322` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` enum('YGQ','WSY','YSY') NOT NULL DEFAULT 'WSY',
  `F05` datetime NOT NULL,
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F07` enum('CZ','TZ') DEFAULT NULL,
  `F08` int(10) unsigned DEFAULT NULL,
  `F09` datetime DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6330`;
CREATE TABLE `S63`.`T6330` (
  `F01` int(10) NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) DEFAULT NULL,
  `F03` varchar(20) NOT NULL,
  `F04` datetime DEFAULT NULL,
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6331`;
CREATE TABLE `S63`.`T6331` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) NOT NULL,
  `F03` int(10) NOT NULL DEFAULT '0',
  `F04` datetime NOT NULL,
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F06` enum('ZQZR','YXLC','FX','TB') NOT NULL DEFAULT 'TB',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6332`;
CREATE TABLE `S63`.`T6332` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) NOT NULL,
  `F03` int(10) NOT NULL DEFAULT '0',
  `F04` datetime NOT NULL,
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6333`;
CREATE TABLE `S63`.`T6333` (
  `F01` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `F02` datetime NOT NULL,
  `F03` datetime NOT NULL,
  `F04` date NOT NULL,
  `F05` date NOT NULL,
  `F06` int(11) NOT NULL,
  `F07` decimal(20,2) unsigned NOT NULL,
  `F08` text,
  `F09` int(11) unsigned NOT NULL,
  `F10` int(11) unsigned NOT NULL,
  `F11` int(11) unsigned NOT NULL,
  `F12` datetime NOT NULL,
  `F13` enum('QY','TY') DEFAULT 'TY',
  `F14` varchar(50) NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6334`;
CREATE TABLE `S63`.`T6334` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(11) unsigned NOT NULL,
  `F03` enum('ZC','CZ', 'TZ', 'RZ') NOT NULL DEFAULT 'CZ',
  `F04` enum('QY','TY') NOT NULL DEFAULT 'QY',
  `F05` int(11) unsigned NOT NULL DEFAULT '1',
  `F06` decimal(20,2) unsigned DEFAULT NULL,
  `F07` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `idx_activity` (`F02`,`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6335`;
CREATE TABLE `S63`.`T6335` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` enum('TZ') NOT NULL DEFAULT 'TZ',
  `F03` decimal(20,2) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` text NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `S63`.`T6336`;
CREATE TABLE `S63`.`T6336` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(11) unsigned NOT NULL,
  `F03` int(11) unsigned NOT NULL,
  `F04` decimal(20,2) unsigned NOT NULL,
  `F05` enum('YSY','WSY') NOT NULL DEFAULT 'WSY',
  `F06` date NOT NULL,
  `F07` date DEFAULT NULL,
  `F08` varchar(100) DEFAULT NULL,
  `F09` datetime DEFAULT NULL,
  `F10` decimal(20,2) unsigned NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`F01`),
  KEY `idx_F02` (`F02`) USING BTREE,
  KEY `idx_F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6340`;
CREATE TABLE `S63`.`T6340` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` varchar(20) NOT NULL,
  `F03` enum('redpacket','interest','experience') NOT NULL,
  `F04` enum('register','recharge','firstrecharge','birthday','investlimit','foruser','tjsccz','tjsctz','tjtzze') NOT NULL ,
  `F05` varchar(20) NOT NULL,
  `F06` datetime DEFAULT NULL ,
  `F07` datetime DEFAULT NULL ,
  `F08` enum('DSJ','YSJ','JXZ','YXJ','YZF') NOT NULL DEFAULT 'DSJ' ,
  `F09` enum('login','invest','all') DEFAULT 'login' ,
  `F10` varchar(200) DEFAULT NULL ,
  `F11` datetime DEFAULT NULL ,
  `F12` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `F13` varchar(20) DEFAULT NULL ,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6340_F02_UNIQUE` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S63`.`T6342`;
CREATE TABLE `S63`.`T6342` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` int(11) NOT NULL,
  `F04` enum('WSY','YSY','YGQ') NOT NULL,
  `F05` datetime DEFAULT NULL,
  `F06` int(11) DEFAULT NULL,
  `F07` datetime DEFAULT NULL,
  `F08` datetime DEFAULT NULL,
  `F09` int(11) DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `S63`.`T6343`;
CREATE TABLE `S63`.`T6343` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` int(10) unsigned NOT NULL,
  `F03` int(10) unsigned NOT NULL,
  `F04` datetime NOT NULL,
  `F05` varchar(200) DEFAULT NULL,
  `F06` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F03` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6344`;
CREATE TABLE `S63`.`T6344` (
  `F01` int(11) NOT NULL AUTO_INCREMENT ,
  `F02` int(11) NOT NULL ,
  `F03` int(20) NOT NULL DEFAULT '0' ,
  `F04` int(2) NOT NULL ,
  `F05` decimal(10,2) NOT NULL ,
  `F06` decimal(10,0) NOT NULL ,
  `F07` decimal(10,0) DEFAULT NULL ,
  `F08` int(20) NOT NULL DEFAULT '0' ,
  `F09` enum('S','F') DEFAULT 'S' ,
  `F10`  int(4) DEFAULT NULL,
  `F11`  varchar(5) DEFAULT NULL,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6344_F05_UNIQUE` (`F06`,`F02`) USING BTREE,
  KEY `T6344_F02_inx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 ;


DROP TABLE IF EXISTS `S63`.`T6350`;
CREATE TABLE `S63`.`T6350` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` varchar(50) NOT NULL,
  `F03` varchar(100) NOT NULL ,
  `F04` enum('on','off') NOT NULL,
  `F05` int(11) NOT NULL,
  `F06` datetime NOT NULL,
  `F07` enum('kind','virtual') DEFAULT 'kind',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


DROP TABLE IF EXISTS `S63`.`T6351`;
CREATE TABLE `S63`.`T6351` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` varchar(50) NOT NULL, 
  `F03` varchar(100) NOT NULL,
  `F04` int(11) NOT NULL,
  `F05` int(11) NOT NULL,
  `F06` int(11) NOT NULL,
  `F07` int(11) DEFAULT '0',
  `F08` varchar(100) DEFAULT NULL,
  `F09` varchar(100) DEFAULT NULL,
  `F10` text noT NULL,
  `F11` enum('sold','unsold','forsold') NOT NULL,
  `F12` int(11) NOT NULL,
  `F13` datetime NOT NULL,
  `F14` datetime NOT NULL,
  `F15` decimal(20,2) NOT NULL DEFAULT '0.00',
  `F16` enum('yes','no') NOT NULL,
  `F17` enum('yes','no') NOT NULL,
  `F18` int(11) DEFAULT '0',
  `F19` decimal(20,2) NOT NULL DEFAULT 0.00,
  `F20`  int(11) NULL,
  PRIMARY KEY (`F01`),
  key `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


DROP TABLE IF EXISTS `S63`.`T6352`;
CREATE TABLE `S63`.`T6352` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` char(11) NOT NULL,
  `F04` datetime DEFAULT NULL,
  `F05` varchar(30) DEFAULT NULL,
  `F06` enum('score','balance') NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  key `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6353`;
CREATE TABLE `S63`.`T6353` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` varchar(11) NOT NULL,
  `F03` varchar(11) NOT NULL,
  `F04` datetime NOT NULL,
  `F05` ENUM('score','amount') NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `S63`.`T6354`;
CREATE TABLE `S63`.`T6354` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` text NOT NULL,
  `F03` text NOT NULL,
  `F04` datetime NOT NULL,
  `F05` datetime NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `S63`.`T6355`;
CREATE TABLE `S63`.`T6355` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` varchar(100) NOT NULL,
  `F04` int(11) NOT NULL,
  `F05` varchar(500) NOT NULL,
  `F06` varchar(13) NOT NULL,
  `F07` varchar(7) DEFAULT NULL,
  `F08` enum('yes','no') DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6356`;
CREATE TABLE `S63`.`T6356` (
  `F01` INT(11) NOT NULL AUTO_INCREMENT,
  `F02` VARCHAR(20) NOT NULL,
  `F03` ENUM('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods') NOT NULL ,
  `F04` ENUM('on','off') NOT NULL,
  `F05` DATETIME NOT NULL,
  `F06` DATETIME NOT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=INnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `S63`.`T6357`;
CREATE TABLE `S63`.`T6357` (
  `F01` INT(11) NOT NULL AUTO_INCREMENT,
  `F02` INT(11) NOT NULL,
  `F03` DATE NOT NULL,
  `F04` DATE NULL,
  `F05` DATETIME NOT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=INnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6358`;
CREATE TABLE `S63`.`T6358` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` int(11) NOT NULL,
  `F04` int(11) NOT NULL,
  `F05` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6359`;
CREATE TABLE `S63`.`T6359` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` int(11) NOT NULL ,
  `F04` int(11) NOT NULL,
  `F05` decimal(20,2) DEFAULT '0.00',
  `F06` int(11) NOT NULL,
  `F07` varchar(11) DEFAULT NULL,
  `F08` enum('pendding','pass','nopass','sended','returned','refunding','norefund','refund') DEFAULT NULL ,
  `F09` int(11) DEFAULT NULL ,
  `F10` datetime DEFAULT NULL ,
  `F11` varchar(100) DEFAULT NULL ,
  `F12` varchar(100) DEFAULT NULL ,
  `F13` varchar(100) DEFAULT NULL,
  `F14` mediumint(6) DEFAULT NULL,
  `F15` varchar(100) DEFAULT NULL,
  `F16` varchar(13) DEFAULT NULL,
  `F17` varchar(7) DEFAULT NULL,
  `F18` decimal(20,2) DEFAULT '0.00',
  `F19` datetime DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `S63`.`T6360`;
CREATE TABLE `S63`.`T6360` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(11) NOT NULL,
  `F03` enum('SH','XG','FH','TH','SQTK','TK','JJTK') DEFAULT NULL,
  `F04` varchar(100) DEFAULT NULL,
  `F05` int(11) NOT NULL,
  `F06` datetime DEFAULT NULL,
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP PROCEDURE IF EXISTS `S63`.`SP_T6331`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6331`()
   
BEGIN
	
	DECLARE _TEL VARCHAR(20);
	
	DECLARE _YWYID INT UNSIGNED DEFAULT 0;


	DECLARE 	_error INT DEFAULT 0;
	
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT F01,F03 FROM S63.T6330;
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION 
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _YWYID,_TEL;
		IF NOT _done THEN
				START TRANSACTION;
				CALL SP_T6331_YH(_YWYID,_TEL);
		END IF;
	UNTIL _done END REPEAT;
	CLOSE _list;
END
;;
DELIMITER ;




DROP PROCEDURE IF EXISTS `S63`.`SP_T6331_JY`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6331_JY`(IN _userId INT, IN _ywyId INT)
BEGIN
	
	DECLARE _userTZ DECIMAL(20,2)  DEFAULT 0.00;
	DECLARE _time DATETIME;
	DECLARE 	_type INT UNSIGNED DEFAULT 0;
	DECLARE 	_typeName VARCHAR(10);
	DECLARE 	_error INT UNSIGNED DEFAULT 0;
	
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT T6102.F07,T6102.F05,T6102.F03 FROM S61.T6102 INNER JOIN S61.T6101 ON T6102.F02 = T6101.F01 WHERE DATE_FORMAT(T6102.F05,'%Y-%m-%d')= ADDDATE(CURRENT_DATE(),INTERVAL 1 DAY)  AND T6101.F02 = _userId  AND T6101.F03 = 'WLZH' AND T6102.F03 IN (3001,4003,1301);
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _userTZ,_time,_type;
		IF NOT _done THEN
				START TRANSACTION;

				IF _type = 1301 THEN
				  SET	_typeName = 'YXLC';
				ELSEIF _type = 4003 THEN
					SET _typeName = 'ZQZR';
				ELSEIF _type = 3001 THEN
				  SET	_typeName = 'TB';
				END IF;
				
				
				INSERT INTO S63.T6331 SET F02 = _ywyId, F03 = _userId , F04 = _time, F05 = _userTZ, F06 = _typeName, F07 = F07 + _userTZ;
				
				IF _error = 1 THEN
						ROLLBACK;
						SET _error = 0;
				ELSE
						COMMIT;
				END IF;
		END IF;
	UNTIL _done END REPEAT;
	CLOSE _list;
END
;;
DELIMITER ;




DROP PROCEDURE IF EXISTS `S63`.`SP_T6331_YH`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6331_YH`(IN _ywyId INT ,IN _tel VARCHAR(20))
BEGIN
	
	DECLARE _userId INT UNSIGNED DEFAULT 0;
	DECLARE 	_error INT UNSIGNED DEFAULT 0;
	
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT T6110.F01 FROM S61.T6111 INNER JOIN S61.T6110 ON T6111.F01 = T6110.F01 WHERE T6111.F04 = _tel;
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _userId;
		IF NOT _done THEN
				START TRANSACTION;
				CALL SP_T6331_JY(_userId,_ywyId);
				
		END IF;
	UNTIL _done END REPEAT;
	CLOSE _list;
END
;;
DELIMITER ;




DROP EVENT IF EXISTS `S63`.`EVT_T6331`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6331` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6331()
;;
DELIMITER ;




-- ----------------------------
-- Procedure structure for `SP_T6340_SJ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_SJ`()
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _F02     VARCHAR(20)     DEFAULT ''; -- 奖励类型
        DECLARE _F03     VARCHAR(20)     DEFAULT ''; -- 活动类型
        DECLARE _F04     INT     DEFAULT 0; -- 存在的进行中的活动ID
        DECLARE _stop_credit  INT   DEFAULT 0;    -- 是否达到记录的末尾控制变量
        DECLARE _error        INT   DEFAULT 0;
        DECLARE t6340s_SJ CURSOR FOR SELECT T6340.F01, T6340.F03, T6340.F04, ( SELECT jxz.F01 FROM S63.T6340 jxz WHERE jxz.F03 = T6340.F03 AND jxz.F04 = T6340.F04 AND jxz.F08 = 'JXZ' LIMIT 1 ) FROM S63.T6340 WHERE T6340.F08 = 'YSJ' AND DATE(T6340.F06) <= DATE(CURRENT_TIMESTAMP());
        
        -- 解决mysql Bug:no data - zero rows fetched,selected,or processed
        DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
        
        IF _error = 0 THEN
            SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
            OPEN t6340s_SJ;
            REPEAT
                FETCH t6340s_SJ INTO _F01,_F02,_F03,_F04;
                
                IF _stop_credit = 0 THEN
                        START TRANSACTION;
                        SELECT _F04;
                        IF _F04 > 0 THEN
                            UPDATE S63.T6340 SET F08 = 'YXJ',F13 = '同类型活动复盖' WHERE F03 = _F02 AND F04 = _F03 AND F08 = 'JXZ';
                            INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F04,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06='同类型活动复盖';
                        END IF;
                        UPDATE S63.T6340 SET F08 = 'JXZ' WHERE F01=_F01;
                        INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='上架';
                        IF _error = 1 THEN
                            ROLLBACK;                            
                        ELSE
                            COMMIT;
                        END IF;
                END IF;
                UNTIL _stop_credit END REPEAT;
            CLOSE t6340s_SJ;
        END IF;
        SET _error = 0;
END
;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_XJ`()
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _stop_credit        INT                    DEFAULT 0;                -- 是否达到记录的末尾控制变量
        DECLARE _error                     INT                      DEFAULT 0;
        DECLARE t6340s_XJ CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE (T6340.F08 = 'JXZ' AND T6340.F06 IS NOT NULL  AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP())) OR  
		(T6340.F01 IN (SELECT T6344.F02 FROM S63.T6344 GROUP BY T6344.F02 HAVING  SUM(T6344.F03) = SUM(T6344.F08)) AND T6340.F08 != 'YXJ');
        
        -- 解决mysql Bug:no data - zero rows fetched,selected,or processed
        DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
        
        IF _error = 0 THEN
            SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
            OPEN t6340s_XJ;
            REPEAT
                FETCH t6340s_XJ INTO _F01;
                
                IF _stop_credit = 0 THEN
                        START TRANSACTION;
                        UPDATE S63.T6340 SET F08 = 'YXJ',F13 = '活动结束时间或达到活动限制数量' WHERE F01=_F01;
                        INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06 ='活动结束时间或达到活动限制数量';
                        IF _error = 1 THEN
                            ROLLBACK;                            
                        ELSE
                            COMMIT;
                        END IF;
                END IF;
                UNTIL _stop_credit END REPEAT;
            CLOSE t6340s_XJ;
        END IF;
        SET _error = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SP_T6340_ZF`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_ZF`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_ZF`()
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- 活动ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_ZF CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE T6340.F08 = 'DSJ' AND T6340.F06 IS NOT NULL AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP());
		
		-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
		DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN t6340s_ZF;
			REPEAT
				FETCH t6340s_ZF INTO _F01;
				
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S63.T6340 SET F08 = 'YZF' WHERE F01=_F01;
						INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='作废';
						IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE t6340s_ZF;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SP_T6342`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6342`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6342`()

BEGIN
	DECLARE P_ID INT DEFAULT 0;
	DECLARE _actId INT DEFAULT 0;
	DECLARE _stop_act INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _t6342s CURSOR FOR SELECT F01 FROM S63.T6342 WHERE F08 < CURRENT_DATE() AND T6342.F04 = 'WSY';
	
	-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_act = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	OPEN _t6342s;
	read_loop:LOOP
		FETCH _t6342s INTO _actId;
    SELECT _actId,_stop_act,P_ID,_error;
    IF P_ID IS NULL THEN
		  SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		IF _stop_act = 0 THEN
				START TRANSACTION;
					UPDATE S63.T6342 SET F04 = 'YGQ' WHERE F01 = _actId;
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
  END LOOP;
	CLOSE _t6342s;
END
;;
DELIMITER ;



-- ----------------------------
-- Event structure for `EVT_T6340_SJ`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6340_SJ` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6340_SJ()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_XJ`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6340_XJ` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6340_XJ()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_ZF`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_ZF`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6340_ZF` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL SP_T6340_ZF()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6342`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6342`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6342` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-08 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6342()
;;
DELIMITER ;



