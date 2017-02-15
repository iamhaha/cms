/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import io.iamhaha.cms.model.user.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Create user request
 *
 * @author dingshenglong
 */
@Getter
@Setter
public class UserCreateReq {
    @NotNull
    @Pattern(regexp = "[a-z0-9]{1,32}")
    private String id;
    @NotNull
    @Size(min = 1, max = 64)
    private String name;
    @NotNull
    @Size(min = 6, max = 32)
    private String password;
    private Role role;
}
