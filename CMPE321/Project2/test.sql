-- Adminer 4.2.5 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `test`;
CREATE DATABASE `test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test`;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `Admin_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  PRIMARY KEY (`Admin_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `App_ID` int(2) NOT NULL AUTO_INCREMENT,
  `Doctor_ID` int(2) NOT NULL,
  `Patient_ID` int(2) NOT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  PRIMARY KEY (`App_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `branch`;
CREATE TABLE `branch` (
  `Branch_ID` int(2) NOT NULL AUTO_INCREMENT,
  `BranchName` varchar(30) NOT NULL,
  PRIMARY KEY (`Branch_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


DELIMITER ;;

CREATE TRIGGER `branch_bd` BEFORE DELETE ON `branch` FOR EACH ROW
begin delete from doctor where old.Branch_ID=doctor.Branch_ID;end;;

DELIMITER ;

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `Doctor_ID` int(2) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Branch_ID` int(2) NOT NULL,
  PRIMARY KEY (`Doctor_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


DELIMITER ;;

CREATE TRIGGER `doctor_bd` BEFORE DELETE ON `doctor` FOR EACH ROW
begin delete from appointment where old.Doctor_ID=appointment.Doctor_ID;end;;

DELIMITER ;

DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `Patient_ID` int(2) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  PRIMARY KEY (`Patient_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


-- 2017-05-16 16:00:54
