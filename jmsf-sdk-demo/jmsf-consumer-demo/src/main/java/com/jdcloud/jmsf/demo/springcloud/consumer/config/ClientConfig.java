package com.jdcloud.jmsf.demo.springcloud.consumer.config;

import com.jdcloud.jmsf.demo.springcloud.consumer.properties.ConfigDemoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * ClientConfig
 *
 * @author Zhiguo.Chen
 * @version 20210702
 */
@Configuration
@EnableConfigurationProperties(ConfigDemoProperties.class)
public class ClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // @Bean
    // @LoadBalanced
    // RestTemplate jmsfRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
    //     return new RestTemplate(clientHttpRequestFactory);
    // }
}
