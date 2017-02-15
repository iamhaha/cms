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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Student entity.
 *
 * @author dingshenglong
 */
@Getter
@Setter
@Entity(name = "student")
public class Student extends EntityBase {

    @Column(nullable = false)
    private String cid;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    public String getName() {
        return user.getName();
    }
}
