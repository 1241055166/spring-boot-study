/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : imoa_dev

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 11/01/2022 16:59:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for feedback_type_object
-- ----------------------------
DROP TABLE IF EXISTS `feedback_type_object`;
CREATE TABLE `feedback_type_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `dealing_with_people` varchar(50) DEFAULT NULL COMMENT '处理人',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `zz_admin_log`;
CREATE TABLE `zz_admin_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4,
  `created_date` datetime DEFAULT NULL,
  `error_content` text CHARACTER SET utf8mb4,
  `function_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `module_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `stack_message` text CHARACTER SET utf8mb4,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1668 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

