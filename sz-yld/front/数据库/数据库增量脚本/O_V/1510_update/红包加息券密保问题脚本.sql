DROP TABLE IF EXISTS `S63`.`T6340`;
CREATE TABLE `S63`.`T6340` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `F02` varchar(20) NOT NULL COMMENT '�����',
  `F03` enum('redpacket','interest') NOT NULL COMMENT '�������ͣ���redpacket�������interest����Ϣ��',
  `F04` enum('register','recharge','birthday','investmultiple','investlimit','foruser','tjsccz','tjsctz','tjtzze') NOT NULL COMMENT '����ͣ���register��ע�����ͣ�recharge����ֵ���ͣ�birthday���������ͣ�investmultiple��Ͷ�ʱ������ͣ�investlimit��Ͷ�ʶ�����ͣ�foruser��ָ���û����ͣ�tjsccz���Ƽ��״γ�ֵ������tjsctz���Ƽ��״�Ͷ�ʽ�����tjtzze���Ƽ�Ͷ���ܶ����',
  `F05` varchar(20) NOT NULL COMMENT '�����',
  `F06` datetime DEFAULT NULL COMMENT '���ʼ����',
  `F07` datetime DEFAULT NULL COMMENT '���������',
  `F08` int(20) NOT NULL DEFAULT '0' COMMENT '��������',
  `F09` int(2) NOT NULL COMMENT 'ʹ����Ч��',
  `F10` decimal(10,2) NOT NULL COMMENT '��ֵ�������λΪԪ����Ϣ��λ��%',
  `F11` decimal(10,0) NOT NULL COMMENT 'Ͷ�ʶ��/����/��ͳ�ֵ���',
  `F12` int(11) NOT NULL DEFAULT '1' COMMENT 'ʹ�ù���0���޹���1���й���',
  `F13` decimal(10,0) DEFAULT NULL COMMENT 'Ͷ��ʹ�ù��������پ���ʹ�ã�',
  `F14` enum('DSJ','YSJ','JXZ','YXJ','YZF') NOT NULL DEFAULT 'DSJ' COMMENT '�״̬��DSJ�����ϼܣ�YSJ��Ԥ�ϼܣ�JXZ�������У�YXJ�����¼ܣ�YZF��������',
  `F15` enum('login','invest','all') DEFAULT 'login' COMMENT '����������ȡ������login�����յ����¼��invest�����յ���Ͷ�ʣ�all������',
  `F16` varchar(200) DEFAULT NULL COMMENT '��ע',
  `F17` datetime DEFAULT NULL COMMENT '����ʱ��',
  `F18` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
  `F19` int(11) DEFAULT '1' COMMENT '�Ƿ��״γ�ֵ��0����1���ǣ�',
  `F20` int(20) NOT NULL DEFAULT '0' COMMENT '��������',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6340_F02_UNIQUE` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='�������Ϣ��';


DROP TABLE IF EXISTS `S63`.`T6342`;
CREATE TABLE `S63`.`T6342` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `F02` int(11) NOT NULL COMMENT '�û�id���ο�T6110.F01',
  `F03` int(11) NOT NULL COMMENT '�id���ο�T6340.F01',
  `F04` enum('WSY','YSY','YCJQ','ZCJQ','YGQ') NOT NULL COMMENT 'ʹ��״̬��WSY��δʹ�ã�YSY����ʹ�ã�YGQ���ѹ��ڣ�ZCJQ���������壻YCJQ���쳣����',
  `F05` datetime DEFAULT NULL COMMENT 'ʹ��ʱ��',
  `F06` int(11) DEFAULT NULL COMMENT '��id���ο�T6230.F01',
  `F07` datetime DEFAULT NULL COMMENT '����ʱ��',
  `F08` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8 COMMENT='�û����';


DROP TABLE IF EXISTS `S63`.`T6343`;
CREATE TABLE `S63`.`T6343` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '��̨�ʺ�ID,�ο�T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '�ID,�ο�T6340.F01',
  `F04` datetime NOT NULL COMMENT '����ʱ��',
  `F05` varchar(200) DEFAULT NULL COMMENT '��������',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='�������־';


DROP TABLE IF EXISTS `S62`.`T6288`;
CREATE TABLE `S62`.`T6288` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT 'Ͷ����ID,�ο�T6110.F01',
  `F04` decimal(9,9) unsigned NOT NULL COMMENT '��Ϣ��',
  `F05` datetime NOT NULL COMMENT 'Ͷ��ʱ��',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ�ȡ��,F:��;S:��;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ�ſ�,F:��;S:��;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ��Զ�Ͷ��',
  `F09` int(10) DEFAULT NULL COMMENT 'Ͷ�ʼ�¼ID,�ο�T6250.F01',
  `F10` int(10) DEFAULT NULL COMMENT '�û��ID;�ο�T6342.F01',
  `F11` decimal(20,2) unsigned NOT NULL COMMENT 'Ͷ�ʽ��',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ϢȯͶ�ʼ�¼';



DROP TABLE IF EXISTS `S62`.`T6289`;
CREATE TABLE `S62`.`T6289` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '����ƽ̨ID,�ο�T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '�տ��û�ID,�ο�T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '��������ID,�ο�T5122.F01',
  `F06` int(10) unsigned NOT NULL COMMENT '�ں�',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '��Ϣȯ������Ϣ',
  `F08` date NOT NULL COMMENT '��������',
  `F09` enum('WFH','YFH','YSX') NOT NULL COMMENT '״̬,WFH:δ����;YFH:�ѷ���;YSX:��ʧЧ;',
  `F10` datetime DEFAULT NULL COMMENT 'ʵ�ʷ���ʱ��',
  `F11` decimal(20,2) DEFAULT NULL COMMENT 'ʣ���Ϣȯ������Ϣ',
  `F12` int(11) DEFAULT '0' COMMENT '�û���ϢȯID,�ο�T6342.F01',
  `F13` int(11) DEFAULT '0' COMMENT 'ծȨID,�ο�T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F05` (`F05`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE,
  KEY `F02_UNIQUE` (`F02`,`F04`,`F06`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ϣȯ��Ϣ������¼';


DROP TABLE IF EXISTS `S62`.`T6292`;
CREATE TABLE `S62`.`T6292` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT 'Ͷ����ID,�ο�T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '������',
  `F05` datetime NOT NULL COMMENT 'Ͷ��ʱ��',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ�ȡ��,F:��;S:��;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ�ſ�,F:��;S:��;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ��Զ�Ͷ��',
  `F09` int(10) DEFAULT NULL COMMENT 'Ͷ�ʼ�¼ID,�ο�T6250.F01',
  `F10` int(10) DEFAULT NULL COMMENT '���id;�ο�T6342.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='���Ͷ�ʼ�¼';


DROP TABLE IF EXISTS `S65`.`T6523`;
CREATE TABLE `S65`.`T6523` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT 'Ͷ���û�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F04` decimal(9,9) unsigned NOT NULL DEFAULT '0.000000000' COMMENT '��Ϣ��',
  `F05` int(10) unsigned DEFAULT NULL COMMENT 'Ͷ�ʼ�¼ID,�ο�T6288.F01,Ͷ�ʳɹ�ʱ��¼',
  `F06` int(10) DEFAULT '0' COMMENT 'Ͷ�ʶ���ID,T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ϢȯͶ�ʶ���';


DROP TABLE IF EXISTS `S65`.`T6524`;
CREATE TABLE `S65`.`T6524` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID,�ο�T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT 'Ͷ���û�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '��ϢȯͶ�ʼ�¼ID,�ο�T6288.F01',
  `F05` decimal(9,9) unsigned NOT NULL COMMENT '��Ϣȯ����',
  `F06` decimal(20,2) NOT NULL COMMENT 'ʹ�ü�Ϣȯ��Ͷ�ʽ��',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ϣȯ�ſ��';



DROP TABLE IF EXISTS `S65`.`T6525`;
CREATE TABLE `S65`.`T6525` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID,�ο�T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT 'Ͷ���û�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '������Ϣ�ں�',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '������Ϣ',
  `F06` int(11) unsigned NOT NULL COMMENT '��Ŀ����ID,�ο�T5122.F01',
  `F07` int(11) NOT NULL COMMENT '������Ϣƽ̨ID',
  `F08` int(11) DEFAULT '0' COMMENT '��Ϣȯ����ID���ο�T6289.F01',
  `F09` int(11) DEFAULT '0' COMMENT '��ķſ��ID���ο�T6505.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ϣȯ��Ϣ��������';


DROP TABLE IF EXISTS `S65`.`T6526`;
CREATE TABLE `S65`.`T6526` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT 'Ͷ���û�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��ϢȯͶ�ʼ�¼ID,�ο�T6288.F01',
  `F04` int(10) unsigned NOT NULL COMMENT 'Ͷ��ȡ������ID,�ο�T6508.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ϢȯͶ��ȡ������';


DROP TABLE IF EXISTS `S65`.`T6527`;
CREATE TABLE `S65`.`T6527` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT 'Ͷ���û�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '������',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '���Ͷ�ʼ�¼ID,�ο�t6292.F01,Ͷ�ʳɹ�ʱ��¼',
  `F06` int(10) DEFAULT '0' COMMENT 'Ͷ�ʶ���ID,T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���Ͷ�ʶ���';

DROP TABLE IF EXISTS `S61`.`T6193`;
CREATE TABLE `S61`.`T6193` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` enum('type3','type2','type1') NOT NULL COMMENT '��������',
  `F03` varchar(100) NOT NULL COMMENT '��������',
  `F04` int(11) NOT NULL COMMENT '�����ֶ�',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='���������';

DROP TABLE IF EXISTS `S61`.`T6194`;
CREATE TABLE `S61`.`T6194` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` int(11) NOT NULL COMMENT '�û�ID,T6110.F01',
  `F03` int(11) NOT NULL COMMENT '����ID,T6193.F01',
  `F04` varchar(500) NOT NULL COMMENT '����ش�',
  `F05` timestamp NULL DEFAULT NULL COMMENT '���¸���ʱ��',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='�����������';



-- ----------------------------
-- Procedure structure for `SP_T6340_SJ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_SJ`()
    COMMENT '�޸ĻΪ�ϼ�״̬,ÿ10��ִ��һ��'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- �ID
		DECLARE _F02     VARCHAR(20)     DEFAULT ''; -- ��������
		DECLARE _F03     VARCHAR(20)     DEFAULT ''; -- �����
		DECLARE _F04     INT     DEFAULT 0; -- ���ڵĽ����еĻID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- �Ƿ�ﵽ��¼��ĩβ���Ʊ���
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_SJ CURSOR FOR SELECT T6340.F01,T6340.F03,T6340.F04,(SELECT jxz.F01 FROM S63.T6340 jxz WHERE jxz.F03 = T6340.F03 AND jxz.F04 = T6340.F04 AND jxz.F14 = 'JXZ' LIMIT 1) FROM S63.T6340 WHERE T6340.F14 = 'YSJ'  AND DATE(T6340.F06) <= DATE(CURRENT_TIMESTAMP());
		
		-- ���mysql Bug:no data - zero rows fetched,selected,or processed
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
							INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F04,F04 = CURRENT_TIMESTAMP(),F05 ='�¼�';
						END IF;
						UPDATE S63.T6340 SET F14 = 'JXZ' WHERE F01=_F01;
						INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='�ϼ�';
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
    COMMENT '�޸ĻΪ�¼�״̬,ÿ10��ִ��һ��'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- �ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- �Ƿ�ﵽ��¼��ĩβ���Ʊ���
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_XJ CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE T6340.F14 = 'JXZ' AND T6340.F06 IS NOT NULL  AND (DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP()) OR T6340.F08 = T6340.F20);
		
		-- ���mysql Bug:no data - zero rows fetched,selected,or processed
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
						INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='�¼�';
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
    COMMENT '�޸ĻΪ����״̬,ÿ10��ִ��һ��'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- �ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- �Ƿ�ﵽ��¼��ĩβ���Ʊ���
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_ZF CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE T6340.F14 = 'DSJ' AND T6340.F06 IS NOT NULL  AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP());
		
		-- ���mysql Bug:no data - zero rows fetched,selected,or processed
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
						INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='����';
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
    COMMENT '�޸ĵ���û��ʹ�õĻ״̬'
BEGIN
	DECLARE P_ID INT DEFAULT 0;
	DECLARE _actId INT DEFAULT 0;
	DECLARE _stop_act INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _t6342s CURSOR FOR SELECT F01 FROM S63.T6342 WHERE F08 < CURRENT_DATE() AND T6342.F04 = 'WSY';
	
	-- ���mysql Bug:no data - zero rows fetched,selected,or processed
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
    COMMENT '�������ͻ'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _act_id INT DEFAULT 0;  -- �ID
		DECLARE _user_id INT DEFAULT 0; -- �û�ID
		DECLARE _stop_act INT DEFAULT 0; -- ֹͣ���α��ʶ
		DECLARE _error INT DEFAULT 0; 
		DECLARE _expired_days INT DEFAULT 0; -- ʧЧ����
		DECLARE _expired_time DATE; -- ʧЧʱ��
		DECLARE _act_count INT DEFAULT 0; -- �����
		DECLARE _send_count INT DEFAULT 0; -- ���������
		DECLARE _users CURSOR FOR SELECT F01 from S61.T6141 where DATE_FORMAT(F08,'%m%d')= DATE_FORMAT(CURRENT_DATE(),'%m%d'); -- �������յ��û�
		DECLARE _t6340s CURSOR FOR SELECT F01, F09, F08, F20 from S63.T6340 where CURRENT_DATE() <= F07 AND CURRENT_DATE() >= F06 AND F04 ='birthday' and F15 = 'all' and F14='JXZ' limit 1;   -- ���ջ

		-- ���mysql Bug:no data - zero rows fetched,selected,or processed
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
					IF _send_count > _act_count THEN -- �жϺ�������Ƿ�������
						SET P_ID = 0;
						LEAVE users_loop;
					END IF;
					IF _stop_act = 0 THEN
						START TRANSACTION;
							INSERT INTO s63.t6342(F02,F03,F04,F07,F08) VALUES(_user_id, _act_id, 'WSY', NOW(), _expired_time); -- ���û��ͻ
							UPDATE S63.T6340 SET F20 = F20 + 1 WHERE F01 = _act_id; -- ���»����
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
ADD COLUMN `F10`  enum('BTG','TG') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'BTG' COMMENT '�ܱ���֤״̬����ͨ����ͨ��' AFTER `F09`;

INSERT INTO `S51`.`T5122` VALUES ('7009', '�������', 'QY');
INSERT INTO `S51`.`T5122` VALUES ('7010', '��Ϣ����', 'QY');











