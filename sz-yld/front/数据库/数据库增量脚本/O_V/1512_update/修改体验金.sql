ALTER TABLE S63.T6340 CHANGE F03 F03 ENUM('redpacket','interest','experience') COMMENT  '�������ͣ���redpacket�������interest����Ϣ��experience�������';

ALTER TABLE S63.T6340 ADD COLUMN F22 ENUM('S','F') DEFAULT 'S' COMMENT 'ʹ����Ч���Ƿ�Ϊ���¼���,S:��;F:��';

ALTER TABLE S61.T6103 ADD COLUMN F14 INT(11) NOT NULL COMMENT '�id���ο�T6340.F01';

ALTER TABLE S61.T6103 ADD COLUMN F15 DATETIME DEFAULT NULL COMMENT 'ʹ��ʱ��';

ALTER TABLE `S61`.`T6103` MODIFY COLUMN `F07` INT(10) NOT NULL COMMENT '�������Ч������';

ALTER TABLE S61.T6103 ADD COLUMN F16 ENUM('true','false') DEFAULT 'true' COMMENT '�����Ͷ��������㷽ʽ(true:����;false:����)';
