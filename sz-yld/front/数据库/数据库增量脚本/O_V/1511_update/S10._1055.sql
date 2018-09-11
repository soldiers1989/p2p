DROP TABLE IF EXISTS `S10`.`_1055`;
CREATE TABLE `S10`.`_1055` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F02` varchar(50) NOT NULL COMMENT '验证码类型',
  `F03` varchar(20) NOT NULL COMMENT '显示内容',
  `F04` varchar(20) NOT NULL COMMENT '验证内容',
  `F05` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='邮箱验证码表';