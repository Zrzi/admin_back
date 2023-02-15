DROP TABLE IF EXISTS `admin_system`;
CREATE TABLE `admin_system`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `system_id` VARCHAR(40) NOT NULL UNIQUE COMMENT '系统编码',
    `system_name` VARCHAR(16) NOT NULL UNIQUE COMMENT '系统名称',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;