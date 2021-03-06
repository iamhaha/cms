/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Create class request
 *
 * @author dingshenglong
 */
@Data
public class ClassCreateReq {
    @NotNull
    @Pattern(regexp = "[a-z0-9]{1,32}")
    private String id;
    @NotNull
    @Size(min = 1, max = 64)
    private String name;
}
