-- ----------------------------
-- Table structure for T6171
-- ----------------------------
DROP TABLE IF EXISTS `S61`.`T6171`;
CREATE TABLE `S61`.`T6171` (
  `F01` int(10) NOT NULL COMMENT 'T6110ID',
  `F03` enum('Y','N') DEFAULT 'N' COMMENT '�Ƿ�������Ȩ',
  `F04` varchar(100) DEFAULT NULL COMMENT '���˻�QQ����Ψһ��ʶ',
  `F05` datetime DEFAULT NULL COMMENT '����¼ʱ��',
  `F06` varchar(2) DEFAULT 'N' COMMENT '�Ƿ���qq��Ȩ',
  `F07` varchar(100) DEFAULT NULL COMMENT '��������¼��Ȩ��',
  `F08` bigint(20) DEFAULT NULL COMMENT '��Ȩ����Ȩʱ��'
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='��������¼��Ϣ��';