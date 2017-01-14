/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Collections;

/**
 * X-CMS-RequestId configuration.
 *
 * @author dingshenglong
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdConfiguration {

    @Bean
    public FilterRegistrationBean requestIdFilterRegistrationBean() {
        RequestIdFilter filter = new RequestIdFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setUrlPatterns(Collections.singleton("/v1/*"));
        return registrationBean;
    }
}
