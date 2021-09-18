package com.jdcloud.jmsf.demo.springcloud.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("test")
public class TestConfiguration {

    static {
        //TODO
    }

}
