update `S10`.`_1010` set F02='${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/gywm/gsjj.html' WHERE F01='URL.GYWM_GSJJ';
update `S10`.`_1010` set F02='${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/gywm/fklc.html' WHERE F01='URL.AQBZ_BXDB';

INSERT INTO `S66`.`T6601` VALUES (20, '运营数据统计', 'com.dimeng.p2p.scheduler.tasks.BusinessStaticTask', 'businessStatic', '0 0 1 * * ?', 'ENABLE', null, null, '2018-03-19 16:57:16', null, '运营数据统计，每天凌晨早上1点执行一次');