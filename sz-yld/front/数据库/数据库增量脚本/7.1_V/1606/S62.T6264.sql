DROP TABLE IF EXISTS `S62`.`T6264`;
CREATE TABLE `S62`.`T6264` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '债权编号',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` enum('DSH','ZRZ','YZR','YXJ','ZRSB') NOT NULL COMMENT '状态,DSH:待审核;ZRZ:转让中;YZR:已转让;YXJ:已下架;ZRSB:转让失败',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '逾期天数',
  `F06` int(10) unsigned NOT NULL COMMENT '标还款ID,参考T6252.F01',
  `F07` datetime NOT NULL COMMENT '申请时间',
  `F08` datetime NOT NULL COMMENT '操作时间',
  `F09` decimal(20,2) DEFAULT NULL COMMENT '债权价值',
  `F10` decimal(20,2) DEFAULT NULL COMMENT '转让价格',
  `F11` varchar(60) DEFAULT NULL COMMENT '备注（审核不通过原因，手动下架，自动下架）',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='不良债权转让申请';
