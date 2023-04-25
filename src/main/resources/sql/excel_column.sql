DROP TABLE IF EXISTS `admin_excel_column`;
CREATE TABLE `admin_excel_column` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `excel_id` VARCHAR(40) NOT NULL COMMENT '关联的Excel映射编码',
    `excel_column` VARCHAR(16) NOT NULL COMMENT 'Excel列名称',
    `sql_column` VARCHAR(40) NOT NULL COMMENT 'Excel列名称',
    `is_special` INTEGER NOT NULL DEFAULT 0 COMMENT '标记是否需要特殊处理 0：不需要；1：需要',
    `created_by` VARCHAR(20) NOT NULL COMMENT '创建用户的用户名',
    `created_date` DATETIME NOT NULL COMMENT '创建时间',
    `updated_by` VARCHAR(20) NOT NULL COMMENT '最近一次修改记录的用户的用户名',
    `updated_date` DATETIME NOT NULL COMMENT '最近一次修改记录的时间',
    `status` INTEGER NOT NULL DEFAULT 0 COMMENT '记录状态 0：正常；1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ALTER TABLE `admin_excel_column` ADD `is_special` INTEGER NOT NULL DEFAULT 0 COMMENT '标记是否需要特殊处理 0：不需要；1：需要';