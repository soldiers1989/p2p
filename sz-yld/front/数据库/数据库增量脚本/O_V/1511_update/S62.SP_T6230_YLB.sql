DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YLB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YLB`()
    COMMENT '修改满足条件的投资中状态为已流标,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE 		_F01     INT     DEFAULT 0; -- 标ID
		DECLARE 		_stop_credit							   	INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE 		_error 					            	INT 					 DEFAULT 0;
		DECLARE _cur_credit CURSOR FOR SELECT F01 FROM S62.T6230 WHERE F20 = 'TBZ' AND F22 IS NOT NULL AND DATE_ADD(F22,INTERVAL F08 DAY) < CURRENT_TIMESTAMP() AND F05 = F07;
		
		-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN _cur_credit;
			REPEAT
				FETCH _cur_credit INTO _F01;
				IF P_ID IS NULL THEN
					SET _stop_credit = 1;
				END IF;
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S62.T6230 SET F20 = 'YLB' WHERE F01=_F01 ;
						UPDATE S62.T6231 SET F11=CURRENT_TIMESTAMP(),F29 = 'F' WHERE F01=_F01;
						IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE _cur_credit;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;
