

DROP PROCEDURE IF EXISTS `S70`.`SP_T7203`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7203`()
BEGIN
	
	DECLARE _F01 INT DEFAULT '0';
	
	DECLARE _F02 INT DEFAULT '0';
	
	DECLARE _F03 DECIMAL(20,2) DEFAULT '0.00'; 

	DECLARE _F04 INT DEFAULT 0;

	DECLARE done INT DEFAULT 0; 
	
	DECLARE t_error INTEGER DEFAULT 0;  


	DECLARE CURSOR_TZJE CURSOR FOR
	SELECT TZJE_T.`YEAR`,TZJE_T.`MONTH`,SUM(TZJE_T.tzje),TZJE_T.userId 
	FROM (SELECT DATE_FORMAT(T6250.F06, '%Y') AS YEAR,DATE_FORMAT(T6250.F06, '%c') AS MONTH,IFNULL(SUM(T6250.F04), 0) AS tzje,T6250.F03 AS userId
		  FROM S62.T6250
		  WHERE T6250.F07 = 'F'
			AND T6250.F08 = 'S'
		  GROUP BY DATE_FORMAT(T6250.F06, '%Y-%m'),T6250.F03
		  UNION
		  SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR,DATE_FORMAT(T6262.F07, '%c') AS MONTH,IFNULL(SUM(T6262.F05), 0) AS tzje,T6262.F03 AS userId
		  FROM S62.T6262
		  GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6262.F03) AS TZJE_T
	GROUP BY TZJE_T.userId,TZJE_T.`YEAR`,TZJE_T.`MONTH`;

	DECLARE CURSOR_ZQZRYK CURSOR FOR
	SELECT ZQZR.`YEAR`,ZQZR.`MONTH`,SUM(ZQZR.zqzryk),ZQZR.userId 
	FROM (SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR,DATE_FORMAT(T6262.F07, '%c') AS MONTH,IFNULL(SUM(T6262.F08), 0) AS zqzryk,T6262.F03 AS userId
		  FROM S62.T6262
		  GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6262.F03
		  UNION 
		  SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR,DATE_FORMAT(T6262.F07, '%c') AS MONTH,IFNULL(SUM(T6262.F09), 0),T6251.F04
		  FROM S62.T6262, S62.T6260, S62.T6251
		  WHERE T6251.F01 = T6260.F02
			AND T6260.F01 = T6262.F02
		  GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6262.F03) AS ZQZR
	GROUP BY ZQZR.userId,ZQZR.`YEAR`,ZQZR.`MONTH`;

	DECLARE CURSOR_YSBJ CURSOR FOR
	SELECT PAY_EXPECT.`YEAR` AS YEAR, PAY_EXPECT.`MONTH` AS MONTH,	SUM(PAY_EXPECT.ysbj) AS ysbj, PAY_EXPECT.userId	 AS userId	
		FROM (SELECT DATE_FORMAT(T6252.F10, '%Y') AS YEAR, DATE_FORMAT(T6252.F10, '%c') AS MONTH,IFNULL(SUM(T6252.F07), 0) AS ysbj,T6252.F04 AS userId
					FROM S62.T6252 WHERE T6252.F09 = 'YH' AND T6252.F05 = 7001 GROUP BY	DATE_FORMAT(T6252.F10, '%Y-%m'),T6252.F04		
		UNION
				 SELECT DATE_FORMAT(IFNULL(T6252.F10,PAY_EXPECT_T.F07), '%Y') AS YEAR, DATE_FORMAT(IFNULL(T6252.F10,PAY_EXPECT_T.F07), '%c') AS MONTH, IFNULL(SUM(T6252.F07), 0) AS ysbj, PAY_EXPECT_T.F04 AS userId	
					 FROM	S62.T6252	
					 INNER JOIN 
				 ( SELECT T6251.F01, T6251.F04, T6253.F07, T6253.F08
					 FROM	S62.T6251
					 INNER JOIN
					 S62.T6253 ON T6251.F03 = T6253.F02	
					 GROUP BY	T6251.F03 ) AS PAY_EXPECT_T ON T6252.F11 = PAY_EXPECT_T.F01		
		WHERE	T6252.F05 = 7001
			AND T6252.F06 >= PAY_EXPECT_T.F08
		GROUP BY DATE_FORMAT(PAY_EXPECT_T.F07, '%Y-%m'), userId	
		UNION
		SELECT DATE_FORMAT(T6253.F07, '%Y') AS YEAR, DATE_FORMAT(T6253.F07, '%c') AS MONTH, SUM(IFNULL(T6252.F07,0)) AS ysbj, T6251.F04 AS userId
		FROM (SELECT T6251.F01 AS F01,T6251.F03 AS F02,T6262.F03 AS F03
						FROM S62.T6260
					INNER JOIN S62.T6251 ON T6260.F02 = T6251.F01
					INNER JOIN S62.T6262 ON T6260.F01 = T6262.F02
					WHERE T6260.F07 = 'YJS') ZQZR
		INNER JOIN S62.T6251 ON T6251.F04 = ZQZR.F03
		INNER JOIN S62.T6252 ON T6252.F11 = T6251.F01
		INNER JOIN S62.T6253 ON T6251.F03 = T6253.F02
		WHERE T6252.F05 = 7001
		GROUP BY DATE_FORMAT(T6253.F07, '%Y-%m'),T6251.F04
		UNION
		SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR, DATE_FORMAT(T6262.F07, '%c') AS MONTH, SUM(IFNULL(T6262.F05,0)) AS ysbj, T6251.F04 AS userId
			FROM S62.T6260
		 INNER JOIN S62.T6251 ON T6260.F02 = T6251.F01
		 INNER JOIN S62.T6262 ON T6260.F01 = T6262.F02
		 WHERE T6260.F07 = 'YJS'
		 GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6251.F04) AS PAY_EXPECT
	  GROUP BY PAY_EXPECT.userId,PAY_EXPECT.`YEAR`,PAY_EXPECT.`MONTH`;

	DECLARE CURSOR_YSLX CURSOR FOR
	SELECT PAY_EXPECT.`YEAR` AS YEAR, PAY_EXPECT.`MONTH` AS MONTH,	SUM(PAY_EXPECT.ysbj) AS ysbj, PAY_EXPECT.userId	 AS userId	
		FROM (SELECT DATE_FORMAT(T6252.F10, '%Y') AS YEAR, DATE_FORMAT(T6252.F10, '%c') AS MONTH,IFNULL(SUM(T6252.F07), 0) AS ysbj,T6252.F04 AS userId
					FROM S62.T6252 WHERE T6252.F09 = 'YH' AND T6252.F05 = 7002 GROUP BY	DATE_FORMAT(T6252.F10, '%Y-%m'),T6252.F04		
		UNION
				 SELECT DATE_FORMAT(IFNULL(T6252.F10,PAY_EXPECT_T.F07), '%Y') AS YEAR, DATE_FORMAT(IFNULL(T6252.F10,PAY_EXPECT_T.F07), '%c') AS MONTH, IFNULL(SUM(T6252.F07), 0) AS ysbj, PAY_EXPECT_T.F04 AS userId	
					 FROM	S62.T6252	
					 INNER JOIN S62.T6230 ON T6252.F02 = T6230.F01
					 INNER JOIN 
				 ( SELECT T6251.F01, T6251.F04, T6253.F07, T6253.F08
					 FROM	S62.T6251
					 INNER JOIN
					 S62.T6253 ON T6251.F03 = T6253.F02	
					 GROUP BY	T6251.F03 ) AS PAY_EXPECT_T ON T6252.F11 = PAY_EXPECT_T.F01		
		WHERE T6252.F05 = 7002
		  AND T6252.F06 >= PAY_EXPECT_T.F08
			AND T6230.F12 = 'BXQEDB'
		GROUP BY DATE_FORMAT(PAY_EXPECT_T.F07, '%Y-%m'), userId	
		UNION
		SELECT DATE_FORMAT(T6253.F07, '%Y') AS YEAR, DATE_FORMAT(T6253.F07, '%c') AS MONTH, SUM(IFNULL(T6252.F07,0)) AS ysbj, T6251.F04 AS userId
			FROM (SELECT T6251.F01 AS F01,T6251.F03 AS F02,T6262.F03 AS F03
						FROM S62.T6260
					 INNER JOIN S62.T6251 ON T6260.F02 = T6251.F01
					 INNER JOIN S62.T6262 ON T6260.F01 = T6262.F02
					 WHERE T6260.F07 = 'YJS') ZQZR
		 INNER JOIN S62.T6251 ON T6251.F04 = ZQZR.F03
		 INNER JOIN S62.T6252 ON T6252.F11 = T6251.F01
		 INNER JOIN S62.T6253 ON T6251.F03 = T6253.F02
		 INNER JOIN S62.T6230 ON T6230.F01 = T6251.F03
		 WHERE T6252.F05 = 7002
			 AND T6230.F12 = 'BXQEDB'
		 GROUP BY DATE_FORMAT(T6253.F07, '%Y-%m'),T6251.F04) AS PAY_EXPECT
	  GROUP BY PAY_EXPECT.userId,PAY_EXPECT.`YEAR`,PAY_EXPECT.`MONTH`;

	DECLARE CURSOR_YSFX CURSOR FOR
		SELECT PAY_EXPECT.`YEAR` AS YEAR, PAY_EXPECT.`MONTH` AS MONTH,	SUM(PAY_EXPECT.ysbj) AS ysbj, PAY_EXPECT.userId	 AS userId	
		FROM (SELECT DATE_FORMAT(T6252.F10, '%Y') AS YEAR, DATE_FORMAT(T6252.F10, '%c') AS MONTH,IFNULL(SUM(T6252.F07), 0) AS ysbj,T6252.F04 AS userId
					FROM S62.T6252 WHERE T6252.F09 = 'YH' AND T6252.F05 = 7004 GROUP BY	DATE_FORMAT(T6252.F10, '%Y-%m'),T6252.F04		
		UNION
				 SELECT DATE_FORMAT(IFNULL(T6252.F10,PAY_EXPECT_T.F07), '%Y') AS YEAR, DATE_FORMAT(IFNULL(T6252.F10,PAY_EXPECT_T.F07), '%c') AS MONTH, IFNULL(SUM(T6252.F07), 0) AS ysbj, PAY_EXPECT_T.F04 AS userId	
					 FROM	S62.T6252	
					 INNER JOIN S62.T6230 ON T6252.F02 = T6230.F01
					 INNER JOIN 
				 ( SELECT T6251.F01, T6251.F04, T6253.F07, T6253.F08
					 FROM	S62.T6251
					 INNER JOIN
					 S62.T6253 ON T6251.F03 = T6253.F02	
					 GROUP BY	T6251.F03 ) AS PAY_EXPECT_T ON T6252.F11 = PAY_EXPECT_T.F01		
		WHERE T6252.F05 = 7004
		  AND T6252.F06 = PAY_EXPECT_T.F08
			AND T6230.F12 = 'BXQEDB'
		GROUP BY DATE_FORMAT(PAY_EXPECT_T.F07, '%Y-%m'), userId	
		UNION
		SELECT DATE_FORMAT(T6253.F07, '%Y') AS YEAR, DATE_FORMAT(T6253.F07, '%c') AS MONTH, SUM(IFNULL(T6252.F07,0)) AS ysbj, T6251.F04 AS userId
			FROM (SELECT T6251.F01 AS F01,T6251.F03 AS F02,T6262.F03 AS F03
						FROM S62.T6260
					 INNER JOIN S62.T6251 ON T6260.F02 = T6251.F01
					 INNER JOIN S62.T6262 ON T6260.F01 = T6262.F02
					 WHERE T6260.F07 = 'YJS') ZQZR
		 INNER JOIN S62.T6251 ON T6251.F04 = ZQZR.F03
		 INNER JOIN S62.T6252 ON T6252.F11 = T6251.F01
		 INNER JOIN S62.T6253 ON T6251.F03 = T6253.F02
		 INNER JOIN S62.T6230 ON T6251.F03 = T6230.F01
		 WHERE T6252.F05 = 7004
			 AND T6230.F12 = 'BXQEDB'
		 GROUP BY DATE_FORMAT(T6253.F07, '%Y-%m'),T6251.F04) AS PAY_EXPECT
	  GROUP BY PAY_EXPECT.userId,PAY_EXPECT.`YEAR`,PAY_EXPECT.`MONTH`;

	DECLARE CURSOR_YSWYJ CURSOR FOR
	SELECT DATE_FORMAT(T6252.F10, '%Y') as year, DATE_FORMAT(T6252.F10, '%c') as month, IFNULL(SUM(T6252.F07),0) as yswyj,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='YH' AND T6252.F05=7005 GROUP BY DATE_FORMAT(T6252.F10, '%Y-%m'),T6252.F04;

	DECLARE CURSOR_DSBJ CURSOR FOR
	SELECT DATE_FORMAT(T6252.F08, '%Y') as year, DATE_FORMAT(T6252.F08, '%c') as month, IFNULL(SUM(T6252.F07),0) as dsbj,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='WH' AND T6252.F05=7001 GROUP BY DATE_FORMAT(T6252.F08, '%Y-%m'),T6252.F04;

	DECLARE CURSOR_DSLX CURSOR FOR
	SELECT DATE_FORMAT(T6252.F08, '%Y') as year, DATE_FORMAT(T6252.F08, '%c') as month,  IFNULL(SUM(T6252.F07),0) dslx,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='WH' AND T6252.F05=7002 GROUP BY DATE_FORMAT(T6252.F08, '%Y-%m'),T6252.F04;

	DECLARE CURSOR_DSFX CURSOR FOR
	SELECT DATE_FORMAT(T6252.F08, '%Y') as year, DATE_FORMAT(T6252.F08, '%c') as month,  IFNULL(SUM(T6252.F07),0) dsfx,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='WH' AND T6252.F05=7004 GROUP BY DATE_FORMAT(T6252.F08, '%Y-%m'),T6252.F04;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error = 1;
	START TRANSACTION;
	
	DELETE FROM S70.T7203;


  OPEN CURSOR_TZJE;
	read_loop:LOOP
		FETCH CURSOR_TZJE INTO _F01,_F02,_F03,_F04;
		IF done THEN
      		LEAVE read_loop;
   	 	END IF;
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F03=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F03=_F03;
	END LOOP;
  CLOSE CURSOR_TZJE;

	SET done = 0;
	

  OPEN CURSOR_ZQZRYK;

	read_loop:LOOP
		FETCH CURSOR_ZQZRYK INTO _F01,_F02,_F03,_F04;

		IF done THEN
      LEAVE read_loop;
    END IF;

		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F04=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F04=_F03;
	END LOOP;

  CLOSE CURSOR_ZQZRYK;  
	SET done = 0;


  OPEN CURSOR_YSBJ;

	read_loop:LOOP
		FETCH CURSOR_YSBJ INTO _F01,_F02,_F03,_F04;

		IF done THEN
      LEAVE read_loop;
    END IF;
		
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F05=_F03,F12= _F04 ON DUPLICATE KEY UPDATE F05=_F03;
	END LOOP;
	
  CLOSE CURSOR_YSBJ;  
	SET done = 0;

	
  OPEN  CURSOR_YSLX;
	
	read_loop:LOOP
		FETCH  CURSOR_YSLX INTO _F01,_F02,_F03,_F04;
		
		IF done THEN
      LEAVE read_loop;
    END IF;
		
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F06=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F06=_F03;
	END LOOP;
	
  CLOSE CURSOR_YSLX;
	SET done = 0;
	
	
	OPEN  CURSOR_YSFX;
	
	read_loop:LOOP
		FETCH  CURSOR_YSFX INTO _F01,_F02,_F03,_F04;
		
		IF done THEN
			LEAVE read_loop;
		END IF;
		
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F07=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F07=_F03;
	END LOOP;
	
	CLOSE CURSOR_YSFX;
		
	SET done = 0;
	
	
  OPEN  CURSOR_YSWYJ;
	read_loop:LOOP 
		FETCH CURSOR_YSWYJ INTO _F01,_F02,_F03,_F04;
		IF done THEN
      		LEAVE read_loop;
    	END IF;
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F08=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F08=_F03;
	END LOOP;
  CLOSE CURSOR_YSWYJ;  

	SET done = 0;

	
  OPEN  CURSOR_DSBJ;
	
	read_loop:LOOP
		FETCH  CURSOR_DSBJ INTO _F01,_F02,_F03,_F04;
		
		IF done THEN
      LEAVE read_loop;
    END IF;
		
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F09=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F09=_F03;
	END LOOP;

  CLOSE CURSOR_DSBJ;  
	SET done = 0;

	
  OPEN  CURSOR_DSLX;
	
	read_loop:LOOP
		FETCH  CURSOR_DSLX INTO _F01,_F02,_F03,_F04;
		
		IF done THEN
      LEAVE read_loop;
    END IF;
		
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F10=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F10=_F03;
	END LOOP;
	
  CLOSE CURSOR_DSLX;  
	SET done = 0;

	
	OPEN  CURSOR_DSFX;
	read_loop:LOOP 
		FETCH  CURSOR_DSFX INTO _F01,_F02,_F03,_F04;
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F11=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F11=_F03;
	END LOOP;
	CLOSE CURSOR_DSFX;
	
	IF t_error = 1 THEN 
		ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
END
;;
DELIMITER ;