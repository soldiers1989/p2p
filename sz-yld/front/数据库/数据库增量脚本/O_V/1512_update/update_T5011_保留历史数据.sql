

/**
 * 
 * ����ִ��T5010_data.sql�ű���ʼ�����ݣ���ִ������sql
 * 
 */
ALTER TABLE S50.T5011 ADD F14 int(10);

UPDATE S50.T5011,S50.T5010 SET T5011.F14 = T5010.F01   WHERE T5011.F02 = T5010.F02;

/**
 * ��������ִ���꣬�鿴S50.T5011��F14�ֶ��Ƿ���Ϊ�յ����ݣ�
 * ���û������Լ���ִ������sql��䣬�����Ϊ�յ�������鿴��Ӧ��F02�ֶε�ֵ�Ƿ������T5010���У����������ڱ������Ӷ�Ӧ�����ݣ�����ִ�������update���
 * 
 */

ALTER TABLE `S50`.`T5011` MODIFY COLUMN `F02`  int(10) NOT NULL DEFAULT '0' COMMENT '�������id,T5010.F01' AFTER `F01`;

UPDATE S50.T5011 SET F02 = F14;

ALTER TABLE S50.T5011 DROP COLUMN F14;