/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Student Create request.
 *
 * @author dingshenglong
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentCreateReq extends UserCreateReq {
    @NotNull
    private String cid;
}
