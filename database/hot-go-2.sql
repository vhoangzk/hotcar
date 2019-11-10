-- Adminer 4.3.1 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `drivers`;
CREATE TABLE `drivers` (
  `user_id` int(11) NOT NULL,
  `is_online` int(11) NOT NULL,
  `is_busy` int(11) NOT NULL,
  `rate` double NOT NULL,
  `rate_count` int(11) NOT NULL,
  `driver_type` int(11) NOT NULL,
  `latitude` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `drivers` (`user_id`, `is_online`, `is_busy`, `rate`, `rate_count`, `driver_type`, `latitude`, `longitude`) VALUES
(6,	1,	1,	0,	0,	1,	'21.001839',	'105.805821');

DROP TABLE IF EXISTS `login_token`;
CREATE TABLE `login_token` (
  `user_id` int(11) NOT NULL,
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `time` int(11) NOT NULL,
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passenger_id` int(11) NOT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `vehicle_type` int(11) NOT NULL,
  `start_lat` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_long` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_location` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `end_lat` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `end_long` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `end_location` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `estimate_distance` double NOT NULL,
  `estimate_fare` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`,`passenger_id`,`driver_id`),
  KEY `passenger_id` (`passenger_id`),
  KEY `driver_id` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `trip_id` int(11) NOT NULL,
  `date_created` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `user_id` (`user_id`,`trip_id`),
  KEY `trip_id` (`trip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `trip`;
CREATE TABLE `trip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passenger_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `vehicle_type` int(11) NOT NULL,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `start_lat` int(11) NOT NULL,
  `start_long` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `end_lat` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `end_long` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `end_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `distance` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `estimate_fare` double DEFAULT NULL,
  `actual_fare` double NOT NULL,
  `driver_rate` double NOT NULL,
  `passenger_rate` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `passenger_id` (`passenger_id`,`driver_id`),
  KEY `driver_id` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gender` int(11) NOT NULL DEFAULT 1,
  `phone` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1,
  `type_tasker` int(11) NOT NULL DEFAULT 3,
  `rate` double NOT NULL DEFAULT 0,
  `rate_count` int(11) NOT NULL DEFAULT 0,
  `date_created` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `users` (`id`, `full_name`, `image`, `email`, `password`, `gender`, `phone`, `status`, `type_tasker`, `rate`, `rate_count`, `date_created`) VALUES
(3,	'Vu Hoang',	'71188785_2197816387186195_2857974764650102784_o.jpg',	'vvhoangzk@gmail.com',	'b59c67bf196a4758191e42f76670ceba',	1,	'11111',	1,	3,	10,	0,	1572598973),
(4,	'Vu Hoang',	'71188785_2197816387186195_2857974764650102784_o.jpg',	'vvhoangzk@gmail.com.vn',	'b59c67bf196a4758191e42f76670ceba',	1,	'11111',	1,	3,	10,	0,	1572599886),
(6,	'Luan',	'7997image1573290638.jpg',	'laixe@gmail.com',	'e10adc3949ba59abbe56e057f20f883e',	1,	'099999999',	1,	1,	10,	0,	1573279000),
(7,	'Hoang',	'8480image1573292843.jpg',	'hoangvuviet@vccorp.vn',	'c4ca4238a0b923820dcc509a6f75849b',	1,	'+84393586523',	1,	3,	10,	0,	1573292843);

DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE `vehicle` (
  `user_id` int(11) NOT NULL,
  `car_plate` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `model` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `type` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `document` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_created` int(11) NOT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image2` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `vehicle` (`user_id`, `car_plate`, `model`, `type`, `status`, `document`, `date_created`, `image`, `image2`) VALUES
(3,	'8888',	'2019',	1,	1,	'',	1572951647,	'57726584_2011178682520646_6270366933187559424_o1572951647.png',	'71188785_2197816387186195_2857974764650102784_o1572951647.jpg'),
(4,	'8888',	'2019',	1,	1,	'',	1573027767,	'57726584_2011178682520646_6270366933187559424_o1573027767.png',	'71188785_2197816387186195_2857974764650102784_o1573027767.jpg'),
(6,	'7777',	'6666',	1,	1,	'',	1573290962,	'4362image1573290962.jpg',	'4780image1573290962.jpg');

-- 2019-11-10 08:09:43
