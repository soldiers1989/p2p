-- T6230增加F34
ALTER TABLE S62.T6230 ADD COLUMN `F34` varchar(20) DEFAULT NULL COMMENT '第三方使用标号';
-- T6242增加F25
ALTER TABLE S62.T6242 ADD COLUMN `F25` varchar(20) DEFAULT NULL COMMENT '第三方使用标号';
-- T6260增加F34
ALTER TABLE S62.T6260 ADD COLUMN `F11` varchar(20) DEFAULT NULL COMMENT '第三方债权使用编码';
-- T6264增加F34
ALTER TABLE S62.T6264 ADD COLUMN `F12` varchar(20) DEFAULT NULL COMMENT '第三方债权使用编码';
-- T6501增加F14
ALTER TABLE S65.T6501 ADD COLUMN `F14` varchar(32) DEFAULT NULL COMMENT '托管请求第三方时订单号';