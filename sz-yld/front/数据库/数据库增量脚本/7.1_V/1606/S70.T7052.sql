DROP TABLE IF EXISTS `S70`.`T7052`;
CREATE TABLE `S70`.`T7052` (
  `F01` varchar(50) NOT NULL DEFAULT '' COMMENT '����',
  `F02` int(11) NOT NULL DEFAULT '0' COMMENT 'ע���û���',
  `F03` int(11) NOT NULL DEFAULT '0' COMMENT 'PC��ע���û���',
  `F04` int(11) NOT NULL DEFAULT '0' COMMENT 'APP��ע���û���',
  `F05` int(11) NOT NULL DEFAULT '0' COMMENT '΢�Ŷ�ע���û���',
  `F06` int(11) NOT NULL DEFAULT '0' COMMENT '��̨ע���û���',
  `F07` int(11) NOT NULL DEFAULT '0' COMMENT '��¼�û���',
  `F08` int(11) NOT NULL DEFAULT '0' COMMENT '��ֵ�û���',
  `F09` int(11) NOT NULL DEFAULT '0' COMMENT '�����û���',
  `F10` int(11) NOT NULL DEFAULT '0' COMMENT 'Ͷ���û���',
  `F11` int(11) NOT NULL DEFAULT '0' COMMENT '����û���',
  `F12` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `F13` char(4) NOT NULL COMMENT '���',
  `F14` char(5) NOT NULL COMMENT '���ȣ����+���ȣ�',
  `F15` char(6) NOT NULL COMMENT '�·ݣ����+�·ݣ�',
  `F16` char(6) NOT NULL COMMENT '�ܣ����+�ܣ�',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ƽ̨�û���ͳ��';