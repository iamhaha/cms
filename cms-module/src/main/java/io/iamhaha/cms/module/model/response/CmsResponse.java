/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cms common response.
 *
 * @author dingshenglong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CmsResponse<T> {
    // response code: 0 means success, all other codes means failure
    public static final int CODE_SUCCESS = 0; // 1 means success
    public static final int CODE_ERROR = 1; // unexpected internal error
    // -- specific error --
    public static final int CODE_BAD_REQUEST = 2; // bad request
    public static final int CODE_CMS_EXCEPTION = 3; // expected cms exception

    private int code;
    private T data;
    private String message; // used as error message for now
}
