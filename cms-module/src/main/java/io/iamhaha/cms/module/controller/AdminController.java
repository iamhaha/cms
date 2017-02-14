/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.controller;

import io.iamhaha.cms.model.clazz.Clazz;
import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.model.user.Student;
import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.model.request.ClassCreateReq;
import io.iamhaha.cms.module.model.request.CourseCreateReq;
import io.iamhaha.cms.module.model.request.EntityIdsReq;
import io.iamhaha.cms.module.model.request.StudentCreateReq;
import io.iamhaha.cms.module.model.request.TeacherCreateReq;
import io.iamhaha.cms.module.model.response.CmsResponse;
import io.iamhaha.cms.module.service.ClassService;
import io.iamhaha.cms.module.service.CourseService;
import io.iamhaha.cms.module.service.StudentService;
import io.iamhaha.cms.module.service.TeacherService;
import io.iamhaha.cms.module.util.CmsResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Admin controller.
 *
 * @author dingshenglong
 */
@RestController
@RequestMapping(value = "/v1/admin", method = RequestMethod.POST)
public class AdminController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    ClassService classService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @RequestMapping("/teacher/list")
    public CmsResponse<List<User>> listTeacher() {
        return CmsResponseUtils.success(teacherService.list());
    }

    @RequestMapping("/teacher/create")
    public CmsResponse createTeacher(@RequestBody @Valid TeacherCreateReq req) {
        teacherService.create(req);
        return CmsResponseUtils.success();
    }

    @RequestMapping("/teacher/delete")
    public CmsResponse deleteTeacher(@RequestBody @Valid EntityIdsReq req) {
        teacherService.delete(req.getIds());
        return CmsResponseUtils.success();
    }

    @RequestMapping("/class/list")
    public CmsResponse<List<Clazz>> listClass() {
        return CmsResponseUtils.success(classService.list());
    }

    @RequestMapping("/class/create")
    public CmsResponse createClass(@RequestBody ClassCreateReq req) {
        classService.create(req);
        return CmsResponseUtils.success();
    }

    @RequestMapping("/class/delete")
    public CmsResponse deleteClass(@RequestBody @Valid EntityIdsReq req) {
        classService.delete(req.getIds());
        return CmsResponseUtils.success();
    }

    @RequestMapping("/student/list")
    public CmsResponse<List<Student>> listStudent() {
        return CmsResponseUtils.success(studentService.list());
    }

    @RequestMapping("/student/create")
    public CmsResponse createStudent(@RequestBody StudentCreateReq req) {
        studentService.create(req);
        return CmsResponseUtils.success();
    }

    @RequestMapping("/student/delete")
    public CmsResponse deleteStudent(@RequestBody @Valid EntityIdsReq req) {
        studentService.delete(req.getIds());
        return CmsResponseUtils.success();
    }

    @RequestMapping("/course/list")
    public CmsResponse<List<Course>> listCourse() {
        return CmsResponseUtils.success(courseService.list());
    }

    @RequestMapping("/course/create")
    public CmsResponse createCourse(@RequestBody CourseCreateReq req) {
        courseService.create(req);
        return CmsResponseUtils.success();
    }

    @RequestMapping("/course/delete")
    public CmsResponse deleteCourse(@RequestBody EntityIdsReq req) {
        courseService.delete(req.getIds());
        return CmsResponseUtils.success();
    }
}
