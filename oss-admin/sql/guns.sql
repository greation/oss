/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.210
Source Server Version : 50626
Source Host           : 192.168.1.210:3306
Source Database       : guns

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2018-04-18 15:37:38
*/
DROP DATABASE IF EXISTS yq_oss;
CREATE DATABASE IF NOT EXISTS yq_oss DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE yq_oss;


SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  `delflag` int(2) NOT NULL DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('24', '1', '0', '[0],', '总公司', '总公司', '', null, '0', '2018-04-09 14:01:35', '2018-04-18 10:56:41');
INSERT INTO `sys_dept` VALUES ('25', '2', '24', '[0],[24],', '开发部', '开发部', '', null, '0', '2018-04-09 14:01:35', '2018-04-09 14:01:35');
INSERT INTO `sys_dept` VALUES ('26', '3', '24', '[0],[24],', '运营司', '运营部', '', null, '0', '2018-04-09 14:01:35', '2018-04-09 14:01:35');
INSERT INTO `sys_dept` VALUES ('27', '1', '24', '[0],[24],', '战略部', '战略部', '', null, '0', '2018-04-09 14:01:35', '2018-04-09 14:01:35');
INSERT INTO `sys_dept` VALUES ('28', '1', '0', '[0],', '宜泉资本', '上海响泉公司', '', null, '0', '2018-04-09 14:01:35', '2018-04-09 14:14:57');


-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('16', '0', '0', '状态', null);
INSERT INTO `sys_dict` VALUES ('17', '1', '16', '启用', null);
INSERT INTO `sys_dict` VALUES ('18', '2', '16', '禁用', null);
INSERT INTO `sys_dict` VALUES ('29', '0', '0', '性别', null);
INSERT INTO `sys_dict` VALUES ('30', '1', '29', '男', null);
INSERT INTO `sys_dict` VALUES ('31', '2', '29', '女', null);
INSERT INTO `sys_dict` VALUES ('35', '0', '0', '账号状态', null);
INSERT INTO `sys_dict` VALUES ('36', '1', '35', '启用', null);
INSERT INTO `sys_dict` VALUES ('37', '2', '35', '冻结', null);
INSERT INTO `sys_dict` VALUES ('38', '3', '35', '已删除', null);

-- ----------------------------
-- Table structure for sys_expense
-- ----------------------------
DROP TABLE IF EXISTS `sys_expense`;
CREATE TABLE `sys_expense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` decimal(20,2) DEFAULT NULL COMMENT '报销金额',
  `desc` varchar(255) DEFAULT '' COMMENT '描述',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP,
  `state` int(11) DEFAULT NULL COMMENT '状态: 1.待提交  2:待审核   3.审核通过 4:驳回',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `processId` varchar(255) DEFAULT NULL COMMENT '流程定义id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='报销表';

-- ----------------------------
-- Records of sys_expense
-- ----------------------------
INSERT INTO `sys_expense` VALUES ('23', '100.00', '222', '2018-03-21 17:02:32', '2', '1', '2501');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1540 DEFAULT CHARSET=utf8 COMMENT='登录记录';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=983181792539324425 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('105', 'system', '0', '[0],', '系统管理', 'fa-user', '#', '4', '1', '1', null, '1', '1');
INSERT INTO `sys_menu` VALUES ('106', 'mgr', 'system', '[0],[system],', '员工管理', '', '/mgr', '1', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('107', 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', '', '/mgr/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('108', 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('109', 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('110', 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('111', 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('112', 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('113', 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('114', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('115', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('116', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('117', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('118', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('119', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('120', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('121', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('122', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('128', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('130', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('131', 'dept', 'system', '[0],[system],', '部门管理', null, '/dept', '3', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('132', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('133', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('134', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('135', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', null, '/dept/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('136', 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', null, '/dept/update', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('137', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', null, '/dept/delete', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('138', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('139', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('140', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('141', 'notice', 'system', '[0],[system],', '通知管理', null, '/notice', '9', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('142', 'notice_add', 'notice', '[0],[system],[notice],', '添加通知', null, '/notice/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('143', 'notice_update', 'notice', '[0],[system],[notice],', '修改通知', null, '/notice/update', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('144', 'notice_delete', 'notice', '[0],[system],[notice],', '删除通知', null, '/notice/delete', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('145', 'hello', '0', '[0],', '通知', 'fa-rocket', '/notice/hello', '1', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('148', 'code', '0', '[0],', '代码生成', 'fa-code', '/code', '3', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('149', 'api_mgr', '0', '[0],', '接口文档', 'fa-leaf', '/swagger-ui.html', '2', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('150', 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('151', 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('152', 'to_dept_update', 'dept', '[0],[system],[dept],', '修改部门跳转', '', '/dept/dept_update', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('153', 'dept_list', 'dept', '[0],[system],[dept],', '部门列表', '', '/dept/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('154', 'dept_detail', 'dept', '[0],[system],[dept],', '部门详情', '', '/dept/detail', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('155', 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('156', 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('157', 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('158', 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('159', 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('160', 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('161', 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('162', 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('163', 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('164', 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', '7', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('165', 'to_assign_role', 'mgr', '[0],[system],[mgr],', '分配角色跳转', '', '/mgr/role_assign', '8', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('166', 'to_user_edit', 'mgr', '[0],[system],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', '9', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('167', 'mgr_list', 'mgr', '[0],[system],[mgr],', '用户列表', '', '/mgr/list', '10', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('980734056867069959', 'operationLogs', 'dept', '[0],[system],[dept],', '数据日志', '', '/dept/operationLogs', '7', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('983181792535130113', 'test', '0', '[0],', '测试生成', '', '/test', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('983181792535130114', 'test_list', 'test', '[0],[test],', '测试生成列表', '', '/test/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('983181792539324417', 'test_add', 'test', '[0],[test],', '测试生成添加', '', '/test/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('983181792539324418', 'test_update', 'test', '[0],[test],', '测试生成更新', '', '/test/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('983181792539324419', 'test_delete', 'test', '[0],[test],', '测试生成删除', '', '/test/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('983181792539324420', 'test_detail', 'test', '[0],[test],', '测试生成详情', '', '/test/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('983181792539324421', 'role_view', 'role', '[0],[system],[role],', '查看详情', '', '/role/view', '8', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('983181792539324422', 'role_log', 'role', '[0],[system],[role],', '查看日志', '', '/role/log', '9', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('983181792539324423', 'to_user_change_dept', 'mgr', '[0],[system],[mgr],', '用户部门变更跳转', '', '/mgr/changeDeptView', '11', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('983181792539324424', 'dept_log', 'dept', '[0],[system],[dept],', '查看部门数据日志', '', '/dept/dept_log', '5', '3', '0', null, '1', null);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='通知表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('6', '世界', '10', '欢迎使用管理系统<p><br></p>', '2017-01-11 08:53:20', '1');

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  `modelclass` varchar(255) DEFAULT NULL,
  `keyvalue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1918 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------


-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5360 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_relation
-- ----------------------------
INSERT INTO `sys_relation` VALUES ('4502', '105', '5');
INSERT INTO `sys_relation` VALUES ('4503', '106', '5');
INSERT INTO `sys_relation` VALUES ('4504', '109', '5');
INSERT INTO `sys_relation` VALUES ('4505', '110', '5');
INSERT INTO `sys_relation` VALUES ('4506', '111', '5');
INSERT INTO `sys_relation` VALUES ('4507', '112', '5');
INSERT INTO `sys_relation` VALUES ('4508', '113', '5');
INSERT INTO `sys_relation` VALUES ('4509', '165', '5');
INSERT INTO `sys_relation` VALUES ('4510', '166', '5');
INSERT INTO `sys_relation` VALUES ('4511', '167', '5');
INSERT INTO `sys_relation` VALUES ('4512', '114', '5');
INSERT INTO `sys_relation` VALUES ('4513', '115', '5');
INSERT INTO `sys_relation` VALUES ('4514', '116', '5');
INSERT INTO `sys_relation` VALUES ('4515', '117', '5');
INSERT INTO `sys_relation` VALUES ('4516', '118', '5');
INSERT INTO `sys_relation` VALUES ('4517', '119', '5');
INSERT INTO `sys_relation` VALUES ('4518', '120', '5');
INSERT INTO `sys_relation` VALUES ('4519', '121', '5');
INSERT INTO `sys_relation` VALUES ('4520', '122', '5');
INSERT INTO `sys_relation` VALUES ('4521', '150', '5');
INSERT INTO `sys_relation` VALUES ('4522', '151', '5');
INSERT INTO `sys_relation` VALUES ('4854', '105', '23');
INSERT INTO `sys_relation` VALUES ('4855', '106', '23');
INSERT INTO `sys_relation` VALUES ('4856', '107', '23');
INSERT INTO `sys_relation` VALUES ('4857', '108', '23');
INSERT INTO `sys_relation` VALUES ('5222', '105', '25');
INSERT INTO `sys_relation` VALUES ('5223', '106', '25');
INSERT INTO `sys_relation` VALUES ('5224', '107', '25');
INSERT INTO `sys_relation` VALUES ('5225', '108', '25');
INSERT INTO `sys_relation` VALUES ('5226', '109', '25');
INSERT INTO `sys_relation` VALUES ('5227', '110', '25');
INSERT INTO `sys_relation` VALUES ('5228', '111', '25');
INSERT INTO `sys_relation` VALUES ('5229', '112', '25');
INSERT INTO `sys_relation` VALUES ('5230', '113', '25');
INSERT INTO `sys_relation` VALUES ('5231', '165', '25');
INSERT INTO `sys_relation` VALUES ('5232', '166', '25');
INSERT INTO `sys_relation` VALUES ('5233', '167', '25');
INSERT INTO `sys_relation` VALUES ('5234', '983181792539324423', '25');
INSERT INTO `sys_relation` VALUES ('5297', '105', '1');
INSERT INTO `sys_relation` VALUES ('5298', '106', '1');
INSERT INTO `sys_relation` VALUES ('5299', '107', '1');
INSERT INTO `sys_relation` VALUES ('5300', '108', '1');
INSERT INTO `sys_relation` VALUES ('5301', '109', '1');
INSERT INTO `sys_relation` VALUES ('5302', '110', '1');
INSERT INTO `sys_relation` VALUES ('5303', '111', '1');
INSERT INTO `sys_relation` VALUES ('5304', '112', '1');
INSERT INTO `sys_relation` VALUES ('5305', '113', '1');
INSERT INTO `sys_relation` VALUES ('5306', '165', '1');
INSERT INTO `sys_relation` VALUES ('5307', '166', '1');
INSERT INTO `sys_relation` VALUES ('5308', '167', '1');
INSERT INTO `sys_relation` VALUES ('5309', '114', '1');
INSERT INTO `sys_relation` VALUES ('5310', '115', '1');
INSERT INTO `sys_relation` VALUES ('5311', '116', '1');
INSERT INTO `sys_relation` VALUES ('5312', '117', '1');
INSERT INTO `sys_relation` VALUES ('5313', '118', '1');
INSERT INTO `sys_relation` VALUES ('5314', '162', '1');
INSERT INTO `sys_relation` VALUES ('5315', '163', '1');
INSERT INTO `sys_relation` VALUES ('5316', '164', '1');
INSERT INTO `sys_relation` VALUES ('5317', '983181792539324421', '1');
INSERT INTO `sys_relation` VALUES ('5318', '983181792539324422', '1');
INSERT INTO `sys_relation` VALUES ('5319', '119', '1');
INSERT INTO `sys_relation` VALUES ('5320', '120', '1');
INSERT INTO `sys_relation` VALUES ('5321', '121', '1');
INSERT INTO `sys_relation` VALUES ('5322', '122', '1');
INSERT INTO `sys_relation` VALUES ('5323', '150', '1');
INSERT INTO `sys_relation` VALUES ('5324', '151', '1');
INSERT INTO `sys_relation` VALUES ('5325', '128', '1');
INSERT INTO `sys_relation` VALUES ('5326', '134', '1');
INSERT INTO `sys_relation` VALUES ('5327', '158', '1');
INSERT INTO `sys_relation` VALUES ('5328', '159', '1');
INSERT INTO `sys_relation` VALUES ('5329', '130', '1');
INSERT INTO `sys_relation` VALUES ('5330', '131', '1');
INSERT INTO `sys_relation` VALUES ('5331', '135', '1');
INSERT INTO `sys_relation` VALUES ('5332', '136', '1');
INSERT INTO `sys_relation` VALUES ('5333', '137', '1');
INSERT INTO `sys_relation` VALUES ('5334', '152', '1');
INSERT INTO `sys_relation` VALUES ('5335', '153', '1');
INSERT INTO `sys_relation` VALUES ('5336', '154', '1');
INSERT INTO `sys_relation` VALUES ('5337', '132', '1');
INSERT INTO `sys_relation` VALUES ('5338', '138', '1');
INSERT INTO `sys_relation` VALUES ('5339', '139', '1');
INSERT INTO `sys_relation` VALUES ('5340', '140', '1');
INSERT INTO `sys_relation` VALUES ('5341', '155', '1');
INSERT INTO `sys_relation` VALUES ('5342', '156', '1');
INSERT INTO `sys_relation` VALUES ('5343', '157', '1');
INSERT INTO `sys_relation` VALUES ('5344', '133', '1');
INSERT INTO `sys_relation` VALUES ('5345', '160', '1');
INSERT INTO `sys_relation` VALUES ('5346', '161', '1');
INSERT INTO `sys_relation` VALUES ('5347', '141', '1');
INSERT INTO `sys_relation` VALUES ('5348', '142', '1');
INSERT INTO `sys_relation` VALUES ('5349', '143', '1');
INSERT INTO `sys_relation` VALUES ('5350', '144', '1');
INSERT INTO `sys_relation` VALUES ('5351', '145', '1');
INSERT INTO `sys_relation` VALUES ('5352', '148', '1');
INSERT INTO `sys_relation` VALUES ('5353', '149', '1');
INSERT INTO `sys_relation` VALUES ('5354', '983181792535130113', '1');
INSERT INTO `sys_relation` VALUES ('5355', '983181792535130114', '1');
INSERT INTO `sys_relation` VALUES ('5356', '983181792539324417', '1');
INSERT INTO `sys_relation` VALUES ('5357', '983181792539324418', '1');
INSERT INTO `sys_relation` VALUES ('5358', '983181792539324419', '1');
INSERT INTO `sys_relation` VALUES ('5359', '983181792539324420', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` int(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `deptid` int(11) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  `delflag` int(2) NOT NULL DEFAULT '0',
  `status` int(11) DEFAULT '1' COMMENT '状态(1：正常  2：冻结）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '0', '超级管理员', '24', 'administrator', '1', '0', '1', null, null, null, null);


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：正常  2：冻结  3：删除）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `prefix` varchar(255) NOT NULL DEFAULT 'YQ' COMMENT '员工编号前缀',
  `jobs` varchar(64) DEFAULT NULL COMMENT '岗位名称',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  `manager` int(11) DEFAULT '0' COMMENT '是否此部门主管 0 不是 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '4344b11e-6fa6-49a9-8b6b-f81ee9f18b6d.jpg', '13888888888', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '李少亦_time00', '2018-05-25 00:00:00', '1', 'sn93@qq.com', '13882818463', '1', '26', '1', '2016-01-29 08:49:53', null, 'YQ', null, '25', '0');
INSERT INTO `sys_user` VALUES ('206', null, '18601767221', '810972939d3bbd754a9755004085818d', 'zek8v', '刘光磊', '2018-04-08 17:22:04', '1', 'tony@ekeyfund.com', '18601767221', '1,5', '29', '1', '2018-04-18 14:32:07', null, 'YQ', 'java dev', '0', '0');
