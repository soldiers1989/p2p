DROP TABLE IF EXISTS `S65`.`T6529`;
CREATE TABLE `S65`.`T6529` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID,�ο�T6501.F01',
  `F02` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '����ծȨ����ID,�ο�T6264.F01',
  `F04` int(10) unsigned NOT NULL COMMENT '������ID,�ο�T6110.F01',
  `F05` int(10) unsigned NOT NULL COMMENT 'ծȨID,�ο�T6251.F01',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT 'ծȨ���',
  `F07` int(11) NOT NULL COMMENT '��������ID,�ο�T5122.F01',
  `F08` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '�Ƿ����,S:��;F:��;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����ծȨת�ö���';