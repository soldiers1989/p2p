DROP TABLE IF EXISTS `S61`.`T6147`;
CREATE TABLE `S61`.`T6147` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '评估分数',
  `F04` enum('JJX','JQX','WJX','JSX','BSX') NOT NULL COMMENT '评估等级:JJX:激进型；JQX：进取型；,WJX：稳健型；,JSX：谨慎型；,BSX：保守型',
  `F05` int(10) unsigned NOT NULL COMMENT '已评估总次数',
  `F06` datetime DEFAULT NULL COMMENT '评估时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户风险评估记录表';

DROP TABLE IF EXISTS `S61`.`T6148`;
CREATE TABLE `S61`.`T6148` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` enum('JJX','JQX','WJX','JSX','BSX') NOT NULL COMMENT '评估等级:JJX:激进型；JQX：进取型；,WJX：稳健型；,JSX：谨慎型；,BSX：保守型',
  `F03` int(10) unsigned NOT NULL COMMENT '最小分值',
  `F04` int(10) unsigned NOT NULL COMMENT '最大分值',
  `F05` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='风险评估类型设置表';

INSERT INTO `S61`.`T6148` VALUES(1, 'BSX', 0,34,CURRENT_TIMESTAMP());
INSERT INTO `S61`.`T6148` VALUES(2, 'JSX', 35,44,CURRENT_TIMESTAMP());
INSERT INTO `S61`.`T6148` VALUES(3, 'WJX', 45,54,CURRENT_TIMESTAMP());
INSERT INTO `S61`.`T6148` VALUES(4, 'JQX', 55,64,CURRENT_TIMESTAMP());
INSERT INTO `S61`.`T6148` VALUES(5, 'JJX', 65,2147483647,CURRENT_TIMESTAMP());

DROP TABLE IF EXISTS `S61`.`T6149`;
CREATE TABLE `S61`.`T6149` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(500) COMMENT '问题内容',
  `F03` varchar(500) NOT NULL COMMENT '选项A',
  `F04` varchar(500) NOT NULL COMMENT '选项B',
  `F05` varchar(500) NOT NULL COMMENT '选项C',
  `F06` varchar(500) NOT NULL COMMENT '选项D',
  `F07` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态，QY：启用；TY：停用',
  `F08` int(11) NOT NULL COMMENT '排序字段',
  `F09` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后操作时间',
  `F10` int(6) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='风险评估问题信息表';

DROP TABLE IF EXISTS `S61`.`T6150`;
CREATE TABLE `S61`.`T6150` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户风险评估记录表ID,T6147.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '风险评估问题信息表ID,T6149.F01',
  `F04` char(1) NOT NULL COMMENT '评估问题选项：A,B,C,D',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户风险评估记录明细表';

-- 产品类型表增加字段
ALTER TABLE `S62`.`T6216`
ADD COLUMN `F18`  enum('BSX','JSX','WJX','JQX','JJX') NOT NULL DEFAULT 'BSX' COMMENT '投资限制:BSX：保守型及以上；JSX：谨慎型及以上；WJX：稳健型及以上；JQX：进取型及以上；JJX:激进型';

-- 风险提示函”协议条款增量脚本
ALTER TABLE `S50`.`T5017`
MODIFY COLUMN `F01`  enum('ZCXY','TFJKXY','FFJKXY','ZQZRXY','GYJZXY','GRXXCQSQTK','QYXXCJSQTK','FXTSH') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '协议类型,ZCXY:注册协议;TFJKXY:三方借款协议;FFJKXY:四方借款协议;ZQZRXY:债权转让协议;GYJZXY:公益捐助协议;GRXXCQSQTK:个人信息采集授权条款;QYXXCJSQTK:企业信息采集授权条款;FXTSH:风险提示函';

INSERT INTO `S50`.`T5017` VALUES('FXTSH', '', 0,CURRENT_TIMESTAMP(), 'QY');


-- 用户信用等级增量脚本
ALTER TABLE `S61`.`T6116`
ADD COLUMN `F05`  enum('AAA','AA','A','B') NOT NULL DEFAULT 'A' COMMENT '用户信用等级';

CREATE TABLE `S62`.`T6248` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '创建用户ID,参考T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID，参考T6230.F01',
  `F04` varchar(50) DEFAULT NULL COMMENT '主题标题',
  `F05` enum('YFB','WFB') NOT NULL DEFAULT 'YFB' COMMENT '状态,YFB:已发布；WFB:未发布;',
  `F06` varchar(600) DEFAULT NULL COMMENT '简要介绍',
  `F07` datetime DEFAULT NULL COMMENT '发布时间',
  `F08` datetime NOT NULL COMMENT '标题时间',
  `F09` varchar(100) DEFAULT NULL COMMENT '查看更多,外链地址',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='标动态信息表';


CREATE TABLE `S61`.`T6195` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) NOT NULL COMMENT '用户ID,T6110.F01',
  `F03` varchar(1000) NOT NULL COMMENT '投诉/建议内容',
  `F04` datetime NOT NULL COMMENT '投诉/建议时间',
  `F05` varchar(1000) DEFAULT NULL COMMENT '回复内容',
  `F06` enum('yes','no') NOT NULL DEFAULT 'no' COMMENT '是否回复,yes:已回复；no:未回复;',
  `F07` int(6) DEFAULT NULL COMMENT '回复人',
  `F08` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,YFB:已发布；WFB:未发布;',
  `F09` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户投诉建议表';

DROP TABLE IF EXISTS `S61`.`T6196`;
CREATE TABLE `S61`.`T6196` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计投资金额',
  `F03` INT(9) UNSIGNED NOT NULL COMMENT '注册用户数',
  `F04` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计赚取收益',
  `F05` INT(9) UNSIGNED NOT NULL COMMENT '累计成交笔数',
  `F06` INT(9) UNSIGNED NOT NULL COMMENT '投资用户数',
  `F07` INT(9) UNSIGNED NOT NULL COMMENT '借款用户数',
  `F08` INT(9) UNSIGNED NOT NULL COMMENT '90后',
  `F09` INT(9) UNSIGNED NOT NULL COMMENT '80后',
  `F10` INT(9) UNSIGNED NOT NULL COMMENT '70后',
  `F11` INT(9) UNSIGNED NOT NULL COMMENT '60后',
  `F12` INT(9) UNSIGNED NOT NULL COMMENT '其他',
  `F13` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '0-3个月',
  `F14` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '3-6个月',
  `F15` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '6-9个月',
  `F16` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '9-12个月',
  `F17` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '12-24个月',
  `F18` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '24个月以上',
  `F19` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '机构担保标',
  `F20` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '抵押认证标',
  `F21` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '实地认证标',
  `F22` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '信用认证标',
  `F23` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计代偿金额',
  `F24` DECIMAL(10,2) NOT NULL COMMENT '最大单户借款余额占比',
  `F25` DECIMAL(10,2) NOT NULL COMMENT '最大10户借款余额占比',
  `F26` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '借款逾期金额',
  `F27` DECIMAL(10,2) NOT NULL COMMENT '借款逾期率',
  `F28` DECIMAL(10,2) NOT NULL COMMENT '借款坏账率',
  `F29` DATETIME DEFAULT NULL COMMENT '最后操作时间',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='运营数据基础设置表';

INSERT INTO `S61`.`T6196` VALUES(1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0.00,0.00,0,0.00,0.00,CURRENT_TIMESTAMP());

DROP TABLE IF EXISTS `S61`.`T6197`;
CREATE TABLE `S61`.`T6197` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计投资金额',
  `F03` DATE NOT NULL COMMENT '累计投资日期',.
  `F04` DATETIME DEFAULT NULL COMMENT '最后操作时间',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='运营数据累计投资设置表';

ALTER TABLE `S70`.`T7042`
MODIFY COLUMN `F02`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

DROP PROCEDURE IF EXISTS `S70`.`SP_T7042`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7042`(IN _date DATE)
   
BEGIN
	
	
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	
	DECLARE		_F02							VARCHAR(2) 					;	
	
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	
	DECLARE		_F04							INT 					UNSIGNED		DEFAULT 0;	
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;
	DECLARE		_current_date				DATE;
	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	IF _F02<10 THEN
		SET _F02 = CONCAT('0',_F02);
	END IF;
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	
	SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01) INTO _F03,_F04	 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01  WHERE T6230.F20 IN ('YDF','YJQ','HKZ') AND T6231.F12 >= _startDate AND T6231.F12 <= _endDate;
	INSERT INTO S70.T7042 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04);
END
;;
DELIMITER ;
