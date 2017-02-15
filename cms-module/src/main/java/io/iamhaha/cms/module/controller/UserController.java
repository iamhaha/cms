/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.controller;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.user.User;
import io.iamhaha.cms.module.model.request.PasswordChangeReq;
import io.iamhaha.cms.module.model.response.CmsResponse;
import io.iamhaha.cms.module.model.request.UserSignInReq;
import io.iamhaha.cms.module.model.response.UserSignInRes;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.util.CmsResponseUtils;
import io.iamhaha.cms.module.configuration.AccessInterceptor;
import io.iamhaha.cms.module.service.JwtService;
import io.iamhaha.cms.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * User controller.
 *
 * @author dingshenglong
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/user", method = RequestMethod.POST)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping("/signin")
    public CmsResponse<UserSignInRes> signIn(HttpServletResponse response, @RequestBody @Valid UserSignInReq req) {
        User user = userService.validate(req);
        UserInfo info = UserInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
        response.setHeader(AccessInterceptor.X_CMS_TOKEN, jwtService.sign(info));
        UserSignInRes result = UserSignInRes.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
        return CmsResponseUtils.success(result);
    }

    @RequestMapping("/changepwd")
    public CmsResponse changePassword(@RequestBody @Valid PasswordChangeReq req) {
        UserInfo info = userService.getUserInfo();
        req.setId(info.getId());
        if (req.getOld() == null) {
            throw new CmsExceptions.InvalidRequest();
        }
        userService.changePassword(req, true);
        return CmsResponseUtils.success();
    }
}
