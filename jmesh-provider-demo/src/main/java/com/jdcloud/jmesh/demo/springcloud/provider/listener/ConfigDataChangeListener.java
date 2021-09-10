package com.jdcloud.jmesh.demo.springcloud.provider.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * ConfigDataChangeListener
 *
 * @author Zhiguo.Chen
 * @version 20210716
 */
@Slf4j
@Component
public class ConfigDataChangeListener implements ApplicationListener<EnvironmentChangeEvent> {

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent environmentChangeEvent) {
        log.info("keys={},data={}", environmentChangeEvent.getKeys(), environmentChangeEvent.getSource());
    }
}
