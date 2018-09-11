DROP TABLE IF EXISTS `S62`.`T6267`;
CREATE TABLE `S62`.`T6267` (
  `F01` int(11) unsigned NOT NULL COMMENT '不良债权转让ID,参考T6265.F01',
  `F02` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不良债权转让协议版本号';