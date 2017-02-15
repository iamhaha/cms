/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.configuration;

import io.iamhaha.cms.model.CmsException;
import io.iamhaha.cms.model.CmsExceptions;
import io.iamhaha.cms.module.model.response.CmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Cms exception handler.
 *
 * @author dingshenglong
 */
@Slf4j
@ControllerAdvice
public class CmsExceptionHandler {

    @ExceptionHandler(CmsExceptions.InvalidCmsToken.class)
    public ResponseEntity<CmsResponse> handle(CmsExceptions.InvalidCmsToken ex) {
        log.debug("[exception:InvalidCmsToken] handled: {}", ex);
        CmsResponse body = new CmsResponse();
        body.setCode(CmsResponse.CODE_BAD_REQUEST);
        body.setMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CmsResponse> handle(MethodArgumentNotValidException ex) {
        log.debug("[exception:MethodArgumentNotValidException] handled: ", ex);
        CmsResponse body = new CmsResponse();
        body.setCode(CmsResponse.CODE_BAD_REQUEST);
        body.setMessage("请求参数格式不合预期，请检查参数");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ExceptionHandler(CmsException.class)
    public ResponseEntity<CmsResponse> handle(CmsException ex) {
        log.debug("[exception:CmsException] handled: ", ex);
        CmsResponse body = new CmsResponse();
        body.setCode(CmsResponse.CODE_CMS_EXCEPTION);
        body.setMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CmsResponse> handle(Throwable ex) {
        log.error("[exception:Throwable] handled: ", ex);
        CmsResponse body = new CmsResponse();
        body.setCode(CmsResponse.CODE_ERROR);
        body.setMessage("服务器内部错误，请向系统管理员反馈");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
