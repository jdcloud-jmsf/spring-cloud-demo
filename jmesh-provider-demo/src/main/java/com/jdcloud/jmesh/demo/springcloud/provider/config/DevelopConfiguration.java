package com.jdcloud.jmesh.demo.springcloud.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("dev")
public class DevelopConfiguration {

    static {
        //TODO
    }

}
