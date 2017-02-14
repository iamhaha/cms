/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service.impl;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.model.user.Student;
import io.iamhaha.cms.module.dao.StudentRepository;
import io.iamhaha.cms.module.model.request.StudentCreateReq;
import io.iamhaha.cms.module.service.ClassService;
import io.iamhaha.cms.module.service.StudentService;
import io.iamhaha.cms.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Student service implementation.
 *
 * @author dingshenglong
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    UserService userService;

    @Autowired
    ClassService classService;

    @Autowired
    StudentRepository repository;

    @Override
    public Student get(String id) {
        Student student = repository.findOne(id);
        if (student == null) {
            throw new CmsExceptions.NoSuchUser(id);
        }
        return student;
    }

    @Override
    public List<Student> list() {
        return repository.findAll();
    }

    @Override
    public List<Student> list(List<String> ids) {
        return repository.findByIdIn(ids);
    }

    @Transactional
    @Override
    public void create(StudentCreateReq req) {
        // check class
        classService.get(req.getCid());
        // create the related user
        req.setRole(Role.student);
        userService.create(req);
        Student one = repository.findOne(req.getId());
        if (one != null) {
            throw new CmsExceptions.DuplicateStudent(req.getId());
        }
        one = new Student();
        one.setId(req.getId());
        one.setCid(req.getCid());
        one.setCreateTime(new Date());
        repository.save(one);
    }

    @Transactional
    @Override
    public void delete(List<String> ids) {
        repository.deleteByIdIn(ids);
        userService.deleteById(ids);
    }
}
