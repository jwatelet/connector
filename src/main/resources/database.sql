DROP TABLE IF EXISTS `customer_keyword`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `keywords`;

CREATE TABLE `customers` (
  `id` INT  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `info` VARCHAR (150),

  PRIMARY KEY ( `id` )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `keywords` (
  `id` INT  NOT NULL AUTO_INCREMENT,
  `keyword` VARCHAR (30) NOT NULL,

  PRIMARY KEY ( `id` )
) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `customer_keyword` (
  `name_id` INT NOT NULL,
  `keyword_id` INT NOT NULL,

  PRIMARY KEY ( `name_id`, `keyword_id` ),

  CONSTRAINT `cons_customer_fk`
    FOREIGN KEY `customer_fk` ( `name_id` ) REFERENCES `customers` ( `id` )
    ON DELETE CASCADE ON UPDATE CASCADE,

  CONSTRAINT `cons_keyword_fk`
    FOREIGN KEY `keyword_fk` ( `keyword_id` ) REFERENCES `keywords` ( `id` )
    ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


INSERT INTO `customers` (`id`, `name`, `info`) VALUES (1, 'Google', 'Technology company specializing in Internet-related services and products');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (2, 'Yahoo!', 'Technology company specializing in Internet-related services and products');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (3, 'Apple', 'Designs, develops, and sells consumer electronics, computer software, online services, and personal computers.');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (4, 'Microsoft', 'Develops, manufactures, licenses, supports and sells computer software, consumer electronics and personal computers and services.');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (5, 'Facebook', 'An online social networking service.');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (6, 'Twitter', 'An online social networking service that enables users to send and read short 140-character messages called "tweets".');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (7, 'Samsung', 'A South Korean multinational conglomerate company.');
INSERT INTO `customers` (`id`, `name`, `info`) VALUES (8, 'Sony', 'A Japanese multinational conglomerate corporation.');

INSERT INTO `keywords` (`id`, `keyword`) VALUES (1, 'search'), (2, 'android'), (3, 'ad'), (4, 'pc'),
  (5, 'computer'), (6, 'google'), (7, 'yahoo'), (8, 'apple'), (9, 'facebook'), (10, 'twitter'),
  (11, 'samsung'), (12, 'sony'), (13, 'microsoft'),  (14, 'asus'), (15, 'youtube'), (16, 'play'),
  (17, 'maps'), (18, 'docs'), (19, 'iphone'), (20, 'windows'), (21, 'office'), (22, 'music'), (23, 'game'),
  (24, 'tv'), (25, 'electronic'), (26, 'phone'), (27, 'tablet'), (28, 'laptop'), (29, 'tweet'), (30, 'friend'),
  (31, 'post'), (32, 'like'), (33, 'xperia'),  (34, 'galaxy'), (35, 'surface'), (36, 'watch'), (37, 'nexus'),
  (38, 'ipod'), (39, 'ipad'), (40, 'macbook'), (41, 'finance');

INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (1, 1), (1, 2), (1, 3), (1, 6), (1, 15), (1, 16), (1, 17), (1, 18), (1, 22), (1, 36), (1, 37), (1, 26);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (2, 1), (2, 7), (2, 3), (2, 41);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (3, 4), (3, 5), (3, 8), (3, 17), (3, 19), (3, 22), (3, 26), (3, 28), (3, 36), (3, 38),  (3, 39), (3, 40);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (4, 4), (4, 5), (4, 13), (4, 17), (4, 19), (4, 22), (4, 26), (4, 28), (4, 35), (4, 36), (4, 38), (4, 39), (4, 40);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (5, 9), (5, 30), (5, 31), (5, 32);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (6, 3), (6, 29), (6, 31);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (7, 2), (7, 11), (7, 24), (7, 25), (7, 26), (7, 27), (7, 28) ,(7, 34);
INSERT INTO `customer_keyword` (`name_id`, `keyword_id`) VALUES (8, 2), (8, 12), (8, 24), (8, 25), (8, 26), (8, 27), (8, 28), (8, 33);
