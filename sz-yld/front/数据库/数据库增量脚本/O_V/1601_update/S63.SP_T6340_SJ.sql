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
        DECLARE _stop_credit  INT   DEFAULT 0;    -- 是否达到记录的末尾控制变量
        DECLARE _error        INT   DEFAULT 0;
        DECLARE t6340s_SJ CURSOR FOR SELECT T6340.F01, T6340.F03, T6340.F04, ( SELECT jxz.F01 FROM S63.T6340 jxz WHERE jxz.F03 = T6340.F03 AND jxz.F04 = T6340.F04 AND jxz.F08 = 'JXZ' LIMIT 1 ) FROM S63.T6340 WHERE T6340.F08 = 'YSJ' AND DATE(T6340.F06) <= DATE(CURRENT_TIMESTAMP());
        
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
                            UPDATE S63.T6340 SET F08 = 'YXJ',F13 = '同类型活动复盖' WHERE F03 = _F02 AND F04 = _F03 AND F08 = 'JXZ';
                            INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F04,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06='同类型活动复盖';
                        END IF;
                        UPDATE S63.T6340 SET F08 = 'JXZ' WHERE F01=_F01;
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