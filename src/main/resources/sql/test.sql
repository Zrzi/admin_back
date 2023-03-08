DROP TABLE IF EXISTS `admin_test`;
CREATE TABLE `admin_test` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `test_id_1` VARCHAR(40) NOT NULL COMMENT '编码1',
    `test_id_2` VARCHAR(40) NOT NULL COMMENT '编码2',
    `test_text` TEXT NOT NULL COMMENT '内容',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;