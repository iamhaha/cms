/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service;

import io.iamhaha.cms.model.user.Student;
import io.iamhaha.cms.module.model.request.StudentCreateReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Student service interface.
 *
 * @author dingshenglong
 */
public interface StudentService {

    Student get(String id);

    List<Student> list();

    List<Student> list(List<String> ids);

    List<Student> listByClass(String cid);

    @Transactional
    void create(StudentCreateReq req);

    @Transactional
    void delete(List<String> ids);
}
