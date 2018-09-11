DELETE FROM S10._1010 WHERE F01='URL.XY_PTDZXY';
DELETE FROM S10._1010 WHERE F01='URL.XY_PTHTDZXY';
DELETE FROM S10._1010 WHERE F01='URL.USER_ZQZR';
DELETE FROM S10._1010 WHERE F01='URL.USER_BLZQZR_URL';
DELETE FROM S10._1010 WHERE F01='URL.CONSOLE_ZQZR';
DELETE FROM S10._1010 WHERE F01='URL.CONSOLE_BLZQZR_URL';
INSERT INTO S10._1010 VALUES('URL.XY_PTDZXY','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/ebq/index.htm','URL','保全合同查看页面地址');
INSERT INTO S10._1010 VALUES('URL.USER_ZQZR','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/ebq/zqzrPreservationView.htm','URL','用户中心债权转让合同页面地址');
INSERT INTO S10._1010 VALUES('URL.USER_BLZQZR_URL','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/ebq/blzqzrPreservationView.htm','URL','用户中心不良债权转让合同页面地址');

INSERT INTO `S66`.`T6601` VALUES (17, '合同保全', 'com.dimeng.p2p.preservations.ebao.task.PreservationTesk', 'contractPreservation', '0 */30 * * * ?', 'ENABLE', '2016-01-17 15:29:55', '2016-01-17 15:29:55', '2016-01-15 15:03:31', null, '定时自动合同保全，每隔30分钟执行一次');
INSERT INTO `S66`.`T6601` VALUES (18, '协议保全', 'com.dimeng.p2p.preservations.ebao.task.PreservationTesk', 'agreementPreservation', '0 */30 * * * ?', 'ENABLE', '2016-01-17 15:29:55', '2016-01-17 15:29:55', '2016-01-15 15:03:31', null, '定时自动协议保全，每隔30分钟执行一次');