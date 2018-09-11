INSERT INTO `S50`.`T5010` VALUES (34, 'XXPL', '信息披露', 'QY', NULL, NULL, NULL);
INSERT INTO `S50`.`T5010` VALUES (35, 'BAXX', '备案信息', 'QY', 34, '/console/info/xxpl/baxx/updateBaxx.htm', 'P2P_C_INFO_XXPL_MENU');
INSERT INTO `S50`.`T5010` VALUES (36, 'ZZXX', '组织信息', 'QY', 34, '/console/info/xxpl/zzxx/updateZzxx.htm', 'P2P_C_INFO_XXPL_MENU');
INSERT INTO `S50`.`T5010` VALUES (37, 'SJBG', '审计报告', 'QY', 34, '/console/info/xxpl/sjbg/searchSjbg.htm', 'P2P_C_INFO_XXPL_MENU');
INSERT INTO `S50`.`T5010` VALUES (38, 'YYBG', '运营报告', 'QY', 34, '/console/info/xxpl/yybg/searchYybg.htm', 'P2P_C_INFO_XXPL_MENU');
INSERT INTO `S50`.`T5010` VALUES (39, 'SFBZ', '收费标准', 'QY', 34, '/console/info/xxpl/sfbz/updateSfbz.htm', 'P2P_C_INFO_XXPL_MENU');
INSERT INTO `S50`.`T5010` VALUES (40, 'ZDSX', '重大事项', 'QY', 34, '/console/info/xxpl/zdsx/updateZdsx.htm', 'P2P_C_INFO_XXPL_MENU');
INSERT INTO `S50`.`T5010` VALUES (41, 'QTXX', '其他信息', 'QY', 34, '/console/info/xxpl/qtxx/updateQtxx.htm', 'P2P_C_INFO_XXPL_MENU');
-- ----------------------------
-- Table structure for `T5011_3`
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5011_3`;
CREATE TABLE `S50`.`T5011_3` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '文章ID,参考T5011.F01',
  `F03` varchar(60) NOT NULL COMMENT '附件路径',
  `F04` varchar(50) NOT NULL COMMENT '附件大小',
  `F05` varchar(50) NOT NULL COMMENT '附件名称',
  `F06` int(11) DEFAULT NULL COMMENT '年份',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='信息披露附件';
-- ----------------------------
-- Records of T5011_3
-- ----------------------------

-- ----------------------------
-- Table structure for T7054
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7054`;
CREATE TABLE `S70`.`T7054` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累计交易金额',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '累计交易笔数',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '借贷余额',
  `F04` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '借贷余额笔数',
  `F05` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '累计出借人数量',
  `F06` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '当前出借人数量',
  `F07` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '累计借款人数量',
  `F08` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '当前借款人数量',
  `F09` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '最大单一借款人待还金额占比',
  `F10` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '前十大借款人待还金额占比',
  `F11` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '注册用户数',
  `F12` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '利息余额',
  `F13` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累计赚取收益',
  `F14` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '关联关系借款金额',
  `F15` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '关联关系借款笔数',
  `F16` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '逾期金额',
  `F17` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '金额逾期率',
  `F18` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '逾期笔数',
  `F19` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '项目逾期率',
  `F20` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累计代偿金额',
  `F21` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '累计代偿笔数',
  `F22` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '逾期90天（不含）以上金额',
  `F23` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '逾期90天（不含）以上笔数',
  `F24` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运营数据统计';

INSERT INTO `S70`.`T7054` VALUES ('0.00', '0', '0.00', '0', '0', '0', '0', '0', '0.00', '0.00','0', '0.00', '0.00', '0.00', '0', '0', '0.00', '0.00', '0.00', '0.00', '0', '0.00', '0.00', CURRENT_TIMESTAMP());

INSERT INTO `S66`.`T6601` VALUES (20, '运营数据统计', 'com.dimeng.p2p.scheduler.tasks.BusinessStaticTask', 'businessStatic', '0 0 1 * * ?', 'ENABLE', null, null, '2018-01-24 16:57:16', null, '运营数据统计，每天凌晨早上1点执行一次');


ALTER TABLE `S61`.`T6196` ADD COLUMN `F31` decimal(20,0) unsigned NOT NULL DEFAULT '0' COMMENT '借贷余额';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F32` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '借贷余额笔数';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F33` decimal(20,0) unsigned NOT NULL DEFAULT '0' COMMENT '利息余额';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F34` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '累计出借人数量';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F35` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '当前出借人数量';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F36` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '累计借款人数量';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F37` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '当前借款人数量';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F38` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '最大单一借款人待还金额占比';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F39` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '前十大借款人待还金额占比';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F40` decimal(20,0) unsigned NOT NULL DEFAULT '0' COMMENT '关联关系借款金额';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F41` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '关联关系借款笔数';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F42` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '逾期笔数';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F43` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '项目逾期率';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F44` decimal(20,0) unsigned NOT NULL DEFAULT '0' COMMENT '逾期90天（不含）以上金额';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F45` decimal(20,0) unsigned NOT NULL DEFAULT '0' COMMENT '逾期90天（不含）以上笔数';
ALTER TABLE `S61`.`T6196` ADD COLUMN `F46` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '累计代偿笔数';