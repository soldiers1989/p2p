ALTER TABLE `S61`.`T6106`
MODIFY COLUMN `F05`  enum('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods','chargeScore','nopassreturn') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '��������:register:ע�ᡢsign:ǩ����invite:���롢invest:Ͷ�ʡ�cellphone:�ֻ���֤��mailbox:������֤��realname:ʵ����֤��trusteeship:��ͨ�������й��˻���charge:��ֵ��buygoods:�ֽ�����Ʒ���֡�nopassreturn:��˲�ͨ���������֡�chargeScore:���ֳ�ֵ��' AFTER `F04`;

ALTER TABLE `S61`.`T6106`
ADD COLUMN `F08`  int(11) NULL COMMENT '������ID,�ο�T7110.F01' AFTER `F07`;