-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 17, 2019 lúc 04:12 CH
-- Phiên bản máy phục vụ: 10.1.21-MariaDB
-- Phiên bản PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `hot-go`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `drivers`
--

CREATE TABLE `drivers` (
  `user_id` int(11) NOT NULL,
  `is_online` int(11) NOT NULL,
  `rate` double NOT NULL,
  `rate_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `login_token`
--

CREATE TABLE `login_token` (
  `user_id` int(11) NOT NULL,
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `time` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `passenger_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `vehicle_type` int(11) NOT NULL,
  `start_lat` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `start_long` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `start_location` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `end_lat` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `end_long` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `end_location` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `estimateDistance` double NOT NULL,
  `estimate_fare` varchar(250) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `trip_id` int(11) NOT NULL,
  `date_created` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `trip`
--

CREATE TABLE `trip` (
  `id` int(11) NOT NULL,
  `passenger_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `vehicle_type` int(11) NOT NULL,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `start_lat` int(11) NOT NULL,
  `start_long` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `start_location` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `end_lat` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `end_long` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `end_location` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `distance` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `estimate_fare` double DEFAULT NULL,
  `actual_fare` double NOT NULL,
  `driver_rate` double NOT NULL,
  `passenger_rate` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gender` int(11) NOT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `date_created` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `full_name`, `image`, `email`, `password`, `gender`, `phone`, `status`, `date_created`) VALUES
(1, 'sadas', 'asdasd', 'asdasd', 'asdasdas', 1, 'dasdasd', 1, 2321);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vehicle`
--

CREATE TABLE `vehicle` (
  `user_id` int(11) NOT NULL,
  `car_plate` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `model` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `type` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `document` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_created` int(11) NOT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `drivers`
--
ALTER TABLE `drivers`
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `login_token`
--
ALTER TABLE `login_token`
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`,`passenger_id`,`driver_id`),
  ADD KEY `passenger_id` (`passenger_id`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Chỉ mục cho bảng `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`),
  ADD KEY `user_id` (`user_id`,`trip_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- Chỉ mục cho bảng `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`),
  ADD KEY `passenger_id` (`passenger_id`,`driver_id`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

--
-- Chỉ mục cho bảng `vehicle`
--
ALTER TABLE `vehicle`
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT cho bảng `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT cho bảng `trip`
--
ALTER TABLE `trip`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
