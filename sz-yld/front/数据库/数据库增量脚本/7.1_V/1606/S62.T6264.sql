DROP TABLE IF EXISTS `S62`.`T6264`;
CREATE TABLE `S62`.`T6264` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` varchar(20) NOT NULL COMMENT 'ծȨ���',
  `F03` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F04` enum('DSH','ZRZ','YZR','YXJ','ZRSB') NOT NULL COMMENT '״̬,DSH:�����;ZRZ:ת����;YZR:��ת��;YXJ:���¼�;ZRSB:ת��ʧ��',
  `F05` int(10) unsigned DEFAULT NULL COMMENT '��������',
  `F06` int(10) unsigned NOT NULL COMMENT '�껹��ID,�ο�T6252.F01',
  `F07` datetime NOT NULL COMMENT '����ʱ��',
  `F08` datetime NOT NULL COMMENT '����ʱ��',
  `F09` decimal(20,2) DEFAULT NULL COMMENT 'ծȨ��ֵ',
  `F10` decimal(20,2) DEFAULT NULL COMMENT 'ת�ü۸�',
  `F11` varchar(60) DEFAULT NULL COMMENT '��ע����˲�ͨ��ԭ���ֶ��¼ܣ��Զ��¼ܣ�',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='����ծȨת������';
