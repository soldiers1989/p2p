DROP TABLE IF EXISTS `S70`.`T7053`;
CREATE TABLE `S70`.`T7053` (
  `F01` int(4) unsigned NOT NULL COMMENT '年份',
  `F02` int(2) unsigned NOT NULL COMMENT '月份',
  `F03` int(11) NOT NULL DEFAULT 0 COMMENT '人数',
  `F04` decimal(20,2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='逾期数据统计';


DELIMITER $$

USE `s70`$$

DROP PROCEDURE IF EXISTS `SP_T7053`$$

CREATE DEFINER=`root`@`%` PROCEDURE `SP_T7053`(IN _date DATE)
    COMMENT '逾期数据统计'
BEGIN 
	DECLARE	 _F01	 INT UNSIGNED	 DEFAULT 0;	
	DECLARE	 _F02	 INT UNSIGNED	 DEFAULT 0;	
	DECLARE	 _F03	 INT UNSIGNED    DEFAULT 0; 
	DECLARE	 _F04	 DECIMAL(20,2) DEFAULT 0.00; 
	DECLARE	 _current_date	 DATE; 
	SET _current_date = IFNULL(_date,CURRENT_DATE()); 
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date); 
	SELECT COUNT(DISTINCT T6252.F03) INTO _F03 FROM S62.T6252 WHERE ((T6252.F08 < DATE(CURRENT_DATE()) AND T6252.F10 IS NULL ) OR (T6252.F08 < DATE(T6252.F10))) AND DATE_FORMAT(DATE_ADD(T6252.F08,INTERVAL 1 DAY),'%Y%m')=DATE_FORMAT(CURRENT_DATE(),'%Y%m');
	SELECT IFNULL(SUM(T6252.F07),0) INTO _F04 FROM S62.T6252 WHERE ((T6252.F08 < DATE(CURRENT_DATE()) AND T6252.F10 IS NULL ) OR (T6252.F08 < DATE(T6252.F10))) AND T6252.F05 IN(7001,7002,7004) AND DATE_FORMAT(DATE_ADD(T6252.F08,INTERVAL 1 DAY),'%Y%m')=DATE_FORMAT(CURRENT_DATE(),'%Y%m');
	INSERT INTO S70.T7053 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04); 
END$$

DELIMITER ;



DROP EVENT IF EXISTS `S70`.`EVT_T7053`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7053` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7053(CURRENT_DATE())
;;
DELIMITER ;
