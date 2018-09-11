/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S63

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T6310
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6310`;
CREATE TABLE `S63`.`T6310` (
  `F01` int(10) unsigned NOT NULL COMMENT '推广用户ID,参考T6110.F01',
  `F02` int(10) unsigned DEFAULT '0' COMMENT '推广数',
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '推广投资金额',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '推广持续奖励金额',
  `F05` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '有效推广奖励金额',
  PRIMARY KEY (`F01`,`F04`,`F03`,`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推广奖励统计';

-- ----------------------------
-- Table structure for T6311
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6311`;
CREATE TABLE `S63`.`T6311` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '首次充奖励自增ID',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '推广用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被推广用户ID,参考T6110.F01',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '首次充值金额',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '奖励金额',
  `F06` datetime DEFAULT NULL COMMENT '首次充值时间',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03` (`F03`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='首次充值奖励记录';

-- ----------------------------
-- Table structure for T6312
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6312`;
CREATE TABLE `S63`.`T6312` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '持续投资奖励记录自增ID',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '推广用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被推广用户ID,参考T6110.F01',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '奖励金额',
  `F06` datetime NOT NULL COMMENT '投资时间',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='持续投资奖励记录';

-- ----------------------------
-- Table structure for T6320
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6320`;
CREATE TABLE `S63`.`T6320` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id，无符号，主键',
  `F02` varchar(120) NOT NULL COMMENT '活动名称',
  `F03` datetime NOT NULL COMMENT '活动开始时间',
  `F04` datetime NOT NULL COMMENT '活动结束时间',
  `F05` date NOT NULL COMMENT '优惠券生效日期',
  `F06` date NOT NULL COMMENT '优惠券失效日期',
  `F07` int(10) unsigned NOT NULL COMMENT '发放数量',
  `F08` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `F09` enum('ZJFF','YHLQ','TG','CZ') NOT NULL DEFAULT 'ZJFF' COMMENT '优惠券获取方式，CZ:充值;TG:推广;YHLQ:用户领取;ZJFF:直接发放;',
  `F10` enum('F','S') NOT NULL DEFAULT 'S' COMMENT '是否充值可用，S:是，F：否',
  `F11` decimal(20,0) DEFAULT NULL COMMENT '使用时最低充值金额',
  `F12` enum('F','S') NOT NULL DEFAULT 'S' COMMENT '是否投资可用,S:是,F:否',
  `F13` decimal(20,2) DEFAULT NULL COMMENT '使用时最低投资金额',
  `F14` enum('YJS','SX','XJ') NOT NULL DEFAULT 'SX' COMMENT '状态,XJ:新建,SX:生效,YJS:已结束',
  `F15` decimal(20,2) DEFAULT '0.00' COMMENT '最低充值金额',
  `F16` int(10) unsigned DEFAULT '0' COMMENT '最低推广人数',
  `F17` int(10) unsigned NOT NULL COMMENT '创建人id,参考T7110.F01',
  `F18` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '剩余可发放数量',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动信息表';

-- ----------------------------
-- Table structure for T6321
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6321`;
CREATE TABLE `S63`.`T6321` (
  `F01` int(10) NOT NULL COMMENT '活动id，参考T6320.F01',
  `F02` text NOT NULL COMMENT '优惠券使用说明',
  `F03` text NOT NULL COMMENT '活动说明',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动说明表';

-- ----------------------------
-- Table structure for T6322
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6322`;
CREATE TABLE `S63`.`T6322` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '活动id，参考T6320.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '用户id，参考T6110.F01',
  `F04` enum('YGQ','WSY','YSY') NOT NULL DEFAULT 'WSY' COMMENT 'WSY:未使用;YSY:已使用;YGQ:已过期;',
  `F05` datetime NOT NULL COMMENT '领取时间',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `F07` enum('CZ','TZ') DEFAULT NULL COMMENT 'CZ:充值;TZ:投资;',
  `F08` int(10) unsigned DEFAULT NULL COMMENT '投资记录id或充值记录id',
  `F09` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优惠券-用户关系表';

-- ----------------------------
-- Table structure for T6330
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6330`;
CREATE TABLE `S63`.`T6330` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '业务员ID',
  `F02` varchar(20) DEFAULT NULL COMMENT '姓名',
  `F03` varchar(20) NOT NULL COMMENT '手机号码',
  `F04` datetime DEFAULT NULL COMMENT '新增时间',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='业务员信息表';

-- ----------------------------
-- Table structure for T6331
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6331`;
CREATE TABLE `S63`.`T6331` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) NOT NULL COMMENT '业务员ID,参考T6330.F01',
  `F03` int(10) NOT NULL DEFAULT '0' COMMENT '推荐人ID,参考T6110.F01',
  `F04` datetime NOT NULL COMMENT '推荐时间',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `F06` enum('ZQZR','YXLC','FX','TB') NOT NULL DEFAULT 'TB' COMMENT '交易类型(''ZQZR'',''YXLC'',''FX'',''TB'',债权转让，优选理财，返现，投资)',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '当前余额',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='业务员推荐统计表';

-- ----------------------------
-- Table structure for T6332
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6332`;
CREATE TABLE `S63`.`T6332` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) NOT NULL COMMENT '购买用户ID,参考T6110.F01',
  `F03` int(10) NOT NULL DEFAULT '0' COMMENT '标的ID,参考T6230.F01',
  `F04` datetime NOT NULL COMMENT '购买时间',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '购买服务费',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='购买标的信息记录表';

-- ----------------------------
-- Table structure for T6333
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6333`;
CREATE TABLE `S63`.`T6333` (
  `F01` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `F02` datetime NOT NULL COMMENT '活动开始时间',
  `F03` datetime NOT NULL COMMENT '活动结束时间',
  `F04` date NOT NULL COMMENT '劵生效日期',
  `F05` date NOT NULL COMMENT '劵失效日期',
  `F06` int(11) NOT NULL COMMENT '劵发放上限',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '劵面值，单位：元',
  `F08` text COMMENT '活动说明',
  `F09` int(11) unsigned NOT NULL COMMENT '劵库存数',
  `F10` int(11) unsigned NOT NULL COMMENT '劵剩余库存数',
  `F11` int(11) unsigned NOT NULL COMMENT '录入人ID，参考T7110.F01',
  `F12` datetime NOT NULL COMMENT '录入时间',
  `F13` enum('QY','TY') DEFAULT 'TY' COMMENT '状态,QY:启用;TY:停用',
  `F14` varchar(50) NOT NULL COMMENT '活动名称',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动信息表';

-- ----------------------------
-- Table structure for T6334
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6334`;
CREATE TABLE `S63`.`T6334` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `F02` int(11) unsigned NOT NULL COMMENT '活动ID，参考T6333.F01',
  `F03` enum('ZC','CZ','RZ','TZ') NOT NULL DEFAULT 'CZ' COMMENT '发放场景,ZC:注册;CZ:充值;TZ:投资;RZ:认证',
  `F04` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用',
  `F05` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '每次发放数量',
  `F06` decimal(20,2) unsigned DEFAULT NULL COMMENT '基数金额，单位：元',
  `F07` int(11) unsigned DEFAULT NULL COMMENT '基数倍上限（单笔金额/基数金额）',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `idx_activity` (`F02`,`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='劵发放规则信息表';

-- ----------------------------
-- Table structure for T6335
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6335`;
CREATE TABLE `S63`.`T6335` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `F02` enum('TZ') NOT NULL DEFAULT 'TZ' COMMENT '使用场景,TZ:投资;',
  `F03` decimal(20,2) unsigned NOT NULL COMMENT '单笔投资金额下限',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '单笔投资基数金额',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '单笔投资使用劵金额',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '单笔投资使用劵金额上限',
  `F07` text NOT NULL COMMENT '规则说明',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='劵使用规则信息表';

-- ----------------------------
-- Table structure for T6336
-- ----------------------------
DROP TABLE IF EXISTS `S63`.`T6336`;
CREATE TABLE `S63`.`T6336` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `F02` int(11) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(11) unsigned NOT NULL COMMENT '活动ID,参考T6333.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '劵面值',
  `F05` enum('YSY','WSY') NOT NULL DEFAULT 'WSY' COMMENT '使用状态,YSY:已使用;WSY:未使用',
  `F06` date NOT NULL COMMENT '劵生效日期',
  `F07` date DEFAULT NULL COMMENT '劵失效日期',
  `F08` varchar(100) DEFAULT NULL COMMENT '奖励来源文字说明',
  `F09` datetime DEFAULT NULL COMMENT '使用时间',
  `F10` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '实际抵扣金额',
  PRIMARY KEY (`F01`),
  KEY `idx_F02` (`F02`) USING BTREE,
  KEY `idx_F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='劵发放记录表';


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


DROP TABLE IF EXISTS `S63`.`T6342`;
CREATE TABLE `S63`.`T6342` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `F02` int(11) NOT NULL COMMENT '用户id：参考T6110.F01',
  `F03` int(11) NOT NULL COMMENT '活动规则id：参考T6344.F01',
  `F04` enum('WSY','YSY','YGQ') NOT NULL COMMENT '使用状态：WSY：未使用；YSY：已使用；YGQ：已过期',
  `F05` datetime DEFAULT NULL COMMENT '使用时间',
  `F06` int(11) DEFAULT NULL COMMENT '标id：参考T6230.F01',
  `F07` datetime DEFAULT NULL COMMENT '赠送时间',
  `F08` datetime DEFAULT NULL COMMENT '过期时间',
  `F09` int(11) DEFAULT NULL COMMENT '推荐用户id：参考T6110.F01',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8 COMMENT='用户活动表';


DROP TABLE IF EXISTS `S63`.`T6343`;
CREATE TABLE `S63`.`T6343` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) unsigned NOT NULL COMMENT '后台帐号ID,参考T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '活动ID,参考T6340.F01',
  `F04` datetime NOT NULL COMMENT '操作时间',
  `F05` varchar(200) DEFAULT NULL COMMENT '操作描述',
  `F06` varchar(20) DEFAULT NULL COMMENT '下架、作废原因',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='活动操作日志';

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
  `F10` int(4) DEFAULT NULL COMMENT '体验金投资有效收益期',
  `F11` varchar(5) DEFAULT NULL COMMENT '体验金投资有效收益期计算方式(true:按月;false:按天)' ,
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6344_F05_UNIQUE` (`F06`,`F02`) USING BTREE,
  KEY `T6344_F02_inx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='活动规则信息表';


DROP TABLE IF EXISTS `S63`.`T6350`;
CREATE TABLE `S63`.`T6350` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(50) NOT NULL COMMENT '商品类别编码',
  `F03` varchar(100) NOT NULL COMMENT '商品类别名称',
  `F04` enum('on','off') NOT NULL COMMENT '状态（on:启用、off:停用）',
  `F05` int(11) NOT NULL COMMENT '创建人',
  `F06` datetime NOT NULL COMMENT '创建时间',
  `F07` enum('kind','virtual') DEFAULT 'kind' COMMENT 'kind:实物，virtual:虚拟',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分商品类别';


DROP TABLE IF EXISTS `S63`.`T6351`;
CREATE TABLE `S63`.`T6351` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(50) NOT NULL COMMENT '商品编号', 
  `F03` varchar(100) NOT NULL COMMENT '商品名称',
  `F04` int(11) NOT NULL COMMENT '商品类别,参考T6350.F01',
  `F05` int(11) NOT NULL COMMENT '商品积分',
  `F06` int(11) NOT NULL COMMENT '商品库存',
  `F07` int(11) DEFAULT '0' COMMENT '成交笔数',
  `F08` varchar(100) DEFAULT NULL COMMENT '商品图片',
  `F09` varchar(100) DEFAULT NULL COMMENT 'APP商品图片',
  `F10` text NOT NULL COMMENT '商品详情',
  `F11` enum('sold','unsold','forsold') NOT NULL COMMENT '状态（sold:已上架、unsold:已下架、 forsold：待上架）',
  `F12` int(11) NOT NULL COMMENT '创建人',
  `F13` datetime NOT NULL COMMENT '创建时间',
  `F14` datetime NOT NULL COMMENT '最后修改时间',
  `F15` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `F16` enum('yes','no') NOT NULL COMMENT '是否支持积分购买，no：不支持；yes：支持',
  `F17` enum('yes','no') NOT NULL COMMENT '是否支持余额购买，no：不支持；yes：支持',
  `F18` int(11) DEFAULT '0' COMMENT '单用户限购数量',
  `F19` decimal(20,2) NOT NULL DEFAULT 0.00 COMMENT '市场参考值',
  `F20`  int(11) NULL COMMENT '活动规则ID（参考S63.T6344.F01）',
  PRIMARY KEY (`F01`),
  key `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分商品';


DROP TABLE IF EXISTS `S63`.`T6352`;
CREATE TABLE `S63`.`T6352` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `F02` int(11) NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` char(11) NOT NULL COMMENT '订单编号',
  `F04` datetime DEFAULT NULL COMMENT '订单时间',
  `F05` varchar(30) DEFAULT NULL COMMENT '来源',
  `F06` enum('score','balance') NOT NULL COMMENT '支付方式（score:积分支付,balance:余额支付）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  key `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台商城订单记录表';

DROP TABLE IF EXISTS `S63`.`T6353`;
CREATE TABLE `S63`.`T6353` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(11) NOT NULL COMMENT '最小值',
  `F03` varchar(11) NOT NULL COMMENT '最大值',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` ENUM('score','amount') NOT NULL COMMENT 'score:积分、amount:金额',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='筛选条件范围表 ';

DROP TABLE IF EXISTS `S63`.`T6354`;
CREATE TABLE `S63`.`T6354` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` text NOT NULL COMMENT '积分说明',
  `F03` text NOT NULL COMMENT '积分规则',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分规则说明表 ';

DROP TABLE IF EXISTS `S63`.`T6355`;
CREATE TABLE `S63`.`T6355` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `F02` int(11) NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` varchar(100) NOT NULL COMMENT '收货人',
  `F04` int(11) NOT NULL COMMENT '区域',
  `F05` varchar(500) NOT NULL COMMENT '详细地址',
  `F06` varchar(13) NOT NULL COMMENT '联系电话',
  `F07` varchar(7) DEFAULT NULL COMMENT '邮编',
  `F08` enum('yes','no') DEFAULT NULL COMMENT '是否为默认地址（yes：是，no：否）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户收货信息表';

DROP TABLE IF EXISTS `S63`.`T6356`;
CREATE TABLE `S63`.`T6356` (
  `F01` INT(11) NOT NULL AUTO_INCREMENT,
  `F02` VARCHAR(20) NOT NULL COMMENT '积分值',
  `F03` ENUM('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods') NOT NULL COMMENT 'register:注册、sign:签到、invite:邀请、invest:投资、cellphone:手机认证、mailbox:邮箱认证、realname:实名认证、trusteeship:开通第三方托管账户、charge:充值、buygoods:现金购买商品积分）',
  `F04` ENUM('on','off') NOT NULL COMMENT 'on:启用、off:停用）',
  `F05` DATETIME NOT NULL COMMENT '创建时间',
  `F06` DATETIME NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=INnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='积分规则设置表';

DROP TABLE IF EXISTS `S63`.`T6357`;
CREATE TABLE `S63`.`T6357` (
  `F01` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `F02` INT(11) NOT NULL COMMENT '操作用户ID,参考T7110.F01',
  `F03` DATE NOT NULL COMMENT '开始时间',
  `F04` DATE NULL COMMENT '结束时间',
  `F05` DATETIME NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=INnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='积分清零设置表';

DROP TABLE IF EXISTS `S63`.`T6358`;
CREATE TABLE `S63`.`T6358` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `F02` int(11) NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(11) NOT NULL COMMENT '商品ID,参考T6351.F01',
  `F04` int(11) NOT NULL COMMENT '数量',
  `F05` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台商城购物车';

DROP TABLE IF EXISTS `S63`.`T6359`;
CREATE TABLE `S63`.`T6359` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `F02` int(11) NOT NULL COMMENT '订单ID,参考T6352.F01',
  `F03` int(11) NOT NULL COMMENT '商品ID,参考T6351.F01',
  `F04` int(11) NOT NULL COMMENT '积分',
  `F05` decimal(20,2) DEFAULT '0.00' COMMENT '购买金额',
  `F06` int(11) NOT NULL COMMENT '数量',
  `F07` varchar(11) DEFAULT NULL COMMENT '充值话费手机号',
  `F08` enum('pendding','pass','nopass','sended','returned','refunding','norefund','refund') DEFAULT NULL COMMENT '状态（pendding:待审核、pass:待发货、 nopass：审核不通过、sended：已发货,returned：已退货、refunding：申请退款中、norefund:拒绝退款、refund：已退款）',
  `F09` int(11) DEFAULT NULL COMMENT '发货人',
  `F10` datetime DEFAULT NULL COMMENT '发货时间',
  `F11` varchar(100) DEFAULT NULL COMMENT '物流方',
  `F12` varchar(100) DEFAULT NULL COMMENT '物流单号',
  `F13` varchar(100) DEFAULT NULL COMMENT '收货人',
  `F14` mediumint(6) DEFAULT NULL COMMENT '收货人省市区',
  `F15` varchar(100) DEFAULT NULL COMMENT '收货人街道地址',
  `F16` varchar(13) DEFAULT NULL COMMENT '收货人联系电话',
  `F17` varchar(7) DEFAULT NULL COMMENT '收货人邮编',
  `F18` decimal(20,2) DEFAULT '0.00' COMMENT '退款金额',
  `F19` datetime DEFAULT NULL COMMENT '退款时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台商城订单明细表';

DROP TABLE IF EXISTS `S63`.`T6360`;
CREATE TABLE `S63`.`T6360` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) NOT NULL COMMENT '订单明细ID,参考T6359.F01',
  `F03` enum('SH','XG','FH','TH','SQTK','TK','JJTK') DEFAULT NULL COMMENT '状态（SH:审核,XG：修改,FH：发货,TH：退货,SQTK：申请退款,TK：退款,JJTK：拒绝退款）',
  `F04` varchar(100) DEFAULT NULL COMMENT '操作备注',
  `F05` int(11) NOT NULL COMMENT '操作人，参考T7110.F01',
  `F06` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单操作日志';
-- ----------------------------
-- Procedure structure for SP_T6331
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6331`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6331`()
    COMMENT '统计业务员推荐记录'
BEGIN
	-- 业务员手机号码
	DECLARE _TEL VARCHAR(20);
	-- 借款总额,本金
	DECLARE _YWYID INT UNSIGNED DEFAULT 0;


	DECLARE 	_error INT DEFAULT 0;
	-- 业务员列表游标定义 
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT F01,F03 FROM S63.T6330;
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION 
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _YWYID,_TEL;
		IF NOT _done THEN
				START TRANSACTION;
				CALL SP_T6331_YH(_YWYID,_TEL);
		END IF;
	UNTIL _done END REPEAT;
	CLOSE _list;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T6331_JY
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6331_JY`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6331_JY`(IN _userId INT, IN _ywyId INT)
BEGIN
	-- 用户投资金额
	DECLARE _userTZ DECIMAL(20,2)  DEFAULT 0.00;
	DECLARE _time DATETIME;
	DECLARE 	_type INT UNSIGNED DEFAULT 0;
	DECLARE 	_typeName VARCHAR(10);
	DECLARE 	_error INT UNSIGNED DEFAULT 0;
	-- 业务员列表游标定义 
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT T6102.F07,T6102.F05,T6102.F03 FROM S61.T6102 INNER JOIN S61.T6101 ON T6102.F02 = T6101.F01 WHERE DATE_FORMAT(T6102.F05,'%Y-%m-%d')= ADDDATE(CURRENT_DATE(),INTERVAL 1 DAY)  AND T6101.F02 = _userId  AND T6101.F03 = 'WLZH' AND T6102.F03 IN (3001,4003,1301);
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _userTZ,_time,_type;
		IF NOT _done THEN
				START TRANSACTION;

				IF _type = 1301 THEN
				  SET	_typeName = 'YXLC';
				ELSEIF _type = 4003 THEN
					SET _typeName = 'ZQZR';
				ELSEIF _type = 3001 THEN
				  SET	_typeName = 'TB';
				END IF;
				
				
				INSERT INTO S63.T6331 SET F02 = _ywyId, F03 = _userId , F04 = _time, F05 = _userTZ, F06 = _typeName, F07 = F07 + _userTZ;
				
				IF _error = 1 THEN
						ROLLBACK;
						SET _error = 0;
				ELSE
						COMMIT;
				END IF;
		END IF;
	UNTIL _done END REPEAT;
	CLOSE _list;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T6331_YH
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6331_YH`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6331_YH`(IN _ywyId INT ,IN _tel VARCHAR(20))
BEGIN
	-- 用户ID
	DECLARE _userId INT UNSIGNED DEFAULT 0;
	DECLARE 	_error INT UNSIGNED DEFAULT 0;
	-- 业务员列表游标定义 
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT T6110.F01 FROM S61.T6111 INNER JOIN S61.T6110 ON T6111.F01 = T6110.F01 WHERE T6111.F04 = _tel;
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _userId;
		IF NOT _done THEN
				START TRANSACTION;
				CALL SP_T6331_JY(_userId,_ywyId);
				
		END IF;
	UNTIL _done END REPEAT;
	CLOSE _list;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T6331
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6331`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6331` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6331()
;;
DELIMITER ;



-- ----------------------------
-- Procedure structure for `SP_T6340_SJ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_SJ`()
    COMMENT '修改活动为上架状态,每10秒执行一次'
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _F02     VARCHAR(20)     DEFAULT ''; -- 奖励类型
        DECLARE _F03     VARCHAR(20)     DEFAULT ''; -- 活动类型
        DECLARE _F04     INT     DEFAULT 0; -- 存在的进行中的活动ID
        DECLARE _stop_credit  INT   DEFAULT 0;    -- 是否达到记录的末尾控制变量
        DECLARE _error        INT   DEFAULT 0;
        DECLARE t6340s_SJ CURSOR FOR SELECT T6340.F01, T6340.F03, T6340.F04, ( SELECT jxz.F01 FROM S63.T6340 jxz WHERE jxz.F03 = T6340.F03 AND jxz.F04 = T6340.F04 AND jxz.F08 = 'JXZ' LIMIT 1 ) FROM S63.T6340 WHERE T6340.F08 = 'YSJ' AND DATE(T6340.F06) <= DATE(CURRENT_TIMESTAMP());
        
        -- 解决mysql Bug:no data - zero rows fetched,selected,or processed
        DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
        
        IF _error = 0 THEN
            SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
            OPEN t6340s_SJ;
            REPEAT
                FETCH t6340s_SJ INTO _F01,_F02,_F03,_F04;
                
                IF _stop_credit = 0 THEN
                        START TRANSACTION;
                        SELECT _F04;
                        IF _F04 > 0 THEN
                            UPDATE S63.T6340 SET F08 = 'YXJ',F13 = '同类型活动复盖' WHERE F03 = _F02 AND F04 = _F03 AND F08 = 'JXZ';
                            INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F04,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06='同类型活动复盖';
                        END IF;
                        UPDATE S63.T6340 SET F08 = 'JXZ' WHERE F01=_F01;
                        INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='上架';
                        IF _error = 1 THEN
                            ROLLBACK;                            
                        ELSE
                            COMMIT;
                        END IF;
                END IF;
                UNTIL _stop_credit END REPEAT;
            CLOSE t6340s_SJ;
        END IF;
        SET _error = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SP_T6340_XJ`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_XJ`()
    COMMENT '修改活动为下架状态,每10秒执行一次'
BEGIN
        DECLARE P_ID INT DEFAULT 0;
        DECLARE _F01     INT     DEFAULT 0; -- 活动ID
        DECLARE _stop_credit        INT                    DEFAULT 0;                -- 是否达到记录的末尾控制变量
        DECLARE _error                     INT                      DEFAULT 0;
        DECLARE t6340s_XJ CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE (T6340.F08 = 'JXZ' AND T6340.F06 IS NOT NULL  AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP())) OR  
		(T6340.F01 IN (SELECT T6344.F02 FROM S63.T6344 GROUP BY T6344.F02 HAVING  SUM(T6344.F03) = SUM(T6344.F08)) AND T6340.F08 != 'YXJ');
        
        -- 解决mysql Bug:no data - zero rows fetched,selected,or processed
        DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
        
        IF _error = 0 THEN
            SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
            OPEN t6340s_XJ;
            REPEAT
                FETCH t6340s_XJ INTO _F01;
                
                IF _stop_credit = 0 THEN
                        START TRANSACTION;
                        UPDATE S63.T6340 SET F08 = 'YXJ',F13 = '活动结束时间或达到活动限制数量' WHERE F01=_F01;
                        INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='下架',F06 ='活动结束时间或达到活动限制数量';
                        IF _error = 1 THEN
                            ROLLBACK;                            
                        ELSE
                            COMMIT;
                        END IF;
                END IF;
                UNTIL _stop_credit END REPEAT;
            CLOSE t6340s_XJ;
        END IF;
        SET _error = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SP_T6340_ZF`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6340_ZF`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6340_ZF`()
    COMMENT '修改活动为作废状态,每10秒执行一次'
BEGIN
		DECLARE P_ID INT DEFAULT 0;
		DECLARE _F01     INT     DEFAULT 0; -- 活动ID
		DECLARE _stop_credit		INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE _error 					INT 					 DEFAULT 0;
		DECLARE t6340s_ZF CURSOR FOR SELECT T6340.F01 FROM S63.T6340 WHERE T6340.F08 = 'DSJ' AND T6340.F06 IS NOT NULL AND DATE(T6340.F07) < DATE(CURRENT_TIMESTAMP());
		
		-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
		DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _error = 1;
		
		IF _error = 0 THEN
			SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
			OPEN t6340s_ZF;
			REPEAT
				FETCH t6340s_ZF INTO _F01;
				
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S63.T6340 SET F08 = 'YZF' WHERE F01=_F01;
						INSERT INTO S63.T6343 SET F02 = 1000,F03 = _F01,F04 = CURRENT_TIMESTAMP(),F05 ='作废';
						IF _error = 1 THEN
							ROLLBACK;							
						ELSE
							COMMIT;
						END IF;
				END IF;
				UNTIL _stop_credit END REPEAT;
			CLOSE t6340s_ZF;
		END IF;
		SET _error = 0;
END
;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for `SP_T6342`
-- ----------------------------
DROP PROCEDURE IF EXISTS `S63`.`SP_T6342`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S63`.`SP_T6342`()

BEGIN
	DECLARE P_ID INT DEFAULT 0;
	DECLARE _actId INT DEFAULT 0;
	DECLARE _stop_act INT DEFAULT 0;
	DECLARE _error INT DEFAULT 0;
	DECLARE _t6342s CURSOR FOR SELECT F01 FROM S63.T6342 WHERE F08 < CURRENT_DATE() AND T6342.F04 = 'WSY';
	
	-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_act = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
	BEGIN 
		SHOW ERRORS;
		SET _error = 1;
	END;
	OPEN _t6342s;
	read_loop:LOOP
		FETCH _t6342s INTO _actId;
    SELECT _actId,_stop_act,P_ID,_error;
    IF P_ID IS NULL THEN
		  SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		IF _stop_act = 0 THEN
				START TRANSACTION;
					UPDATE S63.T6342 SET F04 = 'YGQ' WHERE F01 = _actId;
				IF _error = 1 THEN 
						ROLLBACK;
						SET _error = 0;
				ELSE 
					COMMIT;
				END IF;
		END IF;
  END LOOP;
	CLOSE _t6342s;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_SJ`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_SJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6340_SJ` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6340_SJ()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_XJ`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_XJ`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6340_XJ` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6340_XJ()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6340_ZF`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6340_ZF`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6340_ZF` ON SCHEDULE EVERY 10 SECOND STARTS '2015-10-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL SP_T6340_ZF()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVT_T6342`
-- ----------------------------
DROP EVENT IF EXISTS `S63`.`EVT_T6342`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S63`.`EVT_T6342` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-08 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6342()
;;
DELIMITER ;

