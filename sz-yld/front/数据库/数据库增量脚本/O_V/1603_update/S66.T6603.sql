DROP TABLE IF EXISTS `S66`.`T6603`;
CREATE TABLE `S66`.`T6603` (
  `F01` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(11) NOT NULL COMMENT '任务ID',
  `F03` VARCHAR(20) NOT NULL COMMENT '持续时间',
  `F04` timestamp NULL DEFAULT NULL COMMENT '任务最近运行结束时间',
  `F05` timestamp NULL DEFAULT NULL COMMENT '任务最近运行开始时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='定时任务运行时间表';