-- ----------------------------
-- Procedure structure for `SP_T6242_YJZ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S62`.`SP_T6242_YJZ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6242_YJZ`()

BEGIN
	DECLARE P_ID INT DEFAULT 0;
	DECLARE _actId INT DEFAULT 0;
	DECLARE _stop_act INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _t6242s CURSOR FOR SELECT F01 FROM S62.T6242 WHERE F19 IS NULL AND F13 IS NOT NULL AND DATE_ADD(F13,INTERVAL F08 - 1 DAY) < CURRENT_DATE();
	
	-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_act = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	OPEN _t6242s;
	read_loop:LOOP
		FETCH _t6242s INTO _actId;
    SELECT _actId,_stop_act,P_ID,_error;
    IF P_ID IS NULL THEN
		  SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		IF _stop_act = 0 THEN
				START TRANSACTION;
					UPDATE S62.T6242 SET F11 = 'YJZ',F19 = NOW() WHERE F01 = _actId;
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
  END LOOP;
	CLOSE _t6242s;
END
;;
DELIMITER ;


-- Event structure for `EVT_T6242_YJZ`
-- ----------------------------
DROP EVENT IF EXISTS `S62`.`EVT_T6242_YJZ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6242_YJZ` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-08 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6242_YJZ()
;;
DELIMITER ;