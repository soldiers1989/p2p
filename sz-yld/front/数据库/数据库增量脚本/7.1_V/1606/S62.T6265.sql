DROP TABLE IF EXISTS `S62`.`T6265`;
CREATE TABLE `S62`.`T6265` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT 'ת������ID,�ο�T6264.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '������ID,�ο�T6110.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '�����ID,�ο�T6110.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT 'ծȨ��ֵ',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '�Ϲ��۸�',
  `F07` datetime NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='����ծȨת�ü�¼';