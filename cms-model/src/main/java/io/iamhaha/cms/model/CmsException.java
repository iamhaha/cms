/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Cms exception.
 *
 * @author dingshenglong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class CmsException extends RuntimeException {
    private int httpStatus = HttpStatus.BAD_REQUEST.value();
    private String code;

    public CmsException(String message) {
        super(message);
        String name = this.getClass().getSimpleName();
        if (this.getClass().getName().contains("$")) {
            String[] parts = getClass().getCanonicalName().split("\\.");
            name = parts[parts.length - 2] + "." + parts[parts.length - 1];
        }
        this.code = name;
    }

    public CmsException(String message, int httpStatus) {
        this(message);
        this.httpStatus = httpStatus;
    }

    public CmsException(String message, HttpStatus httpStatus) {
        this(message, httpStatus.value());
    }
}
