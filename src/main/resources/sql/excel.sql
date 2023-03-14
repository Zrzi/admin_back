DROP TABLE IF EXISTS `admin_excel`;
CREATE TABLE `admin_excel` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `excel_id` VARCHAR(40) NOT NULL UNIQUE COMMENT 'Excel映射编码',
    `excel_name` VARCHAR(16) NOT NULL COMMENT 'Excel表格名称',
    `sql_name` VARCHAR(40) NOT NULL COMMENT 'Excel表格名称',
    `is_cover` INTEGER NOT NULL DEFAULT 0 COMMENT '插入数据出现重复时，是否覆盖 0：否；1：是',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;