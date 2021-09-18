package com.jdcloud.jmsf.demo.springcloud.provider.controller;

import com.jdcloud.jmsf.circuitbreaker.entity.CircuitBreakerRule;
import com.jdcloud.jmsf.core.context.JMeshContext;
import com.jdcloud.jmsf.core.entity.CommonResponse;
import com.jdcloud.jmsf.core.entity.Metadata;
import com.jdcloud.jmsf.core.entity.TagPair;
import com.jdcloud.jmsf.demo.springcloud.provider.properties.ConfigDemoProperties;
import com.jdcloud.jmsf.demo.springcloud.provider.vo.TestRequestVo;
import com.jdcloud.jmsf.demo.springcloud.provider.vo.TestResponseVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Slf4j
@RestController
@RefreshScope
@RequestMapping("/")
@Api("provider")
public class ProviderController {

    @Autowired(required = false)
    private Metadata metadata;

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, Long> counts = new ConcurrentHashMap<>();

    @GetMapping("/env/{str}")
    public String getEnv(@PathVariable String str) {
        return System.getProperty(str);
    }

    @GetMapping("/")
    public String home() {
        return "Hello world, from: " + metadata.getServiceName();
    }

    /**
     * 简单的鉴权接口。
     * token = provider-demo 鉴权成功，其它失败。
     *
     * @param token 凭证
     * @return 鉴权结果
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.GET)
    public Map<String, Object> checkToken(@RequestParam String token) {
        log.info("provider-demo -- request param: [" + token + "]");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "provider-demo".equalsIgnoreCase(token));
        resultMap.put("payload", "this is payload");

        log.info("provider-demo -- response info: [" + resultMap + "]");
        return resultMap;
    }

    /**
     * 打印请求
     *
     * @param request 原始请求
     * @return 请求内容
     */
    @RequestMapping(value = "/printRequest", method = RequestMethod.GET)
    public Map<String, Object> printRequest(HttpServletRequest request) {
        Map<String, Object> requestMap = new LinkedHashMap<>();
        Map<String, String> headerMap = new LinkedHashMap<>();
        Map<String, String> parameterMap = new LinkedHashMap<>();
        Map<String, String> cookieMap = new LinkedHashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            parameterMap.put(paramName, request.getParameter(paramName));
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Stream.of(cookies).forEach(e -> {
                cookieMap.put(e.getName(), e.getValue());
            });
        }
        requestMap.put("Protocol", request.getProtocol());
        requestMap.put("Method", request.getMethod());
        requestMap.put("URL", request.getRequestURL());
        requestMap.put("Parameters", parameterMap);
        requestMap.put("Headers", headerMap);
        requestMap.put("Cookies", cookieMap);
        requestMap.put("OriginalCookies", cookies);
        return requestMap;
    }

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        List<TagPair> tagPairs = JMeshContext.getTags(JMeshContext.Type.UPSTREAM);
        log.info("[Provider-demo]--response info: {}, tags={}", str, tagPairs);
        // return restTemplate.getForObject("http://sc-jmesh-consumer/echo2/" + str, String.class) + ", from: serviceName=" + metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId();
        return str + ", from: serviceName=" + metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId();
    }

    @GetMapping(value = "/echo/{str}/{sleepSeconds}")
    public String echoBySleep(@PathVariable("str") String str, @PathVariable("sleepSeconds") Integer seconds) {
        log.info("[Provider-demo]--response info: {}, seconds={}", str, seconds);
        // return restTemplate.getForObject("http://sc-jmesh-consumer/echo2/" + str, String.class) + ", from: serviceName=" + metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId();
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            log.error("Error!", e);
        }
        return str + ", from: serviceName=" + metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId();
    }

    @GetMapping(value = "/login1")
    public TestResponseVo login1(@ModelAttribute TestRequestVo requestVo) {
        TestResponseVo testResponseVo = new TestResponseVo();
        testResponseVo.setCode(1);
        testResponseVo.setMessage("Test Message");
        testResponseVo.setData(requestVo.getUserName() + requestVo.getPassword() + ", from: serviceName=" +
                metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId());
        return testResponseVo;
    }

    @GetMapping(value = "/login2")
    public TestResponseVo login2(@RequestBody TestRequestVo requestVo) {
        TestResponseVo testResponseVo = new TestResponseVo();
        testResponseVo.setCode(1);
        testResponseVo.setMessage("Test Message");
        testResponseVo.setData(requestVo.getUserName() + requestVo.getPassword() + ", from: serviceName=" +
                metadata.getServiceName() + ", instanceId=" + metadata.getInstanceId());
        return testResponseVo;
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

    @GetMapping(value = "/exception")
    public String exception() {
        log.info("Exception method is be invoked!");
        int a = 100 / 0;
        return "success" + a;
    }

    @GetMapping("/status/{code}")
    public synchronized CommonResponse status(@PathVariable int code, HttpServletResponse response) {
        counts.put(String.format("/status/%d", code),
                counts.getOrDefault(String.format("/status/%d", code), 0L) + 1);
        log.info("status-{}", code);
        response.setStatus(code);
        CommonResponse commonResponse = CommonResponse.createResponse();
        commonResponse.put("count", counts.get(String.format("/status/%d", code)));
        return commonResponse;
    }

    @GetMapping("/sleep/{millis}")
    public String sleep(@PathVariable long millis) {
        log.info(String.format("sleep %d millis", millis));
        try {
            Thread.sleep(millis);
        } catch (Exception exp) {
            log.error(exp.toString());
        }
        return "";
    }

    @PostMapping("/post")
    public synchronized String post(@RequestBody CircuitBreakerRule breakerRule) {
        counts.put("/post", counts.getOrDefault("/post", 0L) + 1);
        log.info("port");
        log.info(breakerRule.toString());
        return breakerRule.toString();
    }

    @PutMapping("/put")
    public synchronized void put(@RequestBody CircuitBreakerRule breakerRule) {
        counts.put("/put", counts.getOrDefault("/put", 0L) + 1);
        log.info("put");
        log.info(breakerRule.toString());
    }

}
