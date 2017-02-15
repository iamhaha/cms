/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service;

import io.iamhaha.cms.model.course.Course;
import io.iamhaha.cms.module.model.request.CourseCreateReq;
import io.iamhaha.cms.module.model.response.CourseDetail;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Course service interface.
 *
 * @author dingshenglong
 */
public interface CourseService {
    // ---- get/query/list ---
    Course get(@NonNull String id);

    /**
     * get course detail.
     *
     * @param id course id
     * @return CourseDetail
     */
    CourseDetail getDetail(@NonNull String id);

    List<Course> list();

    List<Course> listByTeacher(String tid);

    List<Course> listByStudent(String sid);

    boolean teacherHasCourse(String tid);

    boolean studentHasCourse(String sid);

    // ---- create ----
    @Transactional
    void create(CourseCreateReq req);

    @Transactional
    void take(List<String> ids);

    @Transactional
    void drop(List<String> ids);

    // ---- delete ----
    @Transactional
    void delete(List<String> ids);
}
