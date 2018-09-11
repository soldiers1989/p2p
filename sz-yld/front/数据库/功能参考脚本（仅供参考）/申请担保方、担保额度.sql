DROP TABLE IF EXISTS `S61`.`T6125`;
CREATE TABLE `S61`.`T6125` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) NOT NULL COMMENT '�û�ID���ο�T6110.F01��',
  `F03` char(7) NOT NULL COMMENT '������',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '�������',
  `F05` enum('SQDCL','QXDCL','SQCG','SQSB','QXCG','QXSB') NOT NULL COMMENT '״̬��''SQDCL''�����뵣��������,''QXDCL''��ȡ������������,''SQCG''�����뵣���ɹ�,''SQSB''�����뵣��ʧ��,''QXCG''��ȡ�������ɹ�,''QXSB''��ȡ������ʧ��',
  `F06` datetime NOT NULL COMMENT '����ʱ��',
  `F07` int(10) DEFAULT NULL COMMENT '����ˣ��ο�T7110.F01��',
  `F08` datetime DEFAULT NULL COMMENT '���ʱ��',
  `F09` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `F10` varchar(200) DEFAULT NULL COMMENT '������',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='�����������';


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
