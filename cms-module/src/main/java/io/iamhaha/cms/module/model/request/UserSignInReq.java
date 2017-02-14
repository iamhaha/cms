/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * User SignIn request.
 *
 * @author dingshenglong
 */
@Getter
@Setter
public class UserSignInReq {
    @NotNull
    private String id;
    @NotNull
    private String password;
}
