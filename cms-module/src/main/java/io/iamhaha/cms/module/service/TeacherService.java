/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service;

import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.model.request.TeacherCreateReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Teacher service interface.
 *
 * @author dingshenglong
 */
public interface TeacherService {

    // ---- get/query/list ----
    User get(String id);

    /**
     * list all teachers
     * @return List, teacher lists
     */
    List<User> list();

    @Transactional
    void create(TeacherCreateReq req);

    @Transactional
    void delete(List<String> ids);
}
