DROP TABLE IF EXISTS `S61`.`T6193`;
CREATE TABLE `S61`.`T6193` (
  `F01` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F02` varchar(100) NOT NULL COMMENT '问题描述',
  `F03` int(11) NOT NULL COMMENT '排序字段',
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='密码问题表';


-- ----------------------------
-- Records of t6193
-- ----------------------------
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('1', '您母亲的姓名是？', '1');
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('2', '您配偶的姓名是？', '2');
INSERT INTO `S61`.`T6193` (F01, F02, F03)VALUES ('3', '您的出生地是？', '3');
INSERT INTO `S61`.`T6193` (F01, F02, F03)VALUES ('4', '您父亲的姓名是？', '4');
INSERT INTO `S61`.`T6193` (F01, F02, F03)VALUES ('5', '您高中班主任的名字是？', '5');
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('6', '您初中班主任的名字是？', '6');
INSERT INTO `S61`.`T6193` (F01, F02, F03)VALUES ('7', '您小学班主任的名字是？', '7');
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('8', '您的小学校名是？', '8');
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('9', '您的学号（或工号）是？', '9');
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('10', '您父亲的生日是？', '10');
INSERT INTO `S61`.`T6193` (F01, F02, F03)VALUES ('11', '您母亲的生日是？', '11');
INSERT INTO `S61`.`T6193`(F01, F02, F03) VALUES ('12', '您配偶的生日是？', '12');
