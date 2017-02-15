/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.configuration;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.module.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Access interceptors.
 *
 * @author dingshenglong
 */
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

    public static final String X_CMS_TOKEN = "x-cms-token";
    private static final long TOKEN_NEED_EXTEND_DURATION_MS = 5 * 60 * 1000; // 5 min

    private JwtService jwtService;

    public AccessInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        String token = httpServletRequest.getHeader(X_CMS_TOKEN);
        String uri = httpServletRequest.getRequestURI();
        try {
            UserInfo info = jwtService.verify(token);
            // 我们假设token是绝对无法伪造的，所以这里不会校验info.id代表的user在cms中是否实际存在
            if (!uri.startsWith("/v1/user") && !uri.startsWith("/v1/" + info.getRole().toString())) {
                throw new CmsExceptions.InvalidCmsToken();
            }
            UserInfoManager.setUserInfo(info);
            log.debug("UserInfoManager set signIn info: {}", info);
        } catch (CmsExceptions.InvalidCmsToken ex) {
            log.debug("validate jwt token ex: ", ex);
            throw ex;
        } catch (Throwable t) {
            log.warn("un-expected ex: ", t);
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        UserInfo info = UserInfoManager.getUserInfo();
        if (info.getExpiresAt().getTime() - new Date().getTime() < TOKEN_NEED_EXTEND_DURATION_MS) {
            String token = jwtService.sign(UserInfo.builder()
                    .id(info.getId())
                    .name(info.getName())
                    .role(info.getRole())
                    .build());
            httpServletResponse.setHeader(X_CMS_TOKEN, token);
            log.debug("extend client token");
        }
        UserInfoManager.removeUserInfo();
        log.debug("UserInfoManager remove signIn info");
    }
}
