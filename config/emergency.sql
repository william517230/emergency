/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : emergency

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2014-03-19 23:55:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` varchar(32) NOT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `leaf` bit(1) NOT NULL,
  `parent` varchar(32) DEFAULT NULL,
  `text` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------

-- ----------------------------
-- Table structure for `loginrecord`
-- ----------------------------
DROP TABLE IF EXISTS `loginrecord`;
CREATE TABLE `loginrecord` (
  `id` varchar(255) NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4BD0551A905BC42B` (`user_id`),
  CONSTRAINT `FK4BD0551A905BC42B` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of loginrecord
-- ----------------------------

-- ----------------------------
-- Table structure for `login_record`
-- ----------------------------
DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
  `id` char(32) NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `login_time` datetime NOT NULL,
  `user_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF43101E7905BC42B` (`user_id`),
  CONSTRAINT `login_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_record
-- ----------------------------

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` char(32) NOT NULL,
  `text` varchar(50) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `leaf` tinyint(4) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `link_url` varchar(100) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `descn` varchar(100) DEFAULT NULL,
  `systemed` tinyint(4) DEFAULT NULL,
  `enName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('0', '系统权限', '3', '0', '1                               ', null, '0', null, '1', 'system');
INSERT INTO `resource` VALUES ('21232f297a57a5a743894a0e4a801fc5', '资源编辑', '2', '1', '4028f28139aaeb5f0139aaeb79d60001', 'pages/resource_check.jsp', '1', null, '1', 'resc');
INSERT INTO `resource` VALUES ('21232f297a57a5a743894a0e4a801fc6', '角色管理', '2', '1', '4028f28139beab350139beadbbb70001', 'pages/role_check.jsp', '1', null, '1', 'role');
INSERT INTO `resource` VALUES ('21232f297a57a5a743894a0e4a801fc7', '用户管理', '2', '1', '4028f28139beab350139beadbbb70001', 'pages/user_check.jsp', '2', null, '1', 'user');
INSERT INTO `resource` VALUES ('4028abab431fc92c01431fc9d1310002', '删除资源', '4', '0', '21232f297a57a5a743894a0e4a801fc5', 'resource!delete.action', '3', null, '1', 'delresc');
INSERT INTO `resource` VALUES ('4028f28139aaeb5f0139aaeb79d60001', '控制面板', '0', '0', '0                               ', '', '1', '请输入对该资源的正确描述', '1', 'manager');
INSERT INTO `resource` VALUES ('4028f28139beab350139beadbbb70001', '系统管理', '0', '0', '0                               ', null, '2', null, '1', 'managersys');
INSERT INTO `resource` VALUES ('4028f28139beab350139beadbbb70002', '添加资源', '4', '0', '21232f297a57a5a743894a0e4a801fc5', 'resource!save.action', '1', null, '1', 'addresc');
INSERT INTO `resource` VALUES ('4028f28139beab350139beadbbb70003', '编辑资源', '4', '0', '21232f297a57a5a743894a0e4a801fc5', 'resource!update.action', '2', null, '1', 'updateresc');
INSERT INTO `resource` VALUES ('4a2382e5434e7d6f01434e810f6a0001', '授权', '4', '0', '8a9597f44343ff9401434698209a0003', 'resource!assignResource.action', '1', '授权按钮', '0', 'assignAuthority');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d674f9f000b', '添加用户', '4', '0', '21232f297a57a5a743894a0e4a801fc7', 'user!save.action', '1', '添加用户按钮', null, 'addUser');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d685343000c', '编辑用户', '4', '0', '21232f297a57a5a743894a0e4a801fc7', 'user!update.action', '2', '编辑用户按钮', null, 'editUser');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d68d351000d', '删除用户', '4', '0', '21232f297a57a5a743894a0e4a801fc7', 'user!delete.action', '3', '删除用户按钮', null, 'delUser');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d697c04000e', '添加角色', '4', '0', '21232f297a57a5a743894a0e4a801fc6', 'role!save.action', '1', '添加角色按钮', null, 'addRole');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d69df04000f', '编辑角色', '4', '0', '21232f297a57a5a743894a0e4a801fc6', 'role!update.action', '2', '编辑角色按钮', null, 'editRole');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d6b0f750010', '分配角色', '4', '0', '21232f297a57a5a743894a0e4a801fc7', 'role!assignRole.action', '4', '分配角色按钮', null, 'assignRole');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d6bbdb70011', '删除角色', '4', '0', '21232f297a57a5a743894a0e4a801fc6', 'role!delete.action', '3', '删除角色按钮', '0', 'delRole');
INSERT INTO `resource` VALUES ('8a9597f4433c223c01433d6c5fa40012', '分配权限', '4', '0', '21232f297a57a5a743894a0e4a801fc6', 'resource!assignResource.action', '4', '分配权限按钮', null, 'assignAuthority');
INSERT INTO `resource` VALUES ('8a9597f44343ff9401434698209a0003', '资源授权', '2', '1', '4028f28139aaeb5f0139aaeb79d60001', 'pages/authority_check.jsp', '2', '资源授权', '0', 'Authority');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` char(32) NOT NULL,
  `role_description` varchar(255) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('21232f297a57a5a743894a0e4a801fc3', '系统管理员', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('21232f297a57a5a743894a0e4a801fc4', '普通用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('21232f297a57a5a743894a0e4a801fc5', '管理员', 'ROLE_MANAGE');
INSERT INTO `role` VALUES ('8a9597f443385cf00143387134eb0002', 'mm管理员', 'ROLE_MM');

-- ----------------------------
-- Table structure for `role_resc`
-- ----------------------------
DROP TABLE IF EXISTS `role_resc`;
CREATE TABLE `role_resc` (
  `resc_id` char(32) NOT NULL,
  `role_id` char(32) NOT NULL,
  PRIMARY KEY (`resc_id`,`role_id`),
  KEY `FK14066DECBA197B66` (`role_id`),
  KEY `FK14066DECF1186E91` (`resc_id`),
  CONSTRAINT `role_resc_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_resc_ibfk_2` FOREIGN KEY (`resc_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resc
-- ----------------------------
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc5', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc6', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc7', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('4028abab431fc92c01431fc9d1310002', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('4028f28139aaeb5f0139aaeb79d60001', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('4028f28139beab350139beadbbb70001', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('4028f28139beab350139beadbbb70002', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('4028f28139beab350139beadbbb70003', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('4a2382e5434e7d6f01434e810f6a0001', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d674f9f000b', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d685343000c', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d68d351000d', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d697c04000e', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d69df04000f', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d6b0f750010', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d6bbdb70011', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d6c5fa40012', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('8a9597f44343ff9401434698209a0003', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc7', '21232f297a57a5a743894a0e4a801fc4');
INSERT INTO `role_resc` VALUES ('4028f28139beab350139beadbbb70001', '21232f297a57a5a743894a0e4a801fc4');
INSERT INTO `role_resc` VALUES ('8a9597f4433c223c01433d674f9f000b', '21232f297a57a5a743894a0e4a801fc4');
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc5', '8a9597f443385cf00143387134eb0002');
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc6', '8a9597f443385cf00143387134eb0002');
INSERT INTO `role_resc` VALUES ('21232f297a57a5a743894a0e4a801fc7', '8a9597f443385cf00143387134eb0002');
INSERT INTO `role_resc` VALUES ('4028f28139aaeb5f0139aaeb79d60001', '8a9597f443385cf00143387134eb0002');
INSERT INTO `role_resc` VALUES ('4028f28139beab350139beadbbb70001', '8a9597f443385cf00143387134eb0002');
INSERT INTO `role_resc` VALUES ('8a9597f44343ff9401434698209a0003', '8a9597f443385cf00143387134eb0002');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` char(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `realname` varchar(50) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('21232f297a57a5a743894a0e4a801fc3', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', 'admin', '男', '123456789', '123456789');

-- ----------------------------
-- Table structure for `user_dept`
-- ----------------------------
DROP TABLE IF EXISTS `user_dept`;
CREATE TABLE `user_dept` (
  `user_id` varchar(255) NOT NULL,
  `dept_id` varchar(32) NOT NULL,
  PRIMARY KEY (`dept_id`,`user_id`),
  KEY `FK14357239780AF913` (`dept_id`),
  KEY `FK14357239905BC42B` (`user_id`),
  CONSTRAINT `user_dept_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`),
  CONSTRAINT `user_dept_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` char(32) NOT NULL,
  `role_id` char(32) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK143BF46ABA197B66` (`role_id`),
  KEY `FK143BF46A905BC42B` (`user_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('21232f297a57a5a743894a0e4a801fc3', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `user_role` VALUES ('21232f297a57a5a743894a0e4a801fc3', '21232f297a57a5a743894a0e4a801fc4');
INSERT INTO `user_role` VALUES ('21232f297a57a5a743894a0e4a801fc3', '21232f297a57a5a743894a0e4a801fc5');
