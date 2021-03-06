
DROP TABLE IF EXISTS `S62`.`T6264`;
CREATE TABLE `S62`.`T6264` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '债权编号',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` enum('DSH','ZRZ','YZR','YXJ','ZRSB') NOT NULL COMMENT '状态,DSH:待审核;ZRZ:转让中;YZR:已转让;YXJ:已下架;ZRSB:转让失败',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '逾期天数',
  `F06` int(10) unsigned NOT NULL COMMENT '标还款ID,参考T6252.F01',
  `F07` date NOT NULL COMMENT '申请时间',
  `F08` datetime NOT NULL COMMENT '操作时间',
  `F09` decimal(20,2) DEFAULT NULL COMMENT '债权价值',
  `F10` decimal(20,2) DEFAULT NULL COMMENT '转让价格',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='不良债权转让申请';


DROP TABLE IF EXISTS `S62`.`T6265`;
CREATE TABLE `S62`.`T6265` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '转让申请ID,参考T6264.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
	`F04` int(10) unsigned NOT NULL COMMENT '借款人ID,参考T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '债权价值',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '认购价格',
  `F07` datetime NOT NULL COMMENT '购买时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='不良债权转让记录';

DROP TABLE IF EXISTS `S62`.`T6266`;
CREATE TABLE `S62`.`T6266` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) NOT NULL COMMENT '转让id，参考T6265.F01',
  `F03` decimal(10,2) NOT NULL COMMENT '金额',
  `F04` int(10) NOT NULL COMMENT '收款人（投资人）',
  `F05` int(10) NOT NULL COMMENT '交易类型',
  `F06` int(10) NOT NULL COMMENT '债权id，T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='不良债权转让还款明细表';



DROP TABLE IF EXISTS `S65`.`T6529`;
CREATE TABLE `S65`.`T6529` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
	`F03` int(10) unsigned NOT NULL COMMENT '不良债权申请ID,参考T6264.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '债权金额',
  `F07` int(11) NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F08` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否完成,S:是;F:否;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不良债权转让订单';



DROP TABLE IF EXISTS `S62`.`T6267;
CREATE TABLE `S62`.`T6267` (
  `F01` int(11) unsigned NOT NULL COMMENT '不良债权转让ID,参考T6265.F01',
  `F02` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不良债权转让协议版本号';




ALTER TABLE `S50.T5017`
MODIFY COLUMN `F01`  enum('ZCXY','TFJKXY','FFJKXY','ZQZRXY','BLZQZRXY','GYJZXY','GRXXCQSQTK','QYXXCJSQTK','FXTSH') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '协议类型,ZCXY:注册协议;TFJKXY:三方借款协议;FFJKXY:四方借款协议;ZQZRXY:债权转让协议;BLZQZRXY:不良债权转让协议;GYJZXY:公益捐助协议;GRXXCQSQTK:个人信息采集授权条款;QYXXCJSQTK:企业信息采集授权条款;FXTSH:风险提示函' FIRST ;

INSERT INTO `S50`.`T5017` VALUES('BLZQZRXY', '', 0,CURRENT_TIMESTAMP(), 'QY');

INSERT INTO `S51`.`T5125` VALUES ('2002', '0', '不良债权转让协议', CURRENT_TIMESTAMP());
INSERT INTO `S51`.`T5126` VALUES (2002, 0, '', CURRENT_TIMESTAMP());

ALTER TABLE `S62.T6230`
MODIFY COLUMN `F20`  enum('SQZ','DSH','DFB','YFB','TBZ','DFK','HKZ','YDF','YZR','YJQ','YLB','YZF') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'SQZ' COMMENT '状态,SQZ:申请中;DSH:待审核;DFB:待发布;YFB:预发布;TBZ:投资中;DFK:待放款;HKZ:还款中;YJQ:已结清;YLB:已流标;YDF:已垫付;YZF:已作废;YZR:已转让;' AFTER `F19`;


INSERT INTO `S51`.`T5122` VALUES ('4004', '购买不良债权', 'QY', 'no', 'no', 'yes', 'no', 'no', 'no');

INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20015,'购买不良债权','QY','no','no','yes','yes');

ALTER TABLE `S62`.`T6252`
MODIFY COLUMN `F09`  enum('WH','HKZ','YH','TQH','YZR','DF') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,WH:未还;YH:已还;HKZ:还款中;TQH:提前还;DF:垫付;YZR:已转让' AFTER `F08`;

ALTER TABLE `S62`.`T6231`
ADD COLUMN `F34`  datetime NULL DEFAULT NULL COMMENT '不良债权转让时间' AFTER `F33`;




DROP PROCEDURE IF EXISTS `S71`.`SP_T7190`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S71`.`SP_T7190`()
    COMMENT '业务员业绩统计'
	BEGIN
	-- 业务员业绩统计
	-- 一级用户投资金额
	DECLARE _F01 DECIMAL(20,2) DEFAULT 0.00;
	-- 一级用户借款金额
	DECLARE _F02 DECIMAL(20,2) DEFAULT 0.00;
	-- 一级用户充值金额
	DECLARE _F03 DECIMAL(20,2) DEFAULT 0.00;
	-- 一级用户提现金额
	DECLARE _F04 DECIMAL(20,2) DEFAULT 0.00;
	-- 二级用户投资金额
	DECLARE _F05 DECIMAL(20,2) DEFAULT 0.00;
	-- 二级用户借款金额
	DECLARE _F06 DECIMAL(20,2) DEFAULT 0.00;
  -- 二级用户充值金额
	DECLARE _F07 DECIMAL(20,2) DEFAULT 0.00;
	-- 二级用户提现金额
	DECLARE _F08 DECIMAL(20,2) DEFAULT 0.00;

	-- 一级用户线下充值金额
	DECLARE _F03_1 DECIMAL(20,2) DEFAULT 0.00;
	-- 二级用户线下充值金额
	DECLARE _F07_1 DECIMAL(20,2) DEFAULT 0.00;

  DECLARE _F10 VARCHAR(50) DEFAULT null;
  DECLARE _F11 VARCHAR(50) DEFAULT null;

  DECLARE T7110_F12 VARCHAR(50);
  DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;
  DECLARE ywyCur CURSOR   FOR SELECT  DISTINCT F12 FROM S71.T7110 WHERE T7110.F12 IS NOT NULL;
  DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
  OPEN ywyCur;
		REPEAT 
			FETCH ywyCur INTO T7110_F12;
			IF NOT _done THEN
				SELECT CURDATE() INTO _F10 FROM dual;	
				-- 一级用户投资金额查询
				SELECT IFNULL(SUM(T6250.F04),0) INTO _F01 FROM S62.T6250 LEFT JOIN S62.T6231 ON T6231.F01 = T6250.F02 WHERE T6250.F12 = T7110_F12 AND T6250.F08='S' AND T6250.F13 = 'QY' AND (SELECT COUNT(1) FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6250.F03) > 0 AND DATE(T6231.F12) = _F10;	 
				-- 一级用户借款金额查询
				SELECT IFNULL(SUM(T6230.F05-T6230.F07),0) INTO _F02 FROM S62.T6230 LEFT JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6231.F31 = T7110_F12 AND T6231.F32 = 'QY' AND T6230.F20 IN('HKZ','YJQ','YDF','YZR') AND (SELECT COUNT(1) FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6230.F02) > 0 AND DATE(T6231.F12) = _F10;
				-- 一级用户充值金额查询
				SELECT IFNULL(SUM(T6502.F03),0) INTO _F03 FROM S65.T6502 LEFT JOIN S65.T6501 ON T6502.F01 = T6501.F01 WHERE T6502.F09 = T7110_F12 AND T6502.F10 = 'QY' AND T6501.F03 = 'CG' AND (SELECT COUNT(1) FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6502.F02) > 0 AND DATE(T6501.F06) = _F10;
				-- 一级用户线下充值金额查询
				SELECT IFNULL(SUM(T7150.F04),0) INTO _F03_1 FROM S71.T7150 WHERE T7150.F16 = T7110_F12 AND T7150.F17 = 'QY' AND T7150.F05 = 'YCZ' AND EXISTS (SELECT 1 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T7150.F02) AND DATE(T7150.F10) = _F10;
				-- 一级用户提现金额查询
				SELECT IFNULL(SUM(T6130.F04),0) INTO _F04 FROM S61.T6130 WHERE T6130.F17 = T7110_F12 AND T6130.F18 = 'QY' AND T6130.F09 = 'YFK' AND EXISTS (SELECT 1 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6130.F02) AND DATE(T6130.F14) = _F10;
				-- 二级用户投资金额查询
				SELECT IFNULL(SUM(T6250.F04),0) INTO _F05 FROM S62.T6250 LEFT JOIN S62.T6231 ON T6231.F01 = T6250.F02 WHERE T6250.F03 IN(SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL))) AND T6250.F08='S' AND T6250.F13 = 'QY' AND T6250.F12 = T7110_F12 AND DATE(T6231.F12) = _F10; 
				-- 二级用户借款金额查询
				SELECT IFNULL(SUM(T6230.F05-T6230.F07),0) INTO _F06 FROM S62.T6230 LEFT JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F02 
				IN(SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 
				IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL))) AND T6230.F20 IN('HKZ','YJQ','YDF','YZR') AND T6231.F32 = 'QY' AND T6231.F31 = T7110_F12 AND DATE(T6231.F12) = _F10;
				-- 二级用户充值金额查询
				SELECT IFNULL(SUM(T6502.F03),0) INTO _F07 FROM S65.T6502 LEFT JOIN S65.T6501 ON T6502.F01 = T6501.F01 WHERE T6502.F09 = T7110_F12 AND T6502.F10 = 'QY' AND T6501.F03 = 'CG' AND T6502.F02 IN (SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL)))  AND DATE(T6501.F06) = _F10;
				-- 二级用户线下充值金额查询
				SELECT IFNULL(SUM(T7150.F04),0) INTO _F07_1 FROM S71.T7150 WHERE T7150.F16 = T7110_F12 AND T7150.F17 = 'QY' AND T7150.F05 = 'YCZ' AND T7150.F02 IN (SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL ))) AND DATE(T7150.F10) = _F10;
				-- 二级用户提现金额查询
				SELECT IFNULL(SUM(T6130.F04),0) INTO _F08 FROM S61.T6130 WHERE T6130.F17 = T7110_F12 AND T6130.F18 = 'QY' AND T6130.F09 = 'YFK' AND T6130.F02 IN (SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL))) AND DATE(T6130.F14) = _F10;
				
        INSERT INTO S71.T7190 SET F01 = _F01, F02 = _F02, F03 = _F03+_F03_1, F04 = _F04, F05 = _F05, F06 = _F06, F07 = _F07+_F07_1, F08 = _F08, F10 = _F10, F11 = T7110_F12 ON DUPLICATE KEY UPDATE F01 = VALUES(F01),F02 = VALUES(F02),F03 = VALUES(F03),F04 = VALUES(F04),F05 = VALUES(F05), F06 = VALUES(F06), F07 = VALUES(F07), F08 = VALUES(F08);
		END IF;
		UNTIL _done END REPEAT;			
 CLOSE ywyCur;

END
;;
DELIMITER ;
