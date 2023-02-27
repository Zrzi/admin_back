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
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rfde99273cfa9455cb099aaa3bf9b66c1 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rb551773913c7480d842fcd98712793a4', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Re0290cabd1394a7ca3887859952fe5b8', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R3d15a8ce1e264376b492ffcd9925c7c3', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R4bce891ed9f2425398cef65016fab21d', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rf9eb47d8ff064053afb619bdbdd8bce6', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R1dd5a9813785434cb762360a2f953ffa ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rba7b5d8093ab4511956302a433ff3c7d ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rea28fd95968342dd8edb7247c2d75526 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rafca1890e91946628f1439598cc0d8d9', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R0b97e0f635174223a28d4bde0400ff28', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R7b9251c51dda4a5aab78ec2366c05c4e', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R128757d7a5a340dfa0d85619f5a5ebeb', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rfb514cccdbf54668bd4c6f77cba52909', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R9a8fba591a4c4d1297973505a3f48d5f ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rad1fcd75e78e4bc284d336a974a58769 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R7931bd23f38c4bf6a9f269d3ddd41e4f ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Ra520f94bf9324de29bd46b38d017beb0', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rde1f9f4068084c3cbb5b058f4a6c474a', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R9f1868fcc57344b99935e5fb702c3b83', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R55e0c6564012407f9db606b2e61b68d6', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R217bec9a029544569ab5899be03ed861', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R17ef94900dc3476f9445c5049cbbf910 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rd2bd2e827c2d43d399d0b1c25880f37f ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Reae88eb0eeaa4cb4960ad359894df82c ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R5c434f48d34241e88144edc0ce9348e9', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R102e54f1c19541a48433e7134d39c38c', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rfd7d453ce918403ab0ccc4b7d00e6931', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rcf43fd93ba10489cbe5f651a4c405b3f', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rfb45f4d2b0ce4751a66a892cfbc6ec96', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Re6654c09a55943ba8e8c63826fdc7a26 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R69411a9a7be94d3a87254dfed001585b ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Re3416db616fe42908a358f7c674e3f60 ', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rb1735029edfc486d8d92ae3a450e8444', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R0388abd789d54dabab2f7eb7a83a5e42', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rb203469630844162994e8e0f8811f4f9', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R2e6d823f53734807951d0170259928b6', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rd695f531f161493f81cb99d31add8251', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R6455af277e32456c9a78de255292e42a', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R681d2ec8e6ed438497570b021a989a25', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R25b04ab19eb24a32a470fbcaed4e3b53', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'Rc4a57f3e530141468bacda5408a6798a', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);

INSERT INTO `admin_role_resource`(`role_id`, `resource_id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUES ('R4f50951574d7454bb9df093e22ac5b24', 'R9fd764676bb644dfb8f554e1b56bf5d4', '00000000000000000000 ', NOW(), '00000000000000000000 ', NOW(), 0);