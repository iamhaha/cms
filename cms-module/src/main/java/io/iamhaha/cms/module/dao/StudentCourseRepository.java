/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.dao;

import io.iamhaha.cms.model.course.StudentCourse;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

/**
 * Student-Course DAO.
 *
 * @author dingshenglong
 */
public interface StudentCourseRepository extends CrudRepository<StudentCourse, Long> {
    Stream<StudentCourse> findBySid(String sid);
    Stream<StudentCourse> findByCid(String cid);
}
