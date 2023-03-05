DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `resource_id` VARCHAR(40) NOT NULL UNIQUE COMMENT '资源编码',
    `resource_name` VARCHAR(50) NOT NULL COMMENT '资源名称',
    `system_id` VARCHAR(40) NOT NULL COMMENT '所属系统编码',
    `system_name` VARCHAR(16) NOT NULL COMMENT '所属系统名称',
    `resource_url` TEXT NOT NULL COMMENT '资源路径',
    `resource_type` INTEGER NOT NULL COMMENT '资源类型 0：功能操作；1：菜单；2：页面元素；3：文件资源',
    `parent_resource` VARCHAR(16) COMMENT '父资源编码 如果没有则为NULL',
    `is_menu` INTEGER NOT NULL DEFAULT 0 COMMENT '是否是导航菜单，0：不是；1：是',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `admin_resource` MODIFY COLUMN `resource_name` VARCHAR(50) NOT NULL COMMENT '资源名称';

ALTER TABLE `admin_resource` ADD COLUMN `is_menu` INTEGER NOT NULL DEFAULT 0 COMMENT '是否是导航菜单，0：不是；1：是';

SELECT `resource_id`, `resource_name`, `resource_url`, `resource_type` FROM `admin_resource` WHERE `status` = 0 LIMIT 8, 8;

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R81a9defb13ac44489557e10b6558b32e', 'addRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/role/post', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R09933f7d15fd4dcabd95dec5bc62bb6d', 'addResource', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resource/post', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rfde99273cfa9455cb099aaa3bf9b66c1', 'getResourcesBySystemId', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resource/get', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rb551773913c7480d842fcd98712793a4', 'getSystems', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/system/get', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Re0290cabd1394a7ca3887859952fe5b8', 'updateResource', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resource/update', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R3d15a8ce1e264376b492ffcd9925c7c3', 'deleteResource', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resource/delete', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4bce891ed9f2425398cef65016fab21d', 'getRoleResources', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/roleResource/get', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rf9eb47d8ff064053afb619bdbdd8bce6', 'editRoleResource', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/roleResource/post', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R1dd5a9813785434cb762360a2f953ffa', 'addSystem', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/system/post', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rba7b5d8093ab4511956302a433ff3c7d', 'updateSystem', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/system/update', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rea28fd95968342dd8edb7247c2d75526', 'deleteSystem', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/system/delete', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rafca1890e91946628f1439598cc0d8d9', 'getRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/role/get', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R0b97e0f635174223a28d4bde0400ff28', 'getRoleByRoleId', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/role/getByRoleId', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R7b9251c51dda4a5aab78ec2366c05c4e', 'updateRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/role/update', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R128757d7a5a340dfa0d85619f5a5ebeb', 'removeRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/role/delete', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rfb514cccdbf54668bd4c6f77cba52909', 'resetPassword', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resetPassword', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R9a8fba591a4c4d1297973505a3f48d5f', 'getUser', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/user/get', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rad1fcd75e78e4bc284d336a974a58769', 'addUser', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/user/post', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R7931bd23f38c4bf6a9f269d3ddd41e4f', 'removeUser', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/user/delete', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Ra520f94bf9324de29bd46b38d017beb0', 'getMemberRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/memberRole/get', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rde1f9f4068084c3cbb5b058f4a6c474a', 'getUnaddedUser', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/memberRole/getUnaddedUser', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R9f1868fcc57344b99935e5fb702c3b83', 'addMemberRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/memberRole/addMemberRole', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R55e0c6564012407f9db606b2e61b68d6', 'deleteMemberRole', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/memberRole/deleteMemberRole', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R217bec9a029544569ab5899be03ed861', 'getUserByUserNo', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/user/getByUserNo', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R17ef94900dc3476f9445c5049cbbf910', 'updateUser', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/user/update', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rd2bd2e827c2d43d399d0b1c25880f37f', 'getResourceById', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resource/getById', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Reae88eb0eeaa4cb4960ad359894df82c', 'checkAuthority', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/checkAuthority', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R5c434f48d34241e88144edc0ce9348e9', 'loginPage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/login', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R102e54f1c19541a48433e7134d39c38c', 'homePage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rfd7d453ce918403ab0ccc4b7d00e6931', 'usersPage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/users', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rcf43fd93ba10489cbe5f651a4c405b3f', 'rolesPage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rfb45f4d2b0ce4751a66a892cfbc6ec96', 'rolePage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles/role', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Re6654c09a55943ba8e8c63826fdc7a26', 'systemsPage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/systems', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R69411a9a7be94d3a87254dfed001585b', 'resourcesPage', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resources', 1, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Re3416db616fe42908a358f7c674e3f60', 'addStudentButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/users', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rb1735029edfc486d8d92ae3a450e8444', 'addTeacherButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/users', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R0388abd789d54dabab2f7eb7a83a5e42', 'addRoleButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rb203469630844162994e8e0f8811f4f9', 'editRoleButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles/role', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R2e6d823f53734807951d0170259928b6', 'authenticateButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles/role', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rd695f531f161493f81cb99d31add8251', 'addMemberButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles/role', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R6455af277e32456c9a78de255292e42a', 'deleteRoleButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/roles/role', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R681d2ec8e6ed438497570b021a989a25', 'addSystemButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/systems', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R25b04ab19eb24a32a470fbcaed4e3b53', 'editSystemButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/home/systems', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rc4a57f3e530141468bacda5408a6798a', 'addResourceButton', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/resources', 2, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R9fd764676bb644dfb8f554e1b56bf5d4', 'getMenus', 'Saeda408ef5de4fdb9a583f9b441cfc97', '权限管理系统', '/getMenus', 0, NULL, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

--- Excel 新增 ---
INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R5197664e9df34269b99da27e5c33483f', 'Excel映射配置', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/home/excels', 1, NULL, 1, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rb8cba5e8ea264301b8dde8ef3f6be95d', 'getExcels', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/getExcels', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R1c36aef7d59049f9ad9a30bfe51f2f53', 'getExcelByExcelId', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/getExcelByExcelId', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rd5049d704c16492b980d46f808dbe7b4', 'getSqlTables', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/getSqlTables', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R7f18cec0bd4547428712be04764fbea9', 'getSqlColumns', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/getSqlColumns', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R3f965fc4d2034ff39736d1766cfd6077', 'addExcel', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/add', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R408a805928c3449e91b658a29b48b7da', 'updateExcel', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/update', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_resource`(`resource_id`, `resource_name`, `system_id`, `system_name`, `resource_url`, `resource_type`, `parent_resource`, `is_menu`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('Rf657fa831f85425fa2a8af478ee2c7d0', 'deleteExcel', 'S6b7980d3d2064c718ed48989bf919eff', 'Excel上传', '/excel/delete', 0, NULL, 0, '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);
