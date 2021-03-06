DROP TABLE IF EXISTS `S70`.`T7052`;
CREATE TABLE `S70`.`T7052` (
  `F01` varchar(50) NOT NULL DEFAULT '' COMMENT '日期',
  `F02` int(11) NOT NULL DEFAULT '0' COMMENT '注册用户数',
  `F03` int(11) NOT NULL DEFAULT '0' COMMENT 'PC端注册用户数',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT 'APP端注册用户数',
  `F05` int(11) NOT NULL DEFAULT '0' COMMENT '微信端注册用户数',
  `F06` int(11) NOT NULL DEFAULT '0' COMMENT '后台注册用户数',
  `F07` int(11) NOT NULL DEFAULT '0' COMMENT '登录用户数',
  `F08` int(11) NOT NULL DEFAULT '0' COMMENT '充值用户数',
  `F09` int(11) NOT NULL DEFAULT '0' COMMENT '提现用户数',
  `F10` int(11) NOT NULL DEFAULT '0' COMMENT '投资用户数',
  `F11` int(11) NOT NULL DEFAULT '0' COMMENT '借款用户数',
  `F12` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F13` char(4) NOT NULL COMMENT '年份',
  `F14` char(5) NOT NULL COMMENT '季度（年份+季度）',
  `F15` char(6) NOT NULL COMMENT '月份（年份+月份）',
  `F16` char(6) NOT NULL COMMENT '周（年份+周）',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台用户数统计';