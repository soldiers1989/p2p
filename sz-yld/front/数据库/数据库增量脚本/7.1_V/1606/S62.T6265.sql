DROP TABLE IF EXISTS `S62`.`T6265`;
CREATE TABLE `S62`.`T6265` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '转让申请ID,参考T6264.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '借款人ID,参考T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '债权价值',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '认购价格',
  `F07` datetime NOT NULL COMMENT '购买时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='不良债权转让记录';