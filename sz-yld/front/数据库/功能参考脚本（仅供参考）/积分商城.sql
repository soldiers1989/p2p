DROP TABLE IF EXISTS `S63`.`T6350`;
CREATE TABLE `S63`.`T6350` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(50) noT NULL COMMENT '商品类别编码',
  `F03` varchar(100) noT NULL COMMENT '商品类别名称',
  `F04` enum('on','off') noT NULL COMMENT '状态（on:启用、off:停用）',
  `F05` int(11) noT NULL COMMENT '创建人',
  `F06` datetime noT NULL COMMENT '创建时间',
  `F07` enum('kind','fee') noT NULL DEFAULT 'kind' COMMENT 'kind:实物，fee:话费',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品类别';


DROP TABLE IF EXISTS `S63`.`T6351`;
CREATE TABLE `S63`.`T6351` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(50) noT NULL COMMENT '商品编号', 
  `F03` varchar(100) noT NULL COMMENT '商品名称',
  `F04` int(11) noT NULL COMMENT '商品类别,参考T6350.F01',
  `F05` int(11) noT NULL COMMENT '商品积分',
  `F06` int(11) noT NULL COMMENT '商品库存',
  `F07` int(11) DEFAULT '0' COMMENT '成交笔数',
  `F08` varchar(100) DEFAULT NULL COMMENT '商品图片',
  `F09` varchar(100) DEFAULT NULL COMMENT 'APP商品图片',
  `F10` text noT NULL COMMENT '商品详情',
  `F11` enum('sold','unsold','soldout') noT NULL COMMENT '状态（sold:上架、unsold:下架、 soldout：缺货）',
  `F12` int(11) noT NULL COMMENT '创建人',
  `F13` datetime noT NULL COMMENT '创建时间',
  `F14` datetime noT NULL COMMENT '最后修改时间',
  `F15` decimal(20,2) noT NULL DEFAULT '0.00' COMMENT '商品价格',
  `F16` enum('yes','no') noT NULL COMMENT '是否支持积分购买，no：不支持；yes：支持',
  `F17` enum('yes','no') noT NULL COMMENT '是否支持余额购买，no：不支持；yes：支持',
  `F18` int(11) DEFAULT '0' COMMENT '单用户限购数量',
  PRIMARY KEY (`F01`),
  key `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品';


DROP TABLE IF EXISTS `S63`.`T6352`;
CREATE TABLE `S63`.`T6352` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '订单ID',
  `F02` int(11) noT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` char(11) noT NULL COMMENT '订单编号',
  `F04` datetime DEFAULT NULL COMMENT '订单时间',
  `F05` varchar(30) DEFAULT NULL COMMENT '来源',
  `F06` enum('score','balance') noT NULL COMMENT '支付方式（score:积分支付,balance:余额支付）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  key `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台商城订单记录表';

DROP TABLE IF EXISTS `S63`.`T6353`;
CREATE TABLE `S63`.`T6353` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(11) noT NULL COMMENT '最小值',
  `F03` varchar(11) noT NULL COMMENT '最大值',
  `F04` datetime noT NULL COMMENT '创建时间',
  `F05` ENUM('score','amount') noT NULL COMMENT 'score:积分、amount:金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='筛选条件范围表 ';

DROP TABLE IF EXISTS `S63`.`T6354`;
CREATE TABLE `S63`.`T6354` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` text noT NULL COMMENT '积分说明',
  `F03` text noT NULL COMMENT '积分规则',
  `F04` datetime noT NULL COMMENT '创建时间',
  `F05` datetime noT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分规则说明表 ';

DROP TABLE IF EXISTS `S63`.`T6355`;
CREATE TABLE `S63`.`T6355` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '主键Id',
  `F02` int(11) noT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` varchar(100) noT NULL COMMENT '收货人',
  `F04` int(11) noT NULL COMMENT '区域',
  `F05` varchar(500) noT NULL COMMENT '详细地址',
  `F06` varchar(13) noT NULL COMMENT '联系电话',
  `F07` varchar(7) DEFAULT NULL COMMENT '邮编',
  `F08` enum('yes','no') DEFAULT NULL COMMENT '是否为默认地址（yes：是，no：否）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户收货信息表';

DROP TABLE IF EXISTS `S63`.`T6356`;
CREATE TABLE `S63`.`T6356` (
  `F01` INT(11) noT NULL AUTO_INCREMENT,
  `F02` VARCHAR(20) noT NULL COMMENT '积分值',
  `F03` ENUM('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods') noT NULL COMMENT 'register:注册、sign:签到、invite:邀请、invest:投资、cellphone:手机认证、mailbox:邮箱认证、realname:实名认证、trusteeship:开通第三方托管账户、charge:充值、buygoods:现金购买商品积分）',
  `F04` ENUM('on','off') noT NULL COMMENT 'on:启用、off:停用）',
  `F05` DATETIME noT NULL COMMENT '创建时间',
  `F06` DATETIME noT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=INnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分规则设置表';

DROP TABLE IF EXISTS `S63`.`T6357`;
CREATE TABLE `S63`.`T6357` (
  `F01` INT(11) noT NULL AUTO_INCREMENT COMMENT '主键Id',
  `F02` INT(11) noT NULL COMMENT '操作用户ID,参考T7110.F01',
  `F03` DATE noT NULL COMMENT '开始时间',
  `F04` DATE NULL COMMENT '结束时间',
  `F05` DATETIME noT NULL COMMENT '操作时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=INnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='积分清零设置表';

DROP TABLE IF EXISTS `S63`.`T6358`;
CREATE TABLE `S63`.`T6358` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '主键Id',
  `F02` int(11) noT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(11) noT NULL COMMENT '商品ID,参考T6351.F01',
  `F04` int(11) noT NULL COMMENT '数量',
  `F05` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台商城购物车';

DROP TABLE IF EXISTS `S63`.`T6359`;
CREATE TABLE `S63`.`T6359` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `F02` int(11) noT NULL COMMENT '订单ID,参考T6352.F01',
  `F03` int(11) noT NULL COMMENT '商品ID,参考T6351.F01',
  `F04` int(11) noT NULL COMMENT '积分',
  `F05` decimal(20,2) DEFAULT '0.00' COMMENT '购买金额',
  `F06` int(11) noT NULL COMMENT '数量',
  `F07` varchar(11) DEFAULT NULL COMMENT '充值话费手机号',
  `F08` enum('pendding','pass','nopass','sended','returned','refunding','norefund','refund') DEFAULT NULL COMMENT '状态（pendding:待审核、pass:待发货、 nopass：审核不通过、sended：已发货,returned：已退货、refunding：申请退款、norefund:拒绝退款、refund：已退款）',
  `F09` int(11) DEFAULT NULL COMMENT '发货人',
  `F10` datetime DEFAULT NULL COMMENT '发货时间',
  `F11` varchar(100) DEFAULT NULL COMMENT '物流方',
  `F12` varchar(100) DEFAULT NULL COMMENT '物流单号',
  `F13` varchar(100) noT NULL COMMENT '收货人',
  `F14` mediumint(6) noT NULL COMMENT '收货人省市区',
  `F15` varchar(100) noT NULL COMMENT '收货人街道地址',
  `F16` varchar(13) noT NULL COMMENT '收货人联系电话',
  `F17` varchar(7) DEFAULT NULL COMMENT '收货人邮编',
  `F18` decimal(20,2) DEFAULT '0.00' COMMENT '退款金额',
  `F19` datetime DEFAULT NULL COMMENT '退款时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台商城订单明细表';

DROP TABLE IF EXISTS `S63`.`T6360`;
CREATE TABLE `S63`.`T6360` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) noT NULL COMMENT '订单明细ID,参考T6359.F01',
  `F03` enum('SH','XG','FH','TH','SQTK','TK','JJTK') DEFAULT NULL COMMENT '状态（SH:审核,XG：修改,FH：发货,TH：退货,SQTK：申请退款,TK：退款,JJTK：拒绝退款）',
  `F04` varchar(100) DEFAULT NULL COMMENT '操作备注',
  `F05` int(11) noT NULL COMMENT '操作人，参考T7110.F01',
  `F06` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单操作日志';

DROP TABLE IF EXISTS `S61`.`T6105`;
CREATE TABLE `S61`.`T6105` (
  `F01` int(11) noT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) noT NULL COMMENT '用户ID，参考T6110.F01',
  `F03` int(11) noT NULL DEFAULT '0' COMMENT '总积分',
  `F04` int(11) noT NULL DEFAULT '0' COMMENT '已用积分',
  `F05` int(11) noT NULL DEFAULT '0' COMMENT '兑换次数',
  `F06` datetime noT NULL COMMENT '创建时间',
  `F07` datetime noT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户积分账户';


DROP TABLE IF EXISTS `S61`.`T6106`;
CREATE TABLE `S61`.`T6106` (
  `F01` int(11) noT NULL AUTO_INCREMENT,
  `F02` int(11) noT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(11) noT NULL COMMENT '积分',
  `F04` datetime noT NULL COMMENT '获取时间',
  `F05` enum('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods','nopassreturn') noT NULL COMMENT '积分类型:register:注册、sign:签到、invite:邀请、invest:投资、cellphone:手机认证、mailbox:邮箱认证、realname:实名认证、trusteeship:开通第三方托管账户、charge:充值、buygoods:现金购买商品积分、nopassreturn:审核不通过返还积分）',
  `F06` enum('yes','no') noT NULL DEFAULT 'no' COMMENT '是否已清零，yes：是；no：否',
  `F07` datetime noT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户积分获取记录';

DROP TABLE IF EXISTS `S65`.`T6528`;
CREATE TABLE `S65`.`T6528` (
  `F01` int(10) unsigned noT NULL COMMENT '订单号,参考T6501.F01',
  `F02` int(10) unsigned noT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F03` decimal(20,2) unsigned noT NULL DEFAULT '0.00' COMMENT '退款金额',
  `F04` int(10) unsigned noT NULL COMMENT '商城订单明细ID,参考T6359.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台商城退款订单';


DROP TABLE IF EXISTS S65.`T6555`;
CREATE TABLE S65.`T6555` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '购买用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '收货地址ID,参考T6355.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '购买金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '商品订单ID,参考T6352.F01,购买成功时记录',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品购买订单';

DROP TABLE IF EXISTS S65.`T6556`;
CREATE TABLE S65.`T6556` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `F02` int(10) unsigned NOT NULL COMMENT '商品ID,参考T6351.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6555.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '购买数量',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '商购物车ID,参考T6358.F01',
  `F06` varchar(11) DEFAULT NULL COMMENT '充值话费手机号',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品购买订单参数';

ALTER TABLE `S61`.`T6110`
ADD COLUMN `F15`  DATE NULL COMMENT '签到时间' AFTER `F14`;

ALTER TABLE `S61`.`T6110`
ADD COLUMN `F16` int(11) noT NULL DEFAULT '0' COMMENT '连续签到天数' AFTER `F15`;

INSERT INTO `S51`.`T5122` VALUES ('1702', '商品交易', 'QY');
INSERT INTO `S51`.`T5122` VALUES ('1703', '商品退款', 'QY');

insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('1','20','register','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('2','5,5,5','sign','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('3','20','invite','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('4','10000,20','invest','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('5','10','cellphone','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('6','10','mailbox','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('7','10','realname','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('8','10','trusteeship','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('9','10000,20','charge','off','2015-01-01 00:00:01','2015-12-21 14:47:37');
insert into `t6356` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`) values('10','100,1','buygoods','off','2015-01-01 00:00:01','2015-12-21 14:47:37');