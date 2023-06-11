package com.jdcloud.jmsf.demo.springcloud.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
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

    @Autowired
    private RefreshScope refreshScope;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent environmentChangeEvent) {
        log.info("keys={},data={}", environmentChangeEvent.getKeys(), environmentChangeEvent.getSource());
        refreshScope.refreshAll();
    }
}
