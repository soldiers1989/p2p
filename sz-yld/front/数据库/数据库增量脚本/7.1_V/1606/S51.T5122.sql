ALTER TABLE `S51`.`T5122`
ADD COLUMN `F09`  enum('yes','no') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'no' COMMENT '�Ƿ����ڵ�������,yes:����;no:������;' AFTER `F08`;

INSERT INTO `S51`.`T5122` VALUES ('1301', '������˵�����ȿ۳�', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1302', '���굣����ȷ���', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1303', '�������ȷ���', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1304', '�˹������������', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1305', 'Ĭ�ϵ������', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1306', '�ſ���ൣ����ȷ���', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1307', '������ծȨ�������', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('2005', '����ʧ�ܽ���', 'QY', 'yes', 'yes', 'yes', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('2006', '����ʧ�������ѷ���', 'QY', 'yes', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1106', '�ſ�������ö�ȷ���', 'QY', 'no', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('4004', '������ծȨ', 'QY', 'no', 'no', 'yes', 'no', 'no', 'no');