DROP TABLE IF EXISTS `S51`.`T5122`;
CREATE TABLE `S51`.`T5122` (
  `F01` int(10) unsigned NOT NULL COMMENT '交易类型编码,非自增',
  `F02` varchar(20) NOT NULL COMMENT '类型名称',
  `F03` enum('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F04` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于个人,yes:属于;no:不属于;',
  `F05` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于企业,yes:属于;no:不属于;',
  `F06` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于机构,yes:属于;no:不属于;',
  `F07` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于平台,yes:属于;no:不属于;',
  `F08` enum('yes','no') DEFAULT 'yes' COMMENT '是否属于信用类型,yes:属于;no:不属于;',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易类型';

INSERT INTO `S51`.`T5122` VALUES ('1001', '充值', 'QY', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1002', '充值手续费', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1003', '充值成本', 'QY', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1004', '线下充值', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1005', '平台充值', 'QY', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('2001', '提现', 'QY', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('2002', '提现手续费', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('2003', '提现成本', 'QY', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('2004', '平台提现', 'QY', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('3001', '投标', 'QY', 'yes', 'no', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('3002', '投标撤销', 'QY', 'yes', 'no', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('3003', '体验金投资', 'QY', 'yes', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('4001', '债权转让手续费', 'QY', 'yes', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('4002', '卖出债权', 'QY', 'yes', 'no', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('4003', '买入债权', 'QY', 'yes', 'no', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('6001', '借款', 'QY', 'yes', 'yes', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7001', '本金', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7002', '利息', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7004', '逾期罚息', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7005', '提前还款违约金', 'QY', 'yes', 'yes', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7007', '违约金手续费', 'QY', 'yes', 'yes', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7008', '投标奖励', 'QY', 'yes', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7009', '红包奖励', 'QY', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('7010', '加息奖励', 'QY', 'yes', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('8001', '活动费用', 'QY', 'yes', 'yes', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('9001', '有效推广', 'QY', 'yes', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('9002', '持续推广', 'QY', 'yes', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1101', '发标审核信用扣除', 'QY', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1102', '还款信用返还', 'QY', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1103', '流标信用返还', 'QY', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1104', '人工调整信用额度', 'QY', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1105', '默认信用额度', 'QY', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1201', '成交服务费', 'QY', 'yes', 'yes', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1202', '理财管理费', 'QY', 'yes', 'yes', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1401', '风险保证金充值', 'QY', 'no', 'no', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1402', '风险保证金提现', 'QY', 'no', 'no', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1701', '平台划拨', 'QY', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1702', '商品交易', 'QY', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1703', '商品退款', 'QY', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1704', '撤销商品交易', 'QY', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1801', '公益捐款', 'QY', 'yes', 'yes', 'yes', 'yes', 'no');
