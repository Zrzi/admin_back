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
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R81a9defb13ac44489557e10b6558b32e ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R09933f7d15fd4dcabd95dec5bc62bb6d ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rb551773913c7480d842fcd98712793a4 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rfde99273cfa9455cb099aaa3bf9b66c1', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R5d36cb0affe249ba84418a1eb8c881c4', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rafca1890e91946628f1439598cc0d8d9', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R4bce891ed9f2425398cef65016fab21d', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R0b97e0f635174223a28d4bde0400ff28', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rf9eb47d8ff064053afb619bdbdd8bce6', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);