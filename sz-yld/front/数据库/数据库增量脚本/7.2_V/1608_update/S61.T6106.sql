ALTER TABLE `S61`.`T6106`
MODIFY COLUMN `F05`  enum('register','sign','invite','invest','cellphone','mailbox','realname','trusteeship','charge','buygoods','chargeScore','nopassreturn') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '积分类型:register:注册、sign:签到、invite:邀请、invest:投资、cellphone:手机认证、mailbox:邮箱认证、realname:实名认证、trusteeship:开通第三方托管账户、charge:充值、buygoods:现金购买商品积分、nopassreturn:审核不通过返还积分、chargeScore:积分充值）' AFTER `F04`;

ALTER TABLE `S61`.`T6106`
ADD COLUMN `F08`  int(11) NULL COMMENT '操作人ID,参考T7110.F01' AFTER `F07`;