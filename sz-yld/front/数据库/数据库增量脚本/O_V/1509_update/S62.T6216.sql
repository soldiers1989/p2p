CREATE TABLE `S62`.`T6216` (
  `F01` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `F02` varchar(100) NOT NULL,
  `F03` int(10) NOT NULL,
  `F04` enum('QY','TY') NOT NULL,
  `F05` decimal(20,2) unsigned NOT NULL,
  `F06` decimal(20,2) unsigned NOT NULL,
  `F07` int(10) unsigned NOT NULL,
  `F08` int(10) unsigned NOT NULL,
  `F09` decimal(20,2) unsigned NOT NULL,
  `F10` decimal(20,2) unsigned NOT NULL,
  `F11` varchar(100) DEFAULT NULL,
  `F12` decimal(20,2) DEFAULT NULL,
  `F13` decimal(20,2) DEFAULT NULL,
  `F14` decimal(20,2) DEFAULT NULL,
  `F15` decimal(20,2) DEFAULT NULL,
   PRIMARY KEY (`F01`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;