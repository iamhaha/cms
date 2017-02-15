/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service.impl;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.model.request.TeacherCreateReq;
import io.iamhaha.cms.module.service.CourseService;
import io.iamhaha.cms.module.service.TeacherService;
import io.iamhaha.cms.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Teacher service implementation.
 *
 * @author dingshenglong
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Override
    public User get(String id) {
        User user = userService.get(id);
        if (user == null || user.getRole() != Role.teacher) {
            throw new CmsExceptions.NoSuchTeacher(id);
        }
        return user;
    }

    @Override
    public List<User> list() {
        return userService.list(Role.teacher);
    }

    @Transactional
    @Override
    public void create(TeacherCreateReq req) {
        req.setRole(Role.teacher);
        userService.create(req);
    }

    @Transactional
    @Override
    public void delete(List<String> ids) {
        // check if teacher
        ids.forEach(id -> {
            if (courseService.teacherHasCourse(id)) {
                throw new CmsExceptions.TeacherInUse(id);
            }
        });
        userService.deleteById(ids);
    }
}
