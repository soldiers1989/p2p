DROP TABLE IF EXISTS `S61`.`T6104`;
CREATE TABLE `S61`.`T6104` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT 'ƽ̨�˺�ID,�ο�T6101.F01',
  `F03` int(10) unsigned DEFAULT NULL COMMENT '����id,�ο�S65.T6501 F01',
  `F04` mediumint(8) unsigned NOT NULL COMMENT '��������ID,�ο�T5122.F01',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '����',
  `F06` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '֧��',
  `F07` enum('DTJ','CG','SB') NOT NULL DEFAULT 'DTJ' COMMENT '����״̬��DTJ:���ύ,CG:�ɹ�,SB:ʧ��',
  `F08` datetime NOT NULL COMMENT '����ʱ��',
  `F09` int(10) NOT NULL COMMENT '�����ˣ��ο� S71.T7110 F01',
  `F10` varchar(300) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`F01`),
  KEY `F03` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='ƽ̨������Ϣ';