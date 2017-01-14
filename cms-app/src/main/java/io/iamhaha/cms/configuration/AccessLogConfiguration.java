/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.configuration;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Access log configuration
 *
 * @author dingshenglong
 */
@Slf4j
@Configuration
public class AccessLogConfiguration {

    @Bean
    public FilterRegistrationBean teeFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new TeeFilter());
        bean.setUrlPatterns(Collections.singleton("/v1/*"));
        return bean;
    }

    // only for tomcat access log
    // TODO: support Jetty access log
    // Ref 1: https://github.com/akihyro/spring-boot-ext-logback-access
    // Ref 2: https://github.com/wacai/spring-boot-starter-logback-access
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    ((TomcatEmbeddedServletContainerFactory) container)
                            .addContextCustomizers(new TomcatContextCustomizer() {
                                @Override
                                public void customize(Context context) {
                                    LogbackValve valve = new LogbackValve();
                                    // set the logback config file name is ok, no need to set the path
                                    valve.setFilename("logback-access.xml");
                                    context.getPipeline().addValve(valve);
                                }
                            });
                } else {
                    log.warn("skip tomcat access-log customization, container = {}", container);
                }
            }
        };
    }
}
