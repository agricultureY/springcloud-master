/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : account

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-25 17:49:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_account_one
-- ----------------------------
DROP TABLE IF EXISTS `tb_account_one`;
CREATE TABLE `tb_account_one` (
  `acct_id` varchar(16) NOT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `frozen` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`acct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
