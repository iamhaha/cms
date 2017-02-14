/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service;

import io.iamhaha.cms.model.clazz.Clazz;
import io.iamhaha.cms.module.model.request.ClassCreateReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class service interface.
 *
 * @author dingshenglong
 */
public interface ClassService {

    Clazz get(String id);

    List<Clazz> list();

    @Transactional
    void create(ClassCreateReq req);

    @Transactional
    void delete(List<String> ids);
}
