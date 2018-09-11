-- ----------------------------
-- Table structure for T7160
-- ----------------------------
DROP TABLE IF EXISTS `S71`.`T7160`;
CREATE TABLE `S71`.`T7160` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '站内信推广自增ID',
  `F02` varchar(50) NOT NULL COMMENT '标题',
  `F03` varchar(8000) NOT NULL COMMENT '内容',
  `F04` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接收人数',
  `F05` int(10) unsigned NOT NULL COMMENT '创建者ID,参考T7110.F01',
  `F06` datetime NOT NULL COMMENT '创建时间',
  `F07` enum('SY','ZDR') NOT NULL DEFAULT 'SY' COMMENT '发送对象,SY:所有;ZDR:指定人',
  PRIMARY KEY (`F01`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='站内信推广';