DROP TABLE IF EXISTS `S63`.`T6340`;
CREATE TABLE `S63`.`T6340` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `F02` varchar(20) NOT NULL COMMENT '活动编码',
  `F03` enum('redpacket','interest','experience') NOT NULL COMMENT '奖励类型：（redpacket：红包；interest：加息卷；experience：体验金）',
  `F04` enum('register','recharge','firstrecharge','birthday','investlimit','foruser','tjsccz','tjsctz','tjtzze') NOT NULL COMMENT '活动类型：（register：注册赠送；recharge：单笔充值赠送；firstrecharge：首次充值赠送；birthday：生日赠送；investlimit：投资额度赠送；foruser：指定用户赠送；tjsccz：推荐首次充值奖励；tjsctz：推荐首次投资奖励；tjtzze：推荐投资总额奖励）',
  `F05` varchar(20) NOT NULL COMMENT '活动名称',
  `F06` datetime DEFAULT NULL COMMENT '活动开始日期',
  `F07` datetime DEFAULT NULL COMMENT '活动结束日期',
  `F08` enum('DSJ','YSJ','JXZ','YXJ','YZF') NOT NULL DEFAULT 'DSJ' COMMENT '活动状态：DSJ：待上架；YSJ：预上架；JXZ：进行中；YXJ：已下架；YZF：已作废',
  `F09` enum('login','invest','all') DEFAULT 'login' COMMENT '生日赠送领取条件：login：生日当天登录；invest：生日当天投资；all：不限',
  `F10` varchar(200) DEFAULT NULL COMMENT '备注',
  `F11` datetime DEFAULT NULL COMMENT '创建时间',
  `F12` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `F13` varchar(20) DEFAULT NULL COMMENT '下架、作废原因',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6340_F02_UNIQUE` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='活动管理信息表';

DROP TABLE IF EXISTS `S63`.`T6344`;
CREATE TABLE `S63`.`T6344` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `F02` int(11) NOT NULL COMMENT '活动id：参考T6340.F01',
  `F03` int(20) NOT NULL DEFAULT '0' COMMENT '发放数量',
  `F04` int(2) NOT NULL COMMENT '使用有效期',
  `F05` decimal(10,2) NOT NULL COMMENT '价值：红包单位为元，加息卷单位是%',
  `F06` decimal(10,0) NOT NULL COMMENT '投资、充值金额',
  `F07` decimal(10,0) DEFAULT NULL COMMENT '投资使用规则（满多少就能使用）',
  `F08` int(20) NOT NULL DEFAULT '0' COMMENT '已送数量',
  `F09` enum('S','F') DEFAULT 'S' COMMENT '使用有效期是否为按月计算,S:是;F:否',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6344_F05_UNIQUE` (`F06`,`F02`) USING BTREE,
  KEY `T6344_F02_inx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='活动规则信息表';

