DROP TABLE IF EXISTS `S71`.`T7190`;
CREATE TABLE `S71`.`T7190` (
  `F01` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT 'һ���û�Ͷ�ʽ��',
  `F02` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT 'һ���û������',
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT 'һ���û���ֵ���',
  `F04` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT 'һ���û����ֽ��',
  `F05` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '�����û�Ͷ�ʽ��',
  `F06` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '�����û������',
  `F07` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '�����û���ֵ���',
  `F08` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '�����û����ֽ��',
  `F09` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `F10` varchar(50) NOT NULL COMMENT '����',
  `F11` varchar(50) NOT NULL COMMENT 'ҵ�񹤺�',
  PRIMARY KEY (`F10`,`F11`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ҵ��Աҵ��ͳ��';