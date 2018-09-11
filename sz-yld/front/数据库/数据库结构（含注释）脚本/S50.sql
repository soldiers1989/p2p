/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S50

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T5010
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5010`;
CREATE TABLE `S50`.`T5010` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '类别编码',
  `F03` varchar(50) NOT NULL COMMENT '类别名称',
  `F04` enum('TY','QY') NOT NULL DEFAULT 'QY' COMMENT '状态：QY:启用；TY：停用',
  `F05` int(10) DEFAULT NULL COMMENT '父类id',
  `F06` varchar(50) DEFAULT NULL COMMENT '菜单URL',
  `F07` varchar(50) DEFAULT NULL COMMENT '菜单权限ID',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章类别';
-- ----------------------------
-- Table structure for T5011
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5011`;
CREATE TABLE `S50`.`T5011` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '文章自增ID',
  `F02` int(10) NOT NULL DEFAULT '0' COMMENT '文章类别id,T5010.F01',
  `F03` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `F04` int(11) NOT NULL DEFAULT '1' COMMENT '排序值',
  `F05` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,WFB:未发布;YFB:已发布;',
  `F06` varchar(50) NOT NULL COMMENT '文章标题',
  `F07` varchar(50) DEFAULT NULL COMMENT '文章来源',
  `F08` varchar(200) DEFAULT NULL COMMENT '文章摘要',
  `F09` varchar(50) DEFAULT NULL COMMENT '封面图片文件编码',
  `F10` int(10) NOT NULL COMMENT '创建者ID,参考T7011.F01',
  `F11` datetime NOT NULL COMMENT '创建时间',
  `F12` datetime DEFAULT NULL COMMENT '发布时间',
  `F13` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  KEY `F10` (`F10`) USING BTREE,
  KEY `F12` (`F12`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章';

-- ----------------------------
-- Table structure for T5011_1
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5011_1`;
CREATE TABLE `S50`.`T5011_1` (
  `F01` int(10) unsigned NOT NULL COMMENT '文章自增ID',
  `F02` text COMMENT '文章内容',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章内容表';

-- ----------------------------
-- Table structure for T5011_2
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5011_2`;
CREATE TABLE `S50`.`T5011_2` (
  `F01` int(10) unsigned NOT NULL COMMENT '文章自增ID',
  `F02` varchar(50) NOT NULL COMMENT '附件编码',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章附件表';

-- ----------------------------
-- Table structure for T5012
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5012`;
CREATE TABLE `S50`.`T5012` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '在线客服自增ID',
  `F02` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `F03` enum('YX','DH','QQ_QY','QQ') NOT NULL DEFAULT 'QQ' COMMENT '客服类型,QQ:QQ;DH:电话号码;YX:邮箱;QQ_QY:企业QQ',
  `F04` int(10) NOT NULL DEFAULT '1' COMMENT '排序值',
  `F05` varchar(50) NOT NULL COMMENT '客服名称',
  `F06` varchar(200) NOT NULL COMMENT '客服号码',
  `F07` varchar(50) NOT NULL COMMENT '图片编码',
  `F08` int(10) NOT NULL COMMENT '创建者,参考T7011.F01',
  `F09` datetime NOT NULL COMMENT '创建时间',
  `F10` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `F11` enum('TY','QY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用',
  PRIMARY KEY (`F01`),
  KEY `F08` (`F08`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='在线客服';

-- ----------------------------
-- Table structure for T5013
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5013`;
CREATE TABLE `S50`.`T5013` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '合作伙伴自增ID',
  `F02` int(10) NOT NULL DEFAULT '0' COMMENT '排序值',
  `F03` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `F04` varchar(50) NOT NULL COMMENT '公司名称',
  `F05` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `F06` varchar(50) NOT NULL COMMENT '图片编码',
  `F07` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `F08` text COMMENT '公司描述',
  `F09` int(10) NOT NULL COMMENT '创建者ID,参考T7011.F01',
  `F10` datetime NOT NULL COMMENT '创建时间',
  `F11` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  KEY `F09` (`F09`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='合作伙伴';

-- ----------------------------
-- Table structure for T5014
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5014`;
CREATE TABLE `S50`.`T5014` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '友情链接自增ID',
  `F02` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `F03` int(10) NOT NULL DEFAULT '1' COMMENT '排序值',
  `F04` varchar(50) NOT NULL COMMENT '名称',
  `F05` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `F06` int(10) NOT NULL COMMENT '创建者,参考T7011.F01',
  `F07` datetime NOT NULL COMMENT '创建时间',
  `F08` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`),
  KEY `F06` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='友情链接';

-- ----------------------------
-- Table structure for T5015
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5015`;
CREATE TABLE `S50`.`T5015` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '网站公告自增ID',
  `F02` enum('HD','XT') NOT NULL DEFAULT 'XT' COMMENT '类型,XT:系统;HD:活动',
  `F03` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `F04` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,WFB:未发布;YFB:已发布',
  `F05` varchar(50) NOT NULL COMMENT '公告标题',
  `F06` text NOT NULL COMMENT '内容',
  `F07` int(10) NOT NULL COMMENT '创建者ID,参考T7011.F01',
  `F08` datetime NOT NULL COMMENT '创建时间',
  `F09` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`F01`),
  KEY `F07` (`F07`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='网站公告';

-- ----------------------------
-- Table structure for T5016
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5016`;
CREATE TABLE `S50`.`T5016` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '广告管理自增ID',
  `F02` int(10) NOT NULL DEFAULT '1' COMMENT '排序值',
  `F03` varchar(50) NOT NULL COMMENT '广告图片标题',
  `F04` varchar(250) DEFAULT NULL COMMENT '图片链接',
  `F05` varchar(50) NOT NULL COMMENT '图片编码',
  `F06` int(10) NOT NULL COMMENT '创建者,参考T7011.F01',
  `F07` datetime NOT NULL COMMENT '上架时间',
  `F08` datetime NOT NULL COMMENT '下架时间',
  `F09` datetime NOT NULL COMMENT '创建时间',
  `F10` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `F11` text COMMENT '广告内容',
  `F12` enum('APP','GYJZ','PC','PCREGISTER','PCLOGIN','IOSPIC','FINDPIC','ANDROIDPIC','APPGYJZ') NOT NULL DEFAULT 'PC' COMMENT '广告类型',
  PRIMARY KEY (`F01`),
  KEY `F06` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='广告管理';

-- ----------------------------
-- Table structure for T5017
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5017`;
CREATE TABLE `S50`.`T5017` (
  `F01` enum('ZCXY','TFJKXY','FFJKXY','ZQZRXY','GYJZXY','GRXXCQSQTK','QYXXCJSQTK','FXTSH','BLZQZRXY') NOT NULL COMMENT '协议类型,ZCXY:注册协议;TFJKXY:三方借款协议;FFJKXY:四方借款协议;ZQZRXY:债权转让协议;GYJZXY:公益捐助协议;GRXXCQSQTK:个人信息采集授权条款;QYXXCJSQTK:企业信息采集授权条款;FXTSH:风险提示函;BLZQZRXY:不良债权转让协议;',
  `F02` text NOT NULL COMMENT '协议内容',
  `F03` int(10) DEFAULT '0' COMMENT '修改者,参考T7011.F01',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `F05` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='协议条款';

-- ----------------------------
-- Table structure for T5017_1
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5017_1`;
CREATE TABLE `S50`.`T5017_1` (
  `F01` enum('ZCXY','BXBZ') NOT NULL COMMENT '协议类型,BXBZ:本息保障;ZCXY:注册协议',
  `F02` varchar(50) NOT NULL COMMENT '附件编码',
  PRIMARY KEY (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='协议附件表';

-- ----------------------------
-- Table structure for T5018
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5018`;
CREATE TABLE `S50`.`T5018` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '业绩报告自增ID',
  `F02` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `F03` enum('YFB','WFB') NOT NULL COMMENT '是否发布,WFB:未发布;YFB:已发布',
  `F04` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `F05` varchar(50) NOT NULL COMMENT '文章标题',
  `F06` varchar(50) NOT NULL COMMENT '附件编码',
  `F07` int(10) NOT NULL COMMENT '创建者ID,参考T7011.F01',
  `F08` datetime NOT NULL COMMENT '创建时间',
  `F09` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`F01`),
  KEY `F07` (`F07`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='业绩报告';

-- ----------------------------
-- Table structure for T5019
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5019`;
CREATE TABLE `S50`.`T5019` (
  `F01` mediumint(6) unsigned NOT NULL COMMENT 'ID,6位数字',
  `F02` tinyint(2) unsigned NOT NULL COMMENT '省级ID',
  `F03` tinyint(2) unsigned NOT NULL COMMENT '市级ID',
  `F04` tinyint(2) unsigned NOT NULL COMMENT '县级ID',
  `F05` char(15) NOT NULL COMMENT '名称,最多15个字符',
  `F06` char(15) DEFAULT NULL COMMENT '省级名称,最多15个字符',
  `F07` char(15) DEFAULT NULL COMMENT '市级名称,最多15个字符',
  `F08` char(15) DEFAULT NULL COMMENT '县级名称,最多15个字符',
  `F09` char(4) DEFAULT NULL COMMENT '电话区号,最多4个字符',
  `F10` char(5) DEFAULT NULL COMMENT '邮政编码,最多5个字符',
  `F11` enum('SHENG','SHI','XIAN') NOT NULL COMMENT '级别,SHENG:省级;SHI:市级;XIAN:县级',
  `F12` char(15) DEFAULT NULL COMMENT '简拼,最多15个字符',
  `F13` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '启用状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`) USING BTREE,
  KEY `F03` (`F03`) USING BTREE,
  KEY `F04` (`F04`) USING BTREE,
  KEY `F13` (`F13`) USING BTREE,
  KEY `F12` (`F12`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中国行政区划';

-- ----------------------------
-- Table structure for T5020
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5020`;
CREATE TABLE `S50`.`T5020` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '银行自增ID',
  `F02` varchar(50) NOT NULL COMMENT '银行名称',
  `F03` enum('TY','QY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用',
  `F04` varchar(20) NOT NULL COMMENT '代码',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='银行列表';

-- ----------------------------
-- Table structure for T5021
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5021`;
CREATE TABLE S50.`T5021` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '问题类型自增ID',
  `F02` varchar(50) NOT NULL COMMENT '问题类型',
  `F03` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,WFB:未发布;YFB:已发布;',
  `F04` varchar(50) DEFAULT NULL COMMENT '封面图片文件编码',
  `F05` int(10) NOT NULL COMMENT '创建者ID,参考T7011.F01',
  `F06` datetime NOT NULL COMMENT '创建时间',
  `F07` datetime DEFAULT NULL COMMENT '发布时间',
  `F08` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `F09` enum('ZCYDL','ZHYAQ','TZYHK','CZYTX') NOT NULL DEFAULT 'CZYTX' COMMENT '注册与登录(ZCYDL)；充值与提现(CZYTX)；账户与安全(ZHYAQ);投资与回款(TZYHK)',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='帮助中心问题类型';

-- ----------------------------
-- Table structure for T5021_1
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5021_1`;
CREATE TABLE S50.`T5021_1` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '问题自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '问题类型ID,参考T5021.F01',
  `F03` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,WFB:未发布;YFB:已发布;',
  `F04` varchar(50) DEFAULT NULL COMMENT '封面图片文件编码',
  `F05` int(10) NOT NULL COMMENT '创建者ID,参考T7011.F01',
  `F06` datetime NOT NULL COMMENT '创建时间',
  `F07` datetime DEFAULT NULL COMMENT '发布时间',
  `F08` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `F09` varchar(50) DEFAULT NULL COMMENT '注册与登录(ZCYDL)；充值与提现(CZYTX)；账户与安全(ZHYAQ);投资与回款(TZYHK)',
  `F10` text COMMENT '问题答案',
  `F11` enum('ZCYDL','ZHYAQ','TZYHK','CZYTX') NOT NULL DEFAULT 'CZYTX' COMMENT '注册与登录(ZCYDL)；充值与提现(CZYTX)；账户与安全(ZHYAQ);投资与回款(TZYHK)',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='帮助中心问题以及答案';

-- ----------------------------
-- Table structure for T5022
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5022`;
CREATE TABLE `S50`.`T5022` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '视频上传自增ID',
  `F02` int(10) NOT NULL DEFAULT '0' COMMENT '置顶值',
  `F03` varchar(50) NOT NULL COMMENT '视频上传标题',
  `F04` varchar(250) DEFAULT NULL COMMENT '文件名称',
  `F05` varchar(50) NOT NULL COMMENT '文件大小',
  `F06` varchar(20) NOT NULL COMMENT '文件格式',
  `F07` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '发布状态,WFB:未发布;YFB:已发布;',
  `F08` int(1) NOT NULL DEFAULT '0' COMMENT '是否自动播放',
  `F09` int(10) NOT NULL COMMENT '创建者,参考T7010.F01',
  `F10` datetime NOT NULL COMMENT '上传时间',
  `F11` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`F01`),
  KEY `F09` (`F09`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='视频管理';

-- ----------------------------
-- Table structure for T5023
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5023`;
CREATE TABLE `S50`.`T5023` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` enum('CHARGE','WITHDRAW','CHARGELINE') NOT NULL COMMENT '类型,CHARGE:充值;WITHDRAW:提现;CHARGELINE:线下充值',
  `F03` text COMMENT '说明内容',
  `F04` datetime NOT NULL COMMENT '创建时间',
  `F05` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
   PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能说明';



