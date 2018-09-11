ALTER TABLE `S63`.`T6351`
MODIFY COLUMN `F11`  enum('forsold','sold','unsold') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（sold:已上架、unsold:已下架、 forsold：待上架）' AFTER `F10`;

