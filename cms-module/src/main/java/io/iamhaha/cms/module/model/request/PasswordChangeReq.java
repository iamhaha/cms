/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Change password request.
 *
 * @author dingshenglong
 */
@Getter
@Setter
public class PasswordChangeReq {
    private String id;

    private String old;

    @NotNull
    @Size(min = 6, max = 32)
    private String password;
}
