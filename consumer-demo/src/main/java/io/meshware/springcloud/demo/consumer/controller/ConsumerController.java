package io.meshware.springcloud.demo.consumer.controller;

import io.meshware.springcloud.demo.consumer.service.FooService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * ConsumerController
 *
 * @author Zhiguo.Chen
 * @version 20210702
 */
@Slf4j
@RestController
public class ConsumerController {

    private final RestTemplate restTemplate;

    private final FooService fooService;

    @Autowired
    public ConsumerController(RestTemplate restTemplate, FooService fooService) {
        this.restTemplate = restTemplate;
        this.fooService = fooService;
    }

    @GetMapping("/echo/{str}")
    public Map<String, Object> echo(@PathVariable String str) {
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromRestTemplate", restTemplate.getForObject("http://sc-provider/echo/" + str, String.class));
        result.put("resultFromFeignClient", fooService.echo(str));
        return result;
    }
}
