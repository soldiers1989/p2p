ALTER TABLE `S61`.`T6161`
ADD COLUMN `F19`  varchar(20) NULL COMMENT '������ô���',
ADD COLUMN `F20`  varchar(10) NULL COMMENT '�Ƿ���֤��һ';

ALTER TABLE `S61`.`T6161`
MODIFY COLUMN `F03`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Ӫҵִ�յǼ�ע���,Ψһ';


ALTER TABLE S61.`T6161`
ADD COLUMN `F21`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '�����������֤';