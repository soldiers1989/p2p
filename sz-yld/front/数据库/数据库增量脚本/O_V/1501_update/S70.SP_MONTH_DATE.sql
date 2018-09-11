DROP PROCEDURE IF EXISTS `S70`.`SP_MONTH_DATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_MONTH_DATE`(IN _current_date DATE, OUT _startDate DATE, OUT _endDate DATETIME)
    COMMENT '计算月份开始和截至日期'
BEGIN 
	SET _current_date = IFNULL(_current_date,CURRENT_DATE());
	SET _startDate = DATE_SUB(_current_date ,INTERVAL DAY(_current_date )-1 DAY); 
	SET _endDate = DATE_FORMAT(CONCAT(LAST_DAY(_startDate),' 23:59:59.999'),'%Y-%m-%d %H:%i:%S.%f'); 
END
;;
DELIMITER ;