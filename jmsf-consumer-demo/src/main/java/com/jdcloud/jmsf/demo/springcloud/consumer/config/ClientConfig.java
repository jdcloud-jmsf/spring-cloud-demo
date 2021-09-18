package com.jdcloud.jmsf.demo.springcloud.consumer.config;

import com.jdcloud.jmsf.demo.springcloud.consumer.properties.ConfigDemoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ClientConfig
 *
 * @author Zhiguo.Chen
 * @version 20210702
 */
@Configuration
@EnableConfigurationProperties(ConfigDemoProperties.class)
public class ClientConfig {

    // @Bean
    // @LoadBalanced
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }
}
