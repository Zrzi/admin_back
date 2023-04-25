DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_no` VARCHAR(20) NOT NULL COMMENT '用户名',
    `user_name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `password` TEXT NOT NULL COMMENT '密码',
    `user_type` INTEGER NOT NULL COMMENT '用户类型 0：系统用户；1：学生；2：教师',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `admin_user` (`user_no`, `user_name`, `password`, `user_type`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUE ('00000000000000000000', 'admin', '96e79218965eb72c92a549dd5a330112', 0, '00000000000000000000', NOW(), '00000000000000000000', NOW(), 0);

INSERT INTO `admin_user` (`user_no`, `user_name`, `password`, `user_type`, `created_by`, `created_date`, `updated_by`, `updated_date`, `status`)
VALUE ('00000000000000000001', '测试用户', '96e79218965eb72c92a549dd5a330112', 0, '00000000000000000000', NOW(), '00000000000000000000', NOW(), 0);

-- ALTER TABLE `admin_user` MODIFY COLUMN `user_no` VARCHAR(20) NOT NULL COMMENT '用户名';