/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model.course;

import io.iamhaha.cms.model.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * Course entity.
 *
 * @author dingshenglong
 */
@Getter
@Setter
@Entity(name = "course")
public class Course extends EntityBase {
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    @Size(max = 256)
    private String description;

    @Column(nullable = false)
    private String tid;
}
