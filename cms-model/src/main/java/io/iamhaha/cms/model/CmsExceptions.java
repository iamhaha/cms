/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model;

import org.springframework.http.HttpStatus;

/**
 * CMS exceptions.
 *
 * @author dingshenglong
 */
public final class CmsExceptions {

    // ---- user exceptions ----
    public static final class NoSuchUser extends CmsException {
        public NoSuchUser(String id) {
            super(String.format("用户%s不存在", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class NoSuchTeacher extends CmsException {
        public NoSuchTeacher(String id) {
            super(String.format("教师%s不存在", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class NoSuchStudent extends CmsException {
        public NoSuchStudent(String id) {
            super(String.format("学生%s不存在", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class DuplicateUser extends CmsException {
        public DuplicateUser(String id) {
            super(String.format("用户%s已存在", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class DuplicateStudent extends CmsException {
        public DuplicateStudent(String id) {
            super(String.format("学生%s已存在", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class TeacherInUse extends CmsException {
        public TeacherInUse(String id) {
            super(String.format("教师%s已开课，无法删除", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class StudentInUse extends CmsException {
        public StudentInUse(String id) {
            super(String.format("学生%s已选课，无法删除", id), HttpStatus.BAD_REQUEST);
        }
    }

    // ---- class exceptions ----
    public static final class NoSuchClass extends CmsException {
        public NoSuchClass(String id) {
            super(String.format("班级%s不存在", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class DuplicateClass extends CmsException {
        public DuplicateClass(String id) {
            super(String.format("班级%s已存在", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class ClassInUse extends CmsException {
        public ClassInUse(String id) {
            super(String.format("班级%s已有学生，无法删除", id), HttpStatus.BAD_REQUEST);
        }
    }

    // --- course exceptions ----
    public static final class NoSuchCourse extends CmsException {
        public NoSuchCourse(String id) {
            super(String.format("课程%s不存在", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class DuplicateCourse extends CmsException {
        public DuplicateCourse(String id) {
            super(String.format("课程%s已存在", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class CourseInUse extends CmsException {
        public CourseInUse(String id) {
            super(String.format("课程%s已有学生，无法删除", id), HttpStatus.BAD_REQUEST);
        }
    }

    // ---- misc ----
    // 仅在用户登录校验密码时使用
    public static final class InvalidUserPassword extends CmsException {
        public InvalidUserPassword(String name) {
            super("密码校验失败", HttpStatus.BAD_REQUEST);
        }
    }

    // 仅在JwtService.verify中使用，用来标识请求中携带的x-cms-token非法
    // 该异常会被AccessInterceptor捕获并返回4xx给前端
    public static final class InvalidCmsToken extends CmsException {
        public InvalidCmsToken() {
            super("安全TOKEN非法或已过期", HttpStatus.UNAUTHORIZED);
        }
    }

}
