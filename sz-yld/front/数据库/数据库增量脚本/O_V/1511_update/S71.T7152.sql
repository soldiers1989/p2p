ALTER TABLE  `S71`.`T7152` 
ADD COLUMN `F11` enum('XXCS','XSCS') default 'XXCS' COMMENT '催收方式。XXCS:线下催收:线上催收';

ALTER TABLE `S71`.`T7152` 
MODIFY COLUMN `F04`  enum('FL','XC','DH','ZNX','DX','YJ') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '催收方式,DH:电话;XC:现场;FL法律,ZNX:站内信,DX:短信,YJ:邮件' AFTER `F03`;