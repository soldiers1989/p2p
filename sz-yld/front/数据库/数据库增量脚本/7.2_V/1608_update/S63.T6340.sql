ALTER TABLE `S63`.`T6340`
MODIFY COLUMN `F04`  enum('register','recharge','firstrecharge','birthday','investlimit','foruser','tjsccz','tjsctz','tjtzze','exchange','integraldraw') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动类型：（register：注册赠送；recharge：单笔充值赠送；firstrecharge：首次充值赠送；birthday：生日赠送；investlimit：投资额度赠送；foruser：指定用户赠送；tjsccz：推荐首次充值奖励；tjsctz：推荐首次投资奖励；tjtzze：推荐投资总额奖励；exchange：积分兑换；integraldraw：积分抽奖）' AFTER `F03`;

