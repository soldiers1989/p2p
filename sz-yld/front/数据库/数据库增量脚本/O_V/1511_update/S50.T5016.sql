ALTER TABLE `S50`.`T5016`
MODIFY COLUMN `F12` enum('APP','GYJZ','PC','APPGYJZ') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PC' COMMENT '广告类型' AFTER `F11`;