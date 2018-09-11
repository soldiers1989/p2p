/*
Navicat MySQL Data Transfer

Source Server         : 标准库-DEVELOPMENT
Source Server Version : 50621
Source Host           : 192.168.0.235:3306
Source Database       : S51

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-17 15:39:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T5122
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5122`;
CREATE TABLE `S51`.`T5122` (
  `F01` int(10) unsigned NOT NULL COMMENT '交易类型编码,非自增',
  `F02` varchar(20) NOT NULL COMMENT '类型名称',
  `F03` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F04` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于个人,yes:属于;no:不属于;',
  `F05` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于企业,yes:属于;no:不属于;',
  `F06` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于机构,yes:属于;no:不属于;',
  `F07` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于平台,yes:属于;no:不属于;',
  `F08` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于信用类型,yes:属于;no:不属于;',
  `F09` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于担保类型,yes:属于;no:不属于;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易类型';


-- ----------------------------
-- Table structure for T5123
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5123`;
CREATE TABLE `S51`.`T5123` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '认证ID,自增',
  `F02` varchar(20) NOT NULL COMMENT '类型名称',
  `F03` enum('S','F') NOT NULL COMMENT '必要认证,S:是;F:否',
  `F04` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用',
  `F05` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最高分数',
  `F06`  enum('GR','QY') NOT NULL DEFAULT 'GR' COMMENT '用户类型,GR:个人;QY:企业',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='信用认证项';

-- ----------------------------
-- Table structure for T5124
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5124`;
CREATE TABLE `S51`.`T5124` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` varchar(20) NOT NULL COMMENT '等级名称',
  `F03` int(10) unsigned NOT NULL COMMENT '下限分数',
  `F04` int(10) unsigned NOT NULL COMMENT '上限分数',
  `F05` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='信用等级';

-- ----------------------------
-- Table structure for T5125
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5125`;
CREATE TABLE `S51`.`T5125` (
  `F01` int(11) unsigned NOT NULL COMMENT '协议范本ID，非自增 ',
  `F02` int(10) unsigned NOT NULL COMMENT '最新版本号',
  `F03` varchar(50) NOT NULL COMMENT '类型名称',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='协议范本';

-- ----------------------------
-- Table structure for T5126
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5126`;
CREATE TABLE `S51`.`T5126` (
  `F01` int(11) unsigned NOT NULL COMMENT '范本类型ID,参考T5125.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '版本号',
  `F03` text COMMENT '模板内容',
  `F04` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='协议内容表';

-- ----------------------------
-- Table structure for T5127
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5127`;
CREATE TABLE `S51`.`T5127` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` enum('TZ','JK') NOT NULL COMMENT '等级类型,TZ:投资;JK:借款;',
  `F03` enum('YJ','EJ','SJ','SIJ','WJ','LJ','QJ') NOT NULL COMMENT '等级,YJ:一级;EJ:二级,SJ:三级,SIJ:四级,WJ:五级,LJ六级:,QJ:七级;',
  `F04` decimal(20,2) NOT NULL COMMENT '上限',
  `F05` decimal(20,2) NOT NULL COMMENT '下限',
  `F06` enum('QY','TY') NOT NULL DEFAULT 'QY' COMMENT '状态,QY:启用;TY:停用;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='等级信息';

-- ----------------------------
-- Table structure for T5129
-- ----------------------------
DROP TABLE IF EXISTS `S51`.`T5129`;
CREATE TABLE `S51`.`T5129` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` date NOT NULL COMMENT '时间',
  `F03` enum('BDBH','HTBH','GYBDBH') NOT NULL COMMENT 'BDBH:标的编号,HTBH:合同编号,GYBDBH:公益标的编号',
  `F04` int(11) NOT NULL COMMENT '编号',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `F02_UNIQUE` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标的编号记录';


DROP TABLE IF EXISTS `S51`.`T5131`;
CREATE TABLE `S51`.`T5131` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` enum('N','BJ','BX') NOT NULL DEFAULT 'N'  COMMENT '垫付类型，N：无平台垫付，BJ：本金垫付，BX：本息垫付',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  COMMENT='平台垫付信息表';

DROP TABLE IF EXISTS `S51`.`T5132`;
CREATE TABLE `S51`.`T5132` (
  `F01` INT(10) UNSIGNED NOT NULL COMMENT '订单类型编码,非自增',
  `F02` VARCHAR(20) NOT NULL COMMENT '类型名称',
  `F03` ENUM('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F04` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于个人,yes:属于;no:不属于;',
  `F05` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于企业,yes:属于;no:不属于;',
  `F06` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于机构,yes:属于;no:不属于;',
  `F07` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于平台,yes:属于;no:不属于;',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单类型';