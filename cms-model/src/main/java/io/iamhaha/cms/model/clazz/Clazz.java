/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model.clazz;

import io.iamhaha.cms.model.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class entity.
 *
 * @author dingshenglong
 */
@Getter
@Setter
@Entity(name = "class")
public class Clazz extends EntityBase {

    @NotNull
    @Size(min = 1, max = 64)
    private String name;
}
