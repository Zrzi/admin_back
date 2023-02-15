DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`(
    `id` BIGINT NOT NULL AUTOINCREMENT,
    `user_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    `user_name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `password` TEXT NOT NULL COMMENT '密码',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `admin_user` (`user_no`, `user_name`, `password`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUE ('00000000000000000000', 'admin', '123456', '00000000000000000000', NOW(), '00000000000000000000', NOW(), 0);