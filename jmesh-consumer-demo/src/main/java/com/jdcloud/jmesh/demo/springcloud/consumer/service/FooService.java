package com.jdcloud.jmesh.demo.springcloud.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FooService
 *
 * @author Zhiguo.Chen
 * @version 20210702
 */
@FeignClient("${PROVIDER_NAME:sc-jmesh-provider}")
public interface FooService {

    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);

    @GetMapping(value = "/echo/{str}/{sleepSeconds}")
    String echoBySleep(@PathVariable("str") String str, @PathVariable("sleepSeconds") Integer seconds);

    @GetMapping(value = "/exception")
    String exception();
}
