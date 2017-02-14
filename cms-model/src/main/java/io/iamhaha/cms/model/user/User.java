/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.iamhaha.cms.model.base.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * User entity in cms, store user common info.
 *
 * @author dingshenglong
 */
@Getter
@Setter
@Entity(name = "user")
public class User extends EntityBase {
    @Size(max = 64)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Role role;

    @JsonIgnore
    @Size(max = 64)
    @Column(name = "password", nullable = false)
    private String passwordMd5;
}
