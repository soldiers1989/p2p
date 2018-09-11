-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7050`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7050`(IN _date DATE)
    COMMENT '用户投资排行 -按周'
BEGIN
	-- 用户投资排行 -按周
	-- 账号ID
	DECLARE		_F01 				INT 		UNSIGNED  DEFAULT 0;
	-- 账号名称
	DECLARE		_F02 				VARCHAR(20);
	-- 投资总额
	DECLARE		_F03 				DECIMAL(20, 2) DEFAULT 0.00;


	DECLARE		_current_date 				DATE;
	DECLARE 	_start_date 					DATE;
	DECLARE 	_end_date 						DATE;
	DECLARE 	_week									VARCHAR(10);

	-- 列表游标定义
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;

	SET 	_current_date = IFNULL(_date, CURRENT_DATE());
	SET 	_start_date 	= DATE_SUB(_current_date, INTERVAL 1 WEEK);
	SET 	_end_date 		= _current_date;
	SET		_week 				= CONCAT(YEAR(_start_date), WEEKOFYEAR(_start_date));

	BEGIN

		DECLARE _total_list 	CURSOR FOR 	SELECT ACCOUNT_ID, ACCOUNT_NAME, SUM(TOTAL_AMOUNT) FROM (( SELECT T6110.F01 AS ACCOUNT_ID, T6110.F02 AS ACCOUNT_NAME, SUM(T6250.F04) AS TOTAL_AMOUNT FROM S62.T6250 INNER JOIN S61.T6110 ON T6110.F01 = T6250.F03 WHERE T6250.F07 = 'F' AND T6250.F04 > 0 AND DATE(T6250.F06) >= _start_date AND DATE(T6250.F06) <= _end_date GROUP BY T6110.F01 ) UNION ALL ( SELECT T6110.F01 AS ACCOUNT_ID, T6110.F02 AS ACCOUNT_NAME, SUM(T6262.F04) AS TOTAL_AMOUNT FROM S62.T6262 INNER JOIN S61.T6110 ON T6110.F01 = T6262.F03 WHERE T6262.F04 > 0 AND DATE(T6262.F07) >= _start_date AND DATE(T6262.F07) <= _end_date GROUP BY T6110.F01 )) AS ACOUNT_TABLE GROUP BY ACOUNT_TABLE.ACCOUNT_ID, ACOUNT_TABLE.ACCOUNT_NAME ORDER BY ACOUNT_TABLE.ACCOUNT_NAME DESC LIMIT 5;

	  DECLARE 	CONTINUE 				HANDLER FOR NOT FOUND SET _done = 1;		
		
		OPEN _total_list; 
 

		REPEAT 
			FETCH _total_list INTO _F01, _F02, _F03;
				
			IF NOT _done THEN
					-- 插入统计数据 
					
		 IF _F03 > 0 THEN
		 INSERT INTO S70.T7050 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _week
					ON DUPLICATE KEY UPDATE F03 = VALUES(F03); 
		 END IF;
					
			END IF;
			UNTIL _done END REPEAT;

		CLOSE _total_list;

	END;

END
;;
DELIMITER ;
