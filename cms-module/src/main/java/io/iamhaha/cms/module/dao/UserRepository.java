/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.dao;

import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.model.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User DAO.
 *
 * @author dingshenglong
 */
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByRole(Role role);

    void deleteByIdIn(List<String> ids);
}
