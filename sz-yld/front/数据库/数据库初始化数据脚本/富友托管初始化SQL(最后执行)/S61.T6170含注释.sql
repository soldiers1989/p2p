ALTER TABLE `S61`.`T6170`
ADD COLUMN `F06` enum('DJZ','YJD') NOT NULL DEFAULT 'DJZ' COMMENT '资金冻结状态：DJZ-冻结中 YJD-已解冻';

ALTER TABLE `S61`.`T6170`
ADD COLUMN `F07` varchar(50) COMMENT '解冻流水号';