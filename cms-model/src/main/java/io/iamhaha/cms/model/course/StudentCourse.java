/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.iamhaha.cms.model.util.UtcTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Student-Course entity.
 *
 * @author dingshenglong
 */
@Getter
@Setter
@Entity(name = "student_course")
public class StudentCourse {
    @Id
    @GeneratedValue
    private Long id;

    private String sid;

    private String cid;

    @UtcTime
    @Column(name = "create_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @JsonIgnore
    public static String getStudentId(StudentCourse st) {
        return st.getSid();
    }

    @JsonIgnore
    public static String getCourseId(StudentCourse st) {
        return st.getCid();
    }
}
