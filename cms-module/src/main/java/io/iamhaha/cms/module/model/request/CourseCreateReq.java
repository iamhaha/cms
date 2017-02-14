/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Create course request
 *
 * @author dingshenglong
 */
@Data
public class CourseCreateReq {
    @NotNull
    @Size(min = 1, max = 32)
    private String id;
    @NotNull
    @Size(min = 1, max = 64)
    private String name;
    @Size(max = 256)
    private String description;
    @NotNull
    private String tid;
}
