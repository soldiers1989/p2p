CREATE TABLE `S50`.`T5010` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '类别编码',
  `F03` varchar(50) NOT NULL COMMENT '类别名称',
  `F04` enum('TY','QY') NOT NULL DEFAULT 'QY' COMMENT '状态：QY:启用；TY：停用',
  `F05` int(10) DEFAULT NULL COMMENT '父类id',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章类别';



