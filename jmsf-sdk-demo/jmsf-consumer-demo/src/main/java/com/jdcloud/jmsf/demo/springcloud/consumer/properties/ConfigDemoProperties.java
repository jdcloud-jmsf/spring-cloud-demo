package com.jdcloud.jmsf.demo.springcloud.consumer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * ConfigDemoProperties
 *
 * @author Zhiguo.Chen
 * @version 20210811
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "dynamic")
public class ConfigDemoProperties {

    private String stringValue;
    private boolean boolValue;
    private int intValue;

}
