DROP TABLE IF EXISTS `S65`.`T6521`;
CREATE TABLE `S65`.`T6521` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6501.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '还款期号',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '还款金额',
  `F07` int(11) unsigned NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  `F08` int(11) NOT NULL COMMENT '当前期数',
  `F09` int(11) NOT NULL COMMENT '付款用户ID',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提前还款订单';