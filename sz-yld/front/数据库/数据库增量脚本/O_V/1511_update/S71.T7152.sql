ALTER TABLE  `S71`.`T7152` 
ADD COLUMN `F11` enum('XXCS','XSCS') default 'XXCS' COMMENT '���շ�ʽ��XXCS:���´���:���ϴ���';

ALTER TABLE `S71`.`T7152` 
MODIFY COLUMN `F04`  enum('FL','XC','DH','ZNX','DX','YJ') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '���շ�ʽ,DH:�绰;XC:�ֳ�;FL����,ZNX:վ����,DX:����,YJ:�ʼ�' AFTER `F03`;