DROP TABLE IF EXISTS `S65`.`T6529`;
CREATE TABLE `S65`.`T6529` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '不良债权申请ID,参考T6264.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '债权金额',
  `F07` int(11) NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F08` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否完成,S:是;F:否;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不良债权转让订单';