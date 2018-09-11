/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : s62

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-10-23 19:18:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t6255`
-- ----------------------------
DROP TABLE IF EXISTS `S62`.`T6255`;
CREATE TABLE `S62`.`T6255` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `F02` int(10) NOT NULL COMMENT '垫付id，参考T6253.F01',
  `F03` decimal(10,2) NOT NULL COMMENT '金额',
  `F04` int(10) NOT NULL COMMENT '收款人（投资人）',
  `F05` int(10) NOT NULL COMMENT '交易类型',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='垫付明细表';


