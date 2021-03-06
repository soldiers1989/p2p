-- 初始化常量部分
INSERT INTO S10._1010 VALUES('URL.ADDXXCZ_URL','/console/sina/sinaAddXxcz.htm','URL','线下充值申请地址');
INSERT INTO S10._1010 VALUES('URL.CHECKYESXXCZ_URL','/console/sina/sinaXxczshtg.htm','URL','线下充值审核通过地址');
INSERT INTO S10._1010 VALUES('URL.CHECKNOXXCZ_URL','/console/sina/sinaXxczqx.htm','URL','线下充值审核不通过地址');
INSERT INTO S10._1010 VALUES('URL.OPEN_ESCROW_GUIDE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/sina/openEscrowGuide.htm','URL','托管开户引导页面地址');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_USERREGISTER','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/sina/sinaUserRegister.htm','URL','第三方托管，用户注册地址');
INSERT INTO S10._1010 VALUES('URL.USER_CHARGE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/sina/charge.html','URL','用户充值链接地址');
INSERT INTO S10._1010 VALUES('URL.USER_WITHDRAW','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/sina/withdraw.html','URL','用户提现链接地址');
INSERT INTO S10._1010 VALUES('URL.CARD_MANAGE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/sina/bankcardlist.html','URL','银行卡管理');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_WITHDRAW','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/sina/sinaWithdraw.htm','URL','第三方托管，提现');
INSERT INTO S10._1010 VALUES('URL.PAY_CHARGE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/sina/sinaCharge.htm','URL','充值地址入口');
INSERT INTO S10._1010 VALUES('URL.PAY_BID_URL','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/sina/sinaBid.htm','URL','投资入口地址');
INSERT INTO S10._1010 VALUES('URL.BID_CHECK_URL','/console/sina/sinaBidRegister.htm','URL','标申请，审核通过URL');
INSERT INTO S10._1010 VALUES('URL.CWGL_MENU_PAGE','/WEB-INF/include/menu/sina/cwgl.jsp','URL','财务管理菜单页面路径');
INSERT INTO S10._1010 VALUES('SYSTEM.ESCROW_PREFIX','sina','SYSTEM','托管前缀');

-- 增加新浪专用通知报文记录表
DROP TABLE IF EXISTS `S71`.`SINANOTIFYLOG`;
CREATE TABLE `S71`.`SINANOTIFYLOG` (
  `F01` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(100) NOT NULL COMMENT '订单号',
  `F03` varchar(100) NOT NULL COMMENT '回调通知地址',
  `F04` varchar(1000) NOT NULL COMMENT '异步回调通知报文内容',
  `F05` datetime NOT NULL COMMENT '通知时间',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='新浪存管异步通知报文';

-- 表结构修改
ALTER TABLE `S65`.`T6508` ADD COLUMN `F04` varchar(200) DEFAULT NULL COMMENT '流标原因';
ALTER TABLE `S65`.`T6501` MODIFY COLUMN `F10` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '流水号';
ALTER TABLE `S65`.`T6509` MODIFY COLUMN `F05` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '流水单号,充值成功时记录';