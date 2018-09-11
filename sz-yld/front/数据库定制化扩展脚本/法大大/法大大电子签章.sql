ALTER TABLE S61.T6110 ADD COLUMN `F20` varchar(50) DEFAULT NULL COMMENT '客户编号';

INSERT INTO `S66`.`T6601` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`, `F08`, `F09`, `F10`, `F11`) 
VALUES ('18', '法大大电子签章定时任务', 'com.dimeng.p2p.scheduler.tasks.FDDExtSignTask', 'taskExtSign', '0 */30 * * * ?', 'ENABLE', '2016-12-21 12:34:00', '2016-12-21 12:24:00', '2016-01-01 17:00:13', NULL, '电子签章定时任务-配置执行时间默认30分钟一次，客户也可按具体情况进行配置');


-- ----------------------------
-- Table structure for T6273
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6273`;
CREATE TABLE `S62`.`T6273` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(11) DEFAULT NULL COMMENT '用户ID，参考T6110.F01',
  `F03` int(11) DEFAULT NULL COMMENT '标的ID,参考T6230.F01',
  `F04` varchar(32) DEFAULT NULL COMMENT '合同编号',
  `F05` varchar(32) DEFAULT NULL COMMENT '电子签章ID',
  `F06` datetime DEFAULT NULL COMMENT '签名时间',
  `F07` enum('DQ','YQ') DEFAULT 'DQ' COMMENT '签名状态：DQ-待签名，YQ-已签名',
  `F08` enum('ZQZRHT','JKHT','DFHT') DEFAULT 'JKHT' COMMENT '合同类型：JKHT-借款合同,ZQZRHT-债权转让合同,DFHT:垫付合同',
  `F09` varchar(128) DEFAULT NULL COMMENT '合同本地存储路径',
  `F10` enum('SRR','ZCR','TZR','JKR','DFR','BDFR') DEFAULT NULL COMMENT '用户类型: TZR-投资人,JKR-借款人,ZCR-转出人,SRR-受让人,DFR-垫付人,BDFR-被垫付人',
  `F11` int(11) DEFAULT NULL COMMENT '债权ID,参考T6251.F01',
  `F12` int(11) DEFAULT NULL COMMENT '债权申请ID, 参考T6260.F01',
  `F13` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F14` int(11) DEFAULT NULL COMMENT '投资记录ID，参考T6250.F01',
  `F15` enum('DSQ','DSC','DQM','DGD','YGD') DEFAULT NULL COMMENT '签章状态: DSQ-待申请,DSC-待上传,DQM-待签名,DGD-待归档,YGD-已归档',
  `F16` varchar(310) DEFAULT NULL COMMENT '在线查看已签文档的地址',
  `F17` varchar(310) DEFAULT NULL COMMENT '签章后文档下载地址',
  `F18` int(11) DEFAULT NULL COMMENT '债权转让id，参考T6251.F01',
  `F19` varchar(38) DEFAULT NULL COMMENT '签章请求交易号',
  `F20` varchar(300) DEFAULT NULL COMMENT '合同标题',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法大大电子签章合同列表';


INSERT INTO S10._1010 VALUES('SYSTEM.IS_SAVE_TRANSFER_CONTRACT','true','SYSTEM','债权转让合同保全开关,true(开启),false(关闭)') ON DUPLICATE KEY UPDATE F02 = VALUES(F02);
INSERT INTO S10._1010 VALUES('SYSTEM.IS_SAVE_BID_CONTRACT','true','SYSTEM','三方/四方借款电子合同保全开关,true(开启),false(关闭)') ON DUPLICATE KEY UPDATE F02 = VALUES(F02);
INSERT INTO S10._1010 VALUES('SYSTEM.IS_SAVE_ADVANCE_CONTRACT','true','SYSTEM','垫付合同保全开关,true(开启),false(关闭)') ON DUPLICATE KEY UPDATE F02 = VALUES(F02);