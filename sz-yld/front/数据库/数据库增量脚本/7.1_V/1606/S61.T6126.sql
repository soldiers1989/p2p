DROP TABLE IF EXISTS `S61`.`T6126`;
CREATE TABLE `S61`.`T6126` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '�û��˺�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��������ID,�ο�T5122.F01',
  `F04` datetime NOT NULL COMMENT '����ʱ��',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '����',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '֧��',
  `F07` decimal(20,2) NOT NULL COMMENT '���',
  `F08` varchar(100) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='�������������׼�¼';
