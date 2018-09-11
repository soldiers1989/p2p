DROP TABLE IF EXISTS `S65`.`T6509`;
CREATE TABLE `S65`.`T6509` (
  `F01` int(10) unsigned NOT NULL ,
  `F02` int(10) unsigned NOT NULL ,
  `F03` decimal(20,2) NOT NULL DEFAULT '0.00' ,
  `F04` int(10) DEFAULT NULL ,
  `F05` varchar(50) DEFAULT NULL ,
  `F06` int(10) unsigned NOT NULL ,
  PRIMARY KEY (`F01`),
  KEY `F02` (`F02`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;