INSERT INTO `S66`.`T6601` VALUES (11, '手动还款对账', 'com.dimeng.p2p.escrow.fuyou.task.PaymentTask', 'taskPayment', '0 0/5 21 * * ?', 'ENABLE',  NULL, NULL, '2016-01-01 17:00:13', NULL, '手动还款对账[每天晚上9点到晚上9:55期间的每5分钟触发]');
INSERT INTO `S66`.`T6601` VALUES (12, '投标对账', 'com.dimeng.p2p.escrow.fuyou.task.BidTask', 'taskBid', '0 */5 * * * ?', 'ENABLE',  NULL, NULL,'2016-01-01 17:00:13', NULL, '投标对账[每5分钟触发]');
INSERT INTO `S66`.`T6601` VALUES (13, '放款对账', 'com.dimeng.p2p.escrow.fuyou.task.LoanTask', 'taskLoan', '0 0/5 22 * * ?', 'ENABLE',  NULL, NULL, '2016-01-01 17:00:13', NULL, '放款对账[每天晚上10点到晚上10:55期间的每5分钟触发]');
INSERT INTO `S66`.`T6601` VALUES (14, '提前还款对账', 'com.dimeng.p2p.escrow.fuyou.task.PrepaymentTask', 'taskPrepayment', '0 0/5 23 * * ?', 'ENABLE',  NULL, NULL, '2016-01-01 17:00:13', NULL, '提前还款对账[每天晚上11点到晚上11:55期间的每5分钟触发]');
INSERT INTO `S66`.`T6601` VALUES (15, '垫付自动定时对账', 'com.dimeng.p2p.escrow.fuyou.task.AdvanceTask', 'taskAdvance', '0 0/5 20 * * ?', 'ENABLE', NULL, NULL, '2016-01-01 17:00:13', NULL, '垫付自动定时对账[每天晚上8点到晚上8:55期间的每5分钟触发]');
INSERT INTO `S66`.`T6601` VALUES (16, '转账定时任务', 'com.dimeng.p2p.escrow.fuyou.task.TransferTask', 'taskTransfer', '0 */2 * * * ?', 'ENABLE', NULL, NULL, '2016-01-01 17:00:13', NULL, '转账[每2分钟触发]-收取用户成功服务费，推广首次充值奖励，奖励标发放等个人与商户间的转账');
INSERT INTO `S66`.`T6601` VALUES (17, '资金解冻定时任务', 'com.dimeng.p2p.escrow.fuyou.task.UnFreezeTask', 'unFreezeTask', '0 */5 * * * ?', 'ENABLE', NULL, NULL, '2016-01-01 17:00:13', NULL, '资金解冻[每5分钟触发]');

UPDATE S66.T6601 SET F03 ='com.dimeng.p2p.escrow.fuyou.task.AutoRepaymentTask' WHERE F01 = 3 AND F02='自动还款';
