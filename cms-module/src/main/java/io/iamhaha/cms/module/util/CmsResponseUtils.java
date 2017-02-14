/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.util;

import io.iamhaha.cms.module.model.response.CmsResponse;

/**
 * Cms response utils.
 *
 * @author dingshenglong
 */
public final class CmsResponseUtils {

    public static CmsResponse success() {
        return create(CmsResponse.CODE_SUCCESS);
    }

    public static <T> CmsResponse<T> success(T data) {
        return create(CmsResponse.CODE_SUCCESS, data);
    }

    private static CmsResponse create(int code) {
        CmsResponse response = new CmsResponse();
        response.setCode(code);
        return response;
    }

    private static CmsResponse create(int code, String message) {
        CmsResponse response = new CmsResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    private static <T> CmsResponse<T> create(int code, T data) {
        CmsResponse<T> response = new CmsResponse<>();
        response.setCode(code);
        response.setData(data);
        return response;
    }

}
