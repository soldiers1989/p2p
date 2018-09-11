/*
Navicat MySQL Data Transfer

Source Server         : 本地宝通金福
Source Server Version : 50621
Source Host           : localhost:3307
Source Database       : s50

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-01-31 18:07:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t5011_3`
-- ----------------------------
DROP TABLE IF EXISTS `S50`.`T5011_3`;
CREATE TABLE `S50`.`T5011_3` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) unsigned NOT NULL COMMENT '文章ID,参考T5011.F01',
  `F03` varchar(60) NOT NULL COMMENT '附件路径',
  `F04` varchar(50) NOT NULL COMMENT '附件大小',
  `F05` varchar(50) NOT NULL COMMENT '附件名称',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='信息披露附件';

-- ----------------------------
-- Records of t5011_3
-- ----------------------------

