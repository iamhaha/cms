/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.dao;

import io.iamhaha.cms.model.clazz.Clazz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Class DAO.
 *
 * @author dingshenglong
 */
public interface ClassRepository extends CrudRepository<Clazz, String> {
    List<Clazz> findAll();

    void deleteByIdIn(List<String> ids);
}
