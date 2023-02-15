DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `resource_id` VARCHAR(40) NOT NULL UNIQUE COMMENT '资源编码',
    `resource_name` VARCHAR(16) NOT NULL COMMENT '资源名称',
    `system_id` VARCHAR(40) NOT NULL COMMENT '所属系统编码',
    `system_name` VARCHAR(16) NOT NULL COMMENT '所属系统名称',
    `resource_url` TEXT NOT NULL COMMENT '资源路径',
    `resource_type` INTEGER NOT NULL COMMENT '资源类型 0：功能操作；1：菜单；2：页面元素；3：文件资源',
    `parent_resource` VARCHAR(16) COMMENT '父资源编码 如果没有则为NULL',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;