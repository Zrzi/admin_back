DROP TABLE IF EXISTS `admin_task`;
CREATE TABLE `admin_task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `task_id` VARCHAR(40) NOT NULL UNIQUE COMMENT '任务编码',
    `task_status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：进行中；1：成功；2：异常',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;