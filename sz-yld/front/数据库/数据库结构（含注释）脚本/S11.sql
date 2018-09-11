/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S11

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for _1030
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1030`;
CREATE TABLE `S11`.`_1030` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` char(45) NOT NULL COMMENT '会话ID',
  `F03` datetime NOT NULL COMMENT '创建时间',
  `F04` int(10) unsigned DEFAULT NULL COMMENT '账号ID',
  `F05` datetime NOT NULL COMMENT '过期时间',
  `F06` varchar(40) NOT NULL COMMENT '登录IP',
  `F07` varchar(40) DEFAULT NULL COMMENT '浏览器特征信息',
  `F08` int(11) DEFAULT '0' COMMENT '会话是否有效:0-有效；1-无效',
  `F09` enum('pc','ios','ad','wx','other') DEFAULT 'pc' COMMENT '登录终端标识: pc，ios，ad，wx，other',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`) USING HASH,
  KEY `F06` (`F06`) USING HASH,
  KEY `F07` (`F07`) USING HASH
) ENGINE=MEMORY AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='会话信息表';

-- ----------------------------
-- Table structure for _1031
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1031`;
CREATE TABLE `S11`.`_1031` (
  `F01` int(10) unsigned NOT NULL COMMENT '会话ID',
  `F02` varchar(50) NOT NULL COMMENT '验证码类型',
  `F03` varchar(20) NOT NULL COMMENT '显示内容',
  `F04` varchar(20) NOT NULL COMMENT '验证内容',
  `F05` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='会话验证码表';

-- ----------------------------
-- Table structure for _1032
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1032`;
CREATE TABLE `S11`.`_1032` (
  `F01` int(10) unsigned NOT NULL COMMENT '会话ID',
  `F02` varchar(60) NOT NULL COMMENT '属性名称',
  `F03` varchar(200) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='会话属性表';

-- ----------------------------
-- Table structure for _1033
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1033`;
CREATE TABLE `S11`.`_1033` (
  `F01` int(10) unsigned NOT NULL COMMENT '会话ID',
  `F02` text NOT NULL COMMENT '浏览器信息',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话浏览器信息表';

-- ----------------------------
-- Table structure for _1034
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1034`;
CREATE TABLE `S11`.`_1034` (
  `F01` int(10) unsigned NOT NULL COMMENT '账户ID',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='临时登录账户集合';

-- ----------------------------
-- Table structure for _1035
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1035`;
CREATE TABLE `S11`.`_1035` (
  `F01` date NOT NULL COMMENT '日期',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '登录人次',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录人次';

-- ----------------------------
-- Table structure for _1036
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1036`;
CREATE TABLE `S11`.`_1036` (
  `F01` date NOT NULL COMMENT '日期',
  `F02` int(10) unsigned NOT NULL COMMENT '时间',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '在线人数',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户在线统计表';

-- ----------------------------
-- Table structure for _1037
-- ----------------------------
DROP TABLE IF EXISTS `S11`.`_1037`;
CREATE TABLE `S11`.`_1037` (
  `F01` varchar(40) NOT NULL COMMENT '账户ID',
  `F02` varchar(40) NOT NULL COMMENT '登录IP',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '失败次数',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常登录次数';

-- ----------------------------
-- Procedure structure for _sp_103
-- ----------------------------
DROP PROCEDURE IF EXISTS `S11`.`_sp_103`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S11`.`_sp_103`()
BEGIN DECLARE _current_date DATETIME DEFAULT CURRENT_TIMESTAMP();DELETE _1033 FROM _1033,_1030 WHERE _1033.F01 = _1030.F01 AND _1030.F05 <= _current_date;DELETE _1032 FROM _1032,_1030 WHERE _1032.F01 = _1030.F01 AND _1030.F05 <= _current_date;DELETE _1031 FROM _1031,_1030 WHERE _1031.F01 = _1030.F01 AND _1030.F05 <= _current_date; DELETE FROM _1030 WHERE _1030.F05 <= _current_date;END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for _sp_1034_5
-- ----------------------------
DROP PROCEDURE IF EXISTS `S11`.`_sp_1034_5`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S11`.`_sp_1034_5`()
BEGIN DECLARE _count INT UNSIGNED DEFAULT 0; SELECT COUNT(*) INTO _count FROM _1034;INSERT INTO _1035 SET F01 = CURRENT_DATE(), F02 = _count ON DUPLICATE KEY UPDATE F02 = VALUES(F02);DELETE FROM _1034;END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for _sp_1036
-- ----------------------------
DROP PROCEDURE IF EXISTS `S11`.`_sp_1036`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S11`.`_sp_1036`()
BEGIN DECLARE _count INT UNSIGNED DEFAULT 0; DECLARE _current_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP();SELECT COUNT(*) INTO _count FROM _1030 WHERE F04 IS NOT NULL AND F05 > _current_time;INSERT INTO _1036 SET F01 = DATE(_current_time), F02 = HOUR(_current_time), F03 = _count ON DUPLICATE KEY UPDATE F03 = VALUES(F03);END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for _daily_sp_103
-- ----------------------------
DROP EVENT IF EXISTS `S11`.`_daily_sp_103`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S11`.`_daily_sp_103` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:30:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_103()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for _daily_sp_1034_5
-- ----------------------------
DROP EVENT IF EXISTS `S11`.`_daily_sp_1034_5`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S11`.`_daily_sp_1034_5` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 23:59:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_1034_5()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for _daily_sp_1036
-- ----------------------------
DROP EVENT IF EXISTS `S11`.`_daily_sp_1036`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S11`.`_daily_sp_1036` ON SCHEDULE EVERY 1 HOUR STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_1036()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_1037
-- ----------------------------
DROP EVENT IF EXISTS `S11`.`EVT_1037`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S11`.`EVT_1037` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO UPDATE _1037 SET F03 = 0
;;
DELIMITER ;
