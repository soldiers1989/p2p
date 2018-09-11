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
  `F07` varchar(100) DEFAULT NULL COMMENT '第三方登录授权码',
  `F08` bigint(20) DEFAULT NULL COMMENT '授权码授权时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='第三方登录信息表';