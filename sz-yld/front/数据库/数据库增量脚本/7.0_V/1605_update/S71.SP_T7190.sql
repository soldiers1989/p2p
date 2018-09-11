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
				SELECT IFNULL(SUM(T6230.F05-T6230.F07),0) INTO _F02 FROM S62.T6230 LEFT JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6231.F31 = T7110_F12 AND T6231.F32 = 'QY' AND T6230.F20 IN('HKZ','YJQ','YDF') AND (SELECT COUNT(1) FROM S61.T6110 WHERE T6110.F14 IS NOT NULL AND T6110.F01 = T6230.F02) > 0 AND DATE(T6231.F12) = _F10;
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
				IN (SELECT T6110.F01 FROM S61.T6110 WHERE T6110.F14 IS NOT NULL))) AND T6230.F20 IN('HKZ','YJQ','YDF') AND T6231.F32 = 'QY' AND T6231.F31 = T7110_F12 AND DATE(T6231.F12) = _F10;
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