/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.configuration;

import io.iamhaha.cms.module.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Cms authorization config.
 *
 * @author dingshenglong
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class AccessConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private JwtService jwtService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor(jwtService))
                .addPathPatterns("/v1/**")
                .excludePathPatterns("/v1/user/signin");
    }
}
