DROP TABLE IF EXISTS `S71`.`T7180`;
CREATE TABLE `S71`.`T7180` (
  `F01` int(11) NOT NULL AUTO_INCREMENT,
  `F02` int(1) NOT NULL DEFAULT '1' ,
  `F03` varchar(100) DEFAULT NULL ,
  `F04` int(1) NOT NULL DEFAULT '0',
  `F05` varchar(500) DEFAULT NULL ,
  `F06` varchar(100) DEFAULT NULL ,
  `F07` int(1) NOT NULL COMMENT ,
  `F08` datetime DEFAULT NULL ,
  `F09` varchar(50) DEFAULT NULL ,
  `F10` datetime DEFAULT NULL,
  `F11` varchar(50) DEFAULT NULL,
  `F12` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`F01`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;