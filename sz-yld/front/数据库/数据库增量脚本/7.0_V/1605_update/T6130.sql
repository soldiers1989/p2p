ALTER TABLE `S61`.`T6130`
ADD COLUMN `F17`  char(6) NULL COMMENT '业务员工号' AFTER `F16`,
ADD COLUMN `F18`  enum('QY','TY') NULL DEFAULT NULL COMMENT '业务员状态（QY:启用,TY:停用）' AFTER `F17`;