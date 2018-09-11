/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S64

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:40:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T6410
-- ----------------------------
DROP TABLE IF EXISTS `S64`.`T6410`;
CREATE TABLE `S64`.`T6410` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(45) NOT NULL COMMENT '优选理财标题',
  `F03` decimal(20,2) unsigned NOT NULL COMMENT '总金额',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '可投余额',
  `F05` decimal(6,6) NOT NULL COMMENT '年化收益率',
  `F06` int(11) unsigned NOT NULL COMMENT '投资类型ID,参考T6211.F01',
  `F07` enum('XJ','YFB','YSX','YJZ') NOT NULL DEFAULT 'XJ' COMMENT '计划状态,XJ:新建;YFB:已发布;YSX:已生效;YJZ:已截止',
  `F08` int(11) unsigned NOT NULL COMMENT '满额用时（秒计算）',
  `F09` datetime NOT NULL COMMENT '申请开始时间',
  `F10` date NOT NULL COMMENT '申请结束日期',
  `F11` int(11) unsigned NOT NULL COMMENT '锁定期限(月)',
  `F12` datetime DEFAULT NULL COMMENT '满额时间',
  `F13` date DEFAULT NULL COMMENT '锁定结束日期',
  `F14` enum('DEBX','MYHXDQHB','YCXHBFX') NOT NULL COMMENT '收益处理,MYHXDQHB:每月还息，到期还本;YCXHBFX:一次性还本付息;DEBX:等额本息',
  `F15` decimal(6,6) unsigned NOT NULL COMMENT '加入费率',
  `F16` decimal(6,6) unsigned NOT NULL COMMENT '服务费率',
  `F17` decimal(6,6) unsigned NOT NULL COMMENT '退出费率',
  `F18` varchar(200) NOT NULL COMMENT '计划介绍',
  `F19` int(11) unsigned NOT NULL COMMENT '创建人,参考T7010.F01',
  `F20` datetime NOT NULL COMMENT '创建时间',
  `F21` date DEFAULT NULL COMMENT '下一还款日',
  `F22` decimal(20,2) unsigned NOT NULL COMMENT '投资下限',
  `F23` decimal(20,2) unsigned NOT NULL COMMENT '投资上限',
  `F24` enum('QEBXBZ') NOT NULL COMMENT '保障方式,QEBXBZ:全额本息保障',
  PRIMARY KEY (`F01`),
  KEY `F06_idx` (`F06`),
  KEY `F19_idx` (`F19`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优选理财计划';

-- ----------------------------
-- Table structure for T6411
-- ----------------------------
DROP TABLE IF EXISTS `S64`.`T6411`;
CREATE TABLE `S64`.`T6411` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '优选理财持有人表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '优选理财计划表ID,参考T6410.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '持有人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '原始债权',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '剩余债权',
  `F06` datetime NOT NULL COMMENT '加入时间',
  `F07` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`,`F03`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优选理财持有人表';

-- ----------------------------
-- Table structure for T6412
-- ----------------------------
DROP TABLE IF EXISTS `S64`.`T6412`;
CREATE TABLE `S64`.`T6412` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '优选理财计划ID,参考T6410.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '付款用户ID,参考T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F06` int(10) unsigned NOT NULL COMMENT '期号',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '金额',
  `F08` date NOT NULL COMMENT '应还日期',
  `F09` enum('WH','YH') NOT NULL COMMENT '状态,WH:未还;YH:已还;',
  `F10` datetime DEFAULT NULL COMMENT '实际还款时间',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02_UNIQUE` (`F02`,`F03`,`F04`,`F05`,`F06`),
  KEY `F05` (`F05`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优选理财还款记录';

-- ----------------------------
-- Table structure for T6413
-- ----------------------------
DROP TABLE IF EXISTS `S64`.`T6413`;
CREATE TABLE `S64`.`T6413` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户账号ID,参考T6010.F01',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已赚总金额',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '优选理财账户资产',
  `F04` decimal(3,3) NOT NULL DEFAULT '0.000' COMMENT '平均收益率',
  `F05` int(10) NOT NULL DEFAULT '0' COMMENT '持有中的计划数量',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '加入费用',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '优选理财投资金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优选计划统计表';

-- ----------------------------
-- Procedure structure for SP_T6413
-- ----------------------------
DROP PROCEDURE IF EXISTS `S64`.`SP_T6413`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S64`.`SP_T6413`()
    COMMENT '优选理财统计'
BEGIN
			-- 用户ID
			DECLARE _F01 	INT        DEFAULT 0;
			-- 已赚总金额
			DECLARE _F02	DECIMAL(20,2) DEFAULT 0.00;
			-- 优选理财账户资产
			DECLARE _F03	DECIMAL(20,2) DEFAULT 0.00;
			-- 平均收益率
			DECLARE _F04 	DECIMAL(20,2) DEFAULT 0.00;
			-- 持有中的计划数量
			DECLARE _F05 	INT	UNSIGNED	DEFAULT	0;         
			-- 加入费用
			DECLARE _F06 	DECIMAL(20,2) 	DEFAULT 0.000;
			-- 优选理财投资金额
			DECLARE _F07  DECIMAL(20,2) DEFAULT 0.00;
			-- 用户ID
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

-- ----------------------------
-- Event structure for EVT_T6413
-- ----------------------------
DROP EVENT IF EXISTS `S64`.`EVT_T6413`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S64`.`EVT_T6413` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 12:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6413()
;;
DELIMITER ;
