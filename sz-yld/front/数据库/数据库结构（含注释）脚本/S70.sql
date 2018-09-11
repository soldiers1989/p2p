/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S70

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:40:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T7010
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7010`;
CREATE TABLE `S70`.`T7010` (
  `F01` int(6) unsigned NOT NULL DEFAULT '0' COMMENT '今日注册用户数',
  `F02` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '历史注册用户总数',
  `F03` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '今日登录用户数',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '今日用户总充值',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '用户历史总充值',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '今日用户总提现',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '用户历史总提现',
  `F08` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '平台总收益',
  `F09` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '用户投资总收益',
  `F10` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '今日用户待还总金额',
  `F11` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '用户待还总金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页统计表';

-- ----------------------------
-- Table structure for T7011
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7011`;
CREATE TABLE `S70`.`T7011` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '后台账号表自增ID',
  `F02` varchar(50) DEFAULT NULL COMMENT '登陆名',
  `F03` varchar(50) DEFAULT NULL COMMENT '密码',
  `F04` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `F05` enum('QY','TY') DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用',
  `F06` datetime DEFAULT NULL COMMENT '创建时间',
  `F07` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `F08` varchar(20) DEFAULT NULL COMMENT '登陆IP',
  `F09` enum('F','S') DEFAULT 'S' COMMENT '是否第一次登陆修改密码,S:是;F:否',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='后台账号表';

-- ----------------------------
-- Table structure for T7012
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7012`;
CREATE TABLE `S70`.`T7012` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '后台登陆日志自增ID',
  `F02` varchar(50) DEFAULT NULL COMMENT '后台账号',
  `F03` datetime DEFAULT NULL COMMENT '登陆时间',
  `F04` varchar(20) DEFAULT NULL COMMENT '登陆IP',
  `F05` enum('CG','SB') DEFAULT 'CG' COMMENT '是否成功登陆,CG:成功;SB:失败',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='后台登陆日志';

-- ----------------------------
-- Table structure for T7014
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7014`;
CREATE TABLE `S70`.`T7014` (
  `F01` varchar(50) NOT NULL COMMENT '系统消息模板表key',
  `F02` enum('ZNX','YJ','DX') DEFAULT 'ZNX' COMMENT '消息类型,ZNX:站内信;YJ:邮件;DX:短信',
  `F03` varchar(50) DEFAULT NULL COMMENT '事件描述',
  `F04` varchar(50) DEFAULT NULL COMMENT '标题',
  `F05` text COMMENT '模板内容',
  `F06` varchar(255) DEFAULT NULL COMMENT '模板参数描述',
  `F07` datetime DEFAULT NULL COMMENT '最后更新时间',
  `F08` text COMMENT '默认模板内容',
  `F09` enum('QY','TY') DEFAULT 'QY' COMMENT '是否启用,QY:启用;TY:停用',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推广消息模版';

-- ----------------------------
-- Table structure for T7015
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7015`;
CREATE TABLE `S70`.`T7015` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '站内信推广自增ID',
  `F02` varchar(50) DEFAULT NULL COMMENT '标题',
  `F03` varchar(250) DEFAULT NULL COMMENT '内容',
  `F04` int(10) unsigned DEFAULT NULL COMMENT '接受人数',
  `F05` int(10) DEFAULT NULL COMMENT '创建者',
  `F06` datetime DEFAULT NULL COMMENT '创建时间',
  `F07` enum('SY','ZDR') DEFAULT 'SY' COMMENT '发送对象,SY:所有;ZDR:指定人',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信推广';

-- ----------------------------
-- Table structure for T7016
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7016`;
CREATE TABLE `S70`.`T7016` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '站内信推广指定人列表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '站内信推广ID,参考T7015.F01',
  `F03` varchar(50) DEFAULT NULL COMMENT '指定人账户名',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信推广指定人列表';

-- ----------------------------
-- Table structure for T7017
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7017`;
CREATE TABLE `S70`.`T7017` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信推广自增ID',
  `F02` varchar(200) DEFAULT NULL COMMENT '内容',
  `F03` int(10) unsigned DEFAULT NULL COMMENT '接受人数',
  `F04` int(10) unsigned DEFAULT NULL COMMENT '创建者,参考T7011.F01',
  `F05` datetime DEFAULT NULL COMMENT '创建时间',
  `F06` enum('SY','ZDR') DEFAULT 'SY' COMMENT '发送对象,SY:所有;ZDR:指定人',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信推广';

-- ----------------------------
-- Table structure for T7018
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7018`;
CREATE TABLE `S70`.`T7018` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信推广指定人列表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '短信推广ID,参考T7017.F01',
  `F03` varchar(20) DEFAULT NULL COMMENT '指定人手机号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信推广指定人列表';

-- ----------------------------
-- Table structure for T7019
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7019`;
CREATE TABLE `S70`.`T7019` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '邮件推广自增ID',
  `F02` varchar(50) DEFAULT NULL COMMENT '标题',
  `F03` text COMMENT '内容',
  `F04` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接受人数',
  `F05` int(10) unsigned NOT NULL COMMENT '创建者,参考T7011.F01',
  `F06` datetime DEFAULT NULL COMMENT '创建时间',
  `F07` enum('SY','ZDR') DEFAULT 'SY' COMMENT '发送对象,SY:所有;ZDR:指定人',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件推广';

-- ----------------------------
-- Table structure for T7020
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7020`;
CREATE TABLE `S70`.`T7020` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '邮件推广指定人列表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '邮件推广ID,参考T7019.F01',
  `F03` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '指定人邮箱',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件推广指定人列表';

-- ----------------------------
-- Table structure for T7021
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7021`;
CREATE TABLE `S70`.`T7021` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '皮肤管理自增ID',
  `F02` varchar(50) DEFAULT NULL COMMENT '主题',
  `F03` enum('GRZX','SY') DEFAULT 'SY' COMMENT '位置,SY:首页;GRZX:个人中心',
  `F04` varchar(200) DEFAULT NULL COMMENT '图片Code',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '创建者,参考T7011.F01',
  `F06` datetime DEFAULT NULL COMMENT '创建时间',
  `F07` datetime DEFAULT NULL COMMENT '最后修改时间',
  `F08` enum('S','F') DEFAULT 'S' COMMENT '是否生效,S:是;F:否',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='皮肤管理';

-- ----------------------------
-- Table structure for T7022
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7022`;
CREATE TABLE `S70`.`T7022` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值总额',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值手续费',
  `F03` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户充值统计表';

-- ----------------------------
-- Table structure for T7023
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7023`;
CREATE TABLE `S70`.`T7023` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现总额',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现手续费',
  `F03` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户提现统计表';

-- ----------------------------
-- Table structure for T7024
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7024`;
CREATE TABLE `S70`.`T7024` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '可用金额总额',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '冻结总金额',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额总额',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '用户总收益',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '借款负债总额 ',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '优选理财资产总额',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '债权资产总额',
  `F08` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资金统计表';

-- ----------------------------
-- Table structure for T7025
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7025`;
CREATE TABLE `S70`.`T7025` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总支出',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总收入',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '盈亏',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现总额',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值总额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台资金账户';

-- ----------------------------
-- Table structure for T7026
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7026`;
CREATE TABLE `S70`.`T7026` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '平台资金流水表自增ID',
  `F02` datetime NOT NULL COMMENT '时间',
  `F03` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F04` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F05` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `F06` enum('YXLCTCF','YXLCFWF','YXLCJRF','YXLCJHHK','PTZHTX','PTZHCZ','YXLCJHZBCG','TGCXJL','YXTGJL','HDFY','SFYZSXF','ZQZRFY','YQGLF','JKGLF','TXCB','CZCB','TXSXF','XXCZ','CZSXF') NOT NULL COMMENT '类型,CZSXF:充值手续费;TXSXF:提现手续费;CZCB:充值成本;TXCB:提现成本;JKGLF:借款管理费;YQGLF:逾期管理费;ZQZRFY:债权转让费用;SFYZSXF:身份验证手续费;HDFY:活动费用;YXTGJL:有效推广奖励;TGCXJL:推广持续奖励;YXLCJHZBCG:优选理财计划招标成功;PTZHCZ:平台账户充值;PTZHTX:平台账户提现;YXLCJHHK:优选理财计划还款;YXLCJRF:优选理财加入费;YXLCFWF:优选理财服务费;YXLCTCF:优选理财退出费;XXCZ:线下充值;',
  `F07` varchar(250) DEFAULT NULL COMMENT '备注',
  `F08` int(10) unsigned DEFAULT NULL COMMENT '引用ID,根据类型引用不同表ID',
  `F09` int(10) unsigned DEFAULT NULL COMMENT '用户ID,参考T6010.F01',
  PRIMARY KEY (`F01`),
  KEY `F08` (`F08`) USING BTREE,
  KEY `F09` (`F09`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台资金流水表';

-- ----------------------------
-- Table structure for T7027
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7027`;
CREATE TABLE `S70`.`T7027` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '风险保证金',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总支出',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '总收入',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '盈亏',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现总额',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值总额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台保证金账户表';

-- ----------------------------
-- Table structure for T7028
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7028`;
CREATE TABLE `S70`.`T7028` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '平台保证金流水表自增ID',
  `F02` datetime NOT NULL COMMENT '时间',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `F06` enum('SDKCBZJ','SDZJBZJ','JKCJFWF','DFHF','DF') NOT NULL COMMENT '类型,DF:垫付;DFHF:垫付返还;JKCJFWF:借款成交服务费;SDZJBZJ:手动增加保证金;SDKCBZJ:手动扣除保证金;',
  `F07` varchar(255) DEFAULT NULL COMMENT '备注',
  `F08` int(10) DEFAULT NULL COMMENT '引用ID,根据类型引用不同表ID',
  `F09` int(11) unsigned DEFAULT NULL COMMENT '用户ID,参考T6010.F01',
  PRIMARY KEY (`F01`),
  KEY `F08` (`F08`) USING BTREE,
  KEY `F09` (`F09`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='平台保证金流水表';

-- ----------------------------
-- Table structure for T7029
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7029`;
CREATE TABLE `S70`.`T7029` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '机构信息表自增ID',
  `F02` varchar(50) NOT NULL COMMENT '名称',
  `F03` enum('SDRZ','DYDB','SDRZJDYDB') NOT NULL DEFAULT 'SDRZJDYDB' COMMENT '类型,SDRZ:实地认证;DYDB:机构担保;SDRZJDYDB:实地认证+机构担保',
  `F04` varchar(20) DEFAULT NULL COMMENT '联系地址',
  `F05` enum('YX','WX','SC') NOT NULL DEFAULT 'YX' COMMENT '状态,YX:有效;WX:无效;SC:删除',
  `F06` int(10) unsigned NOT NULL COMMENT '创建人,参考T7011.F01',
  `F07` datetime NOT NULL COMMENT '创建时间',
  `F08` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `F09` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '机构信用额度',
  `F10` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '机构可用信用额度',
  `F11` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '原始备用金总额',
  `F12` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '备用金余额',
  `F13` varchar(5) NOT NULL COMMENT '机构标识符,唯一',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F13` (`F13`) USING BTREE,
  KEY `F06` (`F06`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for T7030
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7030`;
CREATE TABLE `S70`.`T7030` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '机构风险备用金表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '机构信息表ID,参考T7029.F01',
  `F03` datetime NOT NULL COMMENT '时间',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `F07` enum('SDKCBZJ','SDZJBZJ','JKCJFWF','DFHF','DF') NOT NULL COMMENT '类型,DF:垫付;DFHF:垫付返还;JKCJFWF:借款成交服务费;SDZJBZJ:手动增加保证金;SDKCBZJ:手动扣除保证金;',
  `F08` varchar(255) DEFAULT NULL COMMENT '备注',
  `F09` int(10) unsigned DEFAULT NULL COMMENT '引用ID,具体参考类型引用表ID',
  `F10` int(10) unsigned DEFAULT NULL COMMENT '用户ID,参考T6010.F01',
  PRIMARY KEY (`F01`),
  KEY `F09` (`F09`) USING BTREE,
  KEY `F10` (`F10`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='机构风险备用金流水表';

-- ----------------------------
-- Table structure for T7031
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7031`;
CREATE TABLE `S70`.`T7031` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '合同信息表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '机构信息表ID,参考T7029.F01',
  `F03` enum('DYDB','SDRZ') NOT NULL DEFAULT 'SDRZ' COMMENT '合同类型,SDRZ:实地认证;DYDB:机构担保',
  `F04` varchar(50) NOT NULL COMMENT '合同号',
  `F05` varchar(50) NOT NULL COMMENT '借款者姓名',
  `F06` varchar(20) NOT NULL COMMENT '借款者身份证',
  `F07` decimal(3,3) NOT NULL DEFAULT '0.000' COMMENT '机构服务费率',
  `F08` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '合同金额',
  `F09` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '合同余额',
  `F10` int(10) NOT NULL COMMENT '创建人,参考T7011.F01',
  `F11` datetime NOT NULL COMMENT '创建时间',
  `F12` enum('WX','YX') NOT NULL DEFAULT 'WX' COMMENT '合同状态,YX:有效;WX无效',
  `F13` int(10) unsigned DEFAULT NULL COMMENT '用户ID,参考T6010.F01',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`) USING BTREE,
  KEY `F10` (`F10`) USING BTREE,
  KEY `F13` (`F13`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='合同信息表';

-- ----------------------------
-- Table structure for T7032
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7032`;
CREATE TABLE `S70`.`T7032` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '催收记录表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '借款标ID,参考T6036.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '借款人ID,参考T6010.F01',
  `F04` enum('FL','XC','DH') DEFAULT NULL COMMENT '催收方式,DH:电话;XC:现场;FL法律',
  `F05` varchar(20) DEFAULT NULL COMMENT '催收人',
  `F06` varchar(255) DEFAULT NULL COMMENT '结果概要',
  `F07` varchar(255) DEFAULT NULL COMMENT '备注',
  `F08` datetime DEFAULT NULL COMMENT '催收时间',
  `F09` int(10) unsigned DEFAULT NULL COMMENT '操作人,参考T7011.F01',
  `F10` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='催收记录表';

-- ----------------------------
-- Table structure for T7033
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7033`;
CREATE TABLE `S70`.`T7033` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '拉黑记录表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '被黑ID,参考T6010.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '拉黑管理员ID,参考T7011.F01',
  `F04` varchar(255) DEFAULT NULL COMMENT '拉黑详情',
  `F05` datetime DEFAULT NULL COMMENT '拉黑时间',
  `F06` enum('LH','QXLH') DEFAULT 'LH' COMMENT '状态,LH:拉黑;QXLH:取消拉黑',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='拉黑记录表';


-- ----------------------------
-- Table structure for T7036
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7036`;
CREATE TABLE `S70`.`T7036` (
  `F01` int(4) unsigned NOT NULL COMMENT '年度',
  `F02` tinyint(2) unsigned NOT NULL COMMENT '月度',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '盈亏',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台资金统计-按月度';

-- ----------------------------
-- Table structure for T7037
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7037`;
CREATE TABLE `S70`.`T7037` (
  `F01` int(11) unsigned NOT NULL COMMENT '年度',
  `F02` tinyint(4) unsigned NOT NULL COMMENT '季度',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '盈亏',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台资金统计-按季度';

-- ----------------------------
-- Table structure for T7038
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7038`;
CREATE TABLE `S70`.`T7038` (
  `F01` int(11) NOT NULL COMMENT '年度',
  `F02` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值手续费',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现手续费',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值成本',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现成本',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '身份验证手续费',
  `F08` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '借款管理费',
  `F09` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '逾期管理费',
  `F10` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '债权转让费',
  `F11` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '活动费用',
  `F12` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '理财管理费',
  `F13` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '持续推广费用',
  `F14` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '有效推广费用',
  `F15` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '违约金手续费',
  `F16` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '本金垫付返还',
  `F17` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '利息垫付返还',
  `F18` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '罚息垫付返还',
  `F19` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '本金垫付支出',
  `F20` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '利息垫付支出',
  `F21` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '罚息垫付支出',
  `F22` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '加息奖励费用',
  `F23` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '体验金投资费用',
  `F24` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '红包奖励费用',
  `F25` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '奖励标奖励费用',
  `F26` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '线下充值',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台资金统计-按年度';

-- ----------------------------
-- Table structure for T7039
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7039`;
CREATE TABLE `S70`.`T7039` (
  `F01` int(11) NOT NULL COMMENT '年度',
  `F02` tinyint(4) NOT NULL COMMENT '季度',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '收入',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支出',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '盈亏',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F07` int(11) NOT NULL COMMENT '用户ID(T6110.F01）',
  PRIMARY KEY (`F01`,`F02`,`F07`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='风险保证金统计-按季度';

-- ----------------------------
-- Table structure for T7040
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7040`;
CREATE TABLE `S70`.`T7040` (
  `F01` int(11) NOT NULL COMMENT '年度',
  `F02` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '垫付',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '垫付返还',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '借款成交服务费',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '手动增加保证金',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '手动扣除保证金',
  `F08` int(11) NOT NULL COMMENT '机构ID 如果为0代表平台',
  PRIMARY KEY (`F01`,`F08`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='风险保证金统计-按年度';

-- ----------------------------
-- Table structure for T7041
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7041`;
CREATE TABLE `S70`.`T7041` (
  `F01` int(11) NOT NULL COMMENT '年份',
  `F02` tinyint(4) NOT NULL COMMENT '月份',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `F05` int(11) NOT NULL DEFAULT '0' COMMENT '充值笔数',
  `F06` int(11) NOT NULL DEFAULT '0' COMMENT '提现笔数',
  `F07` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='充值提现数据统计-按月';

-- ----------------------------
-- Table structure for T7042
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7042`;
CREATE TABLE `S70`.`T7042` (
  `F01` int(11) NOT NULL COMMENT '年份',
  `F02` varchar(2) NOT NULL COMMENT '月份',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成交金额',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT '成交笔数',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='成交数据统计-按月';

-- ----------------------------
-- Table structure for T7043
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7043`;
CREATE TABLE `S70`.`T7043` (
  `F01` int(11) NOT NULL COMMENT '年份',
  `F02` tinyint(4) NOT NULL COMMENT '月份',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成交金额金额',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT '成交笔数',
  `F05` enum('SDRZ','JGDB','XYRZ','DYRZ') NOT NULL COMMENT '类型（SDRZ实地认证、JGDB机构担保、XYRZ信用认证、DYRZ抵押认证）',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`,`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='成交数据统计-按月-按类型';

-- ----------------------------
-- Table structure for T7044
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7044`;
CREATE TABLE `S70`.`T7044` (
  `F01` int(11) NOT NULL COMMENT '年份',
  `F02` tinyint(4) NOT NULL COMMENT '月份',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成交金额金额',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT '成交笔数',
  `F05` tinyint(4) NOT NULL COMMENT '期限（1月..36月）',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `F07` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否为按天借款,S:是;F:否',
  PRIMARY KEY (`F01`,`F02`,`F05`,`F07`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='成交数据统计-按月-按期限';

-- ----------------------------
-- Table structure for T7045
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7045`;
CREATE TABLE `S70`.`T7045` (
  `F01` int(11) NOT NULL COMMENT '年份',
  `F02` tinyint(4) NOT NULL COMMENT '月份',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成交金额金额',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT '成交笔数',
  `F05` mediumint(6) unsigned NOT NULL COMMENT '地域代码',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`,`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='成交数据统计-按月-按地域';

-- ----------------------------
-- Table structure for T7046
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7046`;
CREATE TABLE `S70`.`T7046` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动自增ID',
  `F02` varchar(15) DEFAULT NULL COMMENT '活动主题',
  `F03` datetime DEFAULT NULL COMMENT '开始时间',
  `F04` datetime DEFAULT NULL COMMENT '结束时间',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '发布人,参考T7011.F01',
  `F06` text COMMENT '活动描述',
  `F07` decimal(20,2) DEFAULT '0.00' COMMENT '活动费用',
  `F08` int(11) DEFAULT '0' COMMENT '参与人数',
  `F09` int(10) unsigned DEFAULT '0' COMMENT '受益人数',
  `F10` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动信息表';

-- ----------------------------
-- Table structure for T7047
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7047`;
CREATE TABLE `S70`.`T7047` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动-参与人列表自增ID',
  `F02` int(11) unsigned DEFAULT NULL COMMENT '活动ID,参考T7046.F01',
  `F03` int(11) unsigned DEFAULT NULL COMMENT '参与人ID,参考T6010.F01',
  `F04` decimal(20,2) DEFAULT '0.00' COMMENT '奖励金额',
  `F05` enum('F','S') DEFAULT 'S' COMMENT '是否受益,S:是;F:否',
  `F06` datetime DEFAULT NULL COMMENT '参与时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='活动-参与人列表';

-- ----------------------------
-- Table structure for T7048
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7048`;
CREATE TABLE `S70`.`T7048` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累计计划总金额',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '为用户累计赚取',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累计实际总金额',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT '加入总人数',
  `F05` decimal(3,3) NOT NULL DEFAULT '0.000' COMMENT '加权平均收益率',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优选理财计划-统计';

-- ----------------------------
-- Table structure for T7049
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7049`;
CREATE TABLE `S70`.`T7049` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '线下充值记录表自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '用户ID,参考T6010.F01',
  `F03` datetime NOT NULL COMMENT '时间',
  `F04` int(10) unsigned NOT NULL COMMENT '创建人,参考T7011.F01',
  `F05` enum('YQX','YCZ','DSH') NOT NULL DEFAULT 'DSH' COMMENT '充值状态，DSH：待审核；YCZ：已充值；YQX：已取消',
  `F06` decimal(20,2) NOT NULL COMMENT '金额',
  `F07` varchar(200) DEFAULT NULL COMMENT '备注',
  `F08` int(10) unsigned DEFAULT NULL COMMENT '审核人ID,参考T7011.F01',
  `F09` datetime DEFAULT NULL COMMENT '审核时间',
  `F10` varchar(200) DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F04` (`F04`),
  KEY `F05` (`F05`),
  KEY `F08` (`F08`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线下充值记录表';

-- ----------------------------
-- Table structure for T7050
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7050`;
CREATE TABLE `S70`.`T7050` (
  `F01` int(10) unsigned NOT NULL COMMENT '账号ID,参考T6110.F01',
  `F02` varchar(20) NOT NULL COMMENT '登录账号',
  `F03` decimal(20,2) NOT NULL COMMENT '投资总额',
  `F04` varchar(10) NOT NULL COMMENT '周(yyyy+周次)',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  UNIQUE KEY `idx_user` (`F01`,`F04`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户投资排行表 -按周';

-- ----------------------------
-- Table structure for T7051
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7051`;
CREATE TABLE `S70`.`T7051` (
  `F01` int(10) unsigned NOT NULL COMMENT '账号ID,参考T6110.F01',
  `F02` varchar(20) NOT NULL COMMENT '登录账号',
  `F03` decimal(20,2) NOT NULL COMMENT '投资总额',
  `F04` varchar(10) NOT NULL COMMENT '月(yyyyMM)',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  UNIQUE KEY `idx_user` (`F01`,`F04`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户投资排行表 -按月';

-- ----------------------------
-- Table structure for T7052
-- ----------------------------
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

-- ----------------------------
-- Table structure for T7053
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7053`;
CREATE TABLE `S70`.`T7053` (
  `F01` int(4) unsigned NOT NULL COMMENT '年份',
  `F02` int(2) unsigned NOT NULL COMMENT '月份',
  `F03` int(11) NOT NULL DEFAULT 0 COMMENT '人数',
  `F04` decimal(20,2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='逾期数据统计';

-- ----------------------------
-- Table structure for T7100
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7100`;
CREATE TABLE `S70`.`T7100` (
  `F01` char(18) NOT NULL COMMENT '身份证号',
  `F02` varchar(20) NOT NULL COMMENT '姓名',
  `F03` enum('SB','TG') NOT NULL COMMENT '认证状态,TG:通过;SB:失败;',
  `F04` mediumblob COMMENT '图像',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实名认证信息库';

-- ----------------------------
-- Table structure for T7100_1
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7100_1`;
CREATE TABLE `S70`.`T7100_1` (
  `F01` char(18) NOT NULL COMMENT '身份证号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实名认证信息锁';

-- ----------------------------
-- Table structure for T7101
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7101`;
CREATE TABLE `S70`.`T7101` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` char(18) NOT NULL COMMENT '身份证号',
  `F03` varchar(20) NOT NULL COMMENT '姓名',
  `F04` enum('SB','TG') NOT NULL COMMENT '认证结果,TG:认证通过;SB:认证失败;',
  `F05` int(11) NOT NULL COMMENT '错误代码',
  `F06` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '认证时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实名认证日志';

-- ----------------------------
-- Table structure for T7102
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7102`;
CREATE TABLE `S70`.`T7102` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(100) NOT NULL COMMENT 'KEY值',
  `F03` varchar(100) NOT NULL COMMENT '常量名称',
  `F04` text NOT NULL COMMENT '修改前值',
  `F05` text COMMENT '修改后值',
  `F06` int(11) NOT NULL COMMENT '修改人ID,参考T7011表ID',
  `F07` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='修改系统常量日志表';

-- ----------------------------
-- Table structure for T7106
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7106`;
CREATE TABLE `S70`.`T7106` (
  `F01` int(10) unsigned NOT NULL COMMENT '合同id，参考T70',
  `F02` varchar(14) NOT NULL COMMENT '企业编号，参考T6110.F07',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业与合同关系表';

-- ----------------------------
-- Table structure for T7201
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7201`;
CREATE TABLE `S70`.`T7201` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `F02` int(10) NOT NULL COMMENT '后台账号(关联T7011.F01)',
  `F03` int(10) NOT NULL COMMENT '关联ID',
  `F04` datetime NOT NULL COMMENT '操作时间',
  `F05` varchar(200) DEFAULT NULL COMMENT '操作描述',
  `F06` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `F07` varchar(20) DEFAULT NULL COMMENT '访问IP',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='后台操作日志表';

-- ----------------------------
-- Table structure for T7202
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7202`;
CREATE TABLE `S70`.`T7202` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '可用金额总额',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '冻结总金额',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额总额',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '借款负债总额 ',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法人用户资金统计表';


DROP TABLE IF EXISTS `S70`.`T7203`;
CREATE TABLE `S70`.`T7203` (
  `F01` int(11) NOT NULL DEFAULT '0' COMMENT '年',
  `F02` int(11) NOT NULL DEFAULT '0' COMMENT '月',
  `F03` decimal(20,2) DEFAULT '0.00' COMMENT '投资金额',
  `F04` decimal(20,2) DEFAULT '0.00' COMMENT '债权转让盈亏',
  `F05` decimal(20,2) DEFAULT '0.00' COMMENT '已收本金',
  `F06` decimal(20,2) DEFAULT '0.00' COMMENT '已收利息',
  `F07` decimal(20,2) DEFAULT '0.00' COMMENT '已收罚息',
  `F08` decimal(20,2) DEFAULT '0.00' COMMENT '已收违约金',
  `F09` decimal(20,2) DEFAULT '0.00' COMMENT '待收本金',
  `F10` decimal(20,2) DEFAULT '0.00' COMMENT '待收利息',
  `F11` decimal(20,2) DEFAULT '0.00' COMMENT '待收罚息',
  `F12` int(11) NOT NULL COMMENT '用户ID，T6110.F01',
  `F13` decimal(20,2) DEFAULT '0.00' COMMENT '投资管理费',
  PRIMARY KEY (`F01`,`F02`,`F12`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财统计表';

-- ----------------------------
-- Table structure for T7204
-- ----------------------------
DROP TABLE IF EXISTS `S70`.`T7204`;
CREATE TABLE `S70`.`T7204` (
  `F01` int(4) NOT NULL DEFAULT '0' COMMENT '年',
  `F02` int(2) NOT NULL DEFAULT '0' COMMENT '月',
  `F03` decimal(20,2) DEFAULT '0.00' COMMENT '借款金额',
  `F04` decimal(20,2) DEFAULT '0.00' COMMENT '借款利息',
  `F05` decimal(20,2) DEFAULT '0.00' COMMENT '借款管理费',
  `F06` decimal(20,2) DEFAULT '0.00' COMMENT '已还本金',
  `F07` decimal(20,2) DEFAULT '0.00' COMMENT '已还利息',
  `F08` decimal(20,2) DEFAULT '0.00' COMMENT '已还逾期罚息',
  `F09` decimal(20,2) DEFAULT '0.00' COMMENT '违约金',
  `F10` decimal(20,2) DEFAULT '0.00' COMMENT '待还本金',
  `F11` decimal(20,2) DEFAULT '0.00' COMMENT '待还利息',
  `F12` decimal(20,2) DEFAULT '0.00' COMMENT '待还逾期罚息',
  `F13` int(11) NOT NULL COMMENT '借款用户ID',
  `F14` DECIMAL(20,2) DEFAULT '0.00'  COMMENT '提前还款手续费',
  PRIMARY KEY (`F01`,`F02`,`F13`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='借款统计表';

-- ----------------------------
-- Procedure structure for SP_CONFIG_AMOUNT
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_CONFIG_AMOUNT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_CONFIG_AMOUNT`(IN _key VARCHAR(50), OUT _value DECIMAL(20,2))
BEGIN	
	DECLARE 	_done 		INT 		DEFAULT 0;
	DECLARE 	_cursor 	CURSOR FOR 	SELECT _1010.F02 FROM S10._1010 WHERE _1010.F01 = _key LIMIT 1;
	DECLARE 	CONTINUE 	HANDLER FOR NOT FOUND  SET _value = 0.00;
	
	OPEN _cursor;	
			FETCH _cursor INTO _value;
	CLOSE _cursor;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_CONFIG_INT
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_CONFIG_INT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_CONFIG_INT`(IN _key VARCHAR(50), OUT _value INT)
BEGIN	
	DECLARE 	_done 		INT 		DEFAULT 0;
	DECLARE 	_cursor 	CURSOR FOR 	SELECT _1010.F02 FROM S10._1010 WHERE _1010.F01 = _key LIMIT 1;
	DECLARE 	CONTINUE 	HANDLER FOR NOT FOUND  SET _value = 0;
	
	OPEN _cursor;	
			FETCH _cursor INTO _value;
	CLOSE _cursor;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_CONFIG_RATE
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_CONFIG_RATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_CONFIG_RATE`(IN _key VARCHAR(50), OUT _value DECIMAL(6,6))
BEGIN	
	DECLARE 	_done 		INT 		DEFAULT 0;
	DECLARE 	_cursor 	CURSOR FOR 	SELECT _1010.F02 FROM S10._1010 WHERE _1010.F01 = _key LIMIT 1;
	DECLARE 	CONTINUE 	HANDLER FOR NOT FOUND  SET _value = 0.00;
	
	OPEN _cursor;	
			FETCH _cursor INTO _value;
	CLOSE _cursor;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_MONTH_DATE
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_MONTH_DATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_MONTH_DATE`(IN _current_date DATE, OUT _startDate DATE, OUT _endDate DATETIME)
    COMMENT '计算月份开始和截至日期'
BEGIN 
	SET _current_date = IFNULL(_current_date,CURRENT_DATE());
	SET _startDate = DATE_SUB(_current_date ,INTERVAL DAY(_current_date )-1 DAY); 
	SET _endDate = DATE_FORMAT(CONCAT(LAST_DAY(_startDate),' 23:59:59.999'),'%Y-%m-%d %H:%i:%S.%f'); 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_QUARTER_DATE
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_QUARTER_DATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_QUARTER_DATE`(INOUT _year MEDIUMINT UNSIGNED , INOUT  _quarter TINYINT UNSIGNED, OUT _startDate DATE, OUT _endDate DATE)
    COMMENT '计算季度开始和截至日期'
BEGIN
	CASE _quarter
			-- 第一季度
			WHEN 1 THEN SELECT CONCAT(_year,'01','01') INTO _startDate ;SELECT CONCAT(_year,'03','31') INTO _endDate ;	
			-- 第二季度
			WHEN 2 THEN SELECT CONCAT(_year,'04','01') INTO _startDate ;SELECT CONCAT(_year,'06','30') INTO _endDate ;	
			-- 第三季度
			WHEN 3 THEN SELECT CONCAT(_year,'07','01') INTO _startDate ;SELECT CONCAT(_year,'09','30') INTO _endDate ;	
			-- 第四季度
			ELSE SELECT CONCAT(_year,'10','01') INTO _startDate ;SELECT CONCAT(_year,'12','31') INTO _endDate ;					
	END CASE;
	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T6230
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T6230`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T6230`()
    COMMENT '到期未满标更新状态为待放款'
BEGIN
		DECLARE 	_loan_id      								INT    				 DEFAULT 0;       -- 标ID
		DECLARE   _stop_credit							   	INT   				 DEFAULT 0;				-- 是否达到记录的末尾控制变量
		DECLARE   _error 					            	INT 					 DEFAULT 0;
		DECLARE   _loan_curr CURSOR  FOR  SELECT T6230.F01 FROM S62.T6230 WHERE T6230.F20='TBZ' AND DATE_ADD(T6230.F22,INTERVAL T6230.F08 DAY) <= CURRENT_TIMESTAMP();
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET _stop_credit = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
		BEGIN
				SHOW ERRORS;
				SET _error = 1;
		END;
		SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
		OPEN _loan_curr;
		REPEAT
				FETCH _loan_curr INTO _loan_id;
				IF _stop_credit = 0 THEN
						START TRANSACTION;
						UPDATE S62.T6230 SET F20='DFK' WHERE F01=_loan_id;
						IF _error = 1 THEN
						ROLLBACK;
						SET _error = 0;
						ELSE
							COMMIT;
						END IF;
				END IF;
		UNTIL _stop_credit END REPEAT;
		CLOSE _loan_curr;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7010
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7010`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7010`()
BEGIN
	
	-- 今日注册用户数
	DECLARE _F01 INT unsigned DEFAULT '0';
	-- 历史注册用户总数
	DECLARE _F02 INT unsigned DEFAULT '0';
	-- 今日登录用户数
	DECLARE _F03 INT unsigned DEFAULT '0';
	-- 今日用户总充值
	DECLARE _F04 DECIMAL(20,2) DEFAULT '0.00'; 
	-- 用户历史总充值
	DECLARE _F05 DECIMAL(20,2) DEFAULT '0.00'; 
	-- 今日用户总提现
	DECLARE _F06 DECIMAL(20,2) DEFAULT '0.00'; 
	-- 用户历史总提现
	DECLARE _F07 DECIMAL(20,2) DEFAULT '0.00'; 
	-- 平台总收益
	DECLARE _F08 DECIMAL(20,2) DEFAULT '0.00'; 
	-- 用户投资总收益
	DECLARE _F09 DECIMAL(20,2) DEFAULT '0.00';
	-- 今日用户待还总金额
	DECLARE _F10 DECIMAL(20,2) DEFAULT '0.00';
	-- 用户待还总金额
	DECLARE _F11 DECIMAL(20,2) DEFAULT '0.00';

	-- 用户投资总收益之利息+罚息
	DECLARE _F091 DECIMAL(20,2) DEFAULT '0.00';

	-- 用户投资总收益之债权转入盈亏
	DECLARE _F092 DECIMAL(20,2) DEFAULT '0.00';

	-- 用户投资总收益之债权转出盈亏
	DECLARE _F093 DECIMAL(20,2) DEFAULT '0.00';

	DECLARE _ID INT DEFAULT 0;

	SELECT COUNT(*) INTO _F01 FROM S61.T6110 WHERE TO_DAYS(S61.T6110.F09) = TO_DAYS(NOW())AND NOT EXISTS (SELECT 1 FROM S71.T7101 WHERE T7101.F01 = T6110.F01);
	SELECT COUNT(*) INTO _F02 FROM S61.T6110 WHERE NOT EXISTS (SELECT 1 FROM S71.T7101 WHERE T7101.F01 = T6110.F01);
  	SELECT IFNULL(SUM(T6502.F03),0) INTO _F04 FROM S65.T6502 INNER JOIN S65.T6501 ON T6501.F01=T6502.F01 WHERE T6501.F03='CG' AND TO_DAYS(T6501.F06) = TO_DAYS(NOW());
	SELECT IFNULL(SUM(T6502.F03),0) INTO _F05 FROM S65.T6502 INNER JOIN S65.T6501 ON T6501.F01=T6502.F01 WHERE T6501.F03='CG';
	SELECT IFNULL(SUM(T6503.F03),0) INTO _F06 FROM S65.T6503 INNER JOIN S65.T6501 ON T6501.F01=T6503.F01 WHERE T6501.F03='CG' AND TO_DAYS(T6501.F06) = TO_DAYS(NOW());
	SELECT IFNULL(SUM(T6503.F03),0) INTO _F07 FROM S65.T6503 INNER JOIN S65.T6501 ON T6501.F01=T6503.F01 WHERE T6501.F03='CG';
	SELECT T6101.F01 INTO _ID FROM S71.T7101 INNER JOIN S61.T6101 WHERE T7101.F01=T6101.F02 AND T6101.F03='WLZH' LIMIT 1;
	SELECT IFNULL(SUM(T6102.F06-T6102.F07),0) INTO _F08 FROM S61.T6102 WHERE T6102.F02=_ID AND T6102.F03 NOT IN(1005,2004);

	-- 利息收益
	SELECT IFNULL(SUM(T6102.F06), 0) INTO _F091 FROM S61.T6102 WHERE T6102.F03 IN (7004,7005,7002) AND T6102.F02 IN (SELECT T6101.F01 FROM S61.T6101,S61.T6110 WHERE T6101.F02 = T6110.F01 AND T6101.F03 = 'WLZH');

	-- 转入盈亏
	SELECT IFNULL(SUM(T6262.F08),0) INTO _F092 FROM S62.T6262,S61.T6110 WHERE T6262.F03 = T6110.F01;

	-- 转出盈亏
	SELECT IFNULL(SUM(T6262.F09), 0) INTO _F093 FROM S62.T6262,S62.T6260,S62.T6251,S61.T6110 WHERE T6251.F04 = T6110.F01 AND T6251.F01 = T6260.F02 AND T6260.F01 = T6262.F02;

	 SELECT (_F091+_F092+_F093) INTO _F09;

	SELECT IFNULL(SUM(F07),0) INTO _F10 FROM S62.T6252 WHERE F09='WH' AND TO_DAYS(F08) <= TO_DAYS(NOW());
	SELECT IFNULL(SUM(F07),0) INTO _F11 FROM S62.T6252 WHERE F09='WH'; 
	
	UPDATE S70.T7010 SET F01 = _F01,F02 = _F02,F03 = _F03,F04 = _F04,F05 = _F05,F06 = _F06,F07 = _F07,F08 = _F08,F09 = _F09, F10 = _F10, F11 = _F11;
	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7022
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7022`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7022`()
BEGIN
			-- 成功充值总额
		DECLARE _F03 DECIMAL(20,2) DEFAULT 0.00;
		-- 成功充值手续费
		DECLARE _F05 DECIMAL(20,2) DEFAULT 0.00;
		SELECT SUM(F03),SUM(F05) INTO _F03,_F05 FROM S65.T6502 WHERE F08 IS NOT NULL;
		UPDATE S70.T7022 SET F01=_F03,F02=_F05;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7024
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7024`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7024`()
    COMMENT '用户资金统计表'
BEGIN
	-- 用户资金统计表
	-- 可用金额总额
	DECLARE _F01 DECIMAL(20,2) DEFAULT 0.00;
	-- 冻结总金额
	DECLARE _F02 DECIMAL(20,2) DEFAULT 0.00;
	-- 账户余额总额
	DECLARE _F03 DECIMAL(20,2) DEFAULT 0.00;
	-- 用户总收益
	DECLARE _F04 DECIMAL(20,2) DEFAULT 0.00;
	-- 借款负债总额
	DECLARE _F05 DECIMAL(20,2) DEFAULT 0.00;
	-- 优选理财资产总额
	DECLARE _F06 DECIMAL(20,2) DEFAULT 0.00;
	-- 债权资产总额
	DECLARE _F07 DECIMAL(20,2) DEFAULT 0.00;

	DECLARE _F04_1 DECIMAL(20,2) DEFAULT 0.00;
	DECLARE _F04_2 DECIMAL(20,2) DEFAULT 0.00;



	SELECT IFNULL(SUM(F04),0) INTO _F02  FROM S61.T6101  WHERE F03 = 'SDZH';
	SELECT IFNULL(SUM(F04),0) INTO _F03  FROM S61.T6101  WHERE F03 = 'WLZH';
	
	SELECT IFNULL(SUM(F07),0) INTO _F04_1  FROM S62.T6252  WHERE F05 IN (7002,7004,7005) AND F09 = 'YH';
	SELECT IFNULL(SUM(F07),0) INTO _F04_2  FROM S64.T6412  WHERE F05 = 1306 AND F09 = 'YH';
	SET _F04 =  _F04_1 + _F04_2;

  SELECT IFNULL(SUM(F07),0) INTO _F05 FROM S62.T6252 WHERE F05 IN (7001,7002,7003,7004,7005) AND F09 = 'WH';

	SELECT IFNULL(SUM(F05),0) INTO _F06 FROM S64.T6411;
	
	SELECT IFNULL(SUM(F07),0) INTO _F07 FROM S62.T6251;

	UPDATE S70.T7024 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 , F06 = _F06 ,F07 = _F07; 
	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7036
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7036`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7036`(IN _date DATE)
    COMMENT '平台资金统计-按月度'
BEGIN 
	DECLARE	 _F01	 INT UNSIGNED	 DEFAULT	0;	
	DECLARE	 _F02	 INT UNSIGNED	 DEFAULT	0;	
	DECLARE	 _F03	 DECIMAL(20,2) DEFAULT 0.00; 
	DECLARE	 _F04	 DECIMAL(20,2) DEFAULT 0.00; 
	DECLARE	 _F05	 DECIMAL(20,2) DEFAULT 0.00; 
	DECLARE	 _ZJZHID	 INT UNSIGNED	 DEFAULT	0;	
	DECLARE	 _startDate	 DATE; 
	DECLARE	 _endDate	 DATE; 
	DECLARE	 _current_date	 DATE; 
	SET _current_date = IFNULL(_date,CURRENT_DATE()); 
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date); 
	CALL SP_ROSES_DATE(_current_date,_startDate,_endDate); 
	SELECT F01 INTO _ZJZHID FROM S61.T6101 WHERE F02 = (SELECT F01 FROM S71.T7101 LIMIT 1) AND F03 = 'WLZH' LIMIT 1; 
	SELECT IFNULL(SUM(T6102.F06),0),IFNULL(SUM(T6102.F07),0) INTO _F03 , _F04	 FROM S61.T6102 
	WHERE T6102.F05 >= _startDate AND DATE(T6102.F05) <= _endDate AND T6102.F02 =_ZJZHID AND T6102.F03 NOT IN(1005,2004); 
	SET _F05 = _F03 - _F04; 
	INSERT INTO S70.T7036 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04, F05 = _F05 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04),F05 = VALUES(F05); 
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7037
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7037`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7037`(IN _date DATE)
    COMMENT '平台资金统计-按季度'
BEGIN 


	DECLARE	 _F01	 INT UNSIGNED	 DEFAULT	0;	

	DECLARE	 _F02	 INT UNSIGNED	 DEFAULT	0;	

	DECLARE	 _F03	 DECIMAL(20,2) DEFAULT 0.00; 

	DECLARE	 _F04	 DECIMAL(20,2) DEFAULT 0.00; 

	DECLARE	 _F05	 DECIMAL(20,2) DEFAULT 0.00; 

	DECLARE	 _ZJZHID	 INT UNSIGNED	 DEFAULT	0;	
	DECLARE	 _startDate	 DATE; 
	DECLARE	 _endDate	 DATE; 
	DECLARE	 _current_date	 DATE; 
	SET _current_date = IFNULL(_date,CURRENT_DATE()); 
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date); 
	SET _F02 = QUARTER(_current_date); 
	CALL SP_QUARTER_DATE(_F01,_F02,_startDate,_endDate); 

	SELECT F01 INTO _ZJZHID FROM S61.T6101 WHERE F02 = (SELECT F01 FROM S71.T7101 LIMIT 1) AND F03 = 'WLZH' LIMIT 1; 

	SELECT IFNULL(SUM(T6102.F06),0),IFNULL(SUM(T6102.F07),0) INTO _F03 , _F04	 FROM S61.T6102 
	WHERE T6102.F05 >= _startDate AND DATE(T6102.F05) <= _endDate AND T6102.F02 =_ZJZHID AND T6102.F03 NOT IN(1005,2004); 

	SET _F05 = _F03 - _F04; 
	INSERT INTO S70.T7037 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04, F05 = _F05 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04),F05 = VALUES(F05); 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7038
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7038`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7038`(IN _date DATE)
    COMMENT '平台资金统计-按年度'
BEGIN
	-- 平台资金统计-按年度
	-- 年份
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- 充值手续费
	DECLARE		_F03							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 提现手续费
	DECLARE		_F04							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 充值成本
	DECLARE		_F05							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 提现成本
	DECLARE		_F06							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 身份验证手续费
	DECLARE		_F07							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 借款管理费
	DECLARE		_F08							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 逾期管理费
	DECLARE		_F09							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 债权转让费
	DECLARE		_F10							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 活动费用
	DECLARE		_F11							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 理财管理费
	DECLARE		_F12							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 持续推广费用
	DECLARE		_F13							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 有效推广费用
	DECLARE		_F14							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 违约金手续费
	DECLARE		_F15							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 本金垫付返还
	DECLARE		_F16							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 利息垫付返还
	DECLARE		_F17							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 罚息垫付返还
	DECLARE		_F18							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 本金垫付支出
	DECLARE		_F19							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 利息垫付支出
	DECLARE		_F20							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 罚息垫付支出
	DECLARE		_F21							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 加息奖励费用
	DECLARE		_F22							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 体验金投资费用
	DECLARE		_F23							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 红包奖励费用
	DECLARE		_F24							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 奖励标奖励费用
	DECLARE		_F25							DECIMAL(20,2) 			 DEFAULT 	0.00;
	-- 线下充值
	DECLARE		_F26							DECIMAL(20,2) 			 DEFAULT 	0.00;
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATE;
	DECLARE		_current_date				DATE;
	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	CALL SP_YEAR_DATE(_F01,_startDate,_endDate);
	
	SELECT IFNULL(SUM(F07),0) INTO _F03  FROM S61.T6102 WHERE  F03 = 1002 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F06),0) INTO _F04  FROM S61.T6130 WHERE  F09 = 'YFK' AND F08 >= _startDate AND DATE(F08) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F05  FROM S61.T6102 WHERE  F03 = 1003 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(T6102.F07),0) INTO _F06  FROM S61.T6102 INNER JOIN S61.T6101 ON T6101.F01 = T6102.F02 INNER JOIN S61.T6110 ON T6101.F02 = T6110.F01 WHERE  T6102.F03 = 2003 AND T6110.F01 = (SELECT T7101.F01 FROM S71.T7101) AND T6102.F05 >= _startDate AND DATE(T6102.F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F07  FROM S61.T6102 WHERE  F03 = 1501 AND	F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F06),0) INTO _F08  FROM S61.T6102 WHERE  F03 = 1201 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F09  FROM S61.T6102 WHERE  F03 = 7003 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F10  FROM S61.T6102 WHERE  F03 = 4001 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F11  FROM S61.T6102 WHERE  F03 = 8001 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F12  FROM S61.T6102 WHERE  F03 = 1202 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F13  FROM S61.T6102 WHERE  F03 = 9002 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F14  FROM S61.T6102 WHERE  F03 = 9001 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F15  FROM S61.T6102 WHERE  F03 = 7007 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(T6253.F06),0) INTO _F21 FROM S62.T6253 JOIN S62.T6255 ON T6253.F01=T6255.F02 WHERE T6255.F05=7001 AND T6253.F07 >= _startDate AND DATE(T6253.F07) <= _endDate;
	SELECT IFNULL(SUM(T6253.F06),0) INTO _F21 FROM S62.T6253 JOIN S62.T6255 ON T6253.F01=T6255.F02 WHERE T6255.F05=7002 AND T6253.F07 >= _startDate AND DATE(T6253.F07) <= _endDate;
	SELECT IFNULL(SUM(T6253.F06),0) INTO _F21 FROM S62.T6253 JOIN S62.T6255 ON T6253.F01=T6255.F02 WHERE T6255.F05 IN (7003,7004) AND T6253.F07 >= _startDate AND DATE(T6253.F07) <= _endDate;
	SELECT IFNULL(SUM(T6253.F05),0) INTO _F21 FROM S62.T6253 JOIN S62.T6255 ON T6253.F01=T6255.F02 WHERE T6255.F05=7001 AND T6253.F07 >= _startDate AND DATE(T6253.F07) <= _endDate;
	SELECT IFNULL(SUM(T6253.F05),0) INTO _F21 FROM S62.T6253 JOIN S62.T6255 ON T6253.F01=T6255.F02 WHERE T6255.F05=7002 AND T6253.F07 >= _startDate AND DATE(T6253.F07) <= _endDate;
	SELECT IFNULL(SUM(T6253.F05),0) INTO _F21 FROM S62.T6253 JOIN S62.T6255 ON T6253.F01=T6255.F02 WHERE T6255.F05 IN (7003,7004) AND T6253.F07 >= _startDate AND DATE(T6253.F07) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F22  FROM S61.T6102 WHERE  F03 = 7010 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F23  FROM S61.T6102 WHERE  F03 = 3003 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F24  FROM S61.T6102 WHERE  F03 = 7009 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F25  FROM S61.T6102 WHERE  F03 = 7008 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	SELECT IFNULL(SUM(F07),0) INTO _F26  FROM S61.T6102 WHERE  F03 = 1004 AND F05 >= _startDate AND DATE(F05) <= _endDate;
	
	INSERT INTO S70.T7038 SET F01 = _F01, F03 = _F03, F04 = _F04, F05 = _F05 ,F06 = _F06, F07 = _F07, F08 = _F08, F09 = _F09, F10 = _F10, F11 = _F11, F12 = _F12,F13 = _F13, F14 = _F14, F15 = _F15, 
	F16 = _F16, F17 = _F17, F18 = _F18, F19 = _F19, F20 = _F20, F21 = _F21, F22 = _F22, F23 = _F23, F24 = _F24, F25 = _F25, F26 = _F26 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04),F05 = VALUES(F05),F06 = VALUES(F06) , F07 = VALUES(F07),
	F08 = VALUES(F08),F09 = VALUES(F09) , F10 = VALUES(F10), F11 = VALUES(F11), F12 = VALUES(F12), F13 = VALUES(F13), F14 = VALUES(F14), 
	F15 = VALUES(F15), F16 = VALUES(F16), F17 = VALUES(F17), F18 = VALUES(F18), F19 = VALUES(F19), F20 = VALUES(F20), F21 = VALUES(F21), 
	F22 = VALUES(F22), F23 = VALUES(F23), F24 = VALUES(F24), F25 = VALUES(F25), F26 = VALUES(F26);
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7039
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7039`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7039`(IN _date DATE)
    COMMENT '风险保证金统计-按季度'
BEGIN 


	DECLARE	 _F01	 INT UNSIGNED	 DEFAULT	0;	

	DECLARE	 _F02	 INT UNSIGNED	 DEFAULT	0;	

	DECLARE	 _F03	 DECIMAL(20,2) DEFAULT 0.00;	

	DECLARE	 _F04	 DECIMAL(20,2) DEFAULT 0.00;	

	DECLARE	 _F05	 DECIMAL(20,2) DEFAULT 0.00;	

	DECLARE	 _F07	 INT UNSIGNED	 DEFAULT 0;	


	DECLARE	 _startDate	 DATE; 
	DECLARE	 _endDate	 DATE; 
	DECLARE	 _current_date	 DATE; 

	DECLARE _done INT UNSIGNED	 DEFAULT 0; 
	DECLARE _money_list CURSOR FOR SELECT T6125.F02 FROM S61.T6125 T6125 INNER JOIN S61.T6110 T6110 ON T6125.F02 = T6110.F01 WHERE S61.T6125.F05 = 'SQCG' OR 'QXCG';
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1; 

	SET _current_date = IFNULL(_date,CURRENT_DATE()); 
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date); 
	SET _F02 = QUARTER(_current_date); 
	CALL SP_QUARTER_DATE(_F01,_F02,_startDate,_endDate); 

	OPEN _money_list; 

	REPEAT 
	FETCH _money_list INTO _F07; 

	IF NOT _done THEN 

	SELECT IFNULL(SUM(T6102.F06),0),IFNULL(SUM(T6102.F07),0) INTO _F03,_F04 FROM S61.T6102, S61.T6101 WHERE T6102.F02 = T6101.F01 
	AND T6101.F03 = 'FXBZJZH' AND T6102.F05>=_startDate AND DATE(T6102.F05)<= _endDate AND T6101.F02 = _F07; 

	SET _F05 = _F03 - _F04; 


	INSERT INTO S70.T7039 SET F01 = _F01, F02 = _F02,F03 = _F03, F04 = _F04,F05 = _F05 ,F07 = _F07 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03), F04 = VALUES(F04),F05 = VALUES(F05); 
	END IF; 
	UNTIL _done END REPEAT; 

	CLOSE _money_list; 

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7040
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7040`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7040`(IN _date DATE)
    COMMENT '风险保证金统计-按年度'
BEGIN
	-- 风险保证金统计-按年度
	-- 年份
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- 垫付
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	-- 垫付返还
	DECLARE		_F04							DECIMAL(20,2) DEFAULT 0.00;	
	-- 借款成交服务费
	DECLARE		_F05							DECIMAL(20,2) DEFAULT 0.00;	
	-- 手动增加保证金
	DECLARE		_F06							DECIMAL(20,2) DEFAULT 0.00;	
	-- 手动扣除保证金
	DECLARE		_F07							DECIMAL(20,2) DEFAULT 0.00;	
	-- 机构ID 如果为0代表平台
	DECLARE		_F08						INT 					UNSIGNED		DEFAULT 0;	
	

	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATE;
	DECLARE		_current_date		DATE;
	-- 列表游标定义
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_money_list 			CURSOR FOR 		SELECT F01 FROM S70.T7029;
	DECLARE 	CONTINUE 				HANDLER FOR NOT FOUND SET _done = 1;

	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	CALL SP_YEAR_DATE(_F01,_startDate,_endDate);

	-- DF:垫付;DFHF:垫付返还;JKCJFWF:借款成交服务费;SDZJBZJ:手动增加保证金;SDKCBZJ:手动扣除保证金;
	SET _F08 = 0 ;
	SELECT IFNULL(SUM(F04),0) INTO _F03 FROM S70.T7028 WHERE F06 = 'DF' AND F02>=_startDate AND DATE(F02)<= _endDate;
	SELECT IFNULL(SUM(F03),0) INTO _F04 FROM S70.T7028 WHERE F06 = 'DFHF' AND F02>=_startDate AND DATE(F02)<= _endDate;
	SELECT IFNULL(SUM(F03),0) INTO _F05 FROM S70.T7028 WHERE F06 = 'JKCJFWF' AND F02>=_startDate AND DATE(F02)<= _endDate;
	SELECT IFNULL(SUM(F03),0) INTO _F06 FROM S70.T7028 WHERE F06 = 'SDZJBZJ' AND F02>=_startDate AND DATE(F02)<= _endDate;
	SELECT IFNULL(SUM(F04),0) INTO _F07 FROM S70.T7028 WHERE F06 = 'SDKCBZJ' AND F02>=_startDate AND DATE(F02)<= _endDate;

	-- 插入统计数据
	INSERT INTO S70.T7040 SET F01 = _F01, F03 = _F03, F04 = _F04,F05 = _F05,F06 = _F06 ,F07 = _F07 ,F08 = _F08
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03), F04 = VALUES(F04),F05 = VALUES(F05),F06 = VALUES(F06), F07 = VALUES(F07),F08 = VALUES(F08);

	OPEN _money_list;
	
	REPEAT 
		FETCH _money_list INTO _F08;
			
		IF NOT _done THEN
				SELECT IFNULL(SUM(F05),0) INTO _F03 FROM S70.T7030 WHERE F02 = _F08 AND F07 = 'DF' AND F03>=_startDate AND DATE(F03)<= _endDate;
				SELECT IFNULL(SUM(F04),0) INTO _F04 FROM S70.T7030 WHERE F02 = _F08 AND F07 = 'DFHF' AND F03>=_startDate AND DATE(F03)<= _endDate;
				SELECT IFNULL(SUM(F04),0) INTO _F05 FROM S70.T7030 WHERE F02 = _F08 AND F07 = 'JKCJFWF' AND F03>=_startDate AND DATE(F03)<= _endDate;
				SELECT IFNULL(SUM(F04),0) INTO _F06 FROM S70.T7030 WHERE F02 = _F08 AND F07 = 'SDZJBZJ' AND F03>=_startDate AND DATE(F03)<= _endDate;
				SELECT IFNULL(SUM(F05),0) INTO _F07 FROM S70.T7030 WHERE F02 = _F08 AND F07 = 'SDKCBZJ' AND F03>=_startDate AND DATE(F03)<= _endDate;
				SELECT _F03,_F04,_F05,_F06,_F07;
				-- 插入统计数据
				INSERT INTO S70.T7040 SET F01 = _F01, F03 = _F03, F04 = _F04,F05 = _F05,F06 = _F06 ,F07 = _F07 ,F08 = _F08
				ON DUPLICATE KEY UPDATE F03 = VALUES(F03), F04 = VALUES(F04),F05 = VALUES(F05),F06 = VALUES(F06), F07 = VALUES(F07),F08 = VALUES(F08);

		END IF;
		UNTIL _done END REPEAT;

	CLOSE _money_list;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7041
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7041`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7041`(IN _date DATE)
    COMMENT '统计T7041,充值提现数据统计-按月'
BEGIN
	-- 充值提现数据统计-按月
	-- 年份
	DECLARE _F01	INT	UNSIGNED	DEFAULT	0; 
	-- 月份
	DECLARE _F02	INT DEFAULT 0;
	-- 充值金额
	DECLARE _F03 	DECIMAL(20,2) DEFAULT 0.00;
	-- 提现金额
	DECLARE _F04 	DECIMAL(20,2)	DEFAULT	0.00;        
	-- 充值笔数
	DECLARE _F05 	INT UNSIGNED	DEFAULT 0;
	-- 提现笔数
	DECLARE _F06 	INT UNSIGNED	DEFAULT 0;
	-- 线上充值金额
	DECLARE xs_F03 	DECIMAL(20,2) DEFAULT 0.00;
	-- 线下充值金额
	DECLARE xx_F03 	DECIMAL(20,2) DEFAULT 0.00;
	-- 线上充值笔数
	DECLARE xs_F05 	INT UNSIGNED	DEFAULT 0;
	-- 线下充值笔数
	DECLARE xx_F05 	INT UNSIGNED	DEFAULT 0;
	-- 当前日期
	DECLARE		_current_date		DATE;
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;

	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	

	SELECT IFNULL(SUM(T6502.F03),0),COUNT(*) INTO xs_F03,xs_F05 FROM S65.T6501 INNER JOIN S65.T6502 ON T6501.F01 = T6502.F01 WHERE DATE(T6501.F06) <= _endDate AND DATE(T6501.F06) >= _startDate AND T6501.F03 = 'CG' AND T6502.F02 <> (SELECT F01 FROM S71.T7101 LIMIT 1);
	SELECT IFNULL(SUM(T7150.F04),0),COUNT(*) INTO xx_F03,xx_F05 FROM S71.T7150 WHERE DATE(T7150.F10) <= _endDate AND DATE(T7150.F10) >= _startDate AND T7150.F05='YCZ' AND T7150.F02 <> (SELECT F01 FROM S71.T7101 LIMIT 1);
	SET _F03 = xs_F03+xx_F03;
	SET _F05 = xs_F05+xx_F05;

	SELECT IFNULL(SUM(T6503.F03),0),COUNT(T6503.F01) INTO _F04,_F06 FROM S65.T6503 INNER JOIN S65.T6501 ON T6503.F01=T6501.F01  WHERE DATE(T6501.F06)<= _endDate AND DATE(T6501.F06) >= _startDate AND  T6501.F03 = 'CG' AND T6503.F02 <> (SELECT F01 FROM S71.T7101 LIMIT 1);

	INSERT INTO S70.T7041 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04, F05 = _F05, F06 = _F06 ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04),F05 = VALUES(F05),F06 = VALUES(F06);

	END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7042
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7042`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7042`(IN _date DATE)
    COMMENT '成交数据统计-按月'
BEGIN
	-- 成交数据统计-按月			
	-- 年份
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- 月份
	DECLARE		_F02							VARCHAR(2) 		;	
	-- 成交金额
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	-- 成交笔数
	DECLARE		_F04							INT 					UNSIGNED		DEFAULT 0;	
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;
	DECLARE		_current_date				DATE;
	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	IF _F02<10 THEN
		SET _F02 = CONCAT('0',_F02);
	END IF;
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	
	SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01) INTO _F03,_F04  FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01  WHERE T6230.F20 IN ('YDF','YJQ','HKZ','YZR') AND T6231.F12 >= _startDate AND T6231.F12 <= _endDate;
	INSERT INTO S70.T7042 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7043
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7043`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7043`(IN _date DATE)
    COMMENT '成交数据统计-按月-按类型'
BEGIN
	-- 成交数据统计-按月-按类型	
	-- 年份
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- 月份
	DECLARE		_F02							INT 					UNSIGNED		DEFAULT	0;	
	-- 成交金额
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	-- 成交笔数
	DECLARE		_F04							INT 					UNSIGNED		DEFAULT 0;	
	-- 类型
	DECLARE		_F05							VARCHAR(20); 
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;
	DECLARE		_current_date				DATE;
	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	
	SET _F05 = 'XYRZ';
	SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01) INTO _F03,_F04	 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F33 ='S' AND T6230.F20 IN ('YDF','YJQ','HKZ','YZR') AND T6231.F12 >= _startDate AND T6231.F12 <= _endDate;
	INSERT INTO S70.T7043 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);

	SET _F05 = 'JGDB';
	SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01) INTO _F03,_F04	 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F11 ='S' AND T6230.F20 IN ('YDF','YJQ','HKZ','YZR') AND T6231.F12 >= _startDate AND T6231.F12 <= _endDate;
	INSERT INTO S70.T7043 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);

	SET _F05 = 'SDRZ';
	SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01) INTO _F03,_F04	 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F14 ='S' AND T6230.F20 IN ('YDF','YJQ','HKZ','YZR') AND T6231.F12 >= _startDate AND T6231.F12 <= _endDate;
	INSERT INTO S70.T7043 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);

	SET _F05 = 'DYRZ';
	SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01) INTO _F03,_F04	 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01 WHERE T6230.F13 ='S' AND T6230.F20 IN ('YDF','YJQ','HKZ','YZR') AND T6231.F12 >= _startDate AND T6231.F12 <= _endDate;
	INSERT INTO S70.T7043 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7044
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7044`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7044`(IN _date DATE)
    COMMENT '成交数据统计-按月-按期限'
BEGIN
	-- 成交数据统计-按月-按期限	
	-- 年份
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- 月份
	DECLARE		_F02							INT 					UNSIGNED		DEFAULT	0;	
	-- 成交金额
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	-- 成交笔数
	DECLARE		_F04							INT 					UNSIGNED		DEFAULT 0;	
	-- 期限
	DECLARE		_F05							INT 					UNSIGNED		DEFAULT 0;	
	-- 是否按天计算
	DECLARE		_F06							VARCHAR(32)				DEFAULT 'F';
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;
	DECLARE		_current_date				DATE;
	-- 借款列表游标定义
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_loan_list 			CURSOR FOR 		SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(T6230.F01),CASE WHEN T6231.F21='S' THEN T6231.F22 ELSE T6230.F09 END,T6231.F21 FROM S62.T6230 INNER JOIN S62.T6231 ON T6230.F01 = T6231.F01 
	WHERE T6230.F20 IN('HKZ','YJQ','YDF','YZR') AND T6231.F12>=_startDate AND T6231.F12<=_endDate GROUP BY T6230.F09;
	DECLARE 	CONTINUE 				HANDLER FOR NOT FOUND SET _done = 1;

	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	OPEN _loan_list;
	
	REPEAT 
		FETCH _loan_list INTO _F03,_F04,_F05,_F06;
			
		IF NOT _done THEN
				INSERT INTO S70.T7044 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 ,F07=_F06
				ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);
		END IF;
	UNTIL _done END REPEAT;

	CLOSE _loan_list;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7045
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7045`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7045`(IN _date DATE)
    COMMENT '成交数据统计-按月-按地域'
BEGIN
	-- 成交数据统计-按月-按地域
	-- 年份
	DECLARE		_F01							INT 					UNSIGNED		DEFAULT	0;	
	-- 月份
	DECLARE		_F02							INT 					UNSIGNED		DEFAULT	0;	
	-- 成交金额
	DECLARE		_F03							DECIMAL(20,2) DEFAULT 0.00;	
	-- 成交笔数
	DECLARE		_F04							INT 					UNSIGNED		DEFAULT 0;	
	-- 地域代码
	DECLARE		_F05							INT 					UNSIGNED		DEFAULT 0;	
						
	DECLARE		_startDate			DATE;
	DECLARE		_endDate				DATETIME;
	DECLARE		_current_date				DATE;
	-- 借款列表游标定义
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;
	DECLARE 	_loan_list 			CURSOR FOR 		SELECT IFNULL(SUM(T6230.F05-T6230.F07),0),COUNT(*),T6231.F07 FROM S62.T6230,S62.T6231 WHERE  T6230.F20 IN('HKZ','YJQ','YDF','YZR') AND T6231.F12>=_startDate AND T6231.F12<=_endDate AND T6230.F01=T6231.F01 GROUP BY T6231.F07;
	DECLARE 	CONTINUE 				HANDLER FOR NOT FOUND SET _done = 1;

	SET _current_date = IFNULL(_date,CURRENT_DATE());
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date);
	CALL SP_MONTH_DATE(_current_date,_startDate,_endDate);
	OPEN _loan_list;
	
	REPEAT 
		FETCH _loan_list INTO _F03,_F04,_F05;
			
		IF NOT _done THEN
				INSERT INTO S70.T7045 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 ,F05 = _F05 
				ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04), F05 = VALUES(F05);
		END IF;
	UNTIL _done END REPEAT;

	CLOSE _loan_list;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7048
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7048`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7048`()
    MODIFIES SQL DATA
    SQL SECURITY INVOKER
    COMMENT '按天,优选理财计划统计'
BEGIN

	DECLARE _F01	DECIMAL(20,2) DEFAULT 0.00;

	DECLARE _F02	DECIMAL(20,2) DEFAULT 0.00;

	DECLARE _F03 	DECIMAL(20,2) DEFAULT 0.00;

	DECLARE _F04 	INT	UNSIGNED	DEFAULT	0;         
	DECLARE _TOTAL DECIMAL(20,2) DEFAULT 0.00;

	SELECT IFNULL(SUM(F03),0),IFNULL(SUM(F03-F04),0),IFNULL(SUM(F03*F05),0) INTO _F01,_F03,_TOTAL FROM S64.T6410 WHERE F07 = 'YSX' OR F07 = 'YJZ';
	SELECT IFNULL(SUM(F07),0)	INTO _F02 FROM S64.T6412 WHERE F09 = 'YH' AND F05=7002;
	SELECT COUNT(DISTINCT F03) INTO _F04  FROM S64.T6411 ;

	UPDATE S70.T7048 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04, F05 =0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7050
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7050`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7050`(IN _date DATE)
    COMMENT '投资排行榜（按周）统计，每周一执行一次'
BEGIN
	-- 用户投资排行 -按周（执行时间应该为0点）
	-- 账号ID
	DECLARE		_F01 				INT 		UNSIGNED  DEFAULT 0;
	-- 账号名称
	DECLARE		_F02 				VARCHAR(20);
	-- 投资总额
	DECLARE		_F03 				DECIMAL(20, 2) DEFAULT 0.00;


	DECLARE		_current_date 				DATE;
	DECLARE 	_start_date 					DATE;
	DECLARE 	_end_date 						DATE;
	DECLARE 	_week									VARCHAR(10);
	DECLARE		_week_day							INT;

	-- 列表游标定义
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;

	SET 	_current_date = IFNULL(_date, CURRENT_DATE());
	SET		_week_day			=  WEEKDAY(_current_date);

	IF _week_day = 0 THEN 
			SET 	_start_date 	= DATE_SUB(_current_date, INTERVAL 1 WEEK);
	ELSE 
			SET 	_start_date 	= DATE_SUB(_current_date, INTERVAL _week_day DAY);
	END IF;
	SET 	_end_date 		= DATE_ADD(_start_date, INTERVAL 1 WEEK);
	SET	_week = YEARWEEK(_start_date);

	BEGIN

			DECLARE _total_list 	CURSOR FOR 	SELECT ACCOUNT_ID, ACCOUNT_NAME, SUM(TOTAL_AMOUNT) FROM (( SELECT T6110.F01 AS ACCOUNT_ID, T6110.F02 AS ACCOUNT_NAME, SUM(T6250.F04) AS TOTAL_AMOUNT FROM S62.T6250 INNER JOIN S61.T6110 ON T6110.F01 = T6250.F03 WHERE T6250.F07 = 'F' AND T6250.F04 > 0 AND DATE(T6250.F06) >= _start_date AND DATE(T6250.F06) <= _end_date GROUP BY T6110.F01 ) UNION ALL ( SELECT T6110.F01 AS ACCOUNT_ID, T6110.F02 AS ACCOUNT_NAME, SUM(T6262.F05) AS TOTAL_AMOUNT FROM S62.T6262 INNER JOIN S61.T6110 ON T6110.F01 = T6262.F03 WHERE T6262.F04 > 0 AND DATE(T6262.F07) >= _start_date AND DATE(T6262.F07) <= _end_date GROUP BY T6110.F01 )) AS ACOUNT_TABLE GROUP BY ACOUNT_TABLE.ACCOUNT_ID, ACOUNT_TABLE.ACCOUNT_NAME ORDER BY ACOUNT_TABLE.TOTAL_AMOUNT DESC LIMIT 5;
			DECLARE 	CONTINUE 				HANDLER FOR NOT FOUND SET _done = 1;	
		
			OPEN _total_list; 
			REPEAT
			FETCH _total_list INTO _F01, _F02, _F03;
			IF NOT _done THEN
						-- 插入统计数据 
						IF _F03 > 0 THEN
						INSERT INTO S70.T7050 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _week
						ON DUPLICATE KEY UPDATE F03 = VALUES(F03); 
						END IF;
			END IF;
			UNTIL _done END REPEAT;
			CLOSE _total_list;
	END;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7051
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7051`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7051`(IN _date DATE)
    COMMENT '投资排行榜（按月）统计，每月一号执行一次'
BEGIN
	-- 用户投资排行 -按月
	-- 账号ID
	DECLARE		_F01 				INT 		UNSIGNED  DEFAULT 0;
	-- 账号名称
	DECLARE		_F02 				VARCHAR(20);
	-- 投资总额
	DECLARE		_F03 				DECIMAL(20, 2) DEFAULT 0.00;

	DECLARE		_current_date 				DATE;
	DECLARE 	_start_date 					DATE;
	DECLARE 	_end_date 						DATE;
	DECLARE 	_month								VARCHAR(10);
	DECLARE		_month_day						INT;

	-- 列表游标定义
	DECLARE 	_done 					INT 					UNSIGNED		DEFAULT 0;

	SET 	_current_date = IFNULL(_date, CURRENT_DATE());
	SET		_month_day		= DAYOFMONTH(_current_date);
	
	IF _month_day = 1 THEN 
			SET 	_start_date 	= DATE_SUB(_current_date, INTERVAL 1 MONTH);
	ELSE 
			SET 	_start_date 	= DATE_SUB(_current_date, INTERVAL (_month_day-1) DAY);
	END IF;

	SET 	_end_date 		= DATE_ADD(_start_date, INTERVAL 1 MONTH);
	SET		_month 				= CONCAT(YEAR(_start_date), MONTH(_start_date) + 1);

	BEGIN

	  DECLARE _total_list 	CURSOR FOR 	SELECT ACCOUNT_ID, ACCOUNT_NAME, SUM(TOTAL_AMOUNT) FROM (( SELECT T6110.F01 AS ACCOUNT_ID, T6110.F02 AS ACCOUNT_NAME, SUM(T6250.F04) AS TOTAL_AMOUNT FROM S62.T6250 INNER JOIN S61.T6110 ON T6110.F01 = T6250.F03 WHERE T6250.F07 = 'F' AND T6250.F04 > 0 AND DATE(T6250.F06) >= _start_date AND DATE(T6250.F06) <= _end_date GROUP BY T6110.F01 ) UNION ALL ( SELECT T6110.F01 AS ACCOUNT_ID, T6110.F02 AS ACCOUNT_NAME, SUM(T6262.F05) AS TOTAL_AMOUNT FROM S62.T6262 INNER JOIN S61.T6110 ON T6110.F01 = T6262.F03 WHERE T6262.F04 > 0 AND DATE(T6262.F07) >= _start_date AND DATE(T6262.F07) <= _end_date GROUP BY T6110.F01 )) AS ACOUNT_TABLE GROUP BY ACOUNT_TABLE.ACCOUNT_ID, ACOUNT_TABLE.ACCOUNT_NAME;
		
		DECLARE 	CONTINUE 		HANDLER FOR NOT FOUND SET _done = 1;

		OPEN _total_list;
		
		REPEAT 
			FETCH _total_list INTO _F01, _F02, _F03;
				
			IF NOT _done THEN
					-- 插入统计数据
					IF _F03 >0 THEN
					INSERT INTO S70.T7051 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _month
					ON DUPLICATE KEY UPDATE F03 = VALUES(F03);
					END IF;
					
			END IF;
			UNTIL _done END REPEAT;

		CLOSE _total_list;
	END;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7053
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7053`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7053`(IN _date DATE) COMMENT '逾期数据统计'
BEGIN 
	DECLARE	 _F01	 INT UNSIGNED	 DEFAULT 0;	
	DECLARE	 _F02	 INT UNSIGNED	 DEFAULT 0;	
	DECLARE	 _F03	 INT UNSIGNED    DEFAULT 0; 
	DECLARE	 _F04	 DECIMAL(20,2) DEFAULT 0.00; 
	DECLARE	 _current_date	 DATE; 
	SET _current_date = IFNULL(_date,CURRENT_DATE()); 
	SET _current_date = DATE_SUB(_current_date,INTERVAL 1 DAY);
	SET _F01 = YEAR(_current_date);
	SET _F02 = MONTH(_current_date); 
	SELECT COUNT(DISTINCT T6252.F03) INTO _F03 FROM S62.T6252 WHERE ((T6252.F08 < DATE(CURRENT_DATE()) AND T6252.F10 IS NULL ) OR (T6252.F08 < DATE(T6252.F10))) AND DATE_FORMAT(DATE_ADD(T6252.F08,INTERVAL 1 DAY),'%Y%m')=DATE_FORMAT(CURRENT_DATE(),'%Y%m');
	SELECT IFNULL(SUM(T6252.F07),0) INTO _F04 FROM S62.T6252 WHERE ((T6252.F08 < DATE(CURRENT_DATE()) AND T6252.F10 IS NULL ) OR (T6252.F08 < DATE(T6252.F10))) AND T6252.F05 IN(7001,7002,7004) AND DATE_FORMAT(DATE_ADD(T6252.F08,INTERVAL 1 DAY),'%Y%m')=DATE_FORMAT(CURRENT_DATE(),'%Y%m');
	INSERT INTO S70.T7053 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04 
	ON DUPLICATE KEY UPDATE F03 = VALUES(F03) , F04 = VALUES(F04); 
END;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7203
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7203`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7203`()
BEGIN
	DECLARE P_ID INT DEFAULT 0;
	
	DECLARE _F01 INT DEFAULT '0';
	
	DECLARE _F02 INT DEFAULT '0';
	
	DECLARE _F03 DECIMAL(20,2) DEFAULT '0.00'; 

	DECLARE _F04 INT DEFAULT 0;

	DECLARE done INT DEFAULT 0; 
	
	DECLARE t_error INTEGER DEFAULT 0;  


	-- 投资金额
	DECLARE CURSOR_TZJE CURSOR FOR
	SELECT TZJE_T.`YEAR`,TZJE_T.`MONTH`,SUM(TZJE_T.tzje),TZJE_T.userId 
	FROM (SELECT DATE_FORMAT(T6250.F06, '%Y') AS YEAR,DATE_FORMAT(T6250.F06, '%c') AS MONTH,IFNULL(SUM(T6250.F04), 0) AS tzje,T6250.F03 AS userId
		  FROM S62.T6250
		  WHERE T6250.F07 = 'F'
			AND T6250.F08 = 'S'
		  GROUP BY DATE_FORMAT(T6250.F06, '%Y-%m'),T6250.F03
		  UNION
		  SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR,DATE_FORMAT(T6262.F07, '%c') AS MONTH,IFNULL(SUM(T6262.F05), 0) AS tzje,T6262.F03 AS userId
		  FROM S62.T6262
		  GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6262.F03) AS TZJE_T
	GROUP BY TZJE_T.userId,TZJE_T.`YEAR`,TZJE_T.`MONTH`;

	-- 债权转让盈亏
	DECLARE CURSOR_ZQZRYK CURSOR FOR
	SELECT ZQZR.`YEAR`,ZQZR.`MONTH`,SUM(ZQZR.zqzryk),ZQZR.userId 
	FROM (SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR,DATE_FORMAT(T6262.F07, '%c') AS MONTH,IFNULL(SUM(T6262.F08), 0) AS zqzryk,T6262.F03 AS userId
		  FROM S62.T6262
		  GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6262.F03
		  UNION 
		  SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR,DATE_FORMAT(T6262.F07, '%c') AS MONTH,IFNULL(SUM(T6262.F09), 0),T6251.F04
		  FROM S62.T6262, S62.T6260, S62.T6251
		  WHERE T6251.F01 = T6260.F02
			AND T6260.F01 = T6262.F02
		  GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'),T6251.F04) AS ZQZR
	GROUP BY ZQZR.userId,ZQZR.`YEAR`,ZQZR.`MONTH`;

	-- 已收本金
	DECLARE CURSOR_YSBJ CURSOR FOR
	SELECT PAY_EXPECT.`YEAR` AS YEAR, PAY_EXPECT.`MONTH` AS MONTH, SUM(PAY_EXPECT.ysbj) AS ysbj, PAY_EXPECT.userId AS userId
		FROM ( SELECT DATE_FORMAT(T6262.F07, '%Y') AS YEAR, DATE_FORMAT(T6262.F07, '%c') AS MONTH,
						SUM(
							IFNULL(
								(
									SELECT
										T6251_T.F06
									FROM
										S62.T6251 T6251_T
									WHERE
										T6251_T.F12 = (
											SELECT
												T6507.F01
											FROM
												S65.T6507
											INNER JOIN S65.T6501 ON T6501.F01 = T6507.F01
											WHERE
												T6507.F02 = T6260.F01
											AND T6501.F03 = 'CG'
										)
								),
								0
							)
						) AS ysbj, T6251.F04 AS userId
						FROM S62.T6251
						INNER JOIN S62.T6260 ON T6260.F02 = T6251.F01
						INNER JOIN S62.T6262 ON T6262.F02 = T6260.F01
						GROUP BY DATE_FORMAT(T6262.F07, '%Y-%m'), T6251.F04
						UNION
							SELECT DATE_FORMAT(T6252.F10, '%Y') AS YEAR, DATE_FORMAT(T6252.F10, '%c') AS MONTH, IFNULL(SUM(T6252.F07), 0) AS ysbj, T6252.F04 AS userId
							FROM S62.T6252
							WHERE T6252.F09 = 'YH' AND T6252.F05 = 7001 AND NOT EXISTS (SELECT 1 FROM S62.T6236 WHERE T6236.F03 = T6252.F04 AND T6236.F02 = T6252.F02) 
							GROUP BY DATE_FORMAT(T6252.F10, '%Y-%m'), T6252.F04
						UNION
							SELECT DATE_FORMAT(T6253.F07, '%Y') AS YEAR, DATE_FORMAT(T6253.F07, '%c') AS MONTH, SUM(IFNULL(T6252.F07, 0)) AS ysbj, T6251.F04 AS userId
							FROM S62.T6251
							INNER JOIN S62.T6253 ON T6251.F03 = T6253.F02
							INNER JOIN S62.T6252 ON T6252.F02 = T6251.F03 AND T6252.F11 = T6251.F01
							WHERE T6251.F01 NOT IN ( SELECT T6260.F02 FROM S62.T6260
																				INNER JOIN S62.T6262 ON T6262.F02 = T6260.F01
																				WHERE T6260.F02 = T6251.F01 )
							AND T6252.F05 = 7001
							AND T6252.F06 >= T6253.F08
							GROUP BY DATE_FORMAT(T6253.F07, '%Y-%m'), T6251.F04 ) AS PAY_EXPECT
			GROUP BY PAY_EXPECT.userId, PAY_EXPECT.`YEAR`, PAY_EXPECT.`MONTH`;

	-- 已收利息
	DECLARE CURSOR_YSLX CURSOR FOR
		SELECT LX_TBL.F01 AS YEAR,LX_TBL.F02 AS MONTH,SUM(LX_TBL.F03) AS YSLX,LX_TBL.F04 AS USERID
			FROM (SELECT CASE WHEN IFNULL(TBL_LX.F05,0) = 0 THEN DATE_FORMAT(TBL_LX.F06, '%Y') ELSE DATE_FORMAT(TBL_LX.F05, '%Y') END AS F01,
									 CASE WHEN IFNULL(TBL_LX.F05,0) = 0 THEN DATE_FORMAT(TBL_LX.F06, '%c') ELSE DATE_FORMAT(TBL_LX.F05, '%c') END AS F02,
									 CASE WHEN IFNULL(TBL_LX.F05,0) = 0 THEN IFNULL(TBL_LX.F04,0) ELSE IFNULL(TBL_LX.F04,0) + IFNULL(TBL_LX.F03,0) END AS F03,TBL_LX.F02 AS F04
							FROM (SELECT T6252.F02 AS F01,T6251.F04 AS F02,
									 (SELECT SUM(F07) FROM S62.T6252 T6252_WH WHERE T6252_WH.F11 = T6252.F11 AND T6252_WH.F06 = T6252.F06 AND T6252_WH.F09 = 'WH' AND T6252_WH.F05 = 7002) AS F03,
									 (SELECT SUM(F07) FROM S62.T6252 T6252_WH WHERE T6252_WH.F11 = T6252.F11 AND T6252_WH.F06 = T6252.F06 AND T6252_WH.F09 = 'YH' AND T6252_WH.F05 = 7002) AS F04,
									 (SELECT T6253.F07 FROM S62.T6253 WHERE T6253.F02 = T6252.F02) AS F05,T6252.F10 AS F06
											FROM S62.T6252
										 INNER JOIN S62.T6251 ON T6251.F01 = T6252.F11
										 INNER JOIN S62.T6230 ON T6230.F01 = T6252.F02
										 WHERE T6252.F05 = 7002
										   AND T6252.F09 IN ('WH','YH')
										 GROUP BY T6252.F11,T6252.F06) TBL_LX) LX_TBL
			WHERE LX_TBL.F03 > 0
			GROUP BY LX_TBL.F01,LX_TBL.F02,LX_TBL.F04;

	-- 已收罚息
	DECLARE CURSOR_YSFX CURSOR FOR
		SELECT LX_TBL.F01 AS YEAR,LX_TBL.F02 AS MONTH,SUM(LX_TBL.F03) AS YSLX,LX_TBL.F04 AS USERID
			FROM (SELECT CASE WHEN IFNULL(TBL_LX.F05,0) = 0 THEN DATE_FORMAT(TBL_LX.F06, '%Y') ELSE DATE_FORMAT(TBL_LX.F05, '%Y') END AS F01,
									 CASE WHEN IFNULL(TBL_LX.F05,0) = 0 THEN DATE_FORMAT(TBL_LX.F06, '%c') ELSE DATE_FORMAT(TBL_LX.F05, '%c') END AS F02,
									 CASE WHEN IFNULL(TBL_LX.F05,0) = 0 THEN IFNULL(TBL_LX.F04,0) 
									 ELSE 
										CASE WHEN TBL_LX.F07 = 'BJQEDB' THEN IFNULL(TBL_LX.F04,0)
										ELSE IFNULL(TBL_LX.F04,0) + IFNULL(TBL_LX.F03,0) END END AS F03,TBL_LX.F02 AS F04
							FROM (SELECT T6252.F02 AS F01,T6251.F04 AS F02,
									 (SELECT SUM(F07) FROM S62.T6252 T6252_WH WHERE T6252_WH.F11 = T6252.F11 AND T6252_WH.F06 = T6252.F06 AND T6252_WH.F09 = 'WH' AND T6252_WH.F05 = 7004) AS F03,
									 (SELECT SUM(F07) FROM S62.T6252 T6252_WH WHERE T6252_WH.F11 = T6252.F11 AND T6252_WH.F06 = T6252.F06 AND T6252_WH.F09 = 'YH' AND T6252_WH.F05 = 7004) AS F04,
									 (SELECT T6253.F07 FROM S62.T6253 WHERE T6253.F02 = T6252.F02) AS F05,T6252.F10 AS F06,T6230.F12 AS F07
											FROM S62.T6252
										 INNER JOIN S62.T6251 ON T6251.F01 = T6252.F11
										 INNER JOIN S62.T6230 ON T6230.F01 = T6252.F02
										 WHERE T6252.F05 = 7004
										   AND T6252.F09 IN ('WH','YH') 
										   AND T6252.F06 <= (IFNULL((SELECT F08 - 1 FROM S62.T6253 WHERE T6253.F02 = T6252.F02),(SELECT MAX(F06) FROM S62.T6252 T6252_QS WHERE T6252_QS.F02 = T6252.F02)))
										  GROUP BY T6252.F11,T6252.F06 
										 UNION SELECT T6253.F02 AS F01,T6255.F04 AS F02,'' AS F03,T6255.F03 AS F04,T6253.F07 AS F05,T6253.F07 AS F06,'' AS F07 FROM S62.T6255 LEFT JOIN S62.T6253 ON T6255.F02 = T6253.F01 WHERE  T6255.F05 = 7004) TBL_LX) LX_TBL
			WHERE LX_TBL.F03 > 0
			GROUP BY LX_TBL.F01,LX_TBL.F02,LX_TBL.F04;

	-- 已收违约金
	DECLARE CURSOR_YSWYJ CURSOR FOR
	SELECT DATE_FORMAT(T6252.F10, '%Y') as year, DATE_FORMAT(T6252.F10, '%c') as month, IFNULL(SUM(T6252.F07),0) as yswyj,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='YH' AND T6252.F05=7005 GROUP BY DATE_FORMAT(T6252.F10, '%Y-%m'),T6252.F04;

	-- 待收本金
	DECLARE CURSOR_DSBJ CURSOR FOR
	SELECT DATE_FORMAT(T6252.F08, '%Y') as year, DATE_FORMAT(T6252.F08, '%c') as month, IFNULL(SUM(T6252.F07),0) as dsbj,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='WH' AND T6252.F05=7001 AND NOT EXISTS (SELECT 1 FROM S62.T6236 WHERE T6236.F03 = T6252.F04 AND T6236.F02 = T6252.F02) GROUP BY DATE_FORMAT(T6252.F08, '%Y-%m'),T6252.F04;

	-- 待收利息
	DECLARE CURSOR_DSLX CURSOR FOR
	SELECT DATE_FORMAT(T6252.F08, '%Y') as year, DATE_FORMAT(T6252.F08, '%c') as month,  IFNULL(SUM(T6252.F07),0) dslx,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='WH' AND T6252.F05=7002 AND NOT EXISTS (SELECT 1 FROM S62.T6236 WHERE T6236.F03 = T6252.F04 AND T6236.F02 = T6252.F02) GROUP BY DATE_FORMAT(T6252.F08, '%Y-%m'),T6252.F04;

	-- 待收罚息
	DECLARE CURSOR_DSFX CURSOR FOR
	SELECT DATE_FORMAT(T6252.F08, '%Y') as year, DATE_FORMAT(T6252.F08, '%c') as month,  IFNULL(SUM(T6252.F07),0) dsfx,T6252.F04 AS userId
	FROM S62.T6252 WHERE T6252.F09='WH' AND T6252.F05=7004 AND NOT EXISTS (SELECT 1 FROM S62.T6236 WHERE T6236.F03 = T6252.F04 AND T6236.F02 = T6252.F02) GROUP BY DATE_FORMAT(T6252.F08, '%Y-%m'),T6252.F04;

	DECLARE CURSOR_TZGLF CURSOR FOR
	SELECT DATE_FORMAT(T6102.F05,'%Y') as year, DATE_FORMAT(T6102.F05,'%c') as month, IFNULL(SUM(T6102.F07),0) tzglf,T6101.F02 AS userId
	FROM S61.T6102 INNER JOIN S61.T6101 ON T6102.F02 = T6101.F01 WHERE T6102.F03=1202 AND T6101.F03='WLZH' GROUP BY DATE_FORMAT(T6102.F05,'%Y-%m'),T6101.F02;

        -- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error = 1;
	START TRANSACTION;
	
	DELETE FROM S70.T7203;

	-- 打开游标
  OPEN CURSOR_TZJE;
	-- 遍历游标
	read_loop:LOOP
		FETCH CURSOR_TZJE INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F03=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F03=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_TZJE;   

	SET done = 0;
	
	-- 打开游标
  OPEN CURSOR_ZQZRYK;
	-- 遍历游标
	read_loop:LOOP
		FETCH CURSOR_ZQZRYK INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F04=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F04=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_ZQZRYK;  
	SET done = 0;

	-- 打开游标
  OPEN CURSOR_YSBJ;
	-- 遍历游标
	read_loop:LOOP
		FETCH CURSOR_YSBJ INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F05=_F03,F12= _F04 ON DUPLICATE KEY UPDATE F05=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_YSBJ;  
	SET done = 0;

	-- 打开游标
  OPEN  CURSOR_YSLX;
	-- 遍历游标
	read_loop:LOOP
		FETCH  CURSOR_YSLX INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F06=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F06=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_YSLX;
	SET done = 0;
	
	-- 打开游标
	OPEN  CURSOR_YSFX;
	-- 遍历游标
	read_loop:LOOP
		FETCH  CURSOR_YSFX INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
			LEAVE read_loop;
		END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F07=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F07=_F03;
	END LOOP;
	-- 关闭游标
	CLOSE CURSOR_YSFX;
		
	SET done = 0;
	
	-- 打开游标
  OPEN  CURSOR_YSWYJ;
	-- 遍历游标
	read_loop:LOOP 
		FETCH CURSOR_YSWYJ INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F08=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F08=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_YSWYJ;  

	SET done = 0;

	-- 打开游标
  OPEN  CURSOR_DSBJ;
	-- 遍历游标
	read_loop:LOOP
		FETCH  CURSOR_DSBJ INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F09=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F09=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_DSBJ;  
	SET done = 0;

	-- 打开游标
  OPEN  CURSOR_DSLX;
	-- 遍历游标
	read_loop:LOOP
		FETCH  CURSOR_DSLX INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F10=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F10=_F03;
	END LOOP;
	-- 关闭游标
  CLOSE CURSOR_DSLX;  
	SET done = 0;

	-- 打开游标
	OPEN  CURSOR_DSFX;
	-- 遍历游标
	read_loop:LOOP 
		FETCH  CURSOR_DSFX INTO _F01,_F02,_F03,_F04;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		-- 遍历结束跳转循环
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		-- 存在则更新，不存在则插入数据
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F11=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F11=_F03;
	END LOOP;
	-- 关闭游标
	CLOSE CURSOR_DSFX;
	SET done = 0;


	OPEN  CURSOR_TZGLF;
	read_loop:LOOP 
		FETCH  CURSOR_TZGLF INTO _F01,_F02,_F03,_F04;
		
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			LEAVE read_loop;
		END IF;
		
		IF done THEN
	      LEAVE read_loop;
	    END IF;
		INSERT INTO S70.T7203 SET F01=_F01,F02=_F02,F13=_F03,F12=_F04 ON DUPLICATE KEY UPDATE F13=_F03;
	END LOOP;
	CLOSE CURSOR_TZGLF;
	
	IF t_error = 1 THEN 
		ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_T7204
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_T7204`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7204`()
BEGIN
	DECLARE P_ID INT DEFAULT 0;
	
	DECLARE _F01 INT DEFAULT 0;
	
	DECLARE _F02 INT DEFAULT 0;
	
	DECLARE _F03 INT DEFAULT 0; 

	DECLARE _F04 INT DEFAULT 0; 

	DECLARE _F05 VARCHAR(5); 

	DECLARE _F06 DECIMAL(20,2) DEFAULT 0.00; 

	DECLARE done INT DEFAULT 0; 
	
	DECLARE t_error INTEGER DEFAULT 0;  


	-- 借款列表
	DECLARE CURSOR_JKLB CURSOR FOR
	SELECT	T6252.F03 AS ID,	DATE_FORMAT(T6252.F08, '%Y') AS YE,	DATE_FORMAT(T6252.F08, '%m') AS MO,
	T6252.F05 AS TEM,	T6252.F09 AS ST,	IFNULL(SUM(T6252.F07), 0) AS SU 
	FROM	S62.T6252 WHERE T6252.F09  ='WH'GROUP BY	ID,	YE,	MO,	TEM,	ST
	UNION ALL
	SELECT	T6252.F03 AS ID,	DATE_FORMAT(T6252.F10, '%Y') AS YE,	DATE_FORMAT(T6252.F10, '%m') AS MO,
		T6252.F05 AS TEM,	T6252.F09 AS ST,	IFNULL(SUM(T6252.F07), 0) AS SU 
	FROM	S62.T6252 WHERE T6252.F09  ='YH'GROUP BY	ID,	YE,	MO,	TEM,	ST	;

	-- 借款手续费列表
	DECLARE CURSOR_JKSXFLB CURSOR FOR
  SELECT T6101.F02,DATE_FORMAT(T6102.F05, '%Y') AS YE,	DATE_FORMAT(T6102.F05, '%m') AS MO,IFNULL(SUM(T6102.F07),0)
  FROM S61.T6102 ,S61.T6101 WHERE T6102.F02 = T6101.F01 AND T6102.F03=1201 AND T6101.F03 = 'WLZH'GROUP BY T6101.F02,YE,MO;

	-- 借款总和
	DECLARE CURSOR_JKZH CURSOR FOR
	SELECT T6252.F03 AS ID, DATE_FORMAT(T6230.F24, '%Y') AS YE,	DATE_FORMAT(T6230.F24, '%m') AS MO, T6252.F05 AS TEM, 
	IFNULL(SUM(T6252.F07), 0) AS SU		FROM S62.T6252, S62.T6230
	WHERE T6252.F02 = T6230.F01		AND T6252.F05 IN (7001, 7002)		GROUP BY ID, YE, MO, TEM;

	-- 解决mysql Bug:no data - zero rows fetched,selected,or processed
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET P_ID = NULL;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error = 1;
	START TRANSACTION;
	
	DELETE FROM S70.T7204;
	-- 打开游标
  OPEN CURSOR_JKLB;
	-- 遍历游标
	REPEAT
		FETCH CURSOR_JKLB INTO _F01,_F02,_F03,_F04,_F05,_F06;
		-- 如果游标没有提取到数据就跳出循环
		IF P_ID IS NULL THEN
			SET P_ID = 0;
			SET done = 1;
		END IF;
		-- 遍历结束跳转循环
		IF NOT done THEN
			IF _F04 = 7001 THEN
				IF _F05 = 'YH' THEN
					-- 已还本金
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F06=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F06=_F06;
				ELSEIF _F05 = 'WH' THEN
					-- 未还本金
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F10=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F10=_F06;
				END IF;

			ELSEIF _F04 = 7002 THEN
				IF _F05 = 'YH' THEN
					-- 已还利息
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F07=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F07=_F06;
				ELSEIF _F05 = 'WH' THEN
					-- 未还利息
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F11=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F11=_F06;
				END IF;

			ELSEIF _F04 = 7004 THEN
				IF _F05 = 'YH' THEN
					-- 已还罚息
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F08=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F08=_F06;
				ELSEIF _F05 = 'WH' THEN
					-- 未还罚息
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F12=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F12=_F06;
				END IF;
		
			ELSEIF _F04 = 7005 THEN
					-- 违约金
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F09=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F09=_F06;
			
			ELSEIF _F04 = 7007 THEN 
					-- 提前还款手续费
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F14=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F14=_F06;
			END IF;
		END IF;
	UNTIL done END REPEAT;
	-- 关闭游标
  CLOSE CURSOR_JKLB;  

	SET done = 0;
	
	-- 打开游标
  OPEN CURSOR_JKSXFLB;
	-- 遍历游标
	REPEAT
		FETCH CURSOR_JKSXFLB INTO _F01,_F02,_F03,_F06;
			-- 如果游标没有提取到数据就跳出循环
			IF P_ID IS NULL THEN
				SET P_ID = 0;
				SET done = 1;
			END IF;
			-- 遍历结束跳转循环
			IF NOT done THEN
					-- 借款手续费	
					INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F05=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F05=_F06;
			END IF;
		UNTIL done END REPEAT;
	-- 关闭游标
  CLOSE CURSOR_JKSXFLB;  

	SET done = 0;

	-- 打开游标
  OPEN CURSOR_JKZH;
	-- 遍历游标
	REPEAT
		FETCH CURSOR_JKZH INTO _F01,_F02,_F03,_F04,_F06;
			-- 如果游标没有提取到数据就跳出循环
			IF P_ID IS NULL THEN
				SET P_ID = 0;
				SET done = 1;
			END IF;
			-- 遍历结束跳转循环
			IF NOT done THEN
				IF _F04 = 7001 THEN
						-- 借款金额
						INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F03=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F03=_F06;

				ELSEIF _F04 = 7002 THEN
						-- 借款利息
						INSERT INTO S70.T7204 SET F01=_F02,F02=_F03,F04=_F06,F13=_F01 ON DUPLICATE KEY UPDATE F04=_F06;
				END IF;
			END IF;
		UNTIL done END REPEAT;
	-- 关闭游标
  CLOSE CURSOR_JKZH;  
  
  IF t_error = 1 THEN 
		ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
END
;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `S70`.`SP_T7052`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_T7052`()
BEGIN
	-- 日期
	DECLARE _F01  VARCHAR(50) DEFAULT NULL;
	-- 注册用户数
	DECLARE _F02 INT DEFAULT 0;
	-- PC端注册用户数
	DECLARE _F03 INT DEFAULT 0;
	-- APP端注册用户数
	DECLARE _F04 INT DEFAULT 0;
	-- 微信端注册用户数
	DECLARE _F05 INT DEFAULT 0;
	-- 后台注册用户数
	DECLARE _F06 INT DEFAULT 0;
    -- 登录用户数
	DECLARE _F07 INT DEFAULT 0;
	-- 充值用户数
	DECLARE _F08 INT DEFAULT 0;
	-- 提现用户数
	DECLARE _F09 INT DEFAULT 0;
	-- 投资用户数
	DECLARE _F10 INT DEFAULT 0;
	-- 借款用户数
	DECLARE _F11 INT DEFAULT 0;
	-- 年份
  DECLARE _F13 CHAR(4) DEFAULT NULL;
	-- 季度
  DECLARE _F14 CHAR(5) DEFAULT NULL;
	-- 月份
  DECLARE _F15 CHAR(6) DEFAULT NULL;
	-- 周
  DECLARE _F16 CHAR(6) DEFAULT NULL;


	SELECT CURDATE() INTO _F01 FROM dual;	
	SELECT YEAR(_F01) INTO _F13 FROM DUAL;
  SELECT CONCAT(YEAR(_F01),QUARTER(_F01)) INTO _F14 FROM DUAL; 
  SELECT CONCAT(YEAR(_F01),MONTH(_F01)) INTO _F15 FROM DUAL;
  SELECT CONCAT(YEAR(_F01),WEEK(_F01)) INTO _F16 FROM DUAL;
	-- 注册用户数
	SELECT COUNT(1) INTO _F02 FROM S61.T6110 WHERE DATE(T6110.F09) = _F01 AND NOT EXISTS (SELECT 1 FROM S71.T7101 WHERE T7101.F01 = T6110.F01);	 
	-- PC端注册用户数
	SELECT COUNT(1) INTO _F03 FROM S61.T6110 WHERE T6110.F08 = 'PCZC' AND DATE(T6110.F09) = _F01;	 
	-- APP端注册用户数
	SELECT COUNT(1) INTO _F04 FROM S61.T6110 WHERE T6110.F08 = 'APPZC' AND DATE(T6110.F09) = _F01;	 
	-- 微信端注册用户数
	SELECT COUNT(1) INTO _F05 FROM S61.T6110 WHERE T6110.F08 = 'WXZC' AND DATE(T6110.F09) = _F01;	
	-- 后台注册用户数
	SELECT COUNT(1) INTO _F06 FROM S61.T6110 WHERE T6110.F08 = 'HTTJ' AND DATE(T6110.F09) = _F01 AND NOT EXISTS (SELECT 1 FROM S71.T7101 WHERE T7101.F01 = T6110.F01);	
	-- 登录用户数
	SELECT COUNT(1) INTO _F07 FROM S61.T6110 WHERE EXISTS (SELECT 1 FROM S61.T6190 WHERE T6190.F02 = T6110.F01 AND DATE(T6190.F03) = _F01);	
	-- 充值用户数
	SELECT COUNT(1) INTO _F08 FROM S61.T6110 WHERE EXISTS (SELECT 1 FROM S65.T6502 LEFT JOIN S65.T6501 ON T6502.F01 = T6501.F01 WHERE T6502.F02 = T6110.F01 AND T6502.F08 IS NOT NULL AND DATE(T6501.F06) = _F01);
	-- 提现用户数
	SELECT COUNT(DISTINCT T6503.F02) INTO _F09 FROM S65.T6503 INNER JOIN S65.T6501 ON T6503.F01 = T6501.F01 WHERE T6501.F03 = 'CG' AND DATE(T6501.F06) = _F01;
	-- 投资用户数
	SELECT COUNT(1) INTO _F10 FROM S61.T6110 WHERE EXISTS (SELECT 1 FROM S62.T6250 LEFT JOIN S62.T6231 ON T6231.F01 = T6250.F02 WHERE T6250.F03 = T6110.F01 AND T6250.F08 = 'S' AND DATE(T6231.F12) = _F01);
	-- 借款用户数
	SELECT COUNT(1) INTO _F11 FROM S61.T6110 WHERE EXISTS (SELECT 1 FROM S62.T6230 LEFT JOIN S62.T6231 ON T6231.F01 = T6230.F01 WHERE T6230.F02 = T6110.F01 AND T6230.F20 IN ('HKZ','YJQ','YDF','YZR') AND DATE(T6231.F12) = _F01);
	
    INSERT INTO S70.T7052 SET F01 = _F01, F02 = _F02, F03 = _F03, F04 = _F04, F05 = _F05, F06 = _F06, F07 = _F07, F08 = _F08, F09 = _F09, F10 = _F10, F11 = _F11, F13 = _F13, F14 = _F14, F15 = _F15, F16 = _F16 
    ON DUPLICATE KEY UPDATE F01 = VALUES(F01),F02 = VALUES(F02),F03 = VALUES(F03),F04 = VALUES(F04),F05 = VALUES(F05), F06 = VALUES(F06), F07 = VALUES(F07), F08 = VALUES(F08), F09 = VALUES(F09), F10 = VALUES(F10), F11 = VALUES(F11), F13 = VALUES(F13), F14 = VALUES(F14), F15 = VALUES(F15), F16 = VALUES(F16);

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_YEAR_DATE
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_YEAR_DATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_YEAR_DATE`(INOUT _year MEDIUMINT UNSIGNED , OUT _startDate DATE, OUT _endDate DATE)
    COMMENT '计算年份开始和截至日期'
BEGIN
	SELECT CONCAT(_year,'01','01') INTO _startDate ;
	SELECT CONCAT(_year,'12','31') INTO _endDate ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SP_ROSES_DATE
-- ----------------------------
DROP PROCEDURE IF EXISTS `S70`.`SP_ROSES_DATE`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `S70`.`SP_ROSES_DATE`(IN _current_date DATE, OUT _startDate DATE, OUT _endDate DATE)
    COMMENT '计算月份第一天和最后一天'
BEGIN 
	SET _startDate = CONCAT(DATE_FORMAT(LAST_DAY(_current_date),'%Y-%m-'),'01'); 
	SET _endDate = LAST_DAY(_current_date); 
END;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T6230
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T6230`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T6230` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 12:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T6230()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7010
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7010`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7010` ON SCHEDULE EVERY 30 MINUTE STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7010()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7024
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7024`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7024` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7024()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7034
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7034`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7034` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7034(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7035
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7035`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7035` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7035(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7036
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7036`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7036` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7036(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7037
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7037`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7037` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7037(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7038
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7038`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7038` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7038(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7039
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7039`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7039` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 12:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7039(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7040
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7040`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7040` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 12:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7040(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7041
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7041`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7041` ON SCHEDULE EVERY 1 DAY STARTS '2014-01-01 10:59:30' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7041 (CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7042
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7042`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7042` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7042(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7043
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7043`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7043` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7043(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7044
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7044`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7044` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7044(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7045
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7045`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7045` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7045(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7048
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7048`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7048` ON SCHEDULE EVERY 30 MINUTE STARTS '2013-12-31 11:00:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7048()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7050
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7050`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `S70`.`EVT_T7050` ON SCHEDULE EVERY 1 WEEK STARTS '2015-12-14 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7050(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7051
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7051`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `S70`.`EVT_T7051` ON SCHEDULE EVERY 1 MONTH STARTS '2015-12-01 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7051(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7053
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7053`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7053` ON SCHEDULE EVERY 1 DAY STARTS '2013-12-31 11:01:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7053(CURRENT_DATE())
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7203
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7203`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7203` ON SCHEDULE EVERY 1 DAY STARTS '2014-12-01 00:10:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7203()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVT_T7204
-- ----------------------------
DROP EVENT IF EXISTS `S70`.`EVT_T7204`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `S70`.`EVT_T7204` ON SCHEDULE EVERY 1 DAY STARTS '2014-12-01 00:12:00' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7204()
;;
DELIMITER ;

DROP EVENT IF EXISTS `S70`.`EVT_T7052`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `S70`.`EVT_T7052` ON SCHEDULE EVERY 10 SECOND STARTS '2015-12-01 00:00:01' ON COMPLETION PRESERVE ENABLE DO CALL SP_T7052()
;;
DELIMITER ;
