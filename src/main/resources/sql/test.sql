DROP TABLE IF EXISTS `admin_test`;
CREATE TABLE `admin_test` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `test_id_1` VARCHAR(40) NOT NULL COMMENT '编码1',
    `test_id_2` VARCHAR(40) NOT NULL COMMENT '编码2',
    `test_text` TEXT NOT NULL COMMENT '内容',
    `test_number` int NOT NULL COMMENT '数字',
    `test_date` DATE NOT NULL COMMENT '日期',
    `test_nullable_1` VARCHAR(20) COMMENT '不必要的字段1',
    `test_nullable_2` VARCHAR(20) COMMENT '不必要的字段2',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`test_id_1`, `test_id_2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `admin_test` ADD COLUMN `test_number` int NOT NULL COMMENT '数字';

ALTER TABLE `admin_test` ADD COLUMN `test_date` DATE NOT NULL COMMENT '日期';