DROP TABLE IF EXISTS `S51`.`T5132`;
CREATE TABLE `S51`.`T5132` (
  `F01` INT(10) UNSIGNED NOT NULL COMMENT '订单类型编码,非自增',
  `F02` VARCHAR(20) NOT NULL COMMENT '类型名称',
  `F03` ENUM('QY','TY') NOT NULL COMMENT '状态,QY:启用;TY:停用;',
  `F04` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于个人,yes:属于;no:不属于;',
  `F05` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于企业,yes:属于;no:不属于;',
  `F06` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于机构,yes:属于;no:不属于;',
  `F07` ENUM('yes','no') DEFAULT 'yes' COMMENT '是否属于平台,yes:属于;no:不属于;',
  PRIMARY KEY (`F01`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单类型';



INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10001,'充值','QY','yes','yes','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10002,'提现','QY','yes','yes','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10004,'逾期垫付','QY','no','no','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10005,'转入保证金','QY','no','no','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10006,'转出保证金','QY','no','no','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10007,'平台充值','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10008,'平台提现','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20001,'散标投资','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20002,'散标投资撤销','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20003,'散标放款','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20004,'散标还款','QY','yes','yes','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20005,'散标债权转让','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20006,'体验金投资','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20007,'体验金放款','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20008,'体验金还款','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20009,'体验金投资撤销','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20010,'加息券投资','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20011,'加息券放款','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20012,'加息券还款','QY','no','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20013,'加息券投资撤销','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(20014,'红包投资','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(30004,'提前还款','QY','yes','yes','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(50002,'公益标转账','QY','yes','yes','yes','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(50003,'商品购买转账','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(50004,'商品退款转账','QY','yes','no','no','yes');
INSERT INTO `S51`.`T5132` (`F01`, `F02`, `F03`, `F04`, `F05`, `F06`, `F07`) VALUES(10011,'线下充值','QY','yes','yes','yes','yes');