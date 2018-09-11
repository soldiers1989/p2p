/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S61

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T6101
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6101`;
CREATE TABLE `S61`.`T6101` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '账户ID,自增',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` enum('WLZH','FXBZJZH','SDZH') NOT NULL COMMENT '账户类型,WLZH:往来账户;FXBZJZH:风险保证金账户;SDZH:锁定账户;',
  `F04` varchar(20) NOT NULL COMMENT '资金账号',
  `F05` varchar(60) NOT NULL COMMENT '账户名称',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `F07` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F04_UNIQUE` (`F04`),
  UNIQUE KEY `F02_UNIQUE` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资金账户';

-- ----------------------------
-- Table structure for T6102
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6102`;
CREATE TABLE `S61`.`T6102` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '资金账号ID,参考T6101.F01',
  `F03` mediumint(8) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '对方账户ID,参考T6101.F01',
  `F05` datetime NOT NULL COMMENT '创建时间',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F07` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F08` decimal(20,2) NOT NULL COMMENT '余额',
  `F09` varchar(300) DEFAULT NULL COMMENT '备注',
  `F10` enum('WDZ','YDZ') NOT NULL DEFAULT 'WDZ' COMMENT '对账状态,WDZ:未对账;YDZ:已对账;',
  `F11` datetime DEFAULT NULL COMMENT '对账时间',
  `F12` int(11) DEFAULT NULL COMMENT '标ID',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资金流水';

-- ----------------------------
-- Table structure for T6103
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6103`;
CREATE TABLE `S61`.`T6103` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '体验金ID（自增长）',
  `F02` int(10) NOT NULL COMMENT '用户ID（参考T6110.F01）',
  `F03` decimal(20,2) NOT NULL COMMENT '体验金金额',
  `F04` datetime NOT NULL COMMENT '生效时间',
  `F05` datetime NOT NULL COMMENT '失效时间',
  `F06` enum('YGQ','YTZ','YJQ','YWT','WSY') NOT NULL COMMENT '使用状态：已过期：''YGQ'',未使用：''WSY'',已委托：''YWT'',已投资：''YTZ'',已结清：''YJQ''',
  `F07` int(10) NOT NULL COMMENT '体验金有效收益期',
  `F08` enum('ZC','PTZS') NOT NULL COMMENT '来源：（ZC:注册 PTZS:平台赠送）',
  `F09` varchar(200) DEFAULT NULL COMMENT '备注',
  `F10` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `F11` datetime DEFAULT NULL COMMENT '利息开始时间',
  `F12` int(11) DEFAULT NULL COMMENT '操作人id（参考T7110.F01）',
  `F13` int(10) DEFAULT NULL COMMENT '标ID，参考T6230.F01',
  `F14` int(11) NOT NULL COMMENT '活动规则id：参考T6344.F01',
  `F15` datetime DEFAULT NULL COMMENT '使用时间',
  `F16` enum('true','false') DEFAULT 'true' COMMENT '体验金投资收益计算方式(true:按月;false:按天)',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='体验金信息表';


-- ----------------------------
-- Table structure for T6104
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6104`;
CREATE TABLE `S61`.`T6104` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '平台账号ID,参考T6101.F01',
  `F03` int(10) unsigned DEFAULT NULL COMMENT '订单id,参考S65.T6501 F01',
  `F04` mediumint(8) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F06` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F07` enum('DTJ','CG','SB') NOT NULL DEFAULT 'DTJ' COMMENT '交易状态：DTJ:待提交,CG:成功,SB:失败',
  `F08` datetime NOT NULL COMMENT '创建时间',
  `F09` int(10) NOT NULL COMMENT '操作人，参考 S71.T7110 F01',
  `F10` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台调账信息';

-- ----------------------------
-- Table structure for T6105
-- ----------------------------
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

-- ----------------------------
-- Table structure for T6106
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6106`;
CREATE TABLE `S61`.`T6106` (
  `F01` int(11) noT NULL AUTO_INCREMENT,
  `F02` int(11) noT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(11) noT NULL COMMENT '积分',
  `F04` datetime noT NULL COMMENT '获取时间',
  `F05` enum('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods','nopassreturn','chargeScore') noT NULL COMMENT '积分类型:register:注册、sign:签到、invite:邀请、invest:投资、cellphone:手机认证、mailbox:邮箱认证、realname:实名认证、trusteeship:开通第三方托管账户、charge:充值、buygoods:现金购买商品积分、nopassReturn:审核不通过返还积分、chargeScore:积分充值）',
  `F06` enum('yes','no') noT NULL DEFAULT 'no' COMMENT '是否已清零，yes：是；no：否',
  `F07` datetime noT NULL COMMENT '最后修改时间',
  `F08` int(11) NULL COMMENT '操作人ID,参考T7110.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户积分获取记录';

-- ----------------------------
-- Table structure for T6110
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6110`;
CREATE TABLE `S61`.`T6110` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID,自增',
  `F02` varchar(20) NOT NULL COMMENT '用户登录账号',
  `F03` varchar(40) NOT NULL COMMENT '用户登录密码',
  `F04` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `F05` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `F06` enum('ZRR','FZRR') NOT NULL COMMENT '用户类型,ZRR:自然人;FZRR:非自然人',
  `F07` enum('QY','SD','HMD') NOT NULL DEFAULT 'QY' COMMENT '状态,QY启用;SD:锁定;HMD:黑名单;',
  `F08` enum('WXZC','APPZC','PCZC','HTTJ') NOT NULL DEFAULT 'PCZC' COMMENT '注册来源,PCZC:PC注册;WXZC:微信注册;APPZC:APP注册;HTTJ:后台添加',
  `F09` datetime NOT NULL COMMENT '注册时间',
  `F10` enum('S','F') NOT NULL COMMENT '担保方,S:是;F:否;',
  `F11` tinyint(2) NOT NULL DEFAULT '0' COMMENT '当日交易密码输入错误次数',
  `F12` enum('F','S') NOT NULL DEFAULT 'S' COMMENT '是否第一次登陆系统,S:是;F:否;',
  `F13` enum('F','S') DEFAULT 'F' COMMENT '是否删除',
  `F14` char(6) DEFAULT NULL COMMENT '邀请业务员工号',
  `F15` datetime DEFAULT NULL COMMENT '签到时间',
  `F16` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  `F17` enum('S','F') NULL DEFAULT 'F' COMMENT '是否允许投资：S:允许投资,F:禁止投资',
  `F18` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否开启过投资功能：S:是,F:否',
  `F19` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否允许购买不良债权：S：是,F：否',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`),
  KEY `F04` (`F04`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户账号表';

-- ----------------------------
-- Table structure for T6111
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6111`;
CREATE TABLE `S61`.`T6111` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` varchar(10) NOT NULL COMMENT '推广码',
  `F03` varchar(10) DEFAULT NULL COMMENT '邀请码',
  `F04` varchar(20) DEFAULT NULL COMMENT '邀请人手机号',
  `F05` varchar(20) DEFAULT NULL COMMENT '邀请人姓名',
  `F06` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否是用手机号作为邀请码',
  `F07` datetime DEFAULT NULL COMMENT '推广时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户推广信息';

-- ----------------------------
-- Table structure for T6112
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6112`;
CREATE TABLE `S61`.`T6112` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户房产ID,自增',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` varchar(100) NOT NULL COMMENT '小区名称',
  `F04` float(20,2) DEFAULT NULL COMMENT '建筑面积(m2)',
  `F05` int(11) DEFAULT NULL COMMENT '使用年限,单位:年',
  `F06` decimal(20,2) unsigned DEFAULT NULL COMMENT '购买价格',
  `F07` decimal(20,2) unsigned DEFAULT NULL COMMENT '评估价格',
  `F08` mediumint(8) unsigned DEFAULT NULL COMMENT '区划ID,参考T5119.F01',
  `F09` varchar(100) DEFAULT NULL COMMENT '地址',
  `F10` varchar(30) DEFAULT NULL COMMENT '房产证编号',
  `F11` decimal(20,2) unsigned DEFAULT NULL COMMENT '参考价格',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F08_idx` (`F08`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户房产信息';

-- ----------------------------
-- Table structure for T6113
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6113`;
CREATE TABLE `S61`.`T6113` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '车产ID,自增',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` varchar(45) DEFAULT NULL COMMENT '汽车品牌',
  `F04` varchar(20) DEFAULT NULL COMMENT '车牌号码',
  `F05` int(11) DEFAULT NULL COMMENT '购车年份',
  `F06` decimal(20,2) DEFAULT NULL COMMENT '购买价格',
  `F07` decimal(20,2) DEFAULT NULL COMMENT '评估价格',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户车产信息';

-- ----------------------------
-- Table structure for T6114
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6114`;
CREATE TABLE `S61`.`T6114` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户银行卡ID,自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '银行ID,参考T5020.F01',
  `F04` mediumint(8) unsigned NOT NULL COMMENT '开户行所在地区域ID,参考T5119.F01',
  `F05` varchar(60) NOT NULL COMMENT '开户行',
  `F06` varchar(30) NOT NULL COMMENT '银行卡号,前4位,后3位保留,其他星号代替',
  `F07` varchar(60) NOT NULL COMMENT '银行卡号,加密存储',
  `F08` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F09` datetime NOT NULL COMMENT '创建时间',
  `F10` enum('TG','BTG') NOT NULL COMMENT '实名认证,TG:通过;BTG:不通过;',
  `F11` varchar(45) NOT NULL COMMENT '开户名',
  `F12` int(2) NOT NULL COMMENT '开户名类型；1：个人，2：公司',
  `F13` varchar(50) DEFAULT NULL COMMENT '快捷账户认证-系统跟踪号',
  `F14` enum('WRZ','RZZ','RZCG','RZSB') DEFAULT 'WRZ' COMMENT '快捷支付状态,WRZ:未认证;RZZ:认证中;RZCG:认证成功;RZSB:认证失败;',
  `F15` varchar(50) DEFAULT NULL COMMENT '银行卡解绑请求流水号',
  `F16` varchar(50) DEFAULT NULL COMMENT '绑定标识号',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F06_UNIQUE` (`F07`,`F02`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户银行卡';

-- ----------------------------
-- Table structure for T6115
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6115`;
CREATE TABLE `S61`.`T6115` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '借款总额,本金',
  `F03` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '投资总额,本金',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户理财统计';

-- ----------------------------
-- Table structure for T6116
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6116`;
CREATE TABLE `S61`.`T6116` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '信用积分',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '授信额度',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `F05` enum('AAA','AA','A','B') NOT NULL DEFAULT 'A' COMMENT '用户信用等级',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信用账户';

-- ----------------------------
-- Table structure for T6117
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6117`;
CREATE TABLE `S61`.`T6117` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F04` datetime NOT NULL COMMENT '发生时间',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F07` decimal(20,2) NOT NULL COMMENT '余额',
  `F08` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信用流水';

-- ----------------------------
-- Table structure for T6118
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6118`;
CREATE TABLE `S61`.`T6118` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` enum('TG','BTG') NOT NULL DEFAULT 'BTG' COMMENT '身份认证状态,TG:通过;BTG:不通过;',
  `F03` enum('TG','BTG') NOT NULL DEFAULT 'BTG' COMMENT '手机认证状态,TG:通过;BTG:不通过;',
  `F04` enum('TG','BTG') NOT NULL DEFAULT 'BTG' COMMENT '邮箱认证状态,TG:通过;BTG:不通过;',
  `F05` enum('YSZ','WSZ') NOT NULL DEFAULT 'WSZ' COMMENT '交易密码,YSZ:已设置;WSZ:未设置;',
  `F06` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `F07` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `F08` varchar(60) DEFAULT NULL COMMENT '交易密码',
  `F09` enum('TG','BTG') DEFAULT 'BTG' COMMENT '视频认证状态,TG:通过;BTG:不通过',
  `F10` enum('BTG','TG') NOT NULL DEFAULT 'BTG' COMMENT '密保认证状态，不通过，通过',
  `F11` varchar(100) DEFAULT NULL COMMENT '待认证邮箱地址',
  `F12` datetime DEFAULT NULL COMMENT '实名认证提交第三方时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户安全认证';

-- ----------------------------
-- Table structure for T6119
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6119`;
CREATE TABLE `S61`.`T6119` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '托管机构代码',
  `F03` varchar(60) NOT NULL COMMENT '托管帐号ID',
  `F04` varchar(60) DEFAULT NULL COMMENT '第三方授权情况',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户第三方托管帐号';

-- ----------------------------
-- Table structure for T6120
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6120`;
CREATE TABLE `S61`.`T6120` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '认证项ID,参考T5123.F01',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '认证次数',
  `F04` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `F05` enum('WYZ','DSH','TG','BTG') NOT NULL DEFAULT 'WYZ' COMMENT '认证状态,WYZ:未验证;DSH:待审核;TG:通过;BTG:不通过',
  `F06` datetime DEFAULT NULL COMMENT '认证时间',
  `F07` int(10) unsigned DEFAULT NULL COMMENT '有效记录ID,,参考T6121.F01',
  PRIMARY KEY (`F01`,`F02`),
  KEY `F02_idx` (`F02`),
  KEY `F07_idx` (`F07`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证信息';

-- ----------------------------
-- Table structure for T6121
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6121`;
CREATE TABLE `S61`.`T6121` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '认证项ID,参考T5123.F01',
  `F04` varchar(30) DEFAULT NULL COMMENT '认证内容',
  `F05` enum('TG','BTG','DSH') DEFAULT 'BTG' COMMENT '认证结果,TG:通过;BTG:不通过;DSH:待审核;',
  `F06` int(10) unsigned DEFAULT NULL COMMENT '认证人ID,参考T7110.F01',
  `F07` datetime DEFAULT NULL COMMENT '认证时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户认证记录';

-- ----------------------------
-- Table structure for T6122
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6122`;
CREATE TABLE `S61`.`T6122` (
  `F01` int(10) unsigned NOT NULL COMMENT '认证记录ID,参考T6021.F01',
  `F02` varchar(20) NOT NULL COMMENT '附件文件编码',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '附件大小',
  `F04` datetime NOT NULL COMMENT '上传时间',
  `F05` varchar(10) DEFAULT NULL COMMENT '附件格式',
  `F06`  int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  PRIMARY KEY (`F06`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证附件';

-- ----------------------------
-- Table structure for T6123
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6123`;
CREATE TABLE `S61`.`T6123` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '接收用户ID,参考T6110.F01',
  `F03` varchar(60) NOT NULL COMMENT '标题',
  `F04` datetime NOT NULL COMMENT '发送时间',
  `F05` enum('WD','YD','SC') NOT NULL DEFAULT 'WD' COMMENT '状态,WD:未读;YD:已读;SC:删除;',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户站内信';

-- ----------------------------
-- Table structure for T6124
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6124`;
CREATE TABLE `S61`.`T6124` (
  `F01` int(10) unsigned NOT NULL COMMENT '站内信ID,参考T6123.F01',
  `F02` text COMMENT '站内信内容',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户站内信内容';


DROP TABLE IF EXISTS `S61`.`T6125`;
CREATE TABLE `S61`.`T6125` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) NOT NULL COMMENT '用户ID（参考T6110.F01）',
  `F03` char(7) NOT NULL COMMENT '担保码',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '担保额度',
  `F05` enum('SQDCL','QXDCL','SQCG','SQSB','QXCG','QXSB') NOT NULL COMMENT '状态：''SQDCL''：申请担保待处理,''QXDCL''：取消担保待处理,''SQCG''：申请担保成功,''SQSB''：申请担保失败,''QXCG''：取消担保成功,''QXSB''：取消担保失败',
  `F06` datetime NOT NULL COMMENT '申请时间',
  `F07` int(10) DEFAULT NULL COMMENT '审核人（参考T7110.F01）',
  `F08` datetime DEFAULT NULL COMMENT '审核时间',
  `F09` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F10` varchar(200) DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `unquie_index` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='担保方申请表';

DROP TABLE IF EXISTS `S61`.`T6126`;
CREATE TABLE `S61`.`T6126` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '交易类型ID,参考T5122.F01',
  `F04` datetime NOT NULL COMMENT '发生时间',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F07` decimal(20,2) NOT NULL COMMENT '余额',
  `F08` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='担保方担保交易记录';

-- ----------------------------
-- Table structure for T6130
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6130`;
CREATE TABLE `S61`.`T6130` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '银行卡ID,参考T6114.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '提现金额',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '应收手续费',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '实收手续费',
  `F08` datetime NOT NULL COMMENT '创建时间',
  `F09` enum('DSH','DFK','YFK','TXSB','FKZ') NOT NULL DEFAULT 'DSH' COMMENT '状态,DSH:待审核;DFK:待放款;YFK:已放款;TXSB:提现失败:FKZ放款中;',
  `F10` int(10) unsigned DEFAULT NULL COMMENT '审核人ID,参考T7110.F01',
  `F11` datetime DEFAULT NULL COMMENT '审核时间',
  `F12` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `F13` int(10) unsigned DEFAULT NULL COMMENT '放款人ID,参考T7110.F01',
  `F14` datetime DEFAULT NULL COMMENT '放款时间',
  `F15` varchar(100) DEFAULT NULL COMMENT '放款意见',
  `F16` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '是否已到账,F:否;S:是;',
  `F17` char(6) DEFAULT NULL COMMENT '业务员工号',
  `F18` enum('QY','TY') DEFAULT NULL COMMENT '业务员状态（QY:启用,TY:停用）',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`),
  KEY `F10_idx` (`F10`),
  KEY `F13_idx` (`F13`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户提现申请';

-- ----------------------------
-- Table structure for T6131
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6131`;
CREATE TABLE `S61`.`T6131` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` decimal(20,2) unsigned NOT NULL COMMENT '充值金额',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '应收手续费',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '实收手续费',
  `F06` datetime NOT NULL COMMENT '创建时间',
  `F07` enum('WRZ','YRZ') NOT NULL COMMENT '状态,WRZ:未入账;YRZ:已入账;',
  `F08` varchar(20) DEFAULT NULL COMMENT '支付公司',
  `F09` varchar(60) DEFAULT NULL COMMENT '水单号',
  `F10` int(11) DEFAULT NULL COMMENT '入账时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户在线充值订单';

-- ----------------------------
-- Table structure for T6141
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6141`;
CREATE TABLE `S61`.`T6141` (
  `F01` int(10) unsigned NOT NULL COMMENT '登录账号ID',
  `F02` varchar(30) DEFAULT NULL COMMENT '姓名',
  `F03` enum('LC','JK') NOT NULL DEFAULT 'LC' COMMENT '兴趣类型,LC:理财;JK:借款',
  `F04` enum('TG','BTG') NOT NULL DEFAULT 'BTG' COMMENT '实名认证,TG:通过;BTG:不通过;',
  `F05` varchar(20) DEFAULT NULL COMMENT '用户头像文件编码',
  `F06` char(18) DEFAULT NULL COMMENT '身份证号,3-18位星号替换',
  `F07` varchar(60) DEFAULT NULL COMMENT '身份证号,加密存储,唯一',
  `F08` date DEFAULT NULL COMMENT '出身日期',
  `F09` enum('F','M') DEFAULT NULL COMMENT '性别:F女，M男',
  PRIMARY KEY (`F01`),
  KEY `F08_idx` (`F08`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人基础信息';

-- ----------------------------
-- Table structure for T6142
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6142`;
CREATE TABLE `S61`.`T6142` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '学历记录ID,自增',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` varchar(45) NOT NULL COMMENT '毕业院校',
  `F04` int(11) NOT NULL COMMENT '入学年份',
  `F05` varchar(20) NOT NULL COMMENT '专业',
  `F06` text COMMENT '在校情况简介',
  `F07` int(4) NOT NULL COMMENT '毕业年份',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='个人学历记录';

-- ----------------------------
-- Table structure for T6143
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6143`;
CREATE TABLE `S61`.`T6143` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '工作记录ID,自增',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` enum('ZZ','LZ') NOT NULL COMMENT '工作状态,ZZ:在职;LZ:离职;',
  `F04` varchar(30) DEFAULT NULL COMMENT '单位名称',
  `F05` varchar(30) DEFAULT NULL COMMENT '职位',
  `F06` varchar(100) DEFAULT NULL COMMENT '工作邮箱',
  `F07` mediumint(8) unsigned DEFAULT NULL COMMENT '工作城市,参考T5119.F01',
  `F08` varchar(45) DEFAULT NULL COMMENT '工作地址',
  `F09` varchar(30) DEFAULT NULL COMMENT '公司类别',
  `F10` varchar(45) DEFAULT NULL COMMENT '公司行业',
  `F11` varchar(20) DEFAULT NULL COMMENT '公司规模',
  PRIMARY KEY (`F01`,`F02`),
  KEY `F02_idx` (`F02`),
  KEY `F07_idx` (`F07`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='个人工作记录';

-- ----------------------------
-- Table structure for T6144
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6144`;
CREATE TABLE `S61`.`T6144` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F02` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '逾期次数',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '严重逾期次数',
  `F04` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最长逾期天数',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信用档案表';

-- ----------------------------
-- Table structure for T6145
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6145`;
CREATE TABLE `S61`.`T6145` (
  `F01` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` varchar(20) NOT NULL COMMENT '视频文件格式',
  `F04` varchar(20) NOT NULL COMMENT '视频文件编码',
  `F05` int(11) unsigned NOT NULL COMMENT '视频文件大小',
  `F06` datetime NOT NULL COMMENT '上传时间',
  `F07` enum('DSH','SHTG','SHBTG') NOT NULL DEFAULT 'DSH' COMMENT '审核状态,DSH:待审核;SHTG:审核通过;SHBTG:审核不通过',
  `F08` varchar(255) DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`F01`),
  KEY `idx_F02` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户认证视频表';


-- ----------------------------
-- Table structure for T6146
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6146`;
CREATE TABLE `S61`.`T6146` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` INT(10) UNSIGNED NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F03` INT(10) UNSIGNED NOT NULL COMMENT '逾期天数',
  `F04` INT(10) UNSIGNED NOT NULL COMMENT '期号',
  `F05` INT(10) UNSIGNED NOT NULL COMMENT '债权ID,参考T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F04` (`F04`),
  KEY `F05` (`F05`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='逾期记录表';

-- ----------------------------
-- Table structure for T6147
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6147`;
CREATE TABLE `S61`.`T6147` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '评估分数',
  `F04` enum('JJX','JQX','WJX','JSX','BSX') NOT NULL COMMENT '评估等级:JJX:激进型；JQX：进取型；,WJX：稳健型；,JSX：谨慎型；,BSX：保守型',
  `F05` int(10) unsigned NOT NULL COMMENT '已评估总次数',
  `F06` datetime DEFAULT NULL COMMENT '评估时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户风险评估记录表';

-- ----------------------------
-- Table structure for T6148
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6148`;
CREATE TABLE `S61`.`T6148` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` enum('JJX','JQX','WJX','JSX','BSX') NOT NULL COMMENT '评估等级:JJX:激进型；JQX：进取型；,WJX：稳健型；,JSX：谨慎型；,BSX：保守型',
  `F03` int(10) unsigned NOT NULL COMMENT '最小分值',
  `F04` int(10) unsigned NOT NULL COMMENT '最大分值',
  `F05` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='风险评估类型设置表';

-- ----------------------------
-- Table structure for T6149
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6149`;
CREATE TABLE `S61`.`T6149` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(200) NOT NULL COMMENT '问题内容',
  `F03` varchar(200) NOT NULL COMMENT '选项A',
  `F04` varchar(200) NOT NULL COMMENT '选项B',
  `F05` varchar(200) NOT NULL COMMENT '选项C',
  `F06` varchar(200) NOT NULL COMMENT '选项D',
  `F07` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态，QY：启用；TY：停用',
  `F08` int(11) NOT NULL COMMENT '排序字段',
  `F09` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后操作时间',
  `F10` int(6) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='风险评估问题信息表';

-- ----------------------------
-- Table structure for T6150
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6150`;
CREATE TABLE `S61`.`T6150` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户风险评估记录表ID,T6147.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '风险评估问题信息表ID,T6149.F01',
  `F04` char(1) NOT NULL COMMENT '评估问题选项：A,B,C,D',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户风险评估记录明细表';

-- ----------------------------
-- Table structure for T6161
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6161`;
CREATE TABLE `S61`.`T6161` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` varchar(45) NOT NULL COMMENT '企业编号',
  `F03` varchar(45) DEFAULT NULL COMMENT '营业执照登记注册号,唯一',
  `F04` varchar(45) NOT NULL COMMENT '企业名称',
  `F05` varchar(45) DEFAULT NULL COMMENT '企业纳税号',
  `F06` varchar(45) DEFAULT NULL COMMENT '组织机构代码',
  `F07` int(10) unsigned DEFAULT NULL COMMENT '注册年份',
  `F08` decimal(20,2) unsigned DEFAULT NULL COMMENT '注册资金(万元)',
  `F09` varchar(45) DEFAULT NULL COMMENT '行业',
  `F10` int(9) DEFAULT NULL COMMENT '企业规模,单位: 人',
  `F11` varchar(20) DEFAULT NULL COMMENT '法人',
  `F12` char(18) DEFAULT NULL COMMENT '法人身份证号,9-16位星号替换',
  `F13` varchar(60) DEFAULT NULL COMMENT '法人身份证号,加密存储',
  `F14` decimal(20,2) DEFAULT NULL COMMENT '资产净值(万元)',
  `F15` decimal(20,2) DEFAULT NULL COMMENT '上年度经营现金流入(万元)',
  `F16` varchar(60) DEFAULT NULL COMMENT '贷款卡证书编号',
  `F17` varchar(60) DEFAULT NULL COMMENT '企业信用证书编号',
  `F18` varchar(20) DEFAULT NULL COMMENT '企业简称',
  `F19` varchar(20) DEFAULT NULL COMMENT '社会信用代码',
  `F20` varchar(10) DEFAULT 'N' COMMENT '是否三证合一',
  `F21` varchar(20) DEFAULT NULL COMMENT '开户银行许可证',
  `F22` enum('F','M') DEFAULT NULL COMMENT '性别:F女，M男',
  `F23` varchar(60) DEFAULT NULL COMMENT '营业执照所在地',
  `F24` varchar(60) DEFAULT NULL COMMENT '营业执照过期日 格式为 YYYYMMDD',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F03_UNIQUE` (`F03`),
  UNIQUE KEY `F02_UNIQUE` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业基础信息';

-- ----------------------------
-- Table structure for T6162
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6162`;
CREATE TABLE `S61`.`T6162` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F02` text COMMENT '企业简介',
  `F03` text COMMENT '经营情况',
  `F04` text COMMENT '涉诉情况',
  `F05` text COMMENT '征信情况',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业介绍资料';

-- ----------------------------
-- Table structure for T6163
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6163`;
CREATE TABLE `S61`.`T6163` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '年份',
  `F03` decimal(20,2) DEFAULT NULL COMMENT '主营收入(万元)',
  `F04` decimal(6,6) DEFAULT NULL COMMENT '毛利率',
  `F05` decimal(20,2) DEFAULT NULL COMMENT '净利润(万元)',
  `F06` decimal(20,2) DEFAULT NULL COMMENT '总资产(万元)',
  `F07` decimal(20,2) DEFAULT NULL COMMENT '净资产(万元)',
  `F08` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业财务状况';

-- ----------------------------
-- Table structure for T6164
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6164`;
CREATE TABLE `S61`.`T6164` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` mediumint(8) unsigned DEFAULT NULL COMMENT '所在区域ID,参考T5119.F01',
  `F03` varchar(60) DEFAULT NULL COMMENT '联系地址',
  `F04` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `F05` varchar(1000) DEFAULT NULL COMMENT '网站地址',
  `F06` varchar(20) DEFAULT NULL COMMENT '法人联系电话',
  `F07` varchar(20) DEFAULT NULL COMMENT '企业联系人',
  PRIMARY KEY (`F01`),
  KEY `F11_idx` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业联系信息';

-- ----------------------------
-- Table structure for T6165
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6165`;
CREATE TABLE `S61`.`T6165` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户id',
  `F02` enum('Y','K','F','R','P','T','I') NOT NULL DEFAULT 'I' COMMENT '审核状态标记，I：初始，T：提交，P：审核中，R：审核拒绝，F：开户失败，K：开户中，Y：开户成功',
  `F03` varchar(200) DEFAULT NULL COMMENT '审核描述',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业账号审核状态';

DROP TABLE IF EXISTS S61.T6166;
CREATE TABLE S61.T6166 (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID',
  `F02` varchar(200) DEFAULT NULL COMMENT '文件名称',
  `F03` varchar(200) DEFAULT NULL COMMENT '文件摘要',
  `F04` varchar(200) DEFAULT NULL COMMENT '文件备注',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业资质文件信息表';


DROP TABLE IF EXISTS S61.`T6170`;
CREATE TABLE S61.`T6170` (
  `F01` int(100) NOT NULL AUTO_INCREMENT,
  `F02` varchar(50) NOT NULL COMMENT '请求流水号',
  `F03` varchar(20) NOT NULL COMMENT '用户登录账号',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `F05` datetime NOT NULL COMMENT '冻结时间',
  PRIMARY KEY (`F01`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资金冻结流水号';

-- ----------------------------
-- Table structure for T6171
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6171`;
CREATE TABLE `S61`.`T6171` (
  `F01` int(10) NOT NULL COMMENT 'T6110ID',
  `F03` enum('Y','N') DEFAULT 'N' COMMENT '是否新浪授权',
  `F04` varchar(100) DEFAULT NULL COMMENT '新浪或QQ返回唯一标识',
  `F05` datetime DEFAULT NULL COMMENT '最后登录时间',
  `F06` varchar(2) DEFAULT 'N' COMMENT '是否是qq授权',
  `F07` varchar(256) DEFAULT NULL COMMENT '第三方登录授权码',
  `F08` bigint(20) DEFAULT NULL COMMENT '授权码授权时间',
  `F09` enum('Y','N') DEFAULT 'N' COMMENT '是否微信授权'
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='第三方登录信息表';


-- ----------------------------
-- Table structure for T6180
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6180`;
CREATE TABLE `S61`.`T6180` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F02` text COMMENT '担保资质描述',
  `F03` enum('RZZZGS','XDGS','DBGS') DEFAULT 'DBGS' COMMENT '机构类型状态,DBGS:担保公司;XDGS:小贷公司;RZZZGS:融资租债公司;',
  `F04` text COMMENT '担保机构描述',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='担保方基础信息';



-- ----------------------------
-- Table structure for T6190
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6190`;
CREATE TABLE `S61`.`T6190` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) unsigned NOT NULL COMMENT '后台帐号ID,参考T6110.F01',
  `F03` datetime NOT NULL COMMENT '操作时间',
  `F04` varchar(20) NOT NULL COMMENT '操作类型',
  `F05` varchar(200) DEFAULT NULL COMMENT '操作描述',
  `F06` varchar(20) DEFAULT NULL COMMENT '访问IP',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户操作日志';

-- ----------------------------
-- Table structure for T6191
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6191`;
CREATE TABLE `S61`.`T6191` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F02` int(10) unsigned NOT NULL DEFAULT '2014' COMMENT '年',
  `F03` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '季度(1,2,3,4)',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成功借款金额',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`,`F02`,`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的借款金额按季度统计表';

-- ----------------------------
-- Table structure for T6192
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6192`;
CREATE TABLE `S61`.`T6192` (
  `F01` int(10) unsigned NOT NULL COMMENT '用户账号ID,参考T6110.F01',
  `F02` int(10) NOT NULL DEFAULT '2014' COMMENT '年',
  `F03` tinyint(4) NOT NULL DEFAULT '5' COMMENT '月份',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '还款总金额',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`,`F02`,`F03`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='我的借款还款按月份统计表';

-- ----------------------------
-- Table structure for T6193
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6193`;
CREATE TABLE `S61`.`T6193` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` varchar(100) NOT NULL COMMENT '问题描述',
  `F03` int(11) NOT NULL COMMENT '排序字段',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='密码问题表';

-- ----------------------------
-- Table structure for T6194
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6194`;
CREATE TABLE `S61`.`T6194` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` int(11) NOT NULL COMMENT '用户ID,T6110.F01',
  `F03` int(11) NOT NULL COMMENT '问题ID,T6193.F01',
  `F04` varchar(500) NOT NULL COMMENT '问题回答',
  `F05` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='密码问题与答案';

-- ----------------------------
-- Table structure for T6195
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6195`;
CREATE TABLE `S61`.`T6195` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(11) NOT NULL COMMENT '用户ID,T6110.F01',
  `F03` varchar(1000) NOT NULL COMMENT '投诉/建议内容',
  `F04` datetime NOT NULL COMMENT '投诉/建议时间',
  `F05` varchar(1000) DEFAULT NULL COMMENT '回复内容',
  `F06` enum('yes','no') NOT NULL DEFAULT 'no' COMMENT '是否回复,yes:已回复；no:未回复;',
  `F07` int(6) DEFAULT NULL COMMENT '回复人',
  `F08` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,YFB:已发布；WFB:未发布;',
  `F09` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F07_idx` (`F07`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户投诉建议表';

-- ----------------------------
-- Table structure for T6196
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6196`;
CREATE TABLE `S61`.`T6196` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计投资金额',
  `F03` INT(9) UNSIGNED NOT NULL COMMENT '注册用户数',
  `F04` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计赚取收益',
  `F05` INT(9) UNSIGNED NOT NULL COMMENT '累计成交笔数',
  `F06` INT(9) UNSIGNED NOT NULL COMMENT '投资用户数',
  `F07` INT(9) UNSIGNED NOT NULL COMMENT '借款用户数',
  `F08` INT(9) UNSIGNED NOT NULL COMMENT '90后',
  `F09` INT(9) UNSIGNED NOT NULL COMMENT '80后',
  `F10` INT(9) UNSIGNED NOT NULL COMMENT '70后',
  `F11` INT(9) UNSIGNED NOT NULL COMMENT '60后',
  `F12` INT(9) UNSIGNED NOT NULL COMMENT '其他',
  `F13` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '0-3个月',
  `F14` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '3-6个月',
  `F15` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '6-9个月',
  `F16` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '9-12个月',
  `F17` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '12-24个月',
  `F18` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '24个月以上',
  `F19` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '机构担保标',
  `F20` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '抵押认证标',
  `F21` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '实地认证标',
  `F22` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '信用认证标',
  `F23` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计代偿金额',
  `F24` DECIMAL(10,2) NOT NULL COMMENT '最大单户借款余额占比',
  `F25` DECIMAL(10,2) NOT NULL COMMENT '最大10户借款余额占比',
  `F26` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '借款逾期金额',
  `F27` DECIMAL(10,2) NOT NULL COMMENT '借款逾期率',
  `F28` DECIMAL(10,2) NOT NULL COMMENT '借款坏账率',
  `F29` DATETIME DEFAULT NULL COMMENT '最后操作时间',
  `F30` int(10) unsigned NOT NULL COMMENT '其他(天标)',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='运营数据基础设置表';

-- ----------------------------
-- Table structure for T6197
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6197`;
CREATE TABLE `S61`.`T6197` (
  `F01` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` DECIMAL(20,0) UNSIGNED NOT NULL COMMENT '累计投资金额',
  `F03` DATE NOT NULL COMMENT '累计投资日期',.
  `F04` DATETIME DEFAULT NULL COMMENT '最后操作时间',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='运营数据累计投资设置表';

DROP TABLE IF EXISTS `S61`.`T6198`;
CREATE TABLE `S61`.`T6198` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6110.F01',
  `F03` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '错误认证次数',
  `F04` enum('PC','APP','WEIXIN') NOT NULL DEFAULT 'PC' COMMENT '来源，PC:PC;APP:APP;WEIXIN:微信',
  `F05` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '累计登陆次数',
  `F06` datetime DEFAULT NULL COMMENT '认证通过时间',
  `F07` datetime DEFAULT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='实名认证统计表';

-- ----------------------------
-- Procedure structure for SP_T6110
-- ----------------------------
DROP PROCEDURE IF EXISTS `S61`.`SP_T6110`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S61`.`SP_T6110`()
    COMMENT '当日交易密码错误次数清零'
BEGIN
	 UPDATE S61.T6110 SET F11 = 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T6115
-- ----------------------------
DROP PROCEDURE IF EXISTS `S61`.`SP_T6115`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S61`.`SP_T6115`()
    COMMENT '用户理财统计'
BEGIN
	-- 用户ID
	DECLARE _F01 DECIMAL(20,2) DEFAULT 0.00;
	-- 借款总额,本金
	DECLARE _F02 DECIMAL(20,2) DEFAULT 0.00;
	-- 投资总额,本金
	DECLARE _F03 DECIMAL(20,2) DEFAULT 0.00;

		DECLARE 	_error INT DEFAULT 0;
	-- 借款列表游标定义 
	DECLARE 	_done 			INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_list 			CURSOR FOR 		SELECT F01 FROM S61.T6110 WHERE   F10='F';
	DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;
	DECLARE 	CONTINUE 		HANDLER FOR SQLEXCEPTION 
	BEGIN
			SHOW ERRORS;
			SET _error = 1;
	END;
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
	OPEN _list;
	REPEAT 
		FETCH _list INTO _F01;
		IF NOT _done THEN
				START TRANSACTION;
			
				SELECT F01 FROM S61.T6115 WHERE F01 = _F01 FOR UPDATE;
				SELECT IFNULL(SUM(F05),0) INTO _F02  FROM S62.T6230 WHERE T6230.F02 = _F01 AND T6230.F20 IN ('HKZ','YDF','YJQ');
				SELECT IFNULL(SUM(F07),0) INTO _F03 FROM S62.T6252 WHERE T6252.F04 = _F01 AND T6252.F05 = '7001';

				UPDATE S61.T6115 SET F02 = _F02, F03 = _F03 WHERE F01 = _F01;
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
-- Event structure for EVT_T6110
-- ----------------------------
DROP EVENT IF EXISTS `S61`.`EVT_T6110`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S61`.`EVT_T6110` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 23:59:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6110()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T6115
-- ----------------------------
DROP EVENT IF EXISTS `S61`.`EVT_T6115`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S61`.`EVT_T6115` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6115()
;;
DELIMITER ;
