# Android Cha8 - Minimal Chatting Application

[![Cha8 - Android Chatting Application](https://img.youtube.com/vi/Zvp8iEK9aMU/0.jpg)](https://www.youtube.com/watch?v=Zvp8iEK9aMU

## Please Install PHP Scripts along With MySQL Database

* PHP Scripts are located in PHP_SCRIPTS directory
* Please install MySQL Database and change credentials in config.php

```SQL
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `chat_message` (
  `id` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `message` text CHARACTER SET utf8mb4 NOT NULL,
  `time` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `chat_user` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `user` varchar(32) NOT NULL,
  `pass` varchar(32) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

ALTER TABLE `chat_message`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `chat_user`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `chat_message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE `chat_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;
```

* Please Change URL's in ChatActivity.java accordingly

```Java
    public static String registerURL = "http://localhost/com.softopian.cha8/register.php";
    public static String loginURL = "http://localhost/com.softopian.cha8/login.php";
    public static String sendURL = "http://localhost/com.softopian.cha8/send.php";
    public static String fetchURL = "http://localhost/com.softopian.cha8/fetch.php";
```