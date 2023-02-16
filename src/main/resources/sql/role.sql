DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `role_id` VARCHAR(40) NOT NULL UNIQUE COMMENT '角色编码',
    `role_name` VARCHAR(16) NOT NULL COMMENT '角色名称',
    `system_id` VARCHAR(40) NOT NULL COMMENT '所属系统编码',
    `system_name` VARCHAR(16) NOT NULL COMMENT '所属系统名称',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `admin_role`(`role_id`, `role_name`, `system_id`, `system_name`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', '管理员', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '00000000000000000000', NOW(), '00000000000000000000', NOW(), 0);