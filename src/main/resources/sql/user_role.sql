DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role`(
    `id` BIGINT NOT NULL AUTOINCREMENT,
    `user_no` VARCHAR(20) NOT NULL COMMENT '用户名',
    `role_id` VARCHAR(40) NOT NULL COMMENT '角色编码',
    `level` INTEGER NOT NULL DEFAULT 0 COMMENT '角色等级，用于数据及权限管理',
    `user_type` INTEGER NOT NULL COMMENT '角色类别，0：存储在admin_user中；1：存储在student中；2：存储在teacher中',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;