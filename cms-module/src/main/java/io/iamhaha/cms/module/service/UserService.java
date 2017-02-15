/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service;

import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.model.request.PasswordChangeReq;
import io.iamhaha.cms.module.model.request.TeacherCreateReq;
import io.iamhaha.cms.module.model.request.UserCreateReq;
import io.iamhaha.cms.module.model.request.UserSignInReq;
import io.iamhaha.cms.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User service interface.
 *
 * @author dingshenglong
 */
public interface UserService {

    // ---- get/query apis ----
    UserInfo getUserInfo();

    /**
     * get user by id, non-locking
     *
     * @param id user id
     * @return User, null if not exist
     */
    User get(String id);

    List<User> list(Role role);

    // ---- create apis ----
    @Transactional
    void create(UserCreateReq req);

    // ---- delete ----
    @Transactional
    void delete(String id);

    @Transactional
    void delete(List<User> users);

    @Transactional
    void deleteById(List<String> ids);

    // ---- misc ----
    User validate(UserSignInReq req);

    /**
     * check if current user can do operations of some user.
     * @param uid the target user id
     */
    void checkPermission(String uid);

    @Transactional
    void changePassword(PasswordChangeReq req, Boolean checkOld);
}
