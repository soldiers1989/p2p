/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S65

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:40:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T6501
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6501`;
CREATE TABLE `S65`.`T6501` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID,自增',
  `F02` int(10) unsigned NOT NULL COMMENT '类型编码',
  `F03` enum('DTJ','YTJ','DQR','CG','SB') NOT NULL DEFAULT 'DTJ' COMMENT '状态,DTJ:待提交;YTJ:已提交;DQR:待确认;CG:成功;SB:失败;',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` datetime DEFAULT NULL COMMENT '提交时间',
  `F06` datetime DEFAULT NULL COMMENT '完成时间',
  `F07` enum('XT','YH','HT') NOT NULL COMMENT '订单来源,XT:系统;YH:用户;HT:后台;',
  `F08` int(10) unsigned DEFAULT NULL COMMENT '用户ID,参考T6110.F01',
  `F09` int(10) unsigned DEFAULT NULL COMMENT '后台帐号ID,参考T7110.F01',
  `F10` varchar(70) DEFAULT NULL COMMENT '流水号',
  `F11` enum('F','S') NOT NULL DEFAULT 'F' COMMENT 'F:未对账;S:已对账',
  `F12` varchar(100) DEFAULT NULL COMMENT '备注',
  `F13` decimal(20,2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `F14` varchar(32) DEFAULT NULL COMMENT '托管请求第三方时订单号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for T6502
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6502`;
CREATE TABLE `S65`.`T6502` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单号,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '应收手续费',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '实收手续费',
  `F06` varchar(30) DEFAULT NULL COMMENT '银行卡号',
  `F07` int(10) unsigned NOT NULL COMMENT '支付公司代号',
  `F08` varchar(50) DEFAULT NULL COMMENT '流水单号,充值成功时记录',
  `F09` char(6) DEFAULT NULL COMMENT '业务员工号',
  `F10` enum('QY','TY') DEFAULT NULL COMMENT '业务员状态（QY:启用,TY:停用）',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值订单';

-- ----------------------------
-- Table structure for T6503
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6503`;
CREATE TABLE `S65`.`T6503` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '应收手续费',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '实收手续费',
  `F06` varchar(30) NOT NULL COMMENT '银行卡号',
  `F07` int(10) unsigned DEFAULT NULL COMMENT '支付公司代码',
  `F08` varchar(50) DEFAULT NULL COMMENT '流水单号,提现成功时填入',
  `F09` int(11) unsigned DEFAULT NULL COMMENT '提现申请记录ID,参考T6130.F01',
  `F10` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现手续费平台分润收入',
  `F11` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实际到账金额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F09` (`F09`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现订单';

-- ----------------------------
-- Table structure for T6504
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6504`;
CREATE TABLE `S65`.`T6504` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '投资记录ID,参考T6250.F01,投资成功时记录',
  `F06` enum('S','F') NULL DEFAULT 'F' COMMENT '是否自动投资订单',
  `F07` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC' COMMENT '来源，PC:PC;APP:APP;WEIXIN:微信',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资订单';

-- ----------------------------
-- Table structure for T6505
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6505`;
CREATE TABLE `S65`.`T6505` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='放款订单';

-- ----------------------------
-- Table structure for T6506
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6506`;
CREATE TABLE `S65`.`T6506` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6501.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '还款期号',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '还款金额',
  `F07` int(11) unsigned NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款订单';

-- ----------------------------
-- Table structure for T6507
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6507`;
CREATE TABLE `S65`.`T6507` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '债权申请ID,参考T6260.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '购买债权',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '受让价格',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '转让手续费',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债权转让订单';

-- ----------------------------
-- Table structure for T6508
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6508`;
CREATE TABLE `S65`.`T6508` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资记录ID,参考T6250.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资取消订单';

-- ----------------------------
-- Table structure for T6509
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6509`;
CREATE TABLE `S65`.`T6509` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单号,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `F04` int(10) DEFAULT NULL COMMENT '支付公司代号',
  `F05` varchar(50) DEFAULT NULL COMMENT '流水单号,充值成功时记录',
  `F06` int(10) unsigned NOT NULL COMMENT '线下充值申请记录id',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线下充值订单';

-- ----------------------------
-- Table structure for T6510
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6510`;
CREATE TABLE `S65`.`T6510` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '优选理财ID,参考T6410.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '投资金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '优选理财债权ID,参考T6411.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优选理财投资订单';

-- ----------------------------
-- Table structure for T6511
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6511`;
CREATE TABLE `S65`.`T6511` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '优选理财ID,参考T6410.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优选理财放款订单';

-- ----------------------------
-- Table structure for T6512
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6512`;
CREATE TABLE `S65`.`T6512` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6501.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '优选理财ID,参考T6410.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '还款期号',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '还款金额',
  `F06` int(11) NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优选理财还款订单';

-- ----------------------------
-- Table structure for T6513
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6513`;
CREATE TABLE `S65`.`T6513` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F03` decimal(20,2) unsigned NOT NULL COMMENT '金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='保证金充值订单';

-- ----------------------------
-- Table structure for T6514
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6514`;
CREATE TABLE `S65`.`T6514` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '垫付人ID,参考T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '垫付金额',
  `F06` int(11) NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否垫付,S:是;F:否;',
  `F08` int(10) unsigned COMMENT '垫付期号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='垫付订单';

-- ----------------------------
-- Table structure for T6515
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6515`;
CREATE TABLE `S65`.`T6515` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单id',
  `F02` int(10) unsigned NOT NULL COMMENT '关联订单id',
  `F03` decimal(20,2) NOT NULL COMMENT '订单金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='冻结订单';

-- ----------------------------
-- Table structure for T6516
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6516`;
CREATE TABLE `S65`.`T6516` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单id',
  `F02` int(10) unsigned NOT NULL COMMENT '关联订单id',
  `F03` decimal(20,2) NOT NULL COMMENT '订单金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='解冻订单';

-- ----------------------------
-- Table structure for T6517
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6517`;
CREATE TABLE `S65`.`T6517` (
  `F01` int(10) unsigned NOT NULL COMMENT '主键id',
  `F02` decimal(20,2) unsigned NOT NULL COMMENT '金额',
  `F03` int(10) unsigned NOT NULL COMMENT '出账账号id',
  `F04` int(10) unsigned NOT NULL COMMENT '入账账户id',
  `F05` varchar(200) DEFAULT NULL COMMENT '描述',
  `F06` int(10) COMMENT '转账业务类型',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转账订单';

-- ----------------------------
-- Table structure for T6518
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6518`;
CREATE TABLE `S65`.`T6518` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '投资记录ID,参考T6286.F01,投资成功时记录',
  `F06` int(10) unsigned DEFAULT 0 COMMENT '投资订单ID,参考T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='体验金投资订单';

-- ----------------------------
-- Table structure for T6519
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6519`;
CREATE TABLE `S65`.`T6519` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '投资记录ID,参考T6250.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '体验金投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资金放款订单';

-- ----------------------------
-- Table structure for T6520
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6520`;
CREATE TABLE `S65`.`T6520` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6501.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '返还利息期号',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '返还利息',
  `F06` int(11) unsigned NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  `F07` int(11) NOT NULL COMMENT '返还利息平台ID',
  `F08` int(11) DEFAULT 0 COMMENT '体验金返还ID,T6285.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='体验金利息返还订单';

DROP TABLE IF EXISTS `S65`.`T6521`;
CREATE TABLE `S65`.`T6521` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6501.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '还款期号',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '还款金额',
  `F07` int(11) unsigned NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  `F08` int(11) NOT NULL COMMENT '当前期数',
  `F09` int(11) NOT NULL COMMENT '付款用户ID',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提前还款订单';

-- ----------------------------
-- Table structure for T6520
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6522`;
CREATE TABLE `S65`.`T6522` (
`F01`  int(10) unsigned NOT NULL COMMENT '订单ID' ,
`F02`  int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01' ,
`F03`  int(10) unsigned NOT NULL COMMENT '投资记录ID,参考T6286.F01' ,
PRIMARY KEY (`F01`),
INDEX `F02` (`F02`),
INDEX `F03` (`F03`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='体验金投资取消订单';


DROP TABLE IF EXISTS `S65`.`T6529`;
CREATE TABLE `S65`.`T6529` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
	`F03` int(10) unsigned NOT NULL COMMENT '不良债权申请ID,参考T6264.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '购买人ID,参考T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT '债权ID,参考T6251.F01',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '债权金额',
  `F07` int(11) NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F08` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否完成,S:是;F:否;',
  `F09` int(10) unsigned COMMENT '转让期号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不良债权转让订单';

-- ----------------------------
-- Table structure for T6550
-- ----------------------------
DROP TABLE IF EXISTS `S65`.`T6550`;
CREATE TABLE `S65`.`T6550` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F03` text NOT NULL COMMENT '异常信息',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发生时间',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单异常日志';

-- ----------------------------
-- Table structure for T6554
-- ----------------------------
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


DROP TABLE IF EXISTS `S65`.`T6523`;
CREATE TABLE `S65`.`T6523` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` decimal(9,9) unsigned NOT NULL DEFAULT '0.000000000' COMMENT '加息率',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '投资记录ID,参考T6288.F01,投资成功时记录',
  `F06` int(10) DEFAULT '0' COMMENT '投资订单ID,T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券投资订单';


DROP TABLE IF EXISTS `S65`.`T6524`;
CREATE TABLE `S65`.`T6524` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '加息券投资记录ID,参考T6288.F01',
  `F05` decimal(9,9) unsigned NOT NULL COMMENT '加息券利率',
  `F06` decimal(20,2) NOT NULL COMMENT '使用加息券的投资金额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券放款订单';



DROP TABLE IF EXISTS `S65`.`T6525`;
CREATE TABLE `S65`.`T6525` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '返还利息期号',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '返还利息',
  `F06` int(11) unsigned NOT NULL COMMENT '科目类型ID,参考T5122.F01',
  `F07` int(11) NOT NULL COMMENT '返还利息平台ID',
  `F08` int(11) DEFAULT '0' COMMENT '加息券返还ID，参考T6289.F01',
  `F09` int(11) DEFAULT '0' COMMENT '标的放款订单ID，参考T6505.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券利息返还订单';


DROP TABLE IF EXISTS `S65`.`T6526`;
CREATE TABLE `S65`.`T6526` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '投资记录ID,参考T6286.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '投资取消订单，参考T6508.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加息券投资取消订单';


DROP TABLE IF EXISTS `S65`.`T6527`;
CREATE TABLE `S65`.`T6527` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单ID',
  `F02` int(10) unsigned NOT NULL COMMENT '投资用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '标ID,参考T6230.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '红包金额',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '红包投资记录ID,参考t6292.F01,投资成功时记录',
  `F06` int(10) DEFAULT '0' COMMENT '投资订单ID,T6504.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包投资订单';



DROP TABLE IF EXISTS `S65`.`T6528`;
CREATE TABLE `S65`.`T6528` (
  `F01` int(10) unsigned NOT NULL COMMENT '订单号,参考T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '收款用户ID,参考T6110.F01',
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '退款金额',
  `F04` int(10) unsigned NOT NULL COMMENT '商城订单明细ID,参考T6359.F01',
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
  `F07` decimal(20,2) DEFAULT '0.00' COMMENT '商品单价',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品购买订单参数';
