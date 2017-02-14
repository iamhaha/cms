/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.controller;

import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.model.request.EntityIdsReq;
import io.iamhaha.cms.module.model.response.CmsResponse;
import io.iamhaha.cms.module.service.CourseService;
import io.iamhaha.cms.module.service.TeacherService;
import io.iamhaha.cms.module.service.UserService;
import io.iamhaha.cms.module.util.CmsResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Student controller.
 *
 * @author dingshenglong
 */
@RestController
@RequestMapping(value = "/v1/student", method = RequestMethod.POST)
public class StudentController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @RequestMapping("/teacher/list")
    public CmsResponse<List<User>> listTeacher() {
        return CmsResponseUtils.success(teacherService.list());
    }

    @RequestMapping("/course/list")
    public CmsResponse<List<Course>> listCourse() {
        UserInfo info = userService.getUserInfo();
        assert info.getRole() == Role.student;
        return CmsResponseUtils.success(courseService.listByStudent(info.getId()));
    }

    @RequestMapping("/course/listall")
    public CmsResponse<List<Course>> listAllCourse() {
        return CmsResponseUtils.success(courseService.list());
    }

    @RequestMapping("/course/take")
    public CmsResponse takeCourse(@RequestBody @Valid EntityIdsReq req) {
        courseService.take(req.getIds());
        return CmsResponseUtils.success();
    }

    @RequestMapping("/course/drop")
    public CmsResponse dropCourse(@RequestBody @Valid EntityIdsReq req) {
        courseService.drop(req.getIds());
        return CmsResponseUtils.success();
    }
}
