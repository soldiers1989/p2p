CREATE TABLE `T5022` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '��Ƶ�ϴ�����ID',
  `F02` int(10) NOT NULL DEFAULT '0' COMMENT '�ö�ֵ',
  `F03` varchar(50) NOT NULL COMMENT '��Ƶ�ϴ�����',
  `F04` varchar(250) DEFAULT NULL COMMENT '�ļ�����',
  `F05` varchar(50) NOT NULL COMMENT '�ļ���С',
  `F06` varchar(20) NOT NULL COMMENT '�ļ���ʽ',
  `F07` enum('YFB','WFB') NOT NULL DEFAULT 'WFB' COMMENT '����״̬,WFB:δ����;YFB:�ѷ���;',
  `F08` int(1) NOT NULL DEFAULT '0' COMMENT '�Ƿ��Զ�����',
   `F09` int(10) NOT NULL COMMENT '������,�ο�T7010.F01',
  `F10` datetime NOT NULL COMMENT '�ϴ�ʱ��',
  `F11` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����޸�ʱ��',
  PRIMARY KEY (`F01`),
  KEY `F09` (`F09`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='��Ƶ����';