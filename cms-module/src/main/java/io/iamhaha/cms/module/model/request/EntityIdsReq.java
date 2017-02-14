/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Entity id list request
 *
 * @author dingshenglong
 */
@Data
public class EntityIdsReq {
    @NotNull
    @NotEmpty
    private List<String> ids;
}
