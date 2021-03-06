DROP TABLE IF EXISTS `S62`.`T6290`;
CREATE TABLE `S62`.`T6290` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` VARCHAR(30) NOT NULL COMMENT '提醒方式',
  `F03` INT(4) NOT NULL COMMENT '提醒天数',
  `F04` ENUM('HKTX','YQTX') NOT NULL COMMENT 'HKTX:还款提醒、YQTX:逾期提醒）',
  `F05` DATETIME NOT NULL COMMENT '更新时间',
  `F06` ENUM('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='账单提醒设置';
INSERT INTO `S62`.`T6290` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) VALUES (1, 'LETTER,SMS,EMAIL', '7', 'HKTX', CURRENT_TIMESTAMP(),'TY');
INSERT INTO `S62`.`T6290` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) VALUES (2, 'LETTER,SMS,EMAIL', '1', 'YQTX', CURRENT_TIMESTAMP(),'TY');