/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S62

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T6211
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6211`;
CREATE TABLE `S62`.`T6211` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '类型名称',
  `F03` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F04` text COMMENT '类型描述',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标类型';

-- ----------------------------
-- Table structure for T6212
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6212`;
CREATE TABLE `S62`.`T6212` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '类型名称',
  `F03` int(10) unsigned NOT NULL COMMENT '显示顺序',
  `F04` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标附件类型';

-- ----------------------------
-- Table structure for T6213
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6213`;
CREATE TABLE `S62`.`T6213` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(60) NOT NULL COMMENT '类型名称',
  `F03` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='抵押物类型';

-- ----------------------------
-- Table structure for T6214
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6214`;
CREATE TABLE `S62`.`T6214` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '抵押物类型ID,参考T6213.F01',
  `F03` varchar(60) NOT NULL COMMENT '属性名称',
  `F04` enum('TY','QY') NOT NULL DEFAULT 'QY' COMMENT '状态：QY启用，TY停用',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='抵押物属性';

-- ----------------------------
-- Table structure for T6215
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6215`;
CREATE TABLE `S62`.`T6215` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(45) NOT NULL COMMENT '费率项目',
  `F03` enum('S','F') DEFAULT NULL COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标费率项目';

-- ----------------------------
-- Table structure for T6216
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6216`;
CREATE TABLE `S62`.`T6216` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(100) NOT NULL COMMENT '产品名称',
  `F03` varchar(100) NOT NULL COMMENT '标类型:对应 T6211 F01',
  `F04` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '最低年化利率',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '最高年化利率',
  `F07` int(10) unsigned NOT NULL COMMENT '最低借款期限',
  `F08` int(10) unsigned NOT NULL COMMENT '最高借款期限',
  `F09` decimal(20,2) unsigned NOT NULL COMMENT '最低借款金额',
  `F10` decimal(20,2) unsigned NOT NULL COMMENT '最高借款金额',
  `F11` varchar(100) DEFAULT NULL COMMENT '还款方式',
  `F12` decimal(20,3) DEFAULT NULL COMMENT '成交服务费率',
  `F13` decimal(20,3) DEFAULT NULL COMMENT '投资管理费率',
  `F14` decimal(20,3) DEFAULT NULL COMMENT '预期罚息利率',
  `F15` decimal(20,2) DEFAULT NULL COMMENT '起投金额',
  `F16` int(10) unsigned NULL COMMENT '最低借款期限（按天）',
  `F17` int(10) unsigned NULL COMMENT '最高借款期限（按天）',
  `F18` enum('BSX','JSX','WJX','JQX','JJX') NOT NULL DEFAULT 'BSX' COMMENT '投资限制:BSX：保守型及以上；JSX：谨慎型及以上；WJX：稳健型及以上；JQX：进取型及以上；JJX:激进型',
   PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的产品';

-- ----------------------------
-- Table structure for T6230
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6230`;
CREATE TABLE `S62`.`T6230` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '借款用户ID,参考T6110.F01',
  `F03` varchar(200) NOT NULL COMMENT '借款标题',
  `F04` int(10) unsigned NOT NULL COMMENT '借款标类型ID,参考T6211.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '借款金额',
  `F06` decimal(9,9) unsigned NOT NULL COMMENT '年化利率',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '可投金额',
  `F08` int(10) unsigned NOT NULL DEFAULT '7' COMMENT '筹款期限,单位:天',
  `F09` int(10) unsigned NOT NULL COMMENT '借款周期,单位:月',
  `F10` enum('DEBX','MYFX','YCFQ','DEBJ') NOT NULL COMMENT '还款方式,DEBX:等额本息;MYFX:每月付息,到期还本;YCFQ:本息到期一次付清;DEBJ:等额本金;',
  `F11` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否有担保,S:是;F:否;',
  `F12` enum('BXQEDB','BJQEDB') NOT NULL DEFAULT 'BJQEDB' COMMENT '担保方案,BXQEDB:本息全额担保;BJQEDB:本金全额担保;',
  `F13` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否有抵押,S:是;F:否;',
  `F14` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否实地认证,S:是;F:否;',
  `F15` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否自动放款,S:是;F:否;',
  `F16` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否允许流标,S:是;F:否;',
  `F17` enum('ZRY','GDR') NOT NULL COMMENT '付息方式,ZRY:自然月;GDR:固定日;',
  `F18` int(10) unsigned NOT NULL COMMENT '付息日,自然月在满标后设置为满标日+起息日,固定日则必须小于等于28',
  `F19` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '起息天数,T+N,默认为0',
  `F20` enum('SQZ','DSH','DFB','YFB','TBZ','DFK','HKZ','YDF','YZR','YJQ','YLB','YZF') NOT NULL DEFAULT 'SQZ' COMMENT '状态,SQZ:申请中;DSH:待审核;DFB:待发布;YFB:预发布;TBZ:投资中;DFK:待放款;HKZ:还款中;YJQ:已结清;YLB:已流标;YDF:已垫付;YZF:已作废;YZR:已转让;',
  `F21` varchar(20) DEFAULT NULL COMMENT '封面图片编码',
  `F22` datetime DEFAULT NULL COMMENT '发布时间,预发布状态有效',
  `F23` int(10) unsigned DEFAULT NULL COMMENT '信用等级,参考T5124.F01',
  `F24` datetime NOT NULL COMMENT '申请时间',
  `F25` varchar(20) NOT NULL COMMENT '标编号',
  `F26` decimal(20,2) unsigned NOT NULL COMMENT '计息金额',
  `F27` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否线下债权,S:是;F:否;',
  `F28`  enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否为新手标,S:是;F:否;',
  `F29`  enum('S','F') NOT NULL DEFAULT 'S' COMMENT '新手标是否展示,S:是;F:否;',
  `F32`  int(10) NULL DEFAULT 0  COMMENT '产品ID',
  `F33` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否信用标,S:是;F:否;',
  `F34` varchar(32) DEFAULT NULL COMMENT '第三方使用标号',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F25_UNIQUE` (`F25`),
  KEY `F02` (`F02`),
  KEY `F04` (`F04`),
  KEY `F23` (`F23`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标信息';
	
-- ----------------------------
-- Table structure for T6230_EXT
-- ----------------------------
DROP TABLE IF EXISTS S62.`T6230_EXT`;
CREATE TABLE S62.`T6230_EXT` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长 流水号',
  `F02` int(11) NOT NULL COMMENT '用户ID S61.T6110 F01',
  `F03` int(11) NOT NULL COMMENT '标的ID S62.T6230',
  `F04` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否开启自动还款',
  `F05` varchar(50) NOT NULL DEFAULT '' COMMENT '易宝返回的流水号',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `Unique_F01_F03` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='自动还款授权信息';


-- ----------------------------
-- Table structure for T6231
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6231`;
CREATE TABLE `S62`.`T6231` (
  `F01` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '还款总期数',
  `F03` int(10) unsigned NOT NULL COMMENT '剩余期数',
  `F04` decimal(9,9) unsigned NOT NULL COMMENT '月化收益率',
  `F05` decimal(9,9) unsigned NOT NULL COMMENT '日化收益率',
  `F06` date DEFAULT NULL COMMENT '下次还款日期',
  `F07` mediumint(8) unsigned DEFAULT NULL COMMENT '项目区域位置ID,参考T5119.F01',
  `F08` varchar(100) DEFAULT NULL COMMENT '资金用途',
  `F09` text COMMENT '借款描述',
  `F10` datetime DEFAULT NULL COMMENT '审核时间',
  `F11` datetime DEFAULT NULL COMMENT '满标时间',
  `F12` datetime DEFAULT NULL COMMENT '放款时间',
  `F13` datetime DEFAULT NULL COMMENT '结清时间',
  `F14` datetime DEFAULT NULL COMMENT '垫付时间',
  `F15` datetime DEFAULT NULL COMMENT '流标时间',
  `F16` varchar(100) DEFAULT NULL COMMENT '还款来源',
  `F17` date DEFAULT NULL COMMENT '起息日期',
  `F18` date DEFAULT NULL COMMENT '结束日期',
  `F19` enum('YZYQ','S','F') NOT NULL DEFAULT 'F' COMMENT '是否逾期,S:是(逾期);F:否;YZYQ:严重逾期',
  `F20` int(10) unsigned NOT NULL DEFAULT '15' COMMENT '还款提前预警日',
  `F21` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否为按天借款,S:是;F:否',
  `F22` int(11) unsigned zerofill DEFAULT NULL COMMENT '借款天数',
  `F25` decimal(20,2) NOT NULL COMMENT '最小投资额',
  `F26` decimal(20,2) NOT NULL COMMENT '最大投资额',
  `F27` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否奖励标',
  `F28` decimal(9,9) unsigned DEFAULT NULL COMMENT '奖励利率',
  `F29` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否推荐标，S:是；F:否',
  `F30` datetime DEFAULT NULL COMMENT '设置推荐标的时间',
  `F31` char(6) DEFAULT NULL COMMENT '业务员工号',
  `F32` enum('QY','TY') DEFAULT 'QY' COMMENT '业务员状态（QY:启用,TY:停用）',
  `F33` enum('PC_APP','PC','APP') NOT NULL DEFAULT 'PC_APP' COMMENT '允许投资的终端',
  `F34` datetime DEFAULT NULL COMMENT '不良债权转让时间',
  `F35` enum('XYD','DBD','HTXZ') NOT NULL DEFAULT 'HTXZ' COMMENT '来源：信用贷、担保贷、后台新增',
  `F36`  enum('ALL','SINGLE','NONE') DEFAULT 'ALL' COMMENT '活动使用方式（ALL：组合使用,SINGLE：单一使用,NONE：不使用）',
  PRIMARY KEY (`F01`),
  KEY `F07` (`F07`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标扩展信息';

-- ----------------------------
-- Table structure for T6232
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6232`;
CREATE TABLE `S62`.`T6232` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '附件类型,参考T6212.F01',
  `F04` varchar(20) NOT NULL COMMENT '附件文件编码',
  `F05` varchar(60) NOT NULL COMMENT '附件名称',
  `F06` int(10) unsigned NOT NULL COMMENT '附件大小',
  `F07` varchar(20) DEFAULT NULL COMMENT '附件格式',
  `F08` datetime NOT NULL COMMENT '上传时间',
  `F09` int(10) unsigned DEFAULT NULL COMMENT '上传人ID,参考T7110.F01,后台上传时填入',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F09_idx` (`F09`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标附件列表(公开)';

-- ----------------------------
-- Table structure for T6233
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6233`;
CREATE TABLE `S62`.`T6233` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '附件类型,参考T6212.F01',
  `F04` varchar(60) NOT NULL COMMENT '附件名称',
  `F05` int(10) unsigned NOT NULL COMMENT '附件大小',
  `F06` varchar(20) NOT NULL COMMENT '附件文件编码',
  `F07` varchar(20) DEFAULT NULL COMMENT '附件格式',
  `F08` datetime NOT NULL COMMENT '上传时间',
  `F09` int(10) unsigned DEFAULT NULL COMMENT '上传人ID,参考T7110.F01,后台上传时填入',
  `F10` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否是封面图标(S:是,F:否)',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F09` (`F09`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标附件列表(非公开)';

-- ----------------------------
-- Table structure for T6234
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6234`;
CREATE TABLE `S62`.`T6234` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '抵押物类型ID,参考T6213.F01',
  `F04` varchar(60) NOT NULL COMMENT '抵押物名称',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标抵押列表';

-- ----------------------------
-- Table structure for T6235
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6235`;
CREATE TABLE `S62`.`T6235` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) DEFAULT NULL COMMENT '标抵押物ID,参考T6234.F01',
  `F03` int(10) DEFAULT NULL COMMENT '抵押物属性ID,参考T6214.F01',
  `F04` text COMMENT '属性值',
  `F05` int(10) DEFAULT NULL COMMENT '标ID,参考T6230.F01',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标抵押属性';

-- ----------------------------
-- Table structure for T6236
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6236`;
CREATE TABLE `S62`.`T6236` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '担保方ID,参考T6110.F01',
  `F04` enum('S','F') NOT NULL COMMENT '是否主担保方,S:是;F:否;',
  `F05` text COMMENT '担保情况',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标担保方列表';

-- ----------------------------
-- Table structure for T6237
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6237`;
CREATE TABLE `S62`.`T6237` (
  `F01` int(10) NOT NULL COMMENT '标ID,参考T6230.F01',
  `F02` text COMMENT '风险综合描述',
  `F03` text COMMENT '反担保情况',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标风险信息';

-- ----------------------------
-- Table structure for T6238
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6238`;
CREATE TABLE `S62`.`T6238` (
  `F01` int(10) unsigned NOT NULL COMMENT '标的ID,参考T6230.F01',
  `F02` decimal(9,9) NOT NULL COMMENT '成交服务费率',
  `F03` decimal(9,9) NOT NULL COMMENT '投资管理费率',
  `F04` decimal(9,9) NOT NULL COMMENT '逾期罚息利率',
  `F05` decimal(9,9) NOT NULL DEFAULT '0.000000000' COMMENT '标的奖励利率',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的费率';

-- ----------------------------
-- Table structure for T6239
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6239`;
CREATE TABLE `S62`.`T6239` (
  `F01` int(10) unsigned NOT NULL COMMENT '标的ID,参考T6230.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '协议类型ID,参考T5125.F01',
  `F03` int(11) NOT NULL COMMENT '协议版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的协议';

-- ----------------------------
-- Table structure for T6240
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6240`;
CREATE TABLE `S62`.`T6240` (
  `F01` int(10) unsigned NOT NULL COMMENT '标的ID',
  `F02` int(10) unsigned NOT NULL COMMENT '债权人ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '剩余期限',
  `F04` decimal(20,2) NOT NULL COMMENT '债权价值',
  `F05` varchar(200) NOT NULL COMMENT '转让标题',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '预留字段，原为预计收益',
  `F07` decimal(20,2) NOT NULL COMMENT '原始投资金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的线下债权关联';

-- ----------------------------
-- Table structure for T6241
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6241`;
CREATE TABLE `S62`.`T6241` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标的ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '审核人,参考T7110.F01',
  `F04` datetime NOT NULL COMMENT '反馈时间',
  `F05` enum('YCL','WCL','YZF') DEFAULT 'WCL' COMMENT '状态,YCL:已处理;WCL:未处理;',
  `F06` text COMMENT '审核意见',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标的审核记录';

-- ----------------------------
-- Table structure for T6242
-- ----------------------------
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
  `F25` varchar(20) DEFAULT NULL COMMENT '第三方使用标号',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6242.F21_UNIQUE` (`F21`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公益标信息';

-- ----------------------------
-- Table structure for T6243
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6243`;
CREATE TABLE `S62`.`T6243` (
  `F01` int(10) unsigned NOT NULL  COMMENT '公益标ID',
  `F02` longtext COMMENT '公益项目标倡议书内容',
  `F03` varchar(20) DEFAULT NULL COMMENT '倡议书图片编码',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='公益标扩展信息';

-- ----------------------------
-- Table structure for T6244
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6244`;
CREATE TABLE `S62`.`T6244` (
  `F01` int(10) unsigned NOT NULL COMMENT '公益标的ID,参考T6230.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '协议类型ID,参考T5125.F01',
  `F03` int(11) NOT NULL COMMENT '协议版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的协议';

-- ----------------------------
-- Table structure for T6245
-- ----------------------------
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
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公益标进展信息表';

-- ----------------------------
-- Table structure for T6246
-- ----------------------------
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

-- ----------------------------
-- Table structure for T6247
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6247`;
CREATE TABLE `S62`.`T6247` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标的ID,参考T6242.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '审核人,参考T7110.F01',
  `F04` datetime NOT NULL COMMENT '反馈时间',
  `F05` enum('YCL','WCL') NOT NULL DEFAULT 'WCL' COMMENT '状态,YCL:已处理;WCL:未处理;',
  `F06` text COMMENT '审核意见',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公益标的审核记录';

-- ----------------------------
-- Table structure for T6248
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6248`;
CREATE TABLE `S62`.`T6248` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '创建用户ID,参考T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID，参考T6230.F01',
  `F04` varchar(50) DEFAULT NULL COMMENT '主题标题',
  `F05` enum('YFB','WFB') NOT NULL DEFAULT 'YFB' COMMENT '状态,YFB:已发布；WFB:未发布;',
  `F06` varchar(600) DEFAULT NULL COMMENT '简要介绍',
  `F07` datetime DEFAULT NULL COMMENT '发布时间',
  `F08` datetime NOT NULL COMMENT '标题时间',
  `F09` varchar(100) DEFAULT NULL COMMENT '查看更多,外链地址',
  PRIMARY KEY (`F01`),
  KEY `F03_idx` (`F03`),
  KEY `F04_idx` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='标动态信息表';

-- ----------------------------
-- Table structure for T6250
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6250`;
CREATE TABLE `S62`.`T6250` (
 `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '购买价格',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '债权金额',
  `F06` datetime NOT NULL COMMENT '投资时间',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否放款,F:否;S:是;',
  `F09` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否自动投资',
  `F10` varchar(30) DEFAULT NULL COMMENT '预授权合同号',
  `F11` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC' COMMENT '来源，PC:PC;APP:APP;WEIXIN:微信',
  `F12` char(6) DEFAULT NULL COMMENT '业务员工号',
  `F13` enum('QY','TY') DEFAULT 'QY' COMMENT '业务员状态（QY:启用,TY:停用）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='投资记录';

-- ----------------------------
-- Table structure for T6251
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6251`;
CREATE TABLE `S62`.`T6251` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '债权编码',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '债权人ID,参考T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '购买价格',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '原始债权金额',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '持有债权金额',
  `F08` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否正在转让,S:是;F:否;',
  `F09` date NOT NULL COMMENT '创建日期',
  `F10` date NOT NULL COMMENT '起息日期',
  `F11` int(11) unsigned NOT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F12` int(10) unsigned DEFAULT '0' COMMENT '债权转让订单Id',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02_UNIQUE` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F04_idx` (`F04`),
  KEY `F11_idx` (`F11`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标债权记录';

-- ----------------------------
-- Table structure for T6252
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6252`;
CREATE TABLE `S62`.`T6252` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '付款用户ID,参考T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F06` int(10) unsigned NOT NULL COMMENT '期号',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '金额',
  `F08` date NOT NULL COMMENT '应还日期',
  `F09` enum('WH','HKZ','YH', 'TQH', 'DF') NOT NULL COMMENT '状态,WH:未还;YH:已还;HKZ:还款中;TQH:提前还;DF:垫付',
  `F10` datetime DEFAULT NULL COMMENT '实际还款时间',
  `F11` int(11) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F05_2` (`F05`,`F06`,`F11`),
  KEY `F02_idx` (`F02`),
  KEY `F05_idx` (`F05`),
  KEY `F04_idx` (`F04`),
  KEY `F03_idx` (`F03`) USING BTREE,
  KEY `F11_idx` (`F11`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标还款记录';

-- ----------------------------
-- Table structure for T6252_EXT
-- ----------------------------
DROP TABLE IF EXISTS S62.`T6252_EXT`;
CREATE TABLE S62.`T6252_EXT` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `F02` int(11) NOT NULL COMMENT '还款标ID（T6230.F01）',
  `F03` varchar(200) DEFAULT NULL COMMENT '已使用垫付流水号（以，隔开）',
  `F04` varchar(200) DEFAULT NULL COMMENT '已使用还款流水号（以，隔开）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标还款流水序号';

-- ----------------------------
-- Table structure for T6253
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6253`;
CREATE TABLE `S62`.`T6253` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '垫付人,参考T6110.F01',
  `F04` int(11) DEFAULT NULL COMMENT '被垫付人,参考T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '垫付金额',
  `F06` decimal(20,2) unsigned DEFAULT '0.00' COMMENT '垫付返回金额',
  `F07` datetime NOT NULL COMMENT '垫付时间',
  `F08` INT NOT NULL DEFAULT '0' COMMENT '垫付开始期数',
  `F09` VARCHAR(20) DEFAULT NULL COMMENT '操作人,参考T6110.F01',
  `F10` VARCHAR(20) DEFAULT NULL COMMENT '垫付方式,参考T5131.F02',
  `F11` int(11) DEFAULT NULL COMMENT '从哪一期开始垫付的',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F04_idx` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='垫付记录';


-- ----------------------------
-- Table structure for T6255
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6255`;
CREATE TABLE `S62`.`T6255` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) NOT NULL COMMENT '垫付id，参考T6253.F01',
  `F03` decimal(10,2) NOT NULL COMMENT '金额',
  `F04` int(10) NOT NULL COMMENT '收款人（投资人）',
  `F05` int(10) NOT NULL COMMENT '交易类型',
  `F06` int(10) NOT NULL COMMENT '债权id，T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='垫付明细表';


-- ----------------------------
-- Table structure for T6256
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6256`;
CREATE TABLE `S62`.`T6256` (
`F01`  int(11) UNSIGNED NOT NULL COMMENT '垫付标的ID,参考T6230.F01' ,
`F02`  int(11) NOT NULL COMMENT '版本号' ,
PRIMARY KEY (`F01`)
)
ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='垫付记录协议版本号' ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for T6260
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6260`;
CREATE TABLE `S62`.`T6260` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F03` decimal(20,2) unsigned NOT NULL COMMENT '转让价格',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '转让债权',
  `F05` datetime NOT NULL COMMENT '创建时间',
  `F06` date DEFAULT NULL COMMENT '结束日期',
  `F07` enum('DSH','ZRZ','YJS','YXJ','YQX') NOT NULL COMMENT '状态,DSH:待审核;ZRZ:转让中;YJS:已转让;YXJ:已下架;YQX:已取消;',
  `F08` decimal(9,9) NOT NULL DEFAULT '0.000000000' COMMENT '转让手续费率',
  `F09` INT(10) DEFAULT NULL COMMENT '购买债权、下架、取消时的剩余期数',
  `F10` DECIMAL(20,2) DEFAULT NULL COMMENT '购买债权时的本息',
  `F11` varchar(20) DEFAULT NULL COMMENT '第三方债权使用编码',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='债权转让申请';

-- ----------------------------
-- Table structure for T6261
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6261`;
CREATE TABLE `S62`.`T6261` (
  `F01` int(11) unsigned NOT NULL COMMENT '债权申请ID',
  `F02` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债权转让协议版本号';

-- ----------------------------
-- Table structure for T6262
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6262`;
CREATE TABLE `S62`.`T6262` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '转让申请ID,参考T6261.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '购买债权',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '受让价格',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '转让手续费',
  `F07` datetime NOT NULL COMMENT '购买时间',
  `F08` decimal(20,2) NOT NULL COMMENT '转入盈亏',
  `F09` decimal(20,2) NOT NULL COMMENT '转出盈亏',
  `F10` int(11) DEFAULT '0' COMMENT '债权剩余期数',
  `F11` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC' COMMENT '来源，PC:PC;APP:APP;WEIXIN:微信',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='债权转让记录';

-- ----------------------------
-- Table structure for T6263
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6263`;
CREATE TABLE `S62`.`T6263` (
  `F01` int(10) NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '债权转让盈亏',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成功转入金额',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '债权转入盈亏',
  `F05` int(10) NOT NULL DEFAULT '0' COMMENT '已转入债权笔数',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成功转出金额',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '债权转出盈亏',
  `F08` int(10) NOT NULL DEFAULT '0' COMMENT '已转出债权笔数',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债权转让统计表';


DROP TABLE IF EXISTS `S62`.`T6264`;
CREATE TABLE `S62`.`T6264` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '债权编号',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` enum('DSH','ZRZ','YZR','YXJ','ZRSB') NOT NULL COMMENT '状态,DSH:待审核;ZRZ:转让中;YZR:已转让;YXJ:已下架;ZRSB:转让失败',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '逾期天数',
  `F06` int(10) unsigned NOT NULL COMMENT '标还款ID,参考T6252.F01',
  `F07` datetime NOT NULL COMMENT '申请时间',
  `F08` datetime NOT NULL COMMENT '操作时间',
  `F09` decimal(20,2) DEFAULT NULL COMMENT '债权价值',
  `F10` decimal(20,2) DEFAULT NULL COMMENT '转让价格',
  `F11` varchar(60) DEFAULT NULL COMMENT '备注（审核不通过原因，手动下架，自动下架）',
  `F12` varchar(20) DEFAULT NULL COMMENT '第三方债权使用编码',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='不良债权转让申请';



DROP TABLE IF EXISTS `S62`.`T6265`;
CREATE TABLE `S62`.`T6265` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '转让申请ID,参考T6264.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
	`F04` int(10) unsigned NOT NULL COMMENT '借款人ID,参考T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '债权价值',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '认购价格',
  `F07` datetime NOT NULL COMMENT '购买时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='不良债权转让记录';

DROP TABLE IF EXISTS `S62`.`T6266`;
CREATE TABLE `S62`.`T6266` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) NOT NULL COMMENT '转让id，参考T6265.F01',
  `F03` decimal(10,2) NOT NULL COMMENT '金额',
  `F04` int(10) NOT NULL COMMENT '收款人（投资人）',
  `F05` int(10) NOT NULL COMMENT '交易类型',
  `F06` int(10) NOT NULL COMMENT '债权id，T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='不良债权转让还款明细表';

DROP TABLE IF EXISTS `S62`.`T6267`;
CREATE TABLE `S62`.`T6267` (
  `F01` int(11) unsigned NOT NULL COMMENT '不良债权转让ID,参考T6265.F01',
  `F02` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不良债权转让协议版本号';

-- ----------------------------
-- Table structure for T6270
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6270`;
CREATE TABLE `S62`.`T6270` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '期数',
  `F04` datetime NOT NULL COMMENT '申请时间',
  `F05` enum('DSH','YHK','BTG') NOT NULL COMMENT '状态,DSH:待审核;YHK:已还款;BTG:不通过;',
  `F06` int(10) unsigned DEFAULT NULL COMMENT '审核人ID,参考T7110.F01',
  `F07` datetime DEFAULT NULL COMMENT '审核时间',
  `F08` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `F09` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提前还款申请';

DROP TABLE IF EXISTS `S62`.`T6271`;
CREATE TABLE `S62`.`T6271` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(11) DEFAULT NULL COMMENT '用户ID，参考T6110.F01',
  `F03` int(11) DEFAULT NULL COMMENT '标的ID,参考T6230.F01',
  `F04` varchar(32) DEFAULT NULL COMMENT '合同编号',
  `F05` varchar(32) DEFAULT NULL COMMENT '易保全ID',
  `F06` datetime DEFAULT NULL COMMENT '保全时间',
  `F07` enum('WBQ','YBQ') DEFAULT 'WBQ' COMMENT '保全状态：WBQ-未保全，YBQ-已保全',
  `F08` enum('BLZQZRHT','DFHT','ZQZRHT','JKHT') DEFAULT 'JKHT' COMMENT '合同类型：JKHT-借款合同，ZQZRHT-债权转让合同，DFHT-垫付合同，BLZQZRHT-不良债权转让合同',
  `F09` varchar(128) DEFAULT NULL COMMENT '合同本地存储路径',
  `F10` enum('BDFR','DFR','SRR','ZCR','TZR','JKR') DEFAULT NULL COMMENT '用户类型: TZR-投资人，JKR-借款人，ZCR-转出人，SRR-受让人，DFR-垫付人，BDFR-被垫付人',
  `F11` int(11) DEFAULT NULL COMMENT '债权ID,参考T6251.F01',
  `F12` int(11) DEFAULT NULL COMMENT '不良债权ID,参考 T6265.F01',
  `F13` int(11) DEFAULT NULL COMMENT '债权申请ID, 参考T6260.F01',
  `F14` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='合同保全列表';

DROP TABLE IF EXISTS `S62`.`T6272`;
CREATE TABLE `S62`.`T6272` (
`F01`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '网签协议ID,自增' ,
`F02`  int(10) UNSIGNED NOT NULL COMMENT '用户ID,参考T6110.F01' ,
`F03`  int(10) UNSIGNED NOT NULL COMMENT '协议版本号,参考T5126.F02' ,
`F04`  varchar(10) DEFAULT NULL COMMENT '保全ID' ,
`F05`  datetime NULL DEFAULT NULL COMMENT '网签时间' ,
`F06`  enum('WBQ','YBQ') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保全状态，WBQ:未保全;YBQ:已保全;' ,
`F07`  varchar(128) DEFAULT NULL COMMENT '协议本地存储路径',
`F08` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '协议编号',
PRIMARY KEY (`F01`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='网签协议表'
ROW_FORMAT=COMPACT
;

-- ----------------------------
-- Table structure for T6280
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6280`;
CREATE TABLE `S62`.`T6280` (
  `F01` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',	
  `F02` decimal(20,2) unsigned DEFAULT '0.00' COMMENT '每次投资金额',
  `F03` decimal(20,4) unsigned DEFAULT '0.00' COMMENT '利息范围开始',
  `F04` decimal(20,4) unsigned DEFAULT '0.00' COMMENT '利息范围截止',
  `F05` int(11) unsigned DEFAULT '0' COMMENT '借款期限开始(月数)',
  `F06` int(11) unsigned DEFAULT '0' COMMENT '借款期限截止',
  `F07` int(11) unsigned DEFAULT NULL COMMENT '信用等级范围起',
  `F08` int(11) unsigned DEFAULT NULL COMMENT '信用等级范围止',
  `F09` decimal(20,2) unsigned DEFAULT NULL COMMENT '账户保留金额',
  `F10` enum('QY','TY') DEFAULT 'TY' COMMENT '是否启用,QY:启用;TY:停用;',
  `F11` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '设置时间',
  `F12` int(10) NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F13` int(2) NULL DEFAULT 1 COMMENT '每次投资金额（1：指定金额；0：全部）',
  `F14` varchar(20) NULL COMMENT '自动投资时间，精确到微秒',
  PRIMARY KEY (`F01`),
   KEY `F12` (`F12`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自动投资设置';

-- ----------------------------
-- Table structure for T6281
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6281`;
CREATE TABLE `S62`.`T6281` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) unsigned DEFAULT NULL COMMENT '用户ID，参考T6110.F01',
  `F03` varchar(100) NOT NULL COMMENT '企业名称',
  `F04` varchar(20) NOT NULL COMMENT '注册号',
  `F05` varchar(45) NOT NULL COMMENT '联系人',
  `F06` varchar(20) NOT NULL COMMENT '联系电话',
  `F07` char(18) DEFAULT NULL COMMENT '身份证',
  `F08` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `F09` decimal(20,2) unsigned NOT NULL COMMENT '融资金额',
  `F10` int(11) unsigned DEFAULT NULL COMMENT '借款类型ID,参考T6211.F01',
  `F11` mediumint(8) unsigned DEFAULT NULL COMMENT '所在区域ID，参考T5019.F01',
  `F12` varchar(50) DEFAULT NULL COMMENT '预计筹款期限',
  `F13` varchar(500) DEFAULT NULL COMMENT '借款描述',
  `F14` enum('WCL','YCL') DEFAULT 'WCL' COMMENT '处理状态,WCL:未处理;YCL:已处理',
  `F15` int(11) unsigned DEFAULT NULL COMMENT '处理人,参考T7110.F01',
  `F16` datetime NOT NULL COMMENT '申请时间',
  `F17` datetime DEFAULT NULL COMMENT '处理时间',
  `F18` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否有抵押,S:是;F:否;',
  `F19` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否实地认证,S:是;F:否;',
  `F20` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否有担保,S:是;F:否;',
  `F21` varchar(255) DEFAULT NULL COMMENT '处理结果',
  `F22` int(10) NOT NULL COMMENT '借款期限，单位月',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F10_idx` (`F10`),
  KEY `F11_idx` (`F11`),
  KEY `F15_idx` (`F15`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='企业融资申请';

-- ----------------------------
-- Table structure for T6282
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6282`;
CREATE TABLE `S62`.`T6282` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) unsigned DEFAULT '0' COMMENT '用户ID，参考T6110.F01',
  `F03` varchar(45) NOT NULL COMMENT '联系人',
  `F04` varchar(20) NOT NULL COMMENT '联系电话',
  `F05` char(18) DEFAULT NULL COMMENT '身份证',
  `F06` decimal(20,2) NOT NULL COMMENT '融资金额',
  `F07` int(11) unsigned DEFAULT NULL COMMENT '借款类型ID,参考T6211.F01',
  `F08` mediumint(8) unsigned DEFAULT NULL COMMENT '所在区域ID，参考T5019.F01',
  `F09` varchar(50) DEFAULT NULL COMMENT '预计筹款期限',
  `F10` varchar(500) DEFAULT NULL COMMENT '借款描述',
  `F11` enum('WCL','YCL') DEFAULT 'WCL' COMMENT '处理状态,WCL:未处理;YCL:已处理',
  `F12` int(11) unsigned DEFAULT NULL COMMENT '处理人,参考T7110.F01',
  `F13` datetime NOT NULL COMMENT '申请时间',
  `F14` datetime DEFAULT NULL COMMENT '处理时间',
  `F15` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否有抵押,S:是;F:否;',
  `F16` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否实地认证,S:是;F:否;',
  `F17` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否有担保,S:是;F:否;',
  `F18` varchar(225) DEFAULT NULL COMMENT '处理结果',
  `F19` int(10) NOT NULL COMMENT '借款期限，单位月',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F07_idx` (`F07`),
  KEY `F08_idx` (`F08`),
  KEY `F12_idx` (`F12`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='个人融资申请';

-- ----------------------------
-- Table structure for T6283
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6283`;
CREATE TABLE `S62`.`T6283` (
  `F01` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned zerofill DEFAULT NULL COMMENT '企业融资意向ID,参考T6281.F01',
  `F03` longblob NOT NULL COMMENT '附件内容 ',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='企业融资意向附件表';

-- ----------------------------
-- Table structure for T6284
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6284`;
CREATE TABLE `S62`.`T6284` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(45) NOT NULL COMMENT '联系人',
  `F03` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `F04` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `F05` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `F06` int(11) NOT NULL COMMENT '所在区域ID，参考T5019.F01',
  `F07` varchar(500) DEFAULT NULL COMMENT '借款描述',
  `F08` enum('WCL','YCL') DEFAULT 'WCL' COMMENT '处理状态,WCL:未处理;YCL:已处理',
  `F09` int(11) DEFAULT NULL COMMENT '处理人,参考T7110.F01',
  `F10` datetime NOT NULL COMMENT '申请时间',
  `F11` datetime DEFAULT NULL COMMENT '处理时间',
  `F12` varchar(255) DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='企业投资意向';

-- ----------------------------
-- Table structure for T6285
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6285`;
CREATE TABLE `S62`.`T6285` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '付款平台ID,参考T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F06` int(10) unsigned NOT NULL COMMENT '期号',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '体验金返还利息',
  `F11` decimal(20,2) unsigned NOT NULL COMMENT '剩余体验金返还利息',
  `F08` date NOT NULL COMMENT '返还日期',
  `F09` enum('WFH','YFH','FHZ') NOT NULL COMMENT '状态,WFH:未返还;YFH:已返还;,FHZ：返回中',
  `F10` datetime DEFAULT NULL COMMENT '实际返还时间',
  `F12` int(11) DEFAULT '0' COMMENT '体验金ID',
  PRIMARY KEY (`F01`),
  KEY `F02_UNIQUE` (`F02`,`F04`,`F06`),
  KEY `F05` (`F05`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='体验金利息返还记录';

-- ----------------------------
-- Table structure for T6286
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6286`;
CREATE TABLE `S62`.`T6286` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '投资金额',
  `F05` datetime NOT NULL COMMENT '投资时间',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否放款,F:否;S:是;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否自动投资',
  `F09` int(10) DEFAULT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F10` int(10) NOT NULL COMMENT '体验金ID,参考T6103.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='体验金投资记录';


-- ----------------------------
-- Table structure for T6288
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6288`;
CREATE TABLE `S62`.`T6288` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(9,9) unsigned NOT NULL COMMENT '加息率',
  `F05` datetime NOT NULL COMMENT '投资时间',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否放款,F:否;S:是;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否自动投资',
  `F09` int(10) DEFAULT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F10` int(10) DEFAULT NULL COMMENT '用户活动ID;参考T6342.F01',
  `F11` decimal(20,2) unsigned NOT NULL COMMENT '投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券投资记录';


-- ----------------------------
-- Table structure for T6289
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6289`;
CREATE TABLE `S62`.`T6289` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '付款平台ID,参考T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F06` int(10) unsigned NOT NULL COMMENT '期号',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '加息券返还利息',
  `F08` date NOT NULL COMMENT '返还日期',
  `F09` enum('WFH','YFH','YSX') NOT NULL COMMENT '状态,WFH:未返还;YFH:已返还;YSX:已失效;',
  `F10` datetime DEFAULT NULL COMMENT '实际返还时间',
  `F11` decimal(20,2) DEFAULT NULL COMMENT '剩余加息券返还利息',
  `F12` int(11) DEFAULT '0' COMMENT '用户加息券ID,参考T6342.F01',
  `F13` int(11) DEFAULT '0' COMMENT '债权ID,参考T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F05` (`F05`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE,
  KEY `F02_UNIQUE` (`F02`,`F04`,`F06`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券利息返还记录';


-- ----------------------------
-- Table structure for T6290
-- ----------------------------
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

-- ----------------------------
-- Table structure for T6291
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='捐款记录';

-- ----------------------------
-- Table structure for T6292
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6292`;
CREATE TABLE `S62`.`T6292` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '红包金额',
  `F05` datetime NOT NULL COMMENT '投资时间',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否取消,F:否;S:是;',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否放款,F:否;S:是;',
  `F08` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否自动投资',
  `F09` int(10) DEFAULT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F10` int(10) DEFAULT NULL COMMENT '红包id;参考T6342.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='红包投资记录';

DROP TABLE IF EXISTS `S62`.`T6299`;
CREATE TABLE `S62`.`T6299` (
`F01`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`F02`  int(11) NOT NULL COMMENT '搜索下线' ,
`F03`  int(11) NOT NULL COMMENT '搜索上线' ,
`F04`  enum('JKQX','RZJD','NHSY') CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '筛选类型，JKQX:借款期限;RZJD:融资进度;NHSY:年化利率' ,
`F05`  timestamp NULL DEFAULT NULL COMMENT '最后更新时间' ,
PRIMARY KEY (`F01`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
COMMENT='筛选条件设置';

-- ----------------------------
-- Procedure structure for SP_T6230_DFK
-- ----------------------------
DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_DFK`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_DFK`()
    COMMENT '修改满足条件的投资中状态为待放款,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE 		_F01     INT     DEFAULT 0; -- 标ID
		DECLARE 		_stop_credit							   	INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE 		_error 					            	INT 					 DEFAULT 0;
		DECLARE _cur_credit CURSOR FOR SELECT F01 FROM S62.T6230 WHERE F20 = 'TBZ' AND F22 IS NOT NULL AND DATE_ADD(F22,INTERVAL F08 DAY) < CURRENT_TIMESTAMP() AND F05 != F07;
		
		-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN _cur_credit;
			REPEAT
				FETCH _cur_credit INTO _F01;
				IF P_ID IS NULL THEN
					SET _stop_credit = 1;
				END IF;
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S62.T6230 SET F20 = 'DFK' WHERE F01=_F01;
						UPDATE S62.T6231 SET F11=CURRENT_TIMESTAMP(),F29 = 'F',F30=null WHERE F01=_F01;
						IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE _cur_credit;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T6230_YFB
-- ----------------------------
DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YFB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YFB`()
    COMMENT '修改满足条件的预发布状态为投资中'
BEGIN
UPDATE S62.T6230 SET F20 = 'TBZ' WHERE F20 = 'YFB' AND F22 IS NOT NULL AND F22 <= CURRENT_TIMESTAMP();
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T6230_YQ
-- ----------------------------
DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YQ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YQ`()
    COMMENT '修改逾期的标的状态'
BEGIN
	DECLARE P_ID INT;
	DECLARE _loanId INT;
	DECLARE _qh INT;
	DECLARE _yh_time DATE;
	DECLARE _yq_ts INT DEFAULT 0;
	DECLARE _stop_loan INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _loan_yq CURSOR FOR SELECT F02,F06,F08 FROM S62.T6252 WHERE T6252.F08 < CURDATE() AND T6252.F09 = 'WH' GROUP BY F02,F06;
	
	-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_loan = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
	OPEN _loan_yq;
	REPEAT
		FETCH _loan_yq INTO _loanId, _qh,_yh_time;
		IF P_ID IS NULL THEN
				SET _stop_loan = 1;
		END IF;
		IF _stop_loan = 0 THEN
				START TRANSACTION;
				SET _yq_ts = TO_DAYS(CURDATE())-TO_DAYS(T6252.F08);
				-- 更新标的状态
				IF _yq_ts > 30 THEN
					UPDATE S62.S6231 SET F19 = 'YZYQ' WHERE F01 = _loanId;
				ELSE 
					UPDATE S62.S6231 SET F19 = 'S' WHERE F01 = _loanId;
				END IF;
				
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
   UNTIL _stop_loan END REPEAT;
		CLOSE _loan_yq;
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `S62`.`SP_T6230_YLB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6230_YLB`()
    COMMENT '修改满足条件的投资中状态为已流标,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE 		_F01     INT     DEFAULT 0; -- 标ID
		DECLARE 		_F02     decimal(20,2)     DEFAULT 0.00; -- 借款金额
		DECLARE 		_F03     char(1)     DEFAULT 0; -- 是否担保标
		DECLARE 		_F04     INT     DEFAULT 0; -- 借款人ID
		DECLARE 		_F05     INT     DEFAULT 0; -- 担保方ID
		DECLARE 		_F06     decimal(20,2)     DEFAULT 0.00; -- 剩余担保额度
		DECLARE 		_F07     decimal(20,2)     DEFAULT 0.00; -- 剩余信用额度
		DECLARE 		_stop_credit							   	INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE 		_error 					            	INT 					 DEFAULT 0;
		DECLARE _cur_credit CURSOR FOR SELECT F01, F02, F05, F11 FROM S62.T6230 WHERE F20 = 'TBZ' AND F22 IS NOT NULL AND DATE_ADD(F22,INTERVAL F08 DAY) < CURRENT_TIMESTAMP() AND F05 = F07;
		
		-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN _cur_credit;
			REPEAT
				FETCH _cur_credit INTO _F01,_F04,_F02,_F03;
				IF P_ID IS NULL THEN
					SET _stop_credit = 1;
				END IF;
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S62.T6230 SET F20 = 'YLB' WHERE F01=_F01 ;
						UPDATE S62.T6231 SET F11=CURRENT_TIMESTAMP(),F29 = 'F',F30=null WHERE F01=_F01;
							SELECT F03 INTO _F07 FROM S61.T6116 WHERE F01 = _F04;
							UPDATE S61.T6116 SET F03 = _F07+_F02 WHERE F01 = _F04;
							INSERT INTO S61.T6117 SET F02 = _F04, F03 = '1103', F04 = CURRENT_TIMESTAMP(), F05 = _F02, F07 = _F07+_F02, F08 = '自动流标信用额度返还';
						
						IF _F03 = 'S' THEN
							SELECT F03 INTO _F05 FROM S62.T6236 WHERE F02 = _F01;
							SELECT F04 INTO _F06 FROM S61.T6125 WHERE F02 = _F05;
							UPDATE S61.T6125 SET F04 = _F06+_F02 WHERE F02 = _F05;
							INSERT INTO S61.T6126 SET F02 = _F05, F03 = '1302', F04 = CURRENT_TIMESTAMP(), F05 = _F02, F07 = _F06+_F02, F08 = '自动流标担保额度返还';
						ELSE
							IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE _cur_credit;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SP_T6242_YJZ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S62`.`SP_T6242_YJZ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S62`.`SP_T6242_YJZ`()

BEGIN
	DECLARE P_ID INT DEFAULT 0;
	DECLARE _actId INT DEFAULT 0;
	DECLARE _stop_act INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _t6242s CURSOR FOR SELECT F01 FROM S62.T6242 WHERE F19 IS NULL AND F13 IS NOT NULL AND DATE_ADD(F13,INTERVAL F08 - 1 DAY) < CURRENT_DATE();
	
	-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_act = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	OPEN _t6242s;
	read_loop:LOOP
		FETCH _t6242s INTO _actId;
    SELECT _actId,_stop_act,P_ID,_error;
    IF P_ID IS NULL THEN
		  SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		IF _stop_act = 0 THEN
				START TRANSACTION;
					UPDATE S62.T6242 SET F11 = 'YJZ',F19 = NOW() WHERE F01 = _actId;
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
  END LOOP;
	CLOSE _t6242s;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T6230_DFK
-- ----------------------------
DROP EVENT IF EXISTS `S62`.`EVT_T6230_DFK`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6230_DFK` ON SCHEDULE EVERY 10 SECOND STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230_DFK()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T6230_YFB
-- ----------------------------
DROP EVENT IF EXISTS `S62`.`EVT_T6230_YFB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6230_YFB` ON SCHEDULE EVERY 1 SECOND STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230_YFB()
;;
DELIMITER ;

DROP EVENT IF EXISTS `S62`.`EVT_T6230_YLB`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6230_YLB` ON SCHEDULE EVERY 5 SECOND STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230_YLB()
;;
DELIMITER ;

-- Event structure for `EVT_T6242_YJZ`
-- ----------------------------
DROP EVENT IF EXISTS `S62`.`EVT_T6242_YJZ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S62`.`EVT_T6242_YJZ` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-08 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6242_YJZ()
;;
DELIMITER ;


