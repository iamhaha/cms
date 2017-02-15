/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service;

import io.iamhaha.cms.module.configuration.UserInfo;
import lombok.NonNull;

/**
 * JWT service interface.
 *
 * @author dingshenglong
 */
public interface JwtService {
    // default token valid duration
    long TOKEN_SIGN_DURATION_MS = 60 * 60 * 1000; // 60 min

    String sign(@NonNull UserInfo info);

    UserInfo verify(String token);
}
