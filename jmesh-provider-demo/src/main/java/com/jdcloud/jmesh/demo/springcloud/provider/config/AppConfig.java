package com.jdcloud.jmesh.demo.springcloud.provider.config;

import com.jdcloud.jmesh.demo.springcloud.provider.properties.ConfigDemoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig
 *
 * @author Zhiguo.Chen
 * @version 20210811
 */
@Configuration
@RefreshScope
@EnableConfigurationProperties(ConfigDemoProperties.class)
public class AppConfig {

    @Bean
    @RefreshScope
    ConfigDemoProperties configDemoProperties() {
        return new ConfigDemoProperties();
    }
}
