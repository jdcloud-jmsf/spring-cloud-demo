package com.jdcloud.jmsf.demo.springcloud.consumer.controller;

import com.jdcloud.jmsf.core.entity.CommonResponse;
import com.jdcloud.jmsf.core.entity.Metadata;
import com.jdcloud.jmsf.demo.springcloud.consumer.properties.ConfigDemoProperties;
import com.jdcloud.jmsf.demo.springcloud.consumer.service.FooService;
import com.jdcloud.jmsf.demo.springcloud.consumer.vo.RequestVo;
import com.jdcloud.jmsf.meshware.common.entity.TagPair;
import com.jdcloud.jmsf.meshware.context.JmsfContext;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * ConsumerController
 *
 * @author Zhiguo.Chen
 * @version 20210702
 */
@Slf4j
@Api("default")
@RestController
@RefreshScope
public class ConsumerController {

    @Resource
    @LoadBalanced
    private RestTemplate loadBalanced;

    @Autowired
    private FooService fooService;

    @Autowired
    private Metadata metadata;

    @Autowired
    @LoadBalanced
    private WebClient.Builder webClientBuilder;

    @Value("${PROVIDER_NAME:jmsf-provider-demo}")
    private String providerName;

    @GetMapping("/")
    public String home() {
        return "Hello world, from: " + metadata.getServiceName();
    }

    @GetMapping("/env/{str}")
    public Map<String, Object> getEnv(@PathVariable String str) {
        Map<String, Object> result = new HashMap<>();
        // result.put("resultFromFeignClient", fooService.echo(str));
        result.put("resultGetEnv", System.getenv(str));
        return result;
    }

    @GetMapping("/echo/{str}")
    public Map<String, Object> echo(@PathVariable String str) {
        JmsfContext.putTag("aaa", "avalue", TagPair.ControlFlag.TRANSITIVE);
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromRestTemplate", loadBalanced.getForObject("http://" + providerName + "/echo/" + str, String.class));
        result.put("resultFromFeignClient", fooService.echo(str));
        return result;
    }

    @GetMapping(value = "/echo/{str}/{sleepSeconds}")
    public Map<String, Object> echoBySleep(@PathVariable("str") String str, @PathVariable("sleepSeconds") Integer seconds) {
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromRestTemplate", loadBalanced.getForObject("http://" + providerName + "/echo/" + str + "/" + seconds, String.class));
        result.put("resultFromFeignClient", fooService.echoBySleep(str, seconds));
        return result;
    }

    @GetMapping("/echoByRT/{str}")
    public Map<String, Object> echoByRestTemplate(@PathVariable String str) {
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromRestTemplate", loadBalanced.getForObject("http://" + providerName + "/echo/" + str, String.class));
        return result;
    }

    @GetMapping("/echoByFeign/{str}")
    public Map<String, Object> echoByFeign(@PathVariable String str) {
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromFeignClient", fooService.echo(str));
        return result;
    }

    @GetMapping("/echoByWC/{str}")
    public Mono<String> echoByWebClient(@PathVariable String str) {
        Map<String, Object> result = new HashMap<>();
        Mono<String> stringMono = webClientBuilder.build().get().uri("http://" + providerName + "/echo/{id}", str)
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    @GetMapping(value = "/echo2/{str}")
    public String echo2(@PathVariable String str) {
        log.info("consumer-demo -- request info: [" + str + "]");
        return str + ", from: serviceName=" + metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId();
    }

    @PostMapping(value = "/postFunc")
    public CommonResponse postFunc(@RequestBody RequestVo requestVo) {
        log.info("consumer-demo -- request info: [" + requestVo.toString() + "]");
        CommonResponse commonResponse = CommonResponse.createResponse();
        commonResponse.put("serviceName", metadata.getServiceName());
        commonResponse.put("instanceId", metadata.getInstanceId());
        commonResponse.put("requestBody", requestVo);
        return commonResponse;
    }

    @DeleteMapping(value = "/delFunc")
    public CommonResponse delFunc(@RequestBody RequestVo requestVo) {
        log.info("consumer-demo -- request info: [" + requestVo.toString() + "]");
        CommonResponse commonResponse = CommonResponse.createResponse();
        commonResponse.put("serviceName", metadata.getServiceName());
        commonResponse.put("instanceId", metadata.getInstanceId());
        commonResponse.put("requestBody", requestVo);
        return commonResponse;
    }

    @GetMapping(value = "/exception")
    public String exception() {
        // loadBalanced.getForObject("http://"+providerName+"/echo/exception", String.class);
        return fooService.exception();
    }

    @Value("${dynamic.commonData:}")
    private String commonData;

    @Autowired
    private ConfigDemoProperties configDemoProperties;

    @GetMapping(value = "/getConfig")
    public Map<String, Object> getConfig() {
        Map<String, Object> configData = new HashMap<>();
        configData.put("metadata", metadata.toString());
        configData.put("commonData", commonData);
        configData.put("dynamicConfig", configDemoProperties.toString());
        return configData;
    }

    @GetMapping(value = "/invokeHttpBin")
    public String invokeHttpBin() {
        return loadBalanced.getForObject("http://httpbin/get", String.class);
    }

    @GetMapping("/status/{code}")
    public CommonResponse status(@PathVariable int code) {
        CommonResponse commonResponse = CommonResponse.createResponse();
        commonResponse.setData(loadBalanced.getForObject("http://" + providerName + "/status/" + code, String.class));
        return commonResponse;
    }
}
