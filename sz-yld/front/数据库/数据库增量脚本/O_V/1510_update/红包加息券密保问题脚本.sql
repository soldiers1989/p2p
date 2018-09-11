DROP TABLE IF EXISTS `S63`.`T6340`;
CREATE TABLE `S63`.`T6340` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `F02` varchar(20) NOT NULL COMMENT '活动编码',
  `F03` enum('redpacket','interest') NOT NULL COMMENT '奖励类型：（redpacket：红包；interest：加息卷）',
  `F04` enum('register','recharge','birthday','investmultiple','investlimit','foruser','tjsccz','tjsctz','tjtzze') NOT NULL COMMENT '活动类型：（register：注册赠送；recharge：充值赠送；birthday：生日赠送；investmultiple：投资倍数赠送；investlimit：投资额度赠送；foruser：指定用户赠送；tjsccz：推荐首次充值奖励；tjsctz：推荐首次投资奖励；tjtzze：推荐投资总额奖励）',
  `F05` varchar(20) NOT NULL COMMENT '活动名称',
  `F06` datetime DEFAULT NULL COMMENT '活动开始日期',
  `F07` datetime DEFAULT NULL COMMENT '活动结束日期',
  `F08` int(20) NOT NULL DEFAULT '0' COMMENT '发放数量',
  `F09` int(2) NOT NULL COMMENT '使用有效期',
  `F10` decimal(10,2) NOT NULL COMMENT '价值：红包单位为元，加息卷单位是%',
  `F11` decimal(10,0) NOT NULL COMMENT '投资额度/倍数/最低充值额度',
  `F12` int(11) NOT NULL DEFAULT '1' COMMENT '使用规则：0：无规则；1：有规则',
  `F13` decimal(10,0) DEFAULT NULL COMMENT '投资使用规则（满多少就能使用）',
  `F14` enum('DSJ','YSJ','JXZ','YXJ','YZF') NOT NULL DEFAULT 'DSJ' COMMENT '活动状态：DSJ：待上架；YSJ：预上架；JXZ：进行中；YXJ：已下架；YZF：已作废',
  `F15` enum('login','invest','all') DEFAULT 'login' COMMENT '生日赠送领取条件：login：生日当天登录；invest：生日当天投资；all：不限',
  `F16` varchar(200) DEFAULT NULL COMMENT '备注',
  `F17` datetime DEFAULT NULL COMMENT '创建时间',
  `F18` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `F19` int(11) DEFAULT '1' COMMENT '是否首次充值（0：否；1：是）',
  `F20` int(20) NOT NULL DEFAULT '0' COMMENT '已送数量',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6340_F02_UNIQUE` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='活动管理信息表';


DROP TABLE IF EXISTS `S63`.`T6342`;
CREATE TABLE `S63`.`T6342` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `F02` int(11) NOT NULL COMMENT '用户id：参考T6110.F01',
  `F03` int(11) NOT NULL COMMENT '活动id：参考T6340.F01',
  `F04` enum('WSY','YSY','YCJQ','ZCJQ','YGQ') NOT NULL COMMENT '使用状态：WSY：未使用；YSY：已使用；YGQ：已过期；ZCJQ：正常结清；YCJQ：异常结清',
  `F05` datetime DEFAULT NULL COMMENT '使用时间',
  `F06` int(11) DEFAULT NULL COMMENT '标id：参考T6230.F01',
  `F07` datetime DEFAULT NULL COMMENT '赠送时间',
  `F08` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8 COMMENT='用户活动表';


DROP TABLE IF EXISTS `S63`.`T6343`;
CREATE TABLE `S63`.`T6343` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) unsigned NOT NULL COMMENT '后台帐号ID,参考T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '活动ID,参考T6340.F01',
  `F04` datetime NOT NULL COMMENT '操作时间',
  `F05` varchar(200) DEFAULT NULL COMMENT '操作描述',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='活动操作日志';


DROP TABLE IF EXISTS `S62`.`T6288`;
CREATE TABLE `S62`.`T6288` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(9,9) unsigned NOT NULL COMMENT '加息率',
  `F05` datetime NOT NULL COMMENT '投资时间',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否放款,F:否;S:是;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否自动投资',
  `F09` int(10) DEFAULT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F10` int(10) DEFAULT NULL COMMENT '用户活动ID;参考T6342.F01',
  `F11` decimal(20,2) unsigned NOT NULL COMMENT '投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券投资记录';



DROP TABLE IF EXISTS `S62`.`T6289`;
CREATE TABLE `S62`.`T6289` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '付款平台ID,参考T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F06` int(10) unsigned NOT NULL COMMENT '期号',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '加息券返还利息',
  `F08` date NOT NULL COMMENT '返还日期',
  `F09` enum('WFH','YFH','YSX') NOT NULL COMMENT '状态,WFH:未返还;YFH:已返还;YSX:已失效;',
  `F10` datetime DEFAULT NULL COMMENT '实际返还时间',
  `F11` decimal(20,2) DEFAULT NULL COMMENT '剩余加息券返还利息',
  `F12` int(11) DEFAULT '0' COMMENT '用户加息券ID,参考T6342.F01',
  `F13` int(11) DEFAULT '0' COMMENT '债权ID,参考T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F05` (`F05`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE,
  KEY `F02_UNIQUE` (`F02`,`F04`,`F06`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券利息返还记录';


DROP TABLE IF EXISTS `S62`.`T6292`;
CREATE TABLE `S62`.`T6292` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '红包金额',
  `F05` datetime NOT NULL COMMENT '投资时间',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否放款,F:否;S:是;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否自动投资',
  `F09` int(10) DEFAULT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F10` int(10) DEFAULT NULL COMMENT '红包id;参考T6342.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='红包投资记录';


DROP TABLE IF EXISTS `S65`.`T6523`;
CREATE TABLE `S65`.`T6523` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` decimal(9,9) unsigned NOT NULL DEFAULT '0.000000000' COMMENT '加息率',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '投资记录ID,参考T6288.F01,投资成功时记录',
  `F06` int(10) DEFAULT '0' COMMENT '投资订单ID,T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券投资订单';


DROP TABLE IF EXISTS `S65`.`T6524`;
CREATE TABLE `S65`.`T6524` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '加息券投资记录ID,参考T6288.F01',
  `F05` decimal(9,9) unsigned NOT NULL COMMENT '加息券利率',
  `F06` decimal(20,2) NOT NULL COMMENT '使用加息券的投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券放款订单';



DROP TABLE IF EXISTS `S65`.`T6525`;
CREATE TABLE `S65`.`T6525` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '返还利息期号',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '返还利息',
  `F06` int(11) unsigned NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  `F07` int(11) NOT NULL COMMENT '返还利息平台ID',
  `F08` int(11) DEFAULT '0' COMMENT '加息券返还ID，参考T6289.F01',
  `F09` int(11) DEFAULT '0' COMMENT '标的放款订单ID，参考T6505.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券利息返还订单';


DROP TABLE IF EXISTS `S65`.`T6526`;
CREATE TABLE `S65`.`T6526` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '加息券投资记录ID,参考T6288.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '投资取消订单ID,参考T6508.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券投资取消订单';


DROP TABLE IF EXISTS `S65`.`T6527`;
CREATE TABLE `S65`.`T6527` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '红包金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '红包投资记录ID,参考t6292.F01,投资成功时记录',
  `F06` int(10) DEFAULT '0' COMMENT '投资订单ID,T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包投资订单';

DROP TABLE IF EXISTS `S61`.`T6193`;
CREATE TABLE `S61`.`T6193` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` enum('type3','type2','type1') NOT NULL COMMENT '问题类型',
  `F03` varchar(100) NOT NULL COMMENT '问题描述',
  `F04` int(11) NOT NULL COMMENT '排序字段',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='密码问题表';

DROP TABLE IF EXISTS `S61`.`T6194`;
CREATE TABLE `S61`.`T6194` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` int(11) NOT NULL COMMENT '用户ID,T6110.F01',
  `F03` int(11) NOT NULL COMMENT '问题ID,T6193.F01',
  `F04` varchar(500) NOT NULL COMMENT '问题回答',
  `F05` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='密码问题与答案';



-- ----------------------------
-- Procedure structure for `SP_T6340_SJ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_SJ`()
    COMMENT '修改活动为上架状态,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- 活动ID
		DECLARE _F02     VARCHAR(20)     DEFAULT ''; -- 奖励类型
		DECLARE _F03     VARCHAR(20)     DEFAULT ''; -- 活动类型
		DECLARE _F04     INT     DEFAULT 0; -- 存在的进行中的活动ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_SJ CURSOR FOR SELECT T6340.F01,T6340.F03,T6340.F04,(SELECT jxz.F01 FROM S63.T6340 jxz WHERE jxz.F03 = T6340.F03 AND jxz.F04 = T6340.F04 AND jxz.F14 = 'JXZ' LIMIT 1) FROM S63.T6340 WHERE T6340.F14 = 'YSJ'  AND DATE(T6340.F06) <= DATE(CURRENT_TIMESTAMP());
		
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
							UPDATE S63.T6340 SET F14 = 'YXJ' WHERE F03 = _F02 AND F04 = _F03 AND F14 = 'JXZ';
							INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F04,F04 = CURRENT_TIMESTAMP(),F05 ='下架';
						END IF;
						UPDATE S63.T6340 SET F14 = 'JXZ' WHERE F01=_F01;
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

-- ----------------------------
-- Procedure structure for `SP_T6340_XJ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `S63`.`SP_T6340_XJ`()
    COMMENT '修改活动为下架状态,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- 活动ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_XJ CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE T6340.F14 = 'JXZ' AND T6340.F06 IS NOT NULL  AND (DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP()) OR T6340.F08 = T6340.F20);
		
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
						UPDATE S63.T6340 SET F14 = 'YXJ' WHERE F01=_F01;
						INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='下架';
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `S63`.`SP_T6340_ZF`()
    COMMENT '修改活动为作废状态,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- 活动ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_ZF CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE T6340.F14 = 'DSJ' AND T6340.F06 IS NOT NULL  AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP());
		
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
						UPDATE S63.T6340 SET F14 = 'YZF' WHERE F01=_F01;
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
    COMMENT '修改到期没有使用的活动状态'
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
-- Procedure structure for `SP_T6342_birth`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6342_birth`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6342_birth`()
    COMMENT '生日赠送活动'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _act_id INT DEFAULT 0;  -- 活动ID
		DECLARE _user_id INT DEFAULT 0; -- 用户ID
		DECLARE _stop_act INT DEFAULT 0; -- 停止右游标标识
		DECLARE _error INT DEFAULT 0; 
		DECLARE _expired_days INT DEFAULT 0; -- 失效天数
		DECLARE _expired_time DATE; -- 失效时间
		DECLARE _act_count INT DEFAULT 0; -- 活动数量
		DECLARE _send_count INT DEFAULT 0; -- 活动已送数量
		DECLARE _users CURSOR FOR SELECT F01 from S61.T6141 where DATE_FORMAT(F08,'%m%d')= DATE_FORMAT(CURRENT_DATE(),'%m%d'); -- 当天生日的用户
		DECLARE _t6340s CURSOR FOR SELECT F01, F09, F08, F20 from S63.T6340 where CURRENT_DATE() <= F07 AND CURRENT_DATE() >= F06 AND F04 ='birthday' and F15 = 'all' and F14='JXZ' limit 1;   -- 生日活动

		-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_act = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
		BEGIN 
			SHOW ERRORS;
			SET _error = 1;
		END;
		
		OPEN _t6340s;
		read_loop:LOOP
			FETCH _t6340s INTO _act_id, _expired_days, _act_count, _send_count;
			SET _expired_time = DATE_ADD(CURRENT_DATE(),INTERVAL _expired_days MONTH);
			SET _act_count = _act_count -1;
			
		    IF P_ID IS NULL THEN
			  SET P_ID = 0;
				LEAVE read_loop;
			END IF;
			IF _stop_act = 0 THEN
				OPEN _users;
				users_loop:LOOP
					FETCH _users INTO _user_id;
					IF P_ID IS NULL THEN
						SET P_ID = 0;
						LEAVE users_loop;
					END IF;
					IF _send_count > _act_count THEN -- 判断红包数量是否用完了
						SET P_ID = 0;
						LEAVE users_loop;
					END IF;
					IF _stop_act = 0 THEN
						START TRANSACTION;
							INSERT INTO s63.t6342(F02,F03,F04,F07,F08) VALUES(_user_id, _act_id, 'WSY', NOW(), _expired_time); -- 给用户送活动
							UPDATE S63.T6340 SET F20 = F20 + 1 WHERE F01 = _act_id; -- 更新活动数量
							SET _send_count = _send_count + 1;
						IF _error = 1 THEN 
								ROLLBACK;
								SET _error = 0;
						ELSE 
							COMMIT;
						END IF;
					END IF;
				END LOOP;
				CLOSE _users;
			END IF;
	    END LOOP;
		CLOSE _t6340s;
	END
;;
DELIMITER ;


-- ----------------------------
-- Event structure for `EVT_T6340_SJ`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `S63`.`EVT_T6340_SJ` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6340_SJ()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_XJ`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `S63`.`EVT_T6340_XJ` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6340_XJ()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_ZF`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_ZF`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `S63`.`EVT_T6340_ZF` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL SP_T6340_ZF()
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

-- ----------------------------
-- Event structure for `EVT_T6342_birth`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6342_birth`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6342_birth` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-08 00:00:05' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6342_birth()
;;
DELIMITER ;

ALTER TABLE `S61`.`T6118`
ADD COLUMN `F10`  enum('BTG','TG') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'BTG' COMMENT '密保认证状态，不通过，通过' AFTER `F09`;

INSERT INTO `S51`.`T5122` VALUES ('7009', '红包奖励', 'QY');
INSERT INTO `S51`.`T5122` VALUES ('7010', '加息奖励', 'QY');











