
ALTER TABLE `S61`.`T6110`
MODIFY COLUMN `F08`  enum('ZC','PCZC','HTTJ') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PCZC' COMMENT '注册来源,PCZC:PC注册;WXZC:微信注册;APPZC:APP注册;HTTJ:后台添加';

UPDATE `S61`.`T6110` SET F08='PCZC' WHERE F08='ZC';

ALTER TABLE `S61`.`T6110`
MODIFY COLUMN `F08`  enum('WXZC','APPZC','PCZC','HTTJ') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PCZC' COMMENT '注册来源,PCZC:PC注册;WXZC:微信注册;APPZC:APP注册;HTTJ:后台添加';

ALTER TABLE `S61`.`T6110`
ADD COLUMN `F17`  enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否允许投资：S:允许投资,F:禁止投资';

ALTER TABLE `S61`.`T6110`
ADD COLUMN `F18`  enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否开启过投资功能：S:是,F:否';

