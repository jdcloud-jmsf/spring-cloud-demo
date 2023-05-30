package com.jdcloud.jmsf.demo.springcloud.consumer;

import com.jdcloud.jmsf.demo.springcloud.consumer.controller.ConsumerController;
import com.jdcloud.jmsf.meshware.spring.context.ApplicationContextUtil;
import io.meshware.common.timer.ScheduleTask;
import io.meshware.common.timer.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * RequestTask
 *
 * @author Zhiguo.Chen
 * @since 20230516
 */
@Slf4j
@Component
public class RequestTask implements ApplicationListener<ApplicationStartedEvent> {

    private static final Timer timer = new Timer("config-watch-timer", 1, 512, 4);

    private ConsumerController consumerController;

//     @Scheduled(fixedRate = 500)
//     public void onStarted() {
//         try {
//             log.info("RT定时请求结果：{}", consumerController.echoByRestTemplate("Random" + new Random().nextInt()));
//             log.info("Feign定时请求结果：{}", consumerController.echoByFeign("Random" + new Random().nextInt()));
// //             log.info("定时请求结果：{}", consumerController.echoByWebClient("Random" + new Random().nextInt()));
//         } catch (Exception e) {
//             log.error("Error! message={}", e.getMessage(), e);
//         }
//     }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (consumerController == null) {
            consumerController = ApplicationContextUtil.getBean(ConsumerController.class);
        }
        timer.add(new RequestTaskRun(timer, OptionalInt.of(Integer.parseInt(System.getProperty("REQUEST_INTERVAL", "500"))).getAsInt()));
    }

    class RequestTaskRun extends ScheduleTask {

        private final int intervalInMs;

        RequestTaskRun(Timer currentTimer, int intervalInMs) {
            super(currentTimer);
            this.intervalInMs = intervalInMs;
        }

        @Override
        public CompletableFuture<Void> execute() {
            try {
                log.info("RT定时请求结果：{}", consumerController.echoByRestTemplate("Random" + new Random().nextInt()));
                log.info("Feign定时请求结果：{}", consumerController.echoByFeign("Random" + new Random().nextInt()));
//             log.info("定时请求结果：{}", consumerController.echoByWebClient("Random" + new Random().nextInt()));
            } catch (Exception e) {
                log.error("Error! message={}", e.getMessage(), e);
            }
            return null;
        }

        @Override
        public int getIntervalInMs() {
            return intervalInMs;
        }

        @Override
        public boolean isAsync() {
            return false;
        }

        @Override
        public String getName() {
            return "request-task";
        }
    }
}
