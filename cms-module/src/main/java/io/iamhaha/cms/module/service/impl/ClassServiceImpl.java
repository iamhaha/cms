/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.service.impl;

import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.model.clazz.Clazz;
import io.iamhaha.cms.module.dao.ClassRepository;
import io.iamhaha.cms.module.model.request.ClassCreateReq;
import io.iamhaha.cms.module.service.ClassService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Class service implementation.
 *
 * @author dingshenglong
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassRepository repository;

    @Override
    public Clazz get(@NonNull String id) {
        Clazz one = repository.findOne(id);
        if (one == null) {
            throw new CmsExceptions.NoSuchClass(id);
        }
        return one;
    }

    @Override
    public List<Clazz> list() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void create(ClassCreateReq req) {
        Clazz one = repository.findOne(req.getId());
        if (one != null) {
            throw new CmsExceptions.DuplicateClass(req.getId());
        }
        one = new Clazz();
        one.setId(req.getId());
        one.setName(req.getName());
        one.setCreateTime(new Date());
        repository.save(one);
    }

    @Transactional
    @Override
    public void delete(List<String> ids) {
        repository.deleteByIdIn(ids);
    }
}
