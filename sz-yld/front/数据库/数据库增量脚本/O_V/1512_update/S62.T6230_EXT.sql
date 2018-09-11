-- ----------------------------
-- Table structure for T6230_EXT
-- ----------------------------
DROP TABLE IF EXISTS S62.`T6230_EXT`;
CREATE TABLE S62.`T6230_EXT` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长 流水号',
  `F02` int(11) NOT NULL COMMENT '用户ID S61.T6110 F01',
  `F03` int(11) NOT NULL COMMENT '标的ID S62.T6230',
  `F04` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否开启自动还款',
  `F05` varchar(50) NOT NULL DEFAULT '' COMMENT '易宝返回的流水号',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `Unique_F01_F03` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='自动还款授权信息';