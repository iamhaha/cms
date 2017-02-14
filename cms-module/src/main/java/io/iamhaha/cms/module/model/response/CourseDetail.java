/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.response;

import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.model.user.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Course detail response.
 *
 * @author dingshenglong
 */
@Getter
@Setter
public class CourseDetail {
    private Course course;
    private List<Student> students = new ArrayList<>();
}
