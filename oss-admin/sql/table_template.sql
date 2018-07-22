-- ----------------------------
-- Table structure for table_template
-- ----------------------------
DROP TABLE IF EXISTS `table_template`;
CREATE TABLE `table_template` (
  `id` bigint(20) NOT NULL COMMENT '序号',
  `value` varchar(255) DEFAULT NULL COMMENT '名称',
  `version` bigint DEFAULT 0 COMMENT '版本',
  `roleId` bigint DEFAULT NULL COMMENT '数据权限',
  `delflag` int(2) DEFAULT 0 NOT NULL COMMENT '删除标记',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';

