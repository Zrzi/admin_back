DROP TABLE IF EXISTS `course_select`;
CREATE TABLE `course_select` (
    `keyid` nvarchar(30) COMMENT '原文件信息为：课程号+课序号+第几节',
    `courseid` nvarchar(20),
    `courseorderid` int,
    `uniClass` nvarchar(200),
    `Number_stu` int,
    `campus` nvarchar(100),
    `classroom` nvarchar(100) COMMENT '原文件信息为：软件园5区408d,软件园5区408d，需要导入时处理',
    `weekN` nvarchar(10) COMMENT '原文件信息为：星期二 0708节,星期四 0506节,需要导入时处理,如果两次课，需要导入两条记录',
    `classsegment` nvarchar(10),
    PRIMARY KEY (`keyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;