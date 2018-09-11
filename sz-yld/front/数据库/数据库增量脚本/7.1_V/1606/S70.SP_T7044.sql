DROP PROCEDURE IF EXISTS `S70`.`SP_T7044`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7044`(IN _date DATE)
    COMMENT '�ɽ�����ͳ��-����-������'
BEGIN
	-- �ɽ�����ͳ��-����-������	
	-- ���
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- �·�
	DECLARE		_F02							INT 					UNSIGNED		DEFAULT	0;	
	-- �ɽ����
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	-- �ɽ�����
	DECLARE		_F04							INT 					UNSIGNED		DEFAULT 0;	
	-- ����
	DECLARE		_F05							INT 					UNSIGNED		DEFAULT 0;	
	-- �Ƿ������
	DECLARE		_F06							VARCHAR(32)				DEFAULT 'F';
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;
	DECLARE		_current_date				DATE;
	-- ����б��α궨��
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_loan_list 			CURSOR FOR 		SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01),CASE WHEN T6231.F21='S' THEN T6231.F22 ELSE T6230.F09 END,
	T6231.F21 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F20 IN('HKZ','YJQ','YDF') AND T6231.F12>=_startDate AND T6231.F12<=_endDate GROUP BY T6230.F09;
	DECLARE 	CONTINUE 				HANDLER FOR NOT FOUND SET _done = 1;

	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	OPEN _loan_list;
	
	REPEAT 
		FETCH _loan_list INTO _F03,_F04,_F05,_F06;
			
		IF NOT _done THEN
				INSERT INTO S70.T7044 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 ,F07=_F06
				ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);
		END IF;
	UNTIL _done END REPEAT;

	CLOSE _loan_list;
END