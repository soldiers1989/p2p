DROP TABLE IF EXISTS `S62`.`T6266`;
CREATE TABLE `S62`.`T6266` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) NOT NULL COMMENT 'ת��id���ο�T6265.F01',
  `F03` decimal(10,2) NOT NULL COMMENT '���',
  `F04` int(10) NOT NULL COMMENT '�տ��ˣ�Ͷ���ˣ�',
  `F05` int(10) NOT NULL COMMENT '��������',
  `F06` int(10) NOT NULL COMMENT 'ծȨid��T6251.F01',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F04_idx` (`F04`),
  KEY `F06_idx` (`F06`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='����ծȨת�û�����ϸ��';