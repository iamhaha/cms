DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` VARCHAR(32) NOT NULL COMMENT 'class id, 班级id，e.g. 06701',
  `name` VARCHAR(64) NOT NULL COMMENT 'class name, e.g. 高一一班',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last update time',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT 'for optimistic locking',
  PRIMARY KEY `pk_class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'class table 班级';

-- 用户表保存通用的用户的信息，包括id、姓名、密码以及角色，不用角色的用户还会在各自的表中补充额外信息 --
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` VARCHAR(32) NOT NULL COMMENT 'user id, 学号/工号',
  `password` VARCHAR(64) NOT NULL COMMENT 'user password md5',
  `name` VARCHAR(64) NOT NULL COMMENT 'user name',
  `role` TINYINT NOT NULL COMMENT 'role, admin/student/teacher',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last update time',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT 'for optimistic locking',
  PRIMARY KEY `pk_user` (`id`),
  KEY `key_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'user table, common user info';
INSERT INTO `user`(`id`, `name`, `role`, `password`) VALUES ('admin', 'admin', 0, 'e10adc3949ba59abbe56e057f20f883e');

-- 学生与班级的关系，暂时不通过关系表来维护，而在学生表中使用cid来进行映射 --
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` VARCHAR(32) NOT NULL COMMENT 'student id, 与user表中一致',
  `cid` VARCHAR(32) NOT NULL COMMENT 'class id, 与class表中一致',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last update time',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT 'for optimistic locking',
  PRIMARY KEY `pk_student` (`id`),
  KEY `key_cid` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'student table, student info';

-- 教师表暂时不会被使用，因为教师暂时没有除user表中额外的信息 --
-- DROP TABLE IF EXISTS `teacher`;
-- CREATE TABLE `teacher` (
--   `id` VARCHAR(32) NOT NULL COMMENT 'teacher id, 与user表中一致',
--   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
--   `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last update time',
--   `version` int(11) NOT NULL DEFAULT 0 COMMENT 'for optimistic locking',
--   PRIMARY KEY `pk_teacher` (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'teacher table, teacher info';

-- 课程和老师的关系，暂时不通过关系表来维护，而在课程表中使用tid来进行映射 --
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` VARCHAR(32) NOT NULL COMMENT 'course id, 课程代码',
  `name` VARCHAR(64) NOT NULL COMMENT 'course name, 课程名称',
  `description` VARCHAR(256) NOT NULL DEFAULT '' COMMENT 'cource description, 课程描述例如上课时间&地点',
  `tid` VARCHAR(32) NOT NULL COMMENT 'teacher id，授课老师',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last update time',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT 'for optimistic locking',
  PRIMARY KEY `pk_course` (`id`),
  KEY `key_tid` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'course table';

DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto id',
  `sid` VARCHAR(32) NOT NULL COMMENT 'student id',
  `cid` VARCHAR(32) NOT NULL COMMENT 'course id',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  PRIMARY KEY `pk_student_course` (`id`),
  KEY `key_sid` (`sid`),
  KEY `key_cid` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'student-course table';

-- db management --
-- create user
CREATE USER 'cms' IDENTIFIED BY '123456';
-- create db
CREATE DATABASE cms_test DEFAULT CHARACTER SET = 'utf8';
-- grant privilege
GRANT ALL ON cms_test.* TO 'cms';
