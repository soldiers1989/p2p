

/**
 * 
 * 请先执行T5010_data.sql脚本初始化数据，再执行下面sql
 * 
 */
ALTER TABLE S50.T5011 ADD F14 int(10);

UPDATE S50.T5011,S50.T5010 SET T5011.F14 = T5010.F01   WHERE T5011.F02 = T5010.F02;

/**
 * 上面两条执行完，查看S50.T5011的F14字段是否有为空的数据，
 * 如果没有则可以继续执行下面sql语句，如果有为空的数据则查看对应的F02字段的值是否存在于T5010表中，不存在则在表中增加对应的数据，重新执行上面的update语句
 * 
 */

ALTER TABLE `S50`.`T5011` MODIFY COLUMN `F02`  int(10) NOT NULL DEFAULT '0' COMMENT '文章类别id,T5010.F01' AFTER `F01`;

UPDATE S50.T5011 SET F02 = F14;

ALTER TABLE S50.T5011 DROP COLUMN F14;