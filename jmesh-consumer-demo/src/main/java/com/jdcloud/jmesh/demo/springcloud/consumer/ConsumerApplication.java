package com.jdcloud.jmesh.demo.springcloud.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Provider Demo Application
 *
 * @author Zhiguo.Chen
 */
@Slf4j
@EnableScheduling
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.jdcloud.jmesh.demo.springcloud.consumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        //模拟设置环境变量
        // System.setProperty("JMESH_SERVICE_NAME", "CustomConsumerService");
        // System.setProperty("JMESH_REGISTRY_TOKEN", "6fca9ebf-ab1d-8023-9348-e6e12649968f");

        // System.setProperty("JMESH_SERVICE_NAMESPACE", "default");
        // System.setProperty("JMESH_SERVICE_APP", "jmesh-consumer-demo");
        // System.setProperty("JMESH_SERVICE_VERSION", "v1.0");
        // System.setProperty("JMESH_SERVICE_GROUP", "group1");
        // System.setProperty("JMESH_SERVICE_CLUSTER", "cluster-dev");
        // System.setProperty("JMESH_REGISTRY_HOST", "127.0.0.1");
        // System.setProperty("JMESH_REGISTRY_PORT", "8500");
        // System.setProperty("JMESH_MESH_GROUP", "mesh01");
        // System.setProperty("JMESH_SERVICE_DEPLOYMENT", "dep1");
        // System.setProperty("JMESH_ZONE", "zone1");
        // System.setProperty("PROVIDER_NAME", "sc-jmesh-provider");
        SpringApplication.run(ConsumerApplication.class, args);
    }

    // @Autowired
    // private ConsumerController consumerController;
    //
    // // @Scheduled(fixedRate = 5000)
    // public void onStarted() {
    //     try {
    //         log.info("定时请求结果：{}", consumerController.echoByRestTemplate("Random" + new Random().nextInt()));
    //         log.info("定时请求结果：{}", consumerController.echoByFeign("Random" + new Random().nextInt()));
    //         log.info("定时请求结果：{}", consumerController.echoByWebClient("Random" + new Random().nextInt()));
    //     } catch (Exception e) {
    //         log.error("Error! message={}", e.getMessage(), e);
    //     }
    // }
}
