ALTER TABLE `S62`.`T6231`
ADD COLUMN `F29`  enum('S','F') NOT NULL DEFAULT 'F' COMMENT '是否推荐标，S:是；F:否' AFTER `F28`;

ALTER TABLE `S62`.`T6231`
ADD COLUMN `F30`  datetime NULL COMMENT '设置推荐标的时间' AFTER `F29`;