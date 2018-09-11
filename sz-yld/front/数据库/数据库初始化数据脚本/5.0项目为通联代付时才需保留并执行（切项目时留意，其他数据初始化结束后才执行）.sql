INSERT INTO S10._1010 VALUES('URL.CWGL_MENU_PAGE','/WEB-INF/include/menu/allinpay/cwgl.jsp','URL','财务管理菜单页面路径');
INSERT INTO S10._1010 VALUES('SYSTEM.ESCROW_PREFIX','ALLINPAY','SYSTEM','支付标识');
INSERT INTO S10._1010 VALUES ('URL.CHECKWITHDRAW_URL', '/console/finance/allinpay/txgl/checkWithdraw.htm', 'URL', '提现审核放款地址');
INSERT INTO S10._1010 VALUES('URL.CHECK_URL', '/console/finance/allinpay/txgl/check.htm', 'URL', '提现审核地址');
INSERT INTO `S66`.`T6601` VALUES ('17', '提现对账定时器', 'com.dimeng.p2p.scheduler.tasks.WithdrawCheckTask', 'checkTask', '0 0/5 * * * ?', 'ENABLE', '2015-12-07 16:58:00', '2015-12-07 16:58:00', '2015-12-07 10:00:00', null, '定时提交执行订单,5秒中请求一次');

 

DELETE FROM S50.`T5020`;
INSERT INTO `S50`.`T5020`  VALUES ('1', '中国工商银行', 'QY', '0102');
INSERT INTO `S50`.`T5020`  VALUES ('2', '中国农业银行', 'QY', '0103');
INSERT INTO `S50`.`T5020`  VALUES ('3', '中国银行', 'QY', '0104');
INSERT INTO `S50`.`T5020`  VALUES ('4', '中国建设银行', 'QY', '0105');
INSERT INTO `S50`.`T5020`  VALUES ('5', '交通银行', 'QY', '0301');
INSERT INTO `S50`.`T5020`  VALUES ('6', '中信银行', 'QY', '0302');
INSERT INTO `S50`.`T5020`  VALUES ('7', '光大银行', 'QY', '0303');
INSERT INTO `S50`.`T5020`  VALUES ('8', '华夏银行', 'QY', '0304');
INSERT INTO `S50`.`T5020`  VALUES ('9', '中国民生银行', 'QY', '0305');
INSERT INTO `S50`.`T5020`  VALUES ('10', '平安银行', 'QY', '04105840');
INSERT INTO `S50`.`T5020`  VALUES ('11', '招商银行', 'QY', '0308');
INSERT INTO `S50`.`T5020`  VALUES ('12', '兴业银行', 'QY', '0309');
INSERT INTO `S50`.`T5020`  VALUES ('13', '浦发银行', 'QY', '0310');
INSERT INTO `S50`.`T5020`  VALUES ('14', '广发银行', 'QY', '0306');
INSERT INTO `S50`.`T5020`  VALUES ('15', '邮储银行', 'QY', '0403');
INSERT INTO `S50`.`T5020`  VALUES ('16', '大连银行', 'QY', '04202220');
INSERT INTO `S50`.`T5020`  VALUES ('17', '乐山商行', 'QY', '03136650');
INSERT INTO `S50`.`T5020`  VALUES ('18', '贵阳银行', 'QY', '04437010');
INSERT INTO `S50`.`T5020`  VALUES ('19', '北京银行', 'QY', '0313');
INSERT INTO `S50`.`T5020`  VALUES ('20', '宁波银行', 'QY', '04083320');
INSERT INTO `S50`.`T5020`  VALUES ('21', '南京银行', 'QY', '04243010');
INSERT INTO `S50`.`T5020`  VALUES ('22', '东莞银行', 'QY', '04256020');
INSERT INTO `S50`.`T5020`  VALUES ('23', '郑州银行', 'QY', '04354910');
INSERT INTO `S50`.`T5020`  VALUES ('24', '南昌银行', 'QY', '04484220');
INSERT INTO `S50`.`T5020`  VALUES ('25', '泉州银行', 'QY', '04643970');
INSERT INTO `S50`.`T5020`  VALUES ('26', '绵阳商行', 'QY', '04856590');
INSERT INTO `S50`.`T5020`  VALUES ('27', '枣庄银行', 'QY', '03134540');
INSERT INTO `S50`.`T5020`  VALUES ('28', '齐商银行', 'QY', '03134530');
INSERT INTO `S50`.`T5020`  VALUES ('29', '泰安银行', 'QY', '03134630');
INSERT INTO `S50`.`T5020`  VALUES ('30', '长安银行', 'QY', '03137950');
INSERT INTO `S50`.`T5020`  VALUES ('31', '农信山东', 'QY', '14144500');
INSERT INTO `S50`.`T5020`  VALUES ('32', '农信安徽', 'QY', '04023610');
INSERT INTO `S50`.`T5020`  VALUES ('33', '农信福建', 'QY', '04023910');
INSERT INTO `S50`.`T5020`  VALUES ('34', '农信江西', 'QY', '04024210');
INSERT INTO `S50`.`T5020`  VALUES ('35', '农信湖北', 'QY', '04025350');
INSERT INTO `S50`.`T5020`  VALUES ('36', '农信湖南', 'QY', '04025510');
INSERT INTO `S50`.`T5020`  VALUES ('37', '农信内蒙', 'QY', '04021910');
INSERT INTO `S50`.`T5020`  VALUES ('38', '农商重庆', 'QY', '04026530');
 

