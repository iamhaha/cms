/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service.impl;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.configuration.UserInfoManager;
import io.iamhaha.cms.module.model.request.PasswordChangeReq;
import io.iamhaha.cms.module.model.request.UserCreateReq;
import io.iamhaha.cms.module.model.request.UserSignInReq;
import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.util.HashUtils;
import io.iamhaha.cms.module.dao.UserRepository;
import io.iamhaha.cms.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User service implementation.
 *
 * @author dingshenglong
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserInfo getUserInfo() {
        UserInfo info = UserInfoManager.getUserInfo();
        if (info == null) {
            throw new RuntimeException("no signed-in user in this thread");
        }
        return info;
    }

    @Override
    public User get(String id) {
        User user = repository.findOne(id);
        if (user == null) {
            throw new CmsExceptions.NoSuchUser(id);
        }
        return user;
    }

    @Override
    public List<User> list(Role role) {
        return repository.findByRole(role);
    }

    @Transactional
    @Override
    public void create(UserCreateReq user) {
        if (user.getRole() == null) {
            throw new RuntimeException();
        }
        User one = repository.findOne(user.getId());
        if (one != null) {
            throw new CmsExceptions.DuplicateUser(user.getName());
        }
        one = new User();
        one.setId(user.getId());
        one.setName(user.getName());
        one.setRole(user.getRole());
        one.setPasswordMd5(HashUtils.md5(user.getPassword()));
        one.setCreateTime(new Date());
        repository.save(one);
    }

    @Transactional
    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Transactional
    @Override
    public void delete(List<User> users) {
        repository.delete(users);
    }

    @Transactional
    @Override
    public void deleteById(List<String> ids) {
        repository.deleteByIdIn(ids);
    }

    @Override
    public User validate(UserSignInReq req) {
        User one = get(req.getId());
        if (!HashUtils.md5(req.getPassword()).equals(one.getPasswordMd5())) {
            throw new CmsExceptions.InvalidUserPassword(req.getId());
        }
        return one;
    }

    @Override
    public void checkPermission(String uid) {
        UserInfo info = getUserInfo();
        if (info.getRole() != Role.admin && !info.getId().equals(uid)) {
            throw new CmsExceptions.InvalidRequest();
        }
    }

    @Transactional
    @Override
    public void changePassword(PasswordChangeReq req, Boolean checkOld) {
        checkPermission(req.getId());
        User one = get(req.getId());
        if (checkOld && !HashUtils.md5(req.getOld()).equals(one.getPasswordMd5())) {
            throw new CmsExceptions.InvalidUserPassword(one.getId());
        }
        one.setPasswordMd5(HashUtils.md5(req.getPassword()));
        repository.save(one);
    }

    public static void main(String[] args) {
        // output: e10adc3949ba59abbe56e057f20f883e
        log.debug(HashUtils.md5("123456"));
    }
}
