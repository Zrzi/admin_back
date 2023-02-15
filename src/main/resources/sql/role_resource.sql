DROP TABLE IF EXISTS `admin_role_resource`;
CREATE TABLE `admin_role_resource`(
    `id` BIGINT NOT NULL AUTOINCREMENT,
    `role_id` VARCHAR(40) NOT NULL COMMENT '角色编码',
    `resource_id` VARCHAR(40) NOT NULL COMMENT '资源编码',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;