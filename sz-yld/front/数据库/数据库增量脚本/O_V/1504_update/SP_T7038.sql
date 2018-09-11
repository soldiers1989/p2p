DROP PROCEDURE IF EXISTS `S70`.`SP_T7038`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7038`(IN _date DATE)
   
BEGIN
	
	
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	
	DECLARE		_F03							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F04							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F05							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F06							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F07							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F08							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F09							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F10							DECIMAL(20,2) 			 DEFAULT 	0.00;
	
	DECLARE		_F11							DECIMAL(20,2) 			 DEFAULT 	0.00;
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATE;
	DECLARE		_current_date				DATE;
	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	CALL SP_YEAR_DATE(_F01,_startDate,_endDate);
	
	SELECT IFNULL(SUM(F07),0) INTO _F03  FROM S61.T6102 WHERE  F03 = '1002' AND  F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F06),0) INTO _F04  FROM S61.T6102 WHERE  F03 = '2002' AND EXISTS (SELECT 1 FROM S61.T6101 WHERE T6101.F01 = T6102.F02 AND EXISTS (SELECT 1 FROM S61.T6110 WHERE T6101.F02 = T6110.F01 AND T6101.F01 = T6110.F01)) AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F05  FROM S61.T6102 WHERE  F03 = '1003' AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F06  FROM S61.T6102 WHERE  F03 = '2003' AND	F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F07  FROM S61.T6102 WHERE  F03 = '1501' AND	F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F08  FROM S61.T6102 WHERE  F03 = '6002' AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F09  FROM S61.T6102 WHERE  F03 = '7003' AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F10  FROM S61.T6102 WHERE  F03 = '4001' AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F11  FROM S61.T6102 WHERE  F03 = '8001' AND F05 >= _startDate AND DATE(F05) <= _endDate;
	INSERT INTO S70.T7038 SET F01 = _F01, F03 = _F03, F04 = _F04, F05 = _F05 ,F06 = _F06, F07 = _F07, F08 = _F08, F09 = _F09, F10 = _F10, F11 = _F11
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04),F05 = VALUES(F05),F06 = VALUES(F06) , F07 = VALUES(F07),
	F08 = VALUES(F08),F09 = VALUES(F09) , F10 = VALUES(F10),F11 = VALUES(F11);
END
;;
DELIMITER ;