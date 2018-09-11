/*
Navicat MySQL Data Transfer

Source Server         : 
Source Server Version : 50514
Source Host           : 192.168.0.216:3314
Source Database       : S71

Target Server Type    : MYSQL
Target Server Version : 50514
File Encoding         : 65001

Date: 2015-05-13 17:19:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T7181`
-- ----------------------------
DROP TABLE IF EXISTS `S71`.`T7181`;
CREATE TABLE `S71`.`T7181` (
  `F01` int(11) NOT NULL DEFAULT '0' ,
  `F02` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' ,
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;


