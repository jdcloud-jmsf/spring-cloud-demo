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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
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

    // 使用SDK提供的对象，具有通过注册中心进行服务发现的能力
    @Resource
    @Qualifier("jmsfRestTemplate")
    @LoadBalanced
    private RestTemplate loadBalanced;

    // 使用ClientConfig中自定义的RT对象，不具有通过注册中心进行服务发现的能力
    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private FooService fooService;

    @Autowired
    private Metadata metadata;

    @Autowired
    // @LoadBalanced
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
        // 创建自定义的请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // 设置请求的 Content-Type
        headers.set("custom-header", "CustomHeader-Value");
        // 创建 HTTP 请求的 URL
        String url = "http://" + providerName + "/echo/" + str;
        // 创建 HTTP 请求的方法和实体对象
        HttpMethod httpMethod = HttpMethod.GET; // 这里可以改为 POST、PUT、DELETE 等合适的方法
        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, httpMethod, URI.create(url));
        // 发起 HTTP 请求并获取响应
        ResponseEntity<String> response = loadBalanced.exchange(requestEntity, String.class);
        result.put("resultFromRestTemplate", response.getBody());
        // result.put("resultFromRestTemplate", loadBalanced.getForObject("http://" + providerName + "/echo/" + str, String.class));
        result.put("resultFromRestTemplateNoLoadBalanced", restTemplate.getForObject("http://httpbin.org/get", String.class));
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
