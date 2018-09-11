INSERT INTO S10._1010 VALUES('URL.CWGL_MENU_PAGE','/WEB-INF/include/menu/yeepay/cwgl.jsp','URL','财务管理菜单页面路径');
INSERT INTO S10._1010 VALUES('URL.AUTO_BID_INDEX','/user/financing/zdtb/index.htm','URL','自动投资页面');
INSERT INTO S10._1010 VALUES('URL.PAY_CHARGE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/yeepayCharge.htm','URL','充值地址入口');
INSERT INTO S10._1010 VALUES('URL.PAY_BID_URL','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/bid.htm','URL','投资入口地址');
INSERT INTO S10._1010 VALUES('SYSTEM.ESCROW_PREFIX','yeepay','SYSTEM','托管前缀');
INSERT INTO S10._1010 VALUES('SYSTEM.EXPERIENCE_RET_COUNT','20','SYSTEM','每次体验金返还条数');
INSERT INTO S10._1010 VALUES('URL.PAY_SBDF_URL','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/yeepay/yeepaySbdf.htm','URL','机构垫付地址');
INSERT INTO S10._1010 VALUES('URL.PTADVANCE_URL','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/console/yeepay/finance/dfgl/yeepayPTSbdf.htm','URL','平台垫付地址');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_USERREGISTER','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/yeeRegister.htm','URL','第三方托管，用户注册地址');
INSERT INTO S10._1010 VALUES('URL.USER_CHARGE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/yeepay/charge.html','URL','用户充值链接地址');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_BINDCARD','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/bidCardServlet.htm','URL','第三方托管，绑定银行卡');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_UNBINDCARD','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/unFastBindCardServlet.htm','URL','第三方托管，解除银行卡绑定');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_EXCHANGE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/zqExchangeServlet.htm','URL','第三方托管，债权转让');
INSERT INTO S10._1010 VALUES('URL.USER_WITHDRAW','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/yeepay/withdraw.html','URL','用户提现链接地址');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_WITHDRAW','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/withdrawServlet.htm','URL','第三方托管，提现');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_RESETMOBILE', '${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/resetMobileServlet.htm', 'URL', '第三方托管，手机号码修改');
INSERT INTO S10._1010 VALUES('URL.ESCROW_URL_RESETPASSWORD', '${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/resetPasswordServlet.htm', 'URL', '第三方托管，交易密码重置');
INSERT INTO S10._1010 VALUES('URL.AUTO_BID_AUTHORIZED','/user/yeepay/yeepayZztb.htm','URL','自动投资授权');
INSERT INTO S10._1010 VALUES('URL.PAY_PAYMENT_URL_SECOND','/user/yeepay/repaymentServlet.htm','URL','还款明细中手动还款成功跳转地址');
INSERT INTO S10._1010 VALUES('URL.PAY_PERPAYMENT_URL_SECOND','/user/yeepay/preRepaymentServlet.htm','URL','提前还款成功跳转地址');
INSERT INTO S10._1010 VALUES('PAY.CHARGE_MUST_WITHDRAWPSD','false','PAY','充值是否需要设置交易密码');
INSERT INTO S10._1010 VALUES('URL.SCOREORDER_PAYMENT_URL','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/yeeBuy.htm','URL','用户购买积分商城物品链接地址');
INSERT INTO S10._1010 VALUES('URL.PAY_BID_URL_4_GYB','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/donationBid.htm','URL','公益标投资入口地址');
INSERT INTO S10._1010 VALUES('SYSTEM.THIRD_DOMAIN','220.181.25.233','SYSTEM','第三方域名');
INSERT INTO S10._1010 VALUES('URL.OPEN_ESCROW_GUIDE','${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/yeepay/openEscrowGuide.htm','URL','托管开户引导页面地址');


DELETE FROM S10._1010 WHERE F01='URL.PAYBADCLAIM_URL';
INSERT INTO S10._1010 VALUES ('URL.PAYBADCLAIM_URL', '${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/yeepay/yeePayPayBadClaim.htm', 'URL', '不良债权购买地址');

INSERT INTO `S66`.`T6601` VALUES (11, '到期日冻结资金自动解冻任务', 'com.dimeng.p2p.scheduler.tasks.UnFreezeTask', 'returnInterest', '0 0 7 * * ?', 'ENABLE', '2015-11-25 14:22:00', '2015-11-25 14:22:00', '2015-11-25 14:18:06', NULL, '冻结资金自动解冻，每天早上七点执行一次');
DELETE FROM `S66`.`T6601` WHERE F01 = 3;
INSERT INTO `S66`.`T6601` VALUES (12, '易宝自动还款任务', 'com.dimeng.p2p.scheduler.tasks.RepaymentTask', 'yeepayRepayment', '0 0 23 * * ?', 'ENABLE', '2015-12-18 14:22:00', '2015-12-18 14:22:00', '2015-11-25 14:18:06', NULL, '易宝自动还款任务，每天晚上23点执行一次');
INSERT INTO `S66`.`T6601` VALUES (13, '易宝企业机构审核任务', 'com.dimeng.p2p.scheduler.tasks.QyRegisterCheckTask', 'qyCheck', '0 */3 * * * ?', 'ENABLE', '2015-12-18 14:22:00', '2015-12-18 14:22:00', '2015-11-25 14:18:06', NULL, '易宝企业机构审核任务，每隔3分钟执行一次');
INSERT INTO `S66`.`T6601` VALUES (14, '自动提交订单', 'com.dimeng.p2p.escrow.yeepay.task.TransferTask', 'transfer', '0 */3 * * * ?', 'ENABLE', '2016-01-17 15:29:55', '2016-01-17 15:29:55', '2016-01-15 15:03:31', null, '定时自动提交订单，每隔3分钟执行一次');

INSERT INTO `S51`.`T5122` VALUES ('1701', '平台划拨', 'QY', 'yes', 'no', 'no', 'yes', 'no', 'no');
UPDATE S51.T5122 SET F04='yes' WHERE F01 IN (1003,2003);


INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_ZZZZ');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_CHARGEQUERY');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_TRANSSTAT');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_USERAVLQUERY');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_USERCARDQUERY');

INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_WITHDRAWQUERY');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_FREEZE');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_UNFREEZE');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_CONFIRMTRANSFER');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_CZTH');

INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_HKTH');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_JD');
INSERT INTO `S10`.`_1021` VALUES (1000, 'P2P_C_FINANCE_YHZC');

DELETE FROM S50.`T5020`;
INSERT INTO S50.`T5020`(`F01`,`F02`,`F03`,`F04`) VALUES
('1','交通银行','QY','BOCO'),
('2','中国光大银行','QY','CEB'),
('3','上海浦东发展银行','QY','SPDB'),
('4','农业银行','QY','ABC'),
('5','中信银行','QY','ECITIC'),
('6','建设银行','QY','CCB'),
('7','中国民生银行','QY','CMBC'),
('8','平安银行','QY','SDB'),
('9','中国邮政储蓄','QY','PSBC'),
('10','招商银行','QY','CMBCHINA'),
('11','兴业银行','QY','CIB'),
('12','工商银行','QY','ICBC'),
('13','中国银行','QY','BOC'),
('14','北京银行','QY','BCCB'),
('15','广东发展银行','QY','GDB'),
('16','华夏银行','QY','HX'),
('17','西安市商业银行','QY','XAYH'),
('18','上海银行','QY','SHYH'),
('19','天津市商业银行','QY','TJYH'),
('20','深圳农村商业银行','QY','SZNCSYYH'),
('21','北京农商银行','QY','BJNCSYYH'),
('22','杭州市商业银行','QY','HZYH'),
('23','昆仑银行','QY','KLYH'),
('24','郑州银行','QY','ZHENGZYH'),
('25','温州银行','QY','WZYH'),
('26','汉口银行','QY','HKYH'),
('27','南京银行','QY','NJYH'),
('28','厦门银行','QY','XMYH'),
('29','南昌银行','QY','NCYH'),
('30','江苏银行','QY','JISYH'),
('31','中国东亚银行','QY','HKBEA'),
('32','成都银行','QY','CDYH'),
('33','宁波银行','QY','NBYH'),
('34','长沙银行','QY','CSYH'),
('35','河北银行','QY','HBYH'),
('36','广州银行','QY','GUAZYH');


DROP TABLE IF EXISTS S65.`T6517_ext`;
CREATE TABLE S65.`T6517_ext` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `F02` varchar(30) NOT NULL COMMENT '入账帐号',
  `F03` varchar(30) NOT NULL COMMENT '出帐帐号',
  `F04` enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否参数交易流水F:否,S是',
  `F05` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `F06` varchar(30) NOT NULL COMMENT '创建者',
  `F07` decimal(20,2) NOT NULL COMMENT '转账金额',
  `F08` enum('S','F') DEFAULT NULL COMMENT '处理结果，S:成功,F:失败',
  `F09` varchar(500) DEFAULT NULL COMMENT '备注',
  `F10` varchar(50) DEFAULT NULL COMMENT '流水号',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='自助转账记录';


DROP TABLE IF EXISTS S65.`T6517_ext_order`;
CREATE TABLE S65.`T6517_ext_order` (
  `F01` int(10) unsigned NOT NULL COMMENT '主键id',
  `F02` decimal(20,2) unsigned NOT NULL COMMENT '金额',
  `F03` varchar(30) NOT NULL COMMENT '出账账号id',
  `F04` varchar(30) NOT NULL COMMENT '入账账户id',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自助转账订单';

UPDATE S51.T5122 SET F04 = 'yes' WHERE F01 IN (1003,2003);
UPDATE S51.T5122 SET F04 = 'no' WHERE F01 IN (1002,2002);