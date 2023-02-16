DROP TABLE IF EXISTS `admin_role_resource`;
CREATE TABLE `admin_role_resource`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `role_id` VARCHAR(40) NOT NULL COMMENT '角色编码',
    `resource_id` VARCHAR(40) NOT NULL COMMENT '资源编码',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24 ', 'R81a9defb13ac44489557e10b6558b32e ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24 ', 'R09933f7d15fd4dcabd95dec5bc62bb6d ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);