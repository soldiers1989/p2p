INSERT INTO `S66`.`T6601` VALUES (11, 'ת�˶�ʱ����', 'com.dimeng.p2p.escrow.huifu.task.TransferTask', 'transfer', '0 */2 * * * ?', 'ENABLE', NULL, NULL, '2016-07-27 19:00:00', NULL, 'ת��[ÿ1���Ӵ���]-�ƹ��״γ�ֵ�����������귢�ţ����Ͷ�ʽ����ȸ������̻����ת��');

INSERT INTO `S66`.`T6601` VALUES ( 12, '�㸶��ҵ�����������', 'com.dimeng.p2p.scheduler.tasks.QyRegisterCheckTask', 'qyCheck', '0 */3 * * * ?', 'ENABLE', SYSDATE(), SYSDATE(), SYSDATE(), NULL, '�㸶��ҵ�����������ÿ��3����ִ��һ��' );

INSERT INTO `S66`.`T6601` VALUES ( 13, '�㸶���Զ��������', 'com.dimeng.p2p.escrow.huifu.task.BidAddAndCheckTask', 'bidStatusQuery', '0 */5 * * * ?', 'DISABLE', SYSDATE(), SYSDATE(), SYSDATE(), NULL, '�㸶���Զ��������ÿ��3����ִ��һ��' );