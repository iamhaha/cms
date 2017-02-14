/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.configuration;

import io.iamhaha.cms.model.user.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Sign in info.
 *
 * @author dingshenglong
 */
@Data
@Builder
public class UserInfo {
    private String id;
    private String name;
    private Role role;
    private Date expiresAt;
}
