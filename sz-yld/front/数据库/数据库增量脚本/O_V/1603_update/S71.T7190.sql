DROP PROCEDURE IF EXISTS `S71`.`SP_T7190`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S71`.`SP_T7190`()
BEGIN
	-- 业务员业绩统计
	-- 一级用户投资金额
	DECLARE _F01 DECIMAL(20,2) DEFAULT 0.00;
	-- 一级用户借款金额
	DECLARE _F02 DECIMAL(20,2) DEFAULT 0.00;
	-- 二级用户投资金额
	DECLARE _F03 DECIMAL(20,2) DEFAULT 0.00;
	-- 二级用户借款金额
	DECLARE _F04 DECIMAL(20,2) DEFAULT 0.00;

  DECLARE _F06 VARCHAR(50) DEFAULT null;
  DECLARE _F07 VARCHAR(50) DEFAULT null;

  DECLARE T6110_F14 VARCHAR(50);
  DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;
  DECLARE ywyCur CURSOR   FOR SELECT  DISTINCT F14 from S61.T6110  where T6110.F14 is not null;
  DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
  OPEN ywyCur;
		REPEAT 
			FETCH ywyCur INTO T6110_F14;
			IF NOT _done THEN
				SELECT CURDATE() INTO _F06 FROM dual;	
				SELECT IFNULL(SUM(T6250.F04),0) INTO _F01 FROM S62.T6250 LEFT JOIN S62.T6231 ON T6231.F01 = T6250.F02 WHERE T6250.F12 = T6110_F14 AND T6250.F08='S' AND T6250.F13 = 'QY' AND (SELECT COUNT(1) FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6250.F03) > 0 AND DATE(T6231.F12) = CURDATE();	 
				SELECT IFNULL(SUM(T6230.F05),0) INTO _F02 FROM S62.T6230 LEFT JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6231.F31 = T6110_F14 AND T6231.F32 = 'QY' AND T6230.F20 IN('HKZ','YJQ','YDF') AND (SELECT COUNT(1) FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6230.F02) > 0 AND DATE(T6231.F12) = CURDATE();
				SELECT IFNULL(SUM(T6250.F04),0) INTO _F03 FROM S62.T6250 LEFT JOIN S62.T6231 ON T6231.F01 = T6250.F02 WHERE T6250.F03 IN(SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL))) AND T6250.F08='S' AND T6250.F13 = 'QY' AND T6250.F12 = T6110_F14 AND DATE(T6231.F12) = CURDATE(); 
				SELECT IFNULL(SUM(T6230.F05),0) INTO _F04 FROM S62.T6230 LEFT JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F02 
				IN(SELECT F01 FROM S61.T6111 WHERE T6111.F03 IN (SELECT T6111.F02 FROM S61.T6111 WHERE T6111.F01 
				IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL))) AND T6230.F20 IN('HKZ','YJQ','YDF') AND T6231.F32 = 'QY' AND T6231.F31 = T6110_F14 AND DATE(T6231.F12) = CURDATE();
				
        INSERT INTO S71.T7190 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04, F06 = _F06,F07=T6110_F14 ON DUPLICATE KEY UPDATE F01 = VALUES(F01),F02 = VALUES(F02),F03 = VALUES(F03),F04 = VALUES(F04);
		END IF;
		UNTIL _done END REPEAT;			
 CLOSE ywyCur;

END
;;
DELIMITER ;