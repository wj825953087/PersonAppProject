/*
Navicat MySQL Data Transfer

Source Server         : 111.229.180.123
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : Demo

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-07-16 17:59:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jz_expend
-- ----------------------------
DROP TABLE IF EXISTS `jz_expend`;
CREATE TABLE `jz_expend` (
  `ex_id` varchar(40) COLLATE utf8_bin NOT NULL,
  `user_id` int(11) NOT NULL,
  `ex_money` double(100,2) NOT NULL,
  `ex_type` int(11) NOT NULL,
  `ex_account_type` int(11) NOT NULL,
  `ex_remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ex_time` datetime NOT NULL,
  PRIMARY KEY (`ex_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_expend
-- ----------------------------
INSERT INTO `jz_expend` VALUES ('0979b8f6a74011eab2b2525400e6d1a4', '1', '1000.00', '0', '1', null, '2020-06-05 11:19:00');
INSERT INTO `jz_expend` VALUES ('0fae0d44a74011eab2b2525400e6d1a4', '1', '1000.00', '1', '1', null, '2020-06-05 11:20:00');
INSERT INTO `jz_expend` VALUES ('28b18129a74011eab2b2525400e6d1a4', '1', '1000.00', '2', '1', null, '2020-06-05 11:20:00');
INSERT INTO `jz_expend` VALUES ('2e9de698a74011eab2b2525400e6d1a4', '1', '1000.00', '3', '1', null, '2020-06-05 11:21:00');
INSERT INTO `jz_expend` VALUES ('3bba4ca5a74011eab2b2525400e6d1a4', '1', '1000.00', '5', '1', null, '2020-06-05 11:21:00');
INSERT INTO `jz_expend` VALUES ('434f265ea74011eab2b2525400e6d1a4', '1', '1000.00', '6', '1', null, '2020-06-05 11:21:00');
INSERT INTO `jz_expend` VALUES ('4a75beb6a74011eab2b2525400e6d1a4', '1', '1000.00', '7', '1', null, '2020-06-05 11:21:00');
INSERT INTO `jz_expend` VALUES ('81207219a74011eab2b2525400e6d1a4', '1', '1000.00', '0', '1', null, '2020-05-01 11:23:00');
INSERT INTO `jz_expend` VALUES ('89dc1e57a74011eab2b2525400e6d1a4', '1', '1000.00', '1', '1', null, '2020-05-01 11:23:00');
INSERT INTO `jz_expend` VALUES ('91adbad9a74011eab2b2525400e6d1a4', '1', '1000.00', '2', '1', null, '2020-05-01 11:23:00');
INSERT INTO `jz_expend` VALUES ('98c73996a74011eab2b2525400e6d1a4', '1', '1000.00', '3', '1', null, '2020-05-02 11:23:00');
INSERT INTO `jz_expend` VALUES ('a0e45f50a74011eab2b2525400e6d1a4', '1', '1000.00', '5', '1', null, '2020-05-01 11:24:00');
INSERT INTO `jz_expend` VALUES ('ab32ea03a74011eab2b2525400e6d1a4', '1', '1000.00', '5', '1', null, '2020-05-01 11:24:00');
INSERT INTO `jz_expend` VALUES ('bbfcaabda74011eab2b2525400e6d1a4', '1', '1000.00', '6', '1', null, '2020-05-01 11:24:00');
INSERT INTO `jz_expend` VALUES ('cd140ec4a74011eab2b2525400e6d1a4', '1', '4000.00', '7', '1', null, '2020-05-01 11:25:00');
INSERT INTO `jz_expend` VALUES ('e035e33ba74011eab2b2525400e6d1a4', '1', '1000.00', '0', '1', null, '2020-04-10 11:25:00');
INSERT INTO `jz_expend` VALUES ('e962c6e6a74011eab2b2525400e6d1a4', '1', '1000.00', '1', '1', null, '2020-04-17 11:26:00');
INSERT INTO `jz_expend` VALUES ('f4b6a9aaa74011eab2b2525400e6d1a4', '1', '5000.00', '2', '1', null, '2020-04-04 11:26:00');

-- ----------------------------
-- Table structure for jz_expend_type
-- ----------------------------
DROP TABLE IF EXISTS `jz_expend_type`;
CREATE TABLE `jz_expend_type` (
  `id` int(11) NOT NULL,
  `expend_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `is_default` int(11) NOT NULL,
  `super_id` int(11) NOT NULL,
  `permissions` int(11) NOT NULL,
  `expend_pngId` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_expend_type
-- ----------------------------
INSERT INTO `jz_expend_type` VALUES ('0', '早餐', '1', '0', '1', '2131165287');
INSERT INTO `jz_expend_type` VALUES ('1', '午餐', '1', '0', '1', '2131165292');
INSERT INTO `jz_expend_type` VALUES ('2', '晚餐', '1', '0', '1', '2131165294');
INSERT INTO `jz_expend_type` VALUES ('3', '饮料水果', '1', '0', '1', '2131165290');
INSERT INTO `jz_expend_type` VALUES ('4', '买菜原料', '1', '0', '1', '2131165289');
INSERT INTO `jz_expend_type` VALUES ('5', '打车', '1', '0', '1', '2131165291');
INSERT INTO `jz_expend_type` VALUES ('6', '公交', '1', '0', '1', '2131165288');
INSERT INTO `jz_expend_type` VALUES ('7', '加油', '1', '0', '1', '2131165293');

-- ----------------------------
-- Table structure for jz_income
-- ----------------------------
DROP TABLE IF EXISTS `jz_income`;
CREATE TABLE `jz_income` (
  `in_id` varchar(40) COLLATE utf8_bin NOT NULL,
  `user_id` int(11) NOT NULL,
  `in_money` double(60,2) NOT NULL,
  `in_type` int(11) NOT NULL,
  `in_account_type` int(11) NOT NULL,
  `in_time` datetime NOT NULL,
  `in_remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`in_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_income
-- ----------------------------
INSERT INTO `jz_income` VALUES ('144ad1b6a74111eab2b2525400e6d1a4', '1', '8000.00', '1', '1', '2020-04-04 11:27:00', null);
INSERT INTO `jz_income` VALUES ('2eaa8faca74111eab2b2525400e6d1a4', '1', '3000.00', '7', '1', '2020-05-07 11:28:00', null);
INSERT INTO `jz_income` VALUES ('5729f295a73f11eab2b2525400e6d1a4', '1', '1000.00', '0', '1', '2020-06-03 11:14:00', null);
INSERT INTO `jz_income` VALUES ('6287f60ba74111eab2b2525400e6d1a4', '1', '3000.00', '3', '1', '2020-05-29 11:29:00', null);
INSERT INTO `jz_income` VALUES ('656bcdbda73f11eab2b2525400e6d1a4', '1', '1000.00', '0', '1', '2020-05-08 11:15:00', null);
INSERT INTO `jz_income` VALUES ('734ddb03a73f11eab2b2525400e6d1a4', '1', '1000.00', '0', '1', '2020-04-09 11:15:00', null);
INSERT INTO `jz_income` VALUES ('7d7e0ad7a73f11eab2b2525400e6d1a4', '1', '1000.00', '1', '1', '2020-06-05 11:15:00', null);
INSERT INTO `jz_income` VALUES ('841099bea73f11eab2b2525400e6d1a4', '1', '1000.00', '2', '1', '2020-06-05 11:16:00', null);
INSERT INTO `jz_income` VALUES ('900718d9a73f11eab2b2525400e6d1a4', '1', '1000.00', '3', '1', '2020-06-05 11:16:00', null);
INSERT INTO `jz_income` VALUES ('98fe174da73f11eab2b2525400e6d1a4', '1', '1000.00', '4', '1', '2020-06-05 11:16:00', null);
INSERT INTO `jz_income` VALUES ('aa658057a73f11eab2b2525400e6d1a4', '1', '1000.00', '6', '1', '2020-06-05 11:17:00', null);
INSERT INTO `jz_income` VALUES ('e99fed3cc06e11eab2b2525400e6d1a4', '10', '100.00', '0', '1', '2020-07-08 00:28:00', null);

-- ----------------------------
-- Table structure for jz_income_type
-- ----------------------------
DROP TABLE IF EXISTS `jz_income_type`;
CREATE TABLE `jz_income_type` (
  `id` int(11) NOT NULL,
  `income_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `is_default` int(11) NOT NULL,
  `super_id` int(11) NOT NULL,
  `permissions` int(11) NOT NULL,
  `income_pngId` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_income_type
-- ----------------------------
INSERT INTO `jz_income_type` VALUES ('0', '工资薪水', '1', '0', '1', '2131165305');
INSERT INTO `jz_income_type` VALUES ('1', '利息', '1', '0', '1', '2131165303');
INSERT INTO `jz_income_type` VALUES ('2', '兼职外快', '1', '0', '1', '2131165304');
INSERT INTO `jz_income_type` VALUES ('3', '营业收入', '1', '0', '1', '2131165301');
INSERT INTO `jz_income_type` VALUES ('4', '红包', '1', '0', '1', '2131165306');
INSERT INTO `jz_income_type` VALUES ('5', '销售额', '1', '0', '1', '2131165308');
INSERT INTO `jz_income_type` VALUES ('6', '退款返款', '1', '0', '1', '2131165307');
INSERT INTO `jz_income_type` VALUES ('7', '报销款', '1', '0', '1', '2131165302');

-- ----------------------------
-- Table structure for jz_transfer
-- ----------------------------
DROP TABLE IF EXISTS `jz_transfer`;
CREATE TABLE `jz_transfer` (
  `tf_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `tf_money` double(100,2) NOT NULL,
  `tf_type` int(11) NOT NULL,
  `tf_account_type` int(11) NOT NULL,
  `tf_remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `tf_time` date NOT NULL,
  PRIMARY KEY (`tf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_transfer
-- ----------------------------

-- ----------------------------
-- Table structure for jz_user
-- ----------------------------
DROP TABLE IF EXISTS `jz_user`;
CREATE TABLE `jz_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `phone` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_alias` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `headImage` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_user
-- ----------------------------
INSERT INTO `jz_user` VALUES ('1', '1', '1', '17878661842', '上善', '1', '', '2020-01-01');
INSERT INTO `jz_user` VALUES ('2', 'root', '123456', '17878661842', null, null, null, null);
INSERT INTO `jz_user` VALUES ('9', 'wujian', '123456', '17878661842', null, null, null, null);
INSERT INTO `jz_user` VALUES ('10', '17878661842', '123456', '17878661842', null, null, null, null);

-- ----------------------------
-- Table structure for jz_user_account
-- ----------------------------
DROP TABLE IF EXISTS `jz_user_account`;
CREATE TABLE `jz_user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `account_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `account_card_number` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `account_money` double(100,0) DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_user_account
-- ----------------------------
INSERT INTO `jz_user_account` VALUES ('1', '1', '现金', '0000', '0');
INSERT INTO `jz_user_account` VALUES ('1', '2', '现金', '0000', '0');
INSERT INTO `jz_user_account` VALUES ('1', '9', '现金', '0000', '0');
INSERT INTO `jz_user_account` VALUES ('1', '10', '现金', '0000', '0');
INSERT INTO `jz_user_account` VALUES ('2', '1', '支付宝', '0000', '0');
INSERT INTO `jz_user_account` VALUES ('2', '2', '农行', '6549', '0');
INSERT INTO `jz_user_account` VALUES ('3', '1', '微信', '0000', '0');
INSERT INTO `jz_user_account` VALUES ('3', '2', '银行', '4781', '0');

-- ----------------------------
-- Table structure for jz_user_financ_preference
-- ----------------------------
DROP TABLE IF EXISTS `jz_user_financ_preference`;
CREATE TABLE `jz_user_financ_preference` (
  `fp_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `fp_family_
fp_family_
 fp_family_structure` int(11) NOT NULL,
  `fp_annual_investment` int(11) NOT NULL,
  `fp_earnings` int(11) NOT NULL,
  `fp_investment_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`fp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of jz_user_financ_preference
-- ----------------------------

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `keyword` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '这是新闻', '新闻');
INSERT INTO `news` VALUES ('2', '1111', '数据');
INSERT INTO `news` VALUES ('3', '111234', '书籍');
INSERT INTO `news` VALUES ('4', '1', '新闻');
INSERT INTO `news` VALUES ('5', '1sssd', '新闻');
INSERT INTO `news` VALUES ('6', '1dddd', '书籍');
INSERT INTO `news` VALUES ('7', '1edee', '书籍');

-- ----------------------------
-- Table structure for text
-- ----------------------------
DROP TABLE IF EXISTS `text`;
CREATE TABLE `text` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of text
-- ----------------------------
