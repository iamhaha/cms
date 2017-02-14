/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.response;

import io.iamhaha.cms.model.user.Role;
import lombok.Builder;
import lombok.Data;

/**
 * User sign in response.
 *
 * @author dingshenglong
 */
@Data
@Builder
public class UserSignInRes {
    private String id;
    private String name;
    private Role role;
}
