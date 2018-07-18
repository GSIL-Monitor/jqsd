/*
Navicat MySQL Data Transfer

Source Server         : 60.30
Source Server Version : 50720
Source Host           : 192.168.60.30:3306
Source Database       : dbtest

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2017-12-07 17:01:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` varchar(128) NOT NULL COMMENT 'ID主键',
  `userName` varchar(32) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `realName` varchar(32) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` varchar(2) NOT NULL DEFAULT 'y' COMMENT '状态,y:启用;n:禁用',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  `lastLoginIp` varchar(32) DEFAULT NULL COMMENT '最后登录IP',
  `loginTime` datetime DEFAULT NULL COMMENT '登录时间',
  `loginIp` varchar(32) DEFAULT NULL COMMENT '登录IP',
  `roleId` varchar(64) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_acc` (`userName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', '42c34a2473eb95536711d06f8ee6d70a', '2017-11-29 10:15:11', '2017-11-29 10:15:15', '超级管理员', '10086', '10086@qq.com', 'y', '2017-12-07 09:58:57', '0:0:0:0:0:0:0:1', '2017-12-07 14:50:56', '0:0:0:0:0:0:0:1', '0');

-- ----------------------------
-- Table structure for t_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_menu`;
CREATE TABLE `t_admin_menu` (
  `aId` varchar(64) NOT NULL,
  `mId` int(11) NOT NULL,
  PRIMARY KEY (`aId`,`mId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_db_config
-- ----------------------------
DROP TABLE IF EXISTS `t_db_config`;
CREATE TABLE `t_db_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `dbType` varchar(255) DEFAULT NULL,
  `dbName` varchar(255) DEFAULT NULL,
  `dbUrl` varchar(255) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `port` varchar(20) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `pwd` varchar(1024) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_db_config
-- ----------------------------
INSERT INTO `t_db_config` VALUES ('1', 'test', 'MySQL', 'dbTest', 'jdbc:mysql://192.168.60.30:3306/dbtest', '192.168.60.30', '3306', 'esr', '4b19c4d87c0b6d87f32c099c7071653d', '2017-12-04 15:45:30', 'test');
INSERT INTO `t_db_config` VALUES ('2', '6030', 'MySQL', 'demo', 'jdbc:mysql://192.168.60.30/demo', '192.168.60.30', '3306', 'esr', '4b19c4d87c0b6d87f32c099c7071653d', '2017-12-04 17:53:22', null);
INSERT INTO `t_db_config` VALUES ('3', 'cloudtest', 'sqlserver', 'cloudtest', 'jdbc:sqlserver://66820000.com:9187;DatabaseName=cloudtest', '66820000.com', '9187', 'kingdee', '368762adfd429a78a35150e4ce4ca8cf', '2017-12-07 15:03:05', null);

-- ----------------------------
-- Table structure for t_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_job_log`;
CREATE TABLE `t_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobId` bigint(16) DEFAULT NULL,
  `jobName` varchar(255) DEFAULT NULL,
  `msg` varchar(1024) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_job_log
-- ----------------------------
INSERT INTO `t_job_log` VALUES ('1', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:19:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('2', '2', 'bbb', ' job start ------------->bbb', '2017-12-04 14:19:01', '执行成功');
INSERT INTO `t_job_log` VALUES ('3', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:19:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('4', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:20:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('5', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:20:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('6', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:21:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('7', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:21:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('8', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:22:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('9', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:22:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('10', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:23:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('11', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:23:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('12', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:24:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('13', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:24:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('14', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:25:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('15', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:25:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('16', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:26:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('17', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:26:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('18', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:27:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('19', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:27:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('20', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:28:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('21', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:28:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('22', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:29:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('23', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:29:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('24', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:30:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('25', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 14:30:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('26', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:54:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('27', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:55:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('28', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:55:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('29', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:56:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('30', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:56:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('31', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:57:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('32', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:57:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('33', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:58:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('34', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:58:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('35', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 15:59:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('36', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 16:01:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('37', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 16:01:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('38', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 16:02:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('39', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 16:02:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('40', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 16:03:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('41', '1', 'aaa', ' job start ------------->aaa', '2017-12-04 16:03:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('42', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:24', '执行成功');
INSERT INTO `t_job_log` VALUES ('43', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:24', '执行成功');
INSERT INTO `t_job_log` VALUES ('44', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:25', '执行成功');
INSERT INTO `t_job_log` VALUES ('45', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:26', '执行成功');
INSERT INTO `t_job_log` VALUES ('46', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:27', '执行成功');
INSERT INTO `t_job_log` VALUES ('47', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:28', '执行成功');
INSERT INTO `t_job_log` VALUES ('48', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:29', '执行成功');
INSERT INTO `t_job_log` VALUES ('49', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:30', '执行成功');
INSERT INTO `t_job_log` VALUES ('50', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:12:31', '执行成功');
INSERT INTO `t_job_log` VALUES ('51', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:13:01', '执行成功');
INSERT INTO `t_job_log` VALUES ('52', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:14:01', '执行成功');
INSERT INTO `t_job_log` VALUES ('53', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:36:00', '执行成功');
INSERT INTO `t_job_log` VALUES ('54', '2', 'bbb', ' job start ------------->bbb', '2017-12-06 14:37:00', '执行成功');

-- ----------------------------
-- Table structure for t_module
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pId` int(11) DEFAULT NULL COMMENT '父ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(60) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `sn` varchar(32) NOT NULL DEFAULT '' COMMENT '短名',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  `is_use` tinyint(2) DEFAULT NULL COMMENT '是否启动',
  `level` tinyint(2) DEFAULT NULL COMMENT '等级',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='模块表';

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('1', '0', '根模块', null, '1', '根模块', '/#', '1', '0', '所有模块的根节点，不能删除。');
INSERT INTO `t_module` VALUES ('2', '1', '系统管理', null, '99', '系统管理', '', '1', '1', '系统管理菜单');
INSERT INTO `t_module` VALUES ('3', '2', '菜单管理', null, '1', '菜单管理 | CDGL', '/manager/menu', '1', '2', '菜单管理');
INSERT INTO `t_module` VALUES ('4', '2', '用户管理', null, '0', '用户管理 | YHGL', '/manager/list', '1', '2', '权限管理列表');
INSERT INTO `t_module` VALUES ('5', '2', '角色管理', null, '2', '角色管理 | JSGL', '/manager/roleList', '1', '2', '角色列表');
INSERT INTO `t_module` VALUES ('6', '2', '任务管理', null, '6', '任务管理 | YWGL', '/manager/jobList', '1', '2', null);
INSERT INTO `t_module` VALUES ('7', '2', '数据库管理', null, '7', '数据库管理 | DBGL', '/config/list', '1', '2', null);
INSERT INTO `t_module` VALUES ('8', '2', '日志管理', null, '8', '日志管理 | RZGL', '/manager/logPage', '1', '2', null);
INSERT INTO `t_module` VALUES ('9', '2', '百度', null, '0', '百度', 'http://www.baidu.com', '1', '2', null);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `roleName` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色名称',
  `mId` varchar(1024) DEFAULT '0' COMMENT '权限范围',
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('11', 'test', '1,2,4,3,5,6,7', 'test');

-- ----------------------------
-- Table structure for t_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_job`;
CREATE TABLE `t_schedule_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0',
  `cron` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `jobClass` varchar(255) DEFAULT NULL,
  `param` varchar(255) DEFAULT NULL,
  `min` varchar(20) DEFAULT NULL,
  `hour` varchar(20) DEFAULT NULL,
  `day` varchar(20) DEFAULT NULL,
  `mon` varchar(20) DEFAULT NULL,
  `week` varchar(20) DEFAULT NULL,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_schedule_job
-- ----------------------------
INSERT INTO `t_schedule_job` VALUES ('1', null, 'aaa', '0', '* */1 * * * ?', 'test', 'com.jqsd.core.job.DemoJob', null, '*/1', '*', '*', '*', '?', '2014-08-08 14:17:34');
INSERT INTO `t_schedule_job` VALUES ('2', null, 'bbb', '1', '* 35-40 14 ? * 3', 'test2', 'com.jqsd.core.job.DemoJob', 'taskTest', '35-40', '14', '?', '*', '3', '2014-04-25 14:17:19');
INSERT INTO `t_schedule_job` VALUES ('10', '2014-04-25 16:52:17', 'test', '0', '* */5 * * * ?', 'xxx', 'com.jqsd.core.job.DemoJob', '', '*/5', '*', '*', '*', '?', '2014-08-08 14:17:27');
INSERT INTO `t_schedule_job` VALUES ('11', '2017-11-29 16:52:54', 'asfsdfsdfsd', '0', '* */10 * * * ?', 'aaa', 'com.jqsd.core.job.DemoJob', null, '*/10', '*', '*', '*', '?', '2017-11-29 16:52:54');
INSERT INTO `t_schedule_job` VALUES ('12', '2017-12-06 15:55:53', 'aaasdf', '0', '* */1 * * * ?', 'sssssss', 'com.jqsd.core.job.SyncJob', null, '*/1', '*', '*', '*', '?', '2017-12-06 15:55:53');
