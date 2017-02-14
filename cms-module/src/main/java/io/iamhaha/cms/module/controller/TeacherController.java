/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.controller;

import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.model.response.CmsResponse;
import io.iamhaha.cms.module.model.response.CourseDetail;
import io.iamhaha.cms.module.service.CourseService;
import io.iamhaha.cms.module.service.UserService;
import io.iamhaha.cms.module.util.CmsResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Teacher controller.
 *
 * @author dingshenglong
 */
@RestController
@RequestMapping(value = "/v1/teacher", method = RequestMethod.POST)
public class TeacherController {
    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @RequestMapping("/course/list")
    public CmsResponse<List<Course>> listCourse() {
        UserInfo info = userService.getUserInfo();
        assert info.getRole() == Role.teacher;
        return CmsResponseUtils.success(courseService.listByTeacher(info.getId()));
    }

    @RequestMapping(value = "/course/get", params = {"id"})
    public CmsResponse<CourseDetail> courseDetail(@RequestParam("id") String id) {
        return CmsResponseUtils.success(courseService.getDetail(id));
    }
}
