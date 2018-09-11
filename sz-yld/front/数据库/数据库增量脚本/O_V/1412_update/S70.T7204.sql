DROP TABLE IF EXISTS `S70`.`T7204`;
CREATE TABLE `S70`.`T7204` (
  `F01` int(4) NOT NULL DEFAULT '0' ,
  `F02` int(2) NOT NULL DEFAULT '0' ,
  `F03` decimal(20,2) DEFAULT '0.00' ,
  `F04` decimal(20,2) DEFAULT '0.00' ,
  `F05` decimal(20,2) DEFAULT '0.00' ,
  `F06` decimal(20,2) DEFAULT '0.00' ,
  `F07` decimal(20,2) DEFAULT '0.00' ,
  `F08` decimal(20,2) DEFAULT '0.00' ,
  `F09` decimal(20,2) DEFAULT '0.00' ,
  `F10` decimal(20,2) DEFAULT '0.00' ,
  `F11` decimal(20,2) DEFAULT '0.00' ,
  `F12` decimal(20,2) DEFAULT '0.00' ,
  `F13` int(11) NOT NULL  ,
  `F14` decimal(20,2) DEFAULT '0.00' ,
  PRIMARY KEY (`F01`,`F02`,`F13`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;