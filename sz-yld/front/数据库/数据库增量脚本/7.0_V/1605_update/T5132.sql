DROP TABLE IF EXISTS `S51`.`T5132`;
CREATE TABLE `S51`.`T5132` (
  `F01` INT(10) UNSIGNED NOT NULL COMMENT '�������ͱ���,������',
  `F02` VARCHAR(20) NOT NULL COMMENT '��������',
  `F03` ENUM('QY','TY') NOT NULL COMMENT '״̬,QY:����;TY:ͣ��;',
  `F04` ENUM('yes','no') DEFAULT 'yes' COMMENT '�Ƿ����ڸ���,yes:����;no:������;',
  `F05` ENUM('yes','no') DEFAULT 'yes' COMMENT '�Ƿ�������ҵ,yes:����;no:������;',
  `F06` ENUM('yes','no') DEFAULT 'yes' COMMENT '�Ƿ����ڻ���,yes:����;no:������;',
  `F07` ENUM('yes','no') DEFAULT 'yes' COMMENT '�Ƿ�����ƽ̨,yes:����;no:������;',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='��������';



INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10001,'��ֵ','QY','yes','yes','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10002,'����','QY','yes','yes','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10004,'���ڵ渶','QY','no','no','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10005,'ת�뱣֤��','QY','no','no','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10006,'ת����֤��','QY','no','no','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10007,'ƽ̨��ֵ','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10008,'ƽ̨����','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20001,'ɢ��Ͷ��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20002,'ɢ��Ͷ�ʳ���','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20003,'ɢ��ſ�','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20004,'ɢ�껹��','QY','yes','yes','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20005,'ɢ��ծȨת��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20006,'�����Ͷ��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20007,'�����ſ�','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20008,'����𻹿�','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20009,'�����Ͷ�ʳ���','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20010,'��ϢȯͶ��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20011,'��Ϣȯ�ſ�','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20012,'��Ϣȯ����','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20013,'��ϢȯͶ�ʳ���','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20014,'���Ͷ��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(30004,'��ǰ����','QY','yes','yes','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(50002,'�����ת��','QY','yes','yes','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(50003,'��Ʒ����ת��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(50004,'��Ʒ�˿�ת��','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10011,'���³�ֵ','QY','yes','yes','yes','yes');