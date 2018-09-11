/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S10

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for _1010
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1010`;
CREATE TABLE `S10`.`_1010` (
  `F01` varchar(100) NOT NULL COMMENT '变量键值',
  `F02` text NOT NULL COMMENT '变量内容',
  `F03` varchar(100) NOT NULL COMMENT '变量类型',
  `F04` text COMMENT '变量名称',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统变量表';

-- ----------------------------
-- Table structure for _1020
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1020`;
CREATE TABLE `S10`.`_1020` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(45) NOT NULL COMMENT '角色名称',
  `F03` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` int(10) unsigned NOT NULL COMMENT '创建人账户ID',
  `F06` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for _1021
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1021`;
CREATE TABLE `S10`.`_1021` (
  `F01` int(10) unsigned NOT NULL COMMENT '角色ID',
  `F02` varchar(45) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for _1022
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1022`;
CREATE TABLE `S10`.`_1022` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F02` int(10) unsigned NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for _1023
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1023`;
CREATE TABLE `S10`.`_1023` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F02` varchar(45) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Table structure for _1030
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1030`;
CREATE TABLE `S10`.`_1030` (
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
DROP TABLE IF EXISTS `S10`.`_1031`;
CREATE TABLE `S10`.`_1031` (
  `F01` int(10) unsigned NOT NULL COMMENT '会话ID',
  `F02` varchar(20) NOT NULL COMMENT '验证码类型',
  `F03` varchar(20) NOT NULL COMMENT '显示内容',
  `F04` varchar(20) NOT NULL COMMENT '验证内容',
  `F05` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='会话验证码表';

-- ----------------------------
-- Table structure for _1032
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1032`;
CREATE TABLE `S10`.`_1032` (
  `F01` int(10) unsigned NOT NULL COMMENT '会话ID',
  `F02` varchar(60) NOT NULL COMMENT '属性名称',
  `F03` varchar(200) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='会话属性表';

-- ----------------------------
-- Table structure for _1033
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1033`;
CREATE TABLE `S10`.`_1033` (
  `F01` int(10) unsigned NOT NULL COMMENT '会话ID',
  `F02` text NOT NULL COMMENT '浏览器信息',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话浏览器信息表';

-- ----------------------------
-- Table structure for _1034
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1034`;
CREATE TABLE `S10`.`_1034` (
  `F01` int(10) unsigned NOT NULL COMMENT '账户ID',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='临时登录账户集合';

-- ----------------------------
-- Table structure for _1035
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1035`;
CREATE TABLE `S10`.`_1035` (
  `F01` date NOT NULL COMMENT '日期',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '登录人次',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录人次';

-- ----------------------------
-- Table structure for _1036
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1036`;
CREATE TABLE `S10`.`_1036` (
  `F01` date NOT NULL COMMENT '日期',
  `F02` int(10) unsigned NOT NULL COMMENT '时间',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '在线人数',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户在线统计表';

-- ----------------------------
-- Table structure for _1037
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1037`;
CREATE TABLE `S10`.`_1037` (
  `F01` varchar(40) NOT NULL COMMENT '账户ID',
  `F02` varchar(40) NOT NULL COMMENT '登录IP',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '失败次数',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常登录次数';

-- ----------------------------
-- Table structure for _1040
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1040`;
CREATE TABLE `S10`.`_1040` (
  `F01` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `F02` int(11) NOT NULL COMMENT '发送类型',
  `F03` text NOT NULL COMMENT '短信内容',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` enum('W','Z','Y') DEFAULT 'W' COMMENT '发送状态,W:未发送，Z:正在发送, Y:已发送',
  `F06` datetime DEFAULT NULL COMMENT '过期时间',
  `F07` int(10) DEFAULT NULL COMMENT '发送者用户ID，参考S61.T6110.F01',
  `F08` varchar(30) DEFAULT NULL COMMENT '发送者IP地址',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='待发送短信表';

-- ----------------------------
-- Table structure for _1041
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1041`;
CREATE TABLE `S10`.`_1041` (
  `F01` bigint(20) unsigned NOT NULL COMMENT '待发送短信id，参考_1040.F01',
  `F02` varchar(20) DEFAULT NULL COMMENT '手机号码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信息与手机号关系表';

-- ----------------------------
-- Table structure for _1042
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1042`;
CREATE TABLE `S10`.`_1042` (
  `F01` bigint(20) unsigned NOT NULL COMMENT 'id(对应_1040.F01)',
  `F02` int(11) NOT NULL COMMENT '短信类型',
  `F03` text NOT NULL COMMENT '短信内容',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` enum('YES','NO') DEFAULT 'YES' COMMENT '是否发送成功',
  `F06` text COMMENT '短信返回参数',
  `F07` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`F07`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已发送短息表';

-- ----------------------------
-- Table structure for _1042
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1043`;
CREATE TABLE `S10`.`_1043` (
  `F01` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `F02` int(11) NOT NULL COMMENT '发送类型',
  `F03` varchar(50) DEFAULT NULL COMMENT '手机号、邮箱',
  `F04` varchar(12) NOT NULL COMMENT '验证码内容',
  `F05` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='手机号、邮箱验证码匹配错误表';

-- ----------------------------
-- Table structure for _1046
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1046`;
CREATE TABLE `S10`.`_1046` (
  `F01` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `F02` varchar(200) NOT NULL COMMENT '邮件标题',
  `F03` text NOT NULL COMMENT '邮件内容',
  `F04` int(4) DEFAULT '0' COMMENT '邮件类型',
  `F05` datetime NOT NULL COMMENT '创建时间',
  `F06` datetime DEFAULT NULL COMMENT '过期时间',
  `F07` enum('Z','W','Y') NOT NULL DEFAULT 'W' COMMENT '发送状态，Z:正在发送，W:未发送, Y:已发送',
  `F08` int(10) DEFAULT NULL COMMENT '发送者ID，参考S61.T6110.F01',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='待发送邮件表';

-- ----------------------------
-- Table structure for _1047
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1047`;
CREATE TABLE `S10`.`_1047` (
  `F01` bigint(20) unsigned NOT NULL COMMENT '待发送邮件id,参考_1046.F01',
  `F02` varchar(60) DEFAULT NULL COMMENT '邮件发送地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件发送与地址表';

-- ----------------------------
-- Table structure for _1048
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1048`;
CREATE TABLE `S10`.`_1048` (
  `F01` bigint(20) unsigned NOT NULL COMMENT 'id(关联_1046的F01)',
  `F02` varchar(200) NOT NULL COMMENT '邮件标题',
  `F03` text NOT NULL COMMENT '邮件内容',
  `F04` int(4) DEFAULT '0' COMMENT '邮件内容',
  `F05` datetime NOT NULL COMMENT '邮件发送时间',
  `F06` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`F06`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已发送邮件表';

-- ----------------------------
-- Table structure for _1050
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1050`;
CREATE TABLE `S10`.`_1050` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F03` int(10) unsigned NOT NULL COMMENT '附件类型',
  `F04` varchar(20) DEFAULT NULL COMMENT '后缀名',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='附件信息表';

-- ----------------------------
-- Table structure for _1051
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1051`;
CREATE TABLE `S10`.`_1051` (
  `F01` int(10) unsigned NOT NULL COMMENT '附件ID',
  `F02` longblob NOT NULL COMMENT '附件内容',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='附件内容表';

-- ----------------------------
-- Table structure for _1052
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1052`;
CREATE TABLE `S10`.`_1052` (
  `F01` varchar(500) NOT NULL COMMENT 'modulus',
  `F02` int(11) NOT NULL COMMENT ' public exponent',
  `F03` varchar(500) NOT NULL COMMENT 'private exponent',
  `F04` varchar(500) NOT NULL COMMENT 'primeP',
  `F05` varchar(500) NOT NULL COMMENT 'primeQ',
  `F06` varchar(500) NOT NULL COMMENT 'primeExponentP',
  `F07` varchar(500) NOT NULL COMMENT 'primeExponentQ',
  `F08` varchar(500) NOT NULL COMMENT 'crtCoefficient'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='RSA私钥信息';

-- ----------------------------
-- Table structure for _1053
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1053`;
CREATE TABLE `S10`.`_1053` (
  `F01` varchar(500) NOT NULL COMMENT 'modulus',
  `F02` varchar(500) NOT NULL COMMENT 'publicExponent'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公钥信息';

-- ----------------------------
-- Table structure for _1054
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1054`;
CREATE TABLE `S10`.`_1054` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '注册者IP',
  `F03` int(10) NOT NULL COMMENT '注册用户ID，参考S61.T6110.F01',
  `F04` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户注册记录表';

-- ----------------------------
-- Table structure for _1055
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`_1055`;
CREATE TABLE `S10`.`_1055` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F02` varchar(50) NOT NULL COMMENT '验证码类型',
  `F03` varchar(20) NOT NULL COMMENT '显示内容',
  `F04` varchar(20) NOT NULL COMMENT '验证内容',
  `F05` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='邮箱验证码表';
-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `S10`.`sequence`;
CREATE TABLE `S10`.`sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for _sp_103
-- ----------------------------
DROP PROCEDURE IF EXISTS `S10`.`_sp_103`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S10`.`_sp_103`()
BEGIN DECLARE _current_date DATETIME DEFAULT CURRENT_TIMESTAMP();DELETE _1033 FROM _1033,_1030 WHERE _1033.F01 = _1030.F01 AND _1030.F05 <= _current_date;DELETE _1032 FROM _1032,_1030 WHERE _1032.F01 = _1030.F01 AND _1030.F05 <= _current_date;DELETE _1031 FROM _1031,_1030 WHERE _1031.F01 = _1030.F01 AND _1030.F05 <= _current_date; DELETE FROM _1030 WHERE _1030.F05 <= _current_date;END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for _sp_1034_5
-- ----------------------------
DROP PROCEDURE IF EXISTS `S10`.`_sp_1034_5`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S10`.`_sp_1034_5`()
BEGIN DECLARE _count INT UNSIGNED DEFAULT 0; SELECT COUNT(*) INTO _count FROM _1034;INSERT INTO _1035 SET F01 = CURRENT_DATE(), F02 = _count ON DUPLICATE KEY UPDATE F02 = VALUES(F02);DELETE FROM _1034;END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for _sp_1036
-- ----------------------------
DROP PROCEDURE IF EXISTS `S10`.`_sp_1036`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S10`.`_sp_1036`()
BEGIN DECLARE _count INT UNSIGNED DEFAULT 0; DECLARE _current_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP();SELECT COUNT(*) INTO _count FROM _1030 WHERE F04 IS NOT NULL AND F05 > _current_time;INSERT INTO _1036 SET F01 = DATE(_current_time), F02 = HOUR(_current_time), F03 = _count ON DUPLICATE KEY UPDATE F03 = VALUES(F03);END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for _sp_1054
-- ----------------------------
DROP PROCEDURE IF EXISTS `S10`.`_sp_1054`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S10`.`_sp_1054`()
BEGIN DELETE FROM _1054;END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for _SP_1040
-- ----------------------------
DROP PROCEDURE IF EXISTS `S10`.`_sp_1040`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S10`.`_sp_1040`()
 BEGIN
	DECLARE P_ID INT DEFAULT 0;
	
	DECLARE _F01 INT DEFAULT 0;

	DECLARE done INT DEFAULT 0; 
	
	DECLARE t_error INTEGER DEFAULT 0;  


	-- 待删除列表
	DECLARE CURSOR_LIST CURSOR FOR SELECT F01 FROM S10._1040 WHERE F04 < CURRENT_DATE() AND F05 = 'Y';
	
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error = 1;
	START TRANSACTION;
	
	-- 打开游标
  OPEN CURSOR_LIST;
	-- 遍历游标
	REPEAT
		FETCH CURSOR_LIST INTO _F01;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			SET done = 1;
		END IF;
		-- 遍历结束跳转循环
		IF NOT done THEN
			DELETE FROM _1040 WHERE F01 = _F01;
			DELETE FROM _1041 WHERE F01 = _F01;
		END IF;
	UNTIL done END REPEAT;
	-- 关闭游标
  CLOSE CURSOR_LIST;  

  IF t_error = 1 THEN 
		ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
END
;;
DELIMITER ;


-- ----------------------------
-- Function structure for currval
-- ----------------------------
DROP FUNCTION IF EXISTS `S10`.`currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `S10`.`currval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN  
         DECLARE value INTEGER;  
         SET value = 0;  
         SELECT current_value INTO value  
                   FROM sequence  
                   WHERE name = seq_name;  
         RETURN value;  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for nextval
-- ----------------------------
DROP FUNCTION IF EXISTS `S10`.`nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `S10`.`nextval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN  
         UPDATE sequence  
                   SET current_value = current_value + increment  
                   WHERE name = seq_name;  
         RETURN currval(seq_name);  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for setval
-- ----------------------------
DROP FUNCTION IF EXISTS `S10`.`setval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `S10`.`setval`(seq_name VARCHAR(50), value INTEGER) RETURNS int(11)
    DETERMINISTIC
BEGIN  
         UPDATE sequence  
                   SET current_value = value  
                   WHERE name = seq_name;  
         RETURN currval(seq_name);  
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for _daily_sp_103
-- ----------------------------
DROP EVENT IF EXISTS `S10`.`_daily_sp_103`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S10`.`_daily_sp_103` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:30:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_103()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for _daily_sp_1034_5
-- ----------------------------
DROP EVENT IF EXISTS `S10`.`_daily_sp_1034_5`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S10`.`_daily_sp_1034_5` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 23:59:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_1034_5()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for _daily_sp_1036
-- ----------------------------
DROP EVENT IF EXISTS `S10`.`_daily_sp_1036`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S10`.`_daily_sp_1036` ON SCHEDULE EVERY 1 HOUR STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_1036()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_1037
-- ----------------------------
DROP EVENT IF EXISTS `S10`.`EVT_1037`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S10`.`EVT_1037` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO UPDATE _1037 SET F03 = 0
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_1054
-- ----------------------------
DROP EVENT IF EXISTS `S10`.`EVT_1054`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S10`.`EVT_1054` ON SCHEDULE EVERY 1 MONTH STARTS '2015-06-06 01:00:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_1054()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_1054
-- ----------------------------
DROP EVENT IF EXISTS `S10`.`EVT_1040`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S10`.`EVT_1040` ON SCHEDULE EVERY 1 DAY STARTS '2017-03-17 01:00:00' ON COMPLETION PRESERVE ENABLE DO CALL _sp_1040()
;;
DELIMITER ;
