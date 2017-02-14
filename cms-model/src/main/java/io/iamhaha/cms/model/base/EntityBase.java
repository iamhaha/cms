/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.iamhaha.cms.model.util.UtcTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Cms entity base.
 *
 * @author dingshenglong
 */
@MappedSuperclass
@Getter
@Setter
public abstract class EntityBase {

    @Id
    @Pattern(regexp = "[a-z0-9]{1,32}")
    @Column(nullable = false, updatable = false)
    private String id;

    @UtcTime
    @Column(name = "create_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @JsonIgnore
    @Column(name = "update_time", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @JsonIgnore
    @Version
    private Integer version;
}
