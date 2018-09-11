-- ----------------------------
-- Table structure for T6230_EXT
-- ----------------------------
DROP TABLE IF EXISTS S62.`T6230_EXT`;
CREATE TABLE S62.`T6230_EXT` (
  `F01` int(11) NOT NULL AUTO_INCREMENT COMMENT '������ ��ˮ��',
  `F02` int(11) NOT NULL COMMENT '�û�ID S61.T6110 F01',
  `F03` int(11) NOT NULL COMMENT '���ID S62.T6230',
  `F04` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '�Ƿ����Զ�����',
  `F05` varchar(50) NOT NULL DEFAULT '' COMMENT '�ױ����ص���ˮ��',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `Unique_F01_F03` (`F02`,`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='�Զ�������Ȩ��Ϣ';