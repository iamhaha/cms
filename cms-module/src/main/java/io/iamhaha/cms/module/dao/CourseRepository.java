/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.dao;

import io.iamhaha.cms.model.course.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Course DAO.
 *
 * @author dingshenglong
 */
public interface CourseRepository extends CrudRepository<Course, String> {

    List<Course> findAll();

    List<Course> findByTid(String tid);

    void deleteByIdIn(List<String> ids);
}
