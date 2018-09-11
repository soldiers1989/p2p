ALTER TABLE `S51`.`T5122`
ADD COLUMN `F09`  enum('yes','no') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'no' COMMENT '是否属于担保类型,yes:属于;no:不属于;' AFTER `F08`;

INSERT INTO `S51`.`T5122` VALUES ('1301', '发标审核担保额度扣除', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1302', '流标担保额度返还', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1303', '还款担保额度返还', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1304', '人工调整担保额度', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1305', '默认担保额度', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1306', '放款多余担保额度返还', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('1307', '购买不良债权额度赠送', 'QY', 'no', 'no', 'no', 'no', 'no', 'yes');
INSERT INTO `S51`.`T5122` VALUES ('2005', '提现失败金额返还', 'QY', 'yes', 'yes', 'yes', 'no', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('2006', '提现失败手续费返还', 'QY', 'yes', 'yes', 'yes', 'yes', 'no', 'no');
INSERT INTO `S51`.`T5122` VALUES ('1106', '放款多余信用额度返还', 'QY', 'no', 'no', 'no', 'no', 'yes', 'no');
INSERT INTO `S51`.`T5122` VALUES ('4004', '购买不良债权', 'QY', 'no', 'no', 'yes', 'no', 'no', 'no');