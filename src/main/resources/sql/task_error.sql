DROP TABLE IF EXISTS `admin_task_error`;
CREATE TABLE `admin_task_error` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `task_id` VARCHAR(40) NOT NULL COMMENT '任务编码',
    `error_message` TEXT NOT NULL COMMENT '错误信息',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;