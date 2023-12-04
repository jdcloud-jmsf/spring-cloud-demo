package io.meshware.springcloud.demo.consumer;

import io.meshware.springcloud.demo.consumer.controller.ConsumerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

/**
 * @author chenzhiguo
 */
@Slf4j
@EnableScheduling
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "io.meshware.springcloud.demo.consumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        // System.setProperty("JMESH_SERVICE_APP", "sc-consumer-chen");
        // System.setProperty("JMESH_REGISTRY_HOST", "116.196.114.131");
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Autowired
    ConsumerController consumerController;

    // @Scheduled(fixedRate = 5000)
    // @EventListener(ApplicationStartedEvent.class)
    // public void onStarted() {
    //     try {
    //         log.info("定时请求结果：{}", consumerController.echo("Random" + new Random().nextInt()));
    //     } catch (Exception e) {
    //         log.error("Error! message={}", e.getMessage(), e);
    //     }
    // }

}
