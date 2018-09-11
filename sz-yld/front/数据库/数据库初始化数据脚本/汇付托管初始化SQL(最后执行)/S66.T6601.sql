INSERT INTO `S66`.`T6601` VALUES (11, '转账定时任务', 'com.dimeng.p2p.escrow.huifu.task.TransferTask', 'transfer', '0 */2 * * * ?', 'ENABLE', NULL, NULL, '2016-07-27 19:00:00', NULL, '转账[每1分钟触发]-推广首次充值奖励，奖励标发放，红包投资金额返还等个人与商户间的转账');

INSERT INTO `S66`.`T6601` VALUES ( 12, '汇付企业机构审核任务', 'com.dimeng.p2p.scheduler.tasks.QyRegisterCheckTask', 'qyCheck', '0 */3 * * * ?', 'ENABLE', SYSDATE(), SYSDATE(), SYSDATE(), NULL, '汇付企业机构审核任务，每隔3分钟执行一次' );

INSERT INTO `S66`.`T6601` VALUES ( 13, '汇付标自动审核任务', 'com.dimeng.p2p.escrow.huifu.task.BidAddAndCheckTask', 'bidStatusQuery', '0 */5 * * * ?', 'DISABLE', SYSDATE(), SYSDATE(), SYSDATE(), NULL, '汇付标自动审核任务，每隔3分钟执行一次' );