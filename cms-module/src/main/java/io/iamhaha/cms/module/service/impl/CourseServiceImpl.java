/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service.impl;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.model.course.StudentCourse;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.model.user.Student;
import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.dao.CourseRepository;
import io.iamhaha.cms.module.dao.StudentCourseRepository;
import io.iamhaha.cms.module.dao.StudentRepository;
import io.iamhaha.cms.module.model.request.CourseCreateReq;
import io.iamhaha.cms.module.model.response.CourseDetail;
import io.iamhaha.cms.module.service.CourseService;
import io.iamhaha.cms.module.service.StudentService;
import io.iamhaha.cms.module.service.TeacherService;
import io.iamhaha.cms.module.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Course service implementation.
 *
 * @author dingshenglong
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository repository;

    @Autowired
    StudentCourseRepository scRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Override
    public Course get(@NonNull String id) {
        Course one = repository.findOne(id);
        if (one == null) {
            throw new CmsExceptions.NoSuchCourse(id);
        }
        return one;
    }

    @Override
    public CourseDetail getDetail(@NonNull String id) {
        Course one = get(id);
        UserInfo info = userService.getUserInfo();
        assert (info.getRole() == Role.admin || (info.getRole() == Role.teacher && info.getId().equals(one.getTid())));
        List<String> sids = scRepository.findByCid(id).map(StudentCourse::getStudentId).collect(Collectors.toList());
        CourseDetail detail = new CourseDetail();
        detail.setCourse(one);
        if (!sids.isEmpty()) {
            detail.setStudents(studentService.list(sids));
        }
        return detail;
    }

    @Override
    public List<Course> list() {
        return repository.findAll();
    }

    @Override
    public List<Course> listByTeacher(String tid) {
        return repository.findByTid(tid);
    }

    @Override
    public List<Course> listByStudent(String sid) {
        List<String> cids = scRepository.findBySid(sid)
                .map(StudentCourse::getCourseId)
                .collect(Collectors.toList());
        List<Course> result = new ArrayList<>();
        cids.forEach(cid -> result.add(get(cid)));
        return result;
    }

    @Transactional
    @Override
    public void create(CourseCreateReq req) {
        // check teacher
        teacherService.get(req.getTid());
        Course one = repository.findOne(req.getId());
        if (one != null) {
            throw new CmsExceptions.DuplicateCourse(req.getId());
        }
        one = new Course();
        one.setId(req.getId());
        one.setName(req.getName());
        one.setDescription(req.getDescription());
        one.setTid(req.getTid());
        one.setCreateTime(new Date());
        repository.save(one);
    }

    @Transactional
    @Override
    public void take(List<String> ids) {
        User user = userService.getCurrentUser();
        assert user.getRole() == Role.student;
        List<String> cids = scRepository.findBySid(user.getId())
                .map(StudentCourse::getCourseId)
                .collect(Collectors.toList());
        List<StudentCourse> courses = new ArrayList<>();
        for (String cid : ids) {
            if (cids.contains(cid)) {
                // continue if cid is already taken
                continue;
            }
            // check whether course exists
            get(cid);
            StudentCourse one = new StudentCourse();
            one.setSid(user.getId());
            one.setCid(cid);
            one.setCreateTime(new Date());
            courses.add(one);
        }
        if (!courses.isEmpty()) {
            scRepository.save(courses);
        }
    }

    @Transactional
    @Override
    public void drop(List<String> ids) {
        User user = userService.getCurrentUser();
        assert user.getRole() == Role.student;
        List<StudentCourse> courses = scRepository.findBySid(user.getId())
                .filter(one -> ids.contains(one.getCid()))
                .collect(Collectors.toList());
        if (!courses.isEmpty()) {
            scRepository.delete(courses);
        }
    }

    @Transactional
    @Override
    public void delete(List<String> ids) {
        repository.deleteByIdIn(ids);
    }
}
