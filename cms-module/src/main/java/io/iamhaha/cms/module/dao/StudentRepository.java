/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.dao;

import io.iamhaha.cms.model.user.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Student DAO.
 *
 * @author dingshenglong
 */
public interface StudentRepository extends CrudRepository<Student, String> {
    List<Student> findAll();

    List<Student> findByIdIn(List<String> ids);

    void deleteByIdIn(List<String> ids);
}
