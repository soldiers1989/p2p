DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_XJ`()
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _stop_credit        INT                    DEFAULT 0;                -- 是否达到记录的末尾控制变量
        DECLARE _error                     INT                      DEFAULT 0;
        DECLARE t6340s_XJ CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE (T6340.F08 = 'JXZ' AND T6340.F06 IS NOT NULL  AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP())) OR  
		(T6340.F01 IN (SELECT T6344.F02 FROM S63.T6344 GROUP BY T6344.F02 HAVING  SUM(T6344.F03) = SUM(T6344.F08)) AND T6340.F08 != 'YXJ');
        
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
                        UPDATE S63.T6340 SET F08 = 'YXJ',F13 = '活动结束时间或达到活动限制数量' WHERE F01=_F01;
                        INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06 ='活动结束时间或达到活动限制数量';
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