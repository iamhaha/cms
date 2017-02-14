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
            super(String.format("no such user: %s", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class NoSuchTeacher extends CmsException {
        public NoSuchTeacher(String id) {
            super(String.format("no such teacher: %s", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class NoSuchStudent extends CmsException {
        public NoSuchStudent(String id) {
            super(String.format("no such student: %s", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class DuplicateUser extends CmsException {
        public DuplicateUser(String id) {
            super(String.format("duplicate user: %s", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class DuplicateStudent extends CmsException {
        public DuplicateStudent(String id) {
            super(String.format("duplicate student: %s", id), HttpStatus.BAD_REQUEST);
        }
    }

    public static final class InvalidUser extends CmsException {
        public InvalidUser(String name) {
            super(String.format("invalid user: %s, password is not correct", name));
        }
    }

    // ---- class exceptions ----
    public static final class NoSuchClass extends CmsException {
        public NoSuchClass(String id) {
            super(String.format("no such class: %s", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class DuplicateClass extends CmsException {
        public DuplicateClass(String id) {
            super(String.format("duplicate class: %s", id), HttpStatus.BAD_REQUEST);
        }
    }

    // --- course exceptions ----
    public static final class NoSuchCourse extends CmsException {
        public NoSuchCourse(String id) {
            super(String.format("no such course: %s", id), HttpStatus.NOT_FOUND);
        }
    }

    public static final class DuplicateCourse extends CmsException {
        public DuplicateCourse(String id) {
            super(String.format("duplicate course: %s", id), HttpStatus.BAD_REQUEST);
        }
    }

    // ---- misc ----
    // 仅在JwtService.verify中使用，用来标识请求中携带的x-cms-token非法
    // 该异常会被AccessInterceptor捕获并返回4xx给前端
    public static final class InvalidCmsToken extends CmsException {
        public InvalidCmsToken() {
            super("invalid cms token", HttpStatus.UNAUTHORIZED);
        }
    }

}
