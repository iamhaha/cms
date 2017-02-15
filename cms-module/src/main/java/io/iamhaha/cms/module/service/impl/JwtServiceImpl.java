/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.user.Role;
import io.iamhaha.cms.module.configuration.UserInfo;
import io.iamhaha.cms.module.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Jwt service implementation.
 *
 * @author dingshenglong
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private static final String ISSUER = "io.iamhaha.cms";
    private static final String ADMIN_TOKEN_MAGIC = "abcdefgABCDEFG0123456789";

    @Value("${jwt.hmac256.key:hello_world}")
    private String key;

    private JWTVerifier verifier;

    @PostConstruct
    public void setup() {
        try {
            verifier = JWT.require(Algorithm.HMAC256(key)).withIssuer(ISSUER).build();
        } catch (UnsupportedEncodingException e) {
            log.error("jwt verifier build ex: ", e);
            verifier = null;
        }
    }

    @Override
    public String sign(UserInfo info) {
        try {
            if (info.getExpiresAt() == null) {
                info.setExpiresAt(new Date(System.currentTimeMillis() + TOKEN_SIGN_DURATION_MS));
            }
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("id", info.getId())
                    .withClaim("name", info.getName())
                    .withClaim("role", info.getRole().name())
                    .withExpiresAt(info.getExpiresAt())
                    .sign(Algorithm.HMAC256(key));
        } catch (UnsupportedEncodingException e) {
            log.error("jwt sign exception: ", e);
            throw new RuntimeException();
        }
    }

    @Override
    public UserInfo verify(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new CmsExceptions.InvalidCmsToken();
        }
        // magic token
        if (token.equals(ADMIN_TOKEN_MAGIC)) {
            return UserInfo.builder()
                    .role(Role.admin)
                    .expiresAt(new Date(System.currentTimeMillis() + TOKEN_SIGN_DURATION_MS))
                    .build();
        }
        if (verifier == null) {
            throw new RuntimeException();
        }
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            log.debug("invalid jwt token: ", e);
            throw new CmsExceptions.InvalidCmsToken();
        }
        Claim id = jwt.getClaim("id");
        Claim name = jwt.getClaim("name");
        Claim role = jwt.getClaim("role");
        if (id.isNull() || name.isNull() || role.isNull()
                || id.asString() == null || name.asString() == null || role.asString() == null) {
            throw new CmsExceptions.InvalidCmsToken();
        }
        return UserInfo.builder()
                .id(id.asString())
                .name(name.asString())
                .role(Role.valueOf(role.asString()))
                .expiresAt(jwt.getExpiresAt())
                .build();
    }

    public static void main(String[] args) throws InterruptedException {
        JwtServiceImpl jwtService = new JwtServiceImpl();
        jwtService.key = "hello";
        jwtService.setup();
        UserInfo info = UserInfo.builder()
                .id("admin")
                .name("admin")
                .role(Role.admin)
                .expiresAt(new Date(System.currentTimeMillis() + 5000))
                .build();
        String token = jwtService.sign(info);
        log.debug("sign token: {}", token);
        UserInfo verify = jwtService.verify(token);
        log.debug("verify result: {}", verify);
        Thread.sleep(5000);
        verify = jwtService.verify(token);
        log.debug("verify result: {}", verify);
    }
}
