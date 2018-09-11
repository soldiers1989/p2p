/*
SQLyog Ultimate v8.32 
MySQL - 5.5.20 : Database - s63
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`s63` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `s63`;

/* Procedure structure for procedure `SP_T6340_SJ` */

/*!50003 DROP PROCEDURE IF EXISTS  `SP_T6340_SJ` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `SP_T6340_SJ`()
    COMMENT '修改活动为上架状态,每10秒执行一次'
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _F02     VARCHAR(20)     DEFAULT ''; -- 奖励类型
        DECLARE _F03     VARCHAR(20)     DEFAULT ''; -- 活动类型
        DECLARE _F04     INT     DEFAULT 0; -- 存在的进行中的活动ID
        DECLARE _stop_credit        INT                    DEFAULT 0;                -- 是否达到记录的末尾控制变量
        DECLARE _error                     INT                      DEFAULT 0;
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
                            UPDATE S63.T6340 SET F14 = 'YXJ',F21 = '同类型活动复盖' WHERE F03 = _F02 AND F04 = _F03 AND F14 = 'JXZ';
                            INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F04,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06='同类型活动复盖';
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
END */$$
DELIMITER ;

/* Procedure structure for procedure `SP_T6340_XJ` */

/*!50003 DROP PROCEDURE IF EXISTS  `SP_T6340_XJ` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `SP_T6340_XJ`()
    COMMENT '修改活动为下架状态,每10秒执行一次'
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _stop_credit        INT                    DEFAULT 0;                -- 是否达到记录的末尾控制变量
        DECLARE _error                     INT                      DEFAULT 0;
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
                        UPDATE S63.T6340 SET F14 = 'YXJ',F21 = '活动结束时间或达到活动限制数量' WHERE F01=_F01;
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
END */$$
DELIMITER ;

/* Procedure structure for procedure `SP_T6340_ZF` */

/*!50003 DROP PROCEDURE IF EXISTS  `SP_T6340_ZF` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `SP_T6340_ZF`()
    COMMENT '修改活动为作废状态,每10秒执行一次'
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _stop_credit        INT                    DEFAULT 0;                -- 是否达到记录的末尾控制变量
        DECLARE _error                     INT                      DEFAULT 0;
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
                        UPDATE S63.T6340 SET F14 = 'YZF',F21 = '活动结束时间' WHERE F01=_F01;
                        INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='作废',F06 ='活动结束时间';
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
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
