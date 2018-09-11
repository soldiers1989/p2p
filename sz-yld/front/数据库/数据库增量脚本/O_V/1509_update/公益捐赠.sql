
-- ----------------------------
-- Table structure for `T5122`
-- ----------------------------

INSERT INTO S51.`T5122` VALUES ('1801', '������', 'QY');

INSERT INTO S51.`T5125` VALUES ('5001',0, '�������Э��', CURRENT_TIMESTAMP());

INSERT INTO `S51`.`T5126` VALUES (5001, 0, '', CURRENT_TIMESTAMP());

ALTER TABLE S51.T5129 CHANGE F03 F03  ENUM('BDBH','HTBH','GYBDBH') COMMENT 'BDBH:��ı��,HTBH:��ͬ���,GYBDBH:�����ı��';

DROP TABLE IF EXISTS S62.`T6291`;
CREATE TABLE S62.`T6291` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6230.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '�����ID,�ο�T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '�����',
  `F05` datetime NOT NULL COMMENT '���ʱ��',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='����¼';

DROP TABLE IF EXISTS S65.`T6554`;
CREATE TABLE S65.`T6554` (
  `F01` int(10) unsigned NOT NULL COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '����û�ID,�ο�T6110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '��ID,�ο�T6242.F01',
  `F04` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'Ͷ�ʽ��',
  `F05` int(10) unsigned DEFAULT NULL COMMENT 'Ͷ�ʼ�¼ID,�ο�T6291.F01,Ͷ�ʳɹ�ʱ��¼',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F03` (`F03`),
  KEY `F05` (`F05`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����';

DROP TABLE IF EXISTS `S62`.`T6242`;
CREATE TABLE `S62`.`T6242` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '�����û�ID,�ο�T7110.F01',
  `F03` varchar(200) NOT NULL COMMENT '������Ŀ����',
  `F04` int(10) unsigned NOT NULL COMMENT '������ID,�ο�T6211.F01',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT '��Ŀ���',
  `F06` decimal(20,2) unsigned NOT NULL COMMENT '��������',
  `F07` decimal(20,2) unsigned NOT NULL COMMENT '��Ͷ���',
  `F08` int(10) unsigned NOT NULL DEFAULT 0 DEFAULT '7' COMMENT '�������,��λ:��',
  `F09` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '�������,��λ:��',
  `F10` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '�Ƿ���������,S:��;F:��;',
  `F11` enum('SQZ','DSH','DFB','JKZ','YJZ','YZF') NOT NULL DEFAULT 'SQZ' COMMENT '״̬,SQZ:������;DSH:�����;DFB:������;JKZ:�����;YJZ:�Ѿ���;YZF:������;',
  `F12` varchar(20) DEFAULT NULL COMMENT '����ͼƬ����',
  `F13` datetime DEFAULT NULL COMMENT '����ʱ��,Ԥ����״̬��Ч',
  `F14` int(10) unsigned DEFAULT NULL COMMENT '���õȼ�,�ο�T5124.F01',
  `F15` datetime NOT NULL COMMENT '����ʱ��',
  `F16` datetime DEFAULT NULL COMMENT '���ʱ��',
  `F17` datetime DEFAULT NULL COMMENT '����ʱ��',
  `F18` datetime DEFAULT NULL COMMENT '�ſ�ʱ��',
  `F19` datetime DEFAULT NULL COMMENT '����ʱ��',
  `F20` datetime DEFAULT NULL COMMENT '����ʱ��',
  `F21` varchar(20) NOT NULL COMMENT '����',
  `F22` varchar(20) NOT NULL COMMENT '���淽',
  `F23` varchar(20) NOT NULL COMMENT '����ʺ�ID,Ĭ��ƽ̨�ʺ�ID',
  `F24` varchar(500)  NULL COMMENT '����',
  PRIMARY KEY (`F01`),
  UNIQUE KEY `T6242.F21_UNIQUE` (`F21`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='�������Ϣ';

DROP TABLE IF EXISTS `S62`.`T6243`;
CREATE TABLE `S62`.`T6243` (
  `F01` int(10) unsigned NOT NULL  COMMENT '�����ID',
  `F02` longtext COMMENT '������Ŀ�곫��������',
  `F03` varchar(20) DEFAULT NULL COMMENT '������ͼƬ����',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='�������չ��Ϣ';

DROP TABLE IF EXISTS `S62`.`T6244`;
CREATE TABLE `S62`.`T6244` (
  `F01` int(10) unsigned NOT NULL COMMENT '������ID,�ο�T6230.F01',
  `F02` int(10) unsigned NOT NULL COMMENT 'Э������ID,�ο�T5125.F01',
  `F03` int(11) NOT NULL COMMENT 'Э��汾��',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���Э��';

DROP TABLE IF EXISTS `S62`.`T6245`;
CREATE TABLE `S62`.`T6245` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '�����û�ID,�ο�T7110.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '������ĿID',
  `F04` varchar(50) DEFAULT NULL COMMENT '�������',
  `F05` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '�Ƿ񷢲�,S:��;F:��;',
  `F06` varchar(600) DEFAULT NULL COMMENT '��Ҫ����',
  `F07` datetime DEFAULT NULL COMMENT '����ʱ��',
  `F08` datetime NOT NULL COMMENT '����ʱ��',
  `F09` varchar(200) DEFAULT NULL COMMENT '�鿴����,������ַ',
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`),
  KEY `F04` (`F04`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='������չ��Ϣ��';

DROP TABLE IF EXISTS `S62`.`T6246`;
CREATE TABLE `S62`.`T6246` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '�����ID,�ο�T6242.F01',
  `F03` int(10) unsigned NOT NULL COMMENT 'Ͷ����ID,�ο�T6110.F01',
  `F04` decimal(20,2) unsigned NOT NULL COMMENT '�������',
  `F05` decimal(20,2) unsigned NOT NULL COMMENT 'ծȨ���',
  `F06` datetime NOT NULL COMMENT '����ʱ��',
  `F07` enum('F','S') NOT NULL DEFAULT 'F' COMMENT '�Ƿ�ȡ��,F:��;S:��;',
  PRIMARY KEY (`F01`),
  KEY `F02_idx` (`F02`),
  KEY `F03_idx` (`F03`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='����Ͷ�ʼ�¼';

DROP TABLE IF EXISTS `S62`.`T6247`;
CREATE TABLE `S62`.`T6247` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `F02` int(10) unsigned NOT NULL COMMENT '���ID,�ο�T6242.F01',
  `F03` int(10) unsigned NOT NULL COMMENT '�����,�ο�T7110.F01',
  `F04` datetime NOT NULL COMMENT '����ʱ��',
  `F05` enum('YCL','WCL') NOT NULL DEFAULT 'WCL' COMMENT '״̬,YCL:�Ѵ���;WCL:δ����;',
  `F06` text COMMENT '������',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='��������˼�¼'
