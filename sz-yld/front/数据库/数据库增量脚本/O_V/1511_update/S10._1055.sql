DROP TABLE IF EXISTS `S10`.`_1055`;
CREATE TABLE `S10`.`_1055` (
  `F01` int(10) unsigned NOT NULL COMMENT '�û�ID',
  `F02` varchar(50) NOT NULL COMMENT '��֤������',
  `F03` varchar(20) NOT NULL COMMENT '��ʾ����',
  `F04` varchar(20) NOT NULL COMMENT '��֤����',
  `F05` datetime NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`F01`,`F02`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='������֤���';