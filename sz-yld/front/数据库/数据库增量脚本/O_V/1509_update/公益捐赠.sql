
-- ----------------------------
-- Table structure for `T5122`
-- ----------------------------

INSERT INTO S51.`T5122` VALUES ('1801', '公益捐款', 'QY');

INSERT INTO S51.`T5125` VALUES ('5001',0, '公益捐助协议', CURRENT_TIMESTAMP());

INSERT INTO `S51`.`T5126` VALUES (5001, 0, '', CURRENT_TIMESTAMP());

ALTER TABLE S51.T5129 CHANGE F03 F03  ENUM('BDBH','HTBH','GYBDBH') COMMENT 'BDBH:标的编号,HTBH:合同编号,GYBDBH:公益标的编号';

DROP TABLE IF EXISTS S62.`T6291`;
CREATE TABLE S62.`T6291` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '捐款人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '捐款金额',
  `F05` datetime NOT NULL COMMENT '捐款时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='捐款记录';

DROP TABLE IF EXISTS S65.`T6554`;
CREATE TABLE S65.`T6554` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '捐款用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6242.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '投资记录ID,参考T6291.F01,投资成功时记录',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='捐款订单';

DROP TABLE IF EXISTS `S62`.`T6242`;
CREATE TABLE `S62`.`T6242` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '创建用户ID,参考T7110.F01',
  `F03` varchar(200) NOT NULL COMMENT '公益项目标题',
  `F04` int(10) unsigned NOT NULL COMMENT '标类型ID,参考T6211.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '项目金额',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '最低起捐金额',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '可投金额',
  `F08` int(10) unsigned NOT NULL DEFAULT 0 DEFAULT '7' COMMENT '筹款期限,单位:天',
  `F09` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '借款周期,单位:月',
  `F10` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否允许流标,S:是;F:否;',
  `F11` enum('SQZ','DSH','DFB','JKZ','YJZ','YZF') NOT NULL DEFAULT 'SQZ' COMMENT '状态,SQZ:申请中;DSH:待审核;DFB:待发布;JKZ:捐款中;YJZ:已捐助;YZF:已作废;',
  `F12` varchar(20) DEFAULT NULL COMMENT '封面图片编码',
  `F13` datetime DEFAULT NULL COMMENT '发布时间,预发布状态有效',
  `F14` int(10) unsigned DEFAULT NULL COMMENT '信用等级,参考T5124.F01',
  `F15` datetime NOT NULL COMMENT '申请时间',
  `F16` datetime DEFAULT NULL COMMENT '审核时间',
  `F17` datetime DEFAULT NULL COMMENT '满标时间',
  `F18` datetime DEFAULT NULL COMMENT '放款时间',
  `F19` datetime DEFAULT NULL COMMENT '结束时间',
  `F20` datetime DEFAULT NULL COMMENT '流标时间',
  `F21` varchar(20) NOT NULL COMMENT '标编号',
  `F22` varchar(20) NOT NULL COMMENT '公益方',
  `F23` varchar(20) NOT NULL COMMENT '借款帐号ID,默认平台帐号ID',
  `F24` varchar(500)  NULL COMMENT '描述',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6242.F21_UNIQUE` (`F21`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公益标信息';

DROP TABLE IF EXISTS `S62`.`T6243`;
CREATE TABLE `S62`.`T6243` (
  `F01` int(10) unsigned NOT NULL  COMMENT '公益标ID',
  `F02` longtext COMMENT '公益项目标倡议书内容',
  `F03` varchar(20) DEFAULT NULL COMMENT '倡议书图片编码',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='公益标扩展信息';

DROP TABLE IF EXISTS `S62`.`T6244`;
CREATE TABLE `S62`.`T6244` (
  `F01` int(10) unsigned NOT NULL COMMENT '公益标的ID,参考T6230.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '协议类型ID,参考T5125.F01',
  `F03` int(11) NOT NULL COMMENT '协议版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的协议';

DROP TABLE IF EXISTS `S62`.`T6245`;
CREATE TABLE `S62`.`T6245` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '创建用户ID,参考T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '公益项目ID',
  `F04` varchar(50) DEFAULT NULL COMMENT '主题标题',
  `F05` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否发布,S:是;F:否;',
  `F06` varchar(600) DEFAULT NULL COMMENT '简要介绍',
  `F07` datetime DEFAULT NULL COMMENT '发布时间',
  `F08` datetime NOT NULL COMMENT '标题时间',
  `F09` varchar(200) DEFAULT NULL COMMENT '查看更多,外链地址',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公益标进展信息表';

DROP TABLE IF EXISTS `S62`.`T6246`;
CREATE TABLE `S62`.`T6246` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '公益标ID,参考T6242.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '捐助金额',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '债权金额',
  `F06` datetime NOT NULL COMMENT '捐助时间',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公益投资记录';

DROP TABLE IF EXISTS `S62`.`T6247`;
CREATE TABLE `S62`.`T6247` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标的ID,参考T6242.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '审核人,参考T7110.F01',
  `F04` datetime NOT NULL COMMENT '反馈时间',
  `F05` enum('YCL','WCL') NOT NULL DEFAULT 'WCL' COMMENT '状态,YCL:已处理;WCL:未处理;',
  `F06` text COMMENT '审核意见',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='公益标的审核记录'
