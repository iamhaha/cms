/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.configuration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * RequestId filter.
 *
 * @author dingshenglong
 */
@Slf4j
class RequestIdFilter implements Filter {

    private static final String X_CMS_REQUEST_ID = "x-cms-request-id";

    private ThreadLocal<Long> beginTime = new ThreadLocal<>();
    private ThreadLocal<String> threadLocalRequestId = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        preHandle((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
        afterCompletion((HttpServletRequest) request, (HttpServletResponse) response);
    }

    private void preHandle(HttpServletRequest request, HttpServletResponse response) {
        // get or create requestId
        String requestId = request.getHeader(X_CMS_REQUEST_ID);
        if (requestId == null) {
            requestId = response.getHeader(X_CMS_REQUEST_ID);
            if (requestId != null) {
                log.debug("X_BCE_REQUEST_ID found in HttpServletResponse");
            }
        }
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
            MDC.put(X_CMS_REQUEST_ID, requestId);
            log.info("X_BCE_REQUEST_ID not found in header, generate requestId:{} ", requestId);
        }

        response.addHeader(X_CMS_REQUEST_ID, requestId);
        threadLocalRequestId.set(requestId);
        MDC.put(X_CMS_REQUEST_ID, requestId);
        beginTime.set(System.currentTimeMillis());
        log.info("[begin] {} {}", request.getMethod(), request.getRequestURI());
    }

    private void afterCompletion(HttpServletRequest request, HttpServletResponse response) {
        String requestId = threadLocalRequestId.get();
        String responseRequestId = response.getHeader(X_CMS_REQUEST_ID);
        if (!requestId.equals(responseRequestId)) {
            log.error("response requestId changed. requestId={}, responseRequestId={}", requestId, responseRequestId);
        }
        response.setHeader(X_CMS_REQUEST_ID, requestId);

        // log access time
        long timeUsed = System.currentTimeMillis() - beginTime.get();
        log.info("[status:{},time:{}ms] {} {}", response.getStatus(), timeUsed,
                request.getMethod(), request.getRequestURI());

        String mdcRequestId = MDC.get(X_CMS_REQUEST_ID);
        if (!requestId.equals(mdcRequestId)) {
            log.error("MDC requestId changed. requestId={},  MDC[requestId]={}", requestId, mdcRequestId);
        }
        MDC.remove(X_CMS_REQUEST_ID);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
