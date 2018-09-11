ALTER TABLE `S63`.`T6350`
MODIFY COLUMN `F07`  enum('kind','virtual') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'kind' COMMENT '类别属性：kind:实物，virtual:虚拟' AFTER `F06`;

