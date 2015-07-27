DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `keywords`;

CREATE TABLE `customers` (
  `name` VARCHAR(30) NOT NULL,
  `info` VARCHAR (150),

  PRIMARY KEY ( `name` )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `keywords` (
  `keyword` VARCHAR (30) NOT NULL,

  PRIMARY KEY ( `keyword` )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `customer_keyword` (
  `name` VARCHAR(30) NOT NULL,
  `keyword` VARCHAR(30) NOT NULL,

  CONSTRAINT `cons_customer_fk`
    FOREIGN KEY `customer_fk` ( `name` ) REFERENCES `customers` ( `name` )
    ON DELETE CASCADE ON UPDATE CASCADE,

  CONSTRAINT `cons_keyword_fk`
    FOREIGN KEY `keyword_fk` ( `keyword` ) REFERENCES `keywords` ( `keyword` )
    ON DELETE CASCADE ON UPDATE CASCADE,

  PRIMARY KEY ( `name`, `keyword` )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

/* SELECT * FROM customer_keyword; */

