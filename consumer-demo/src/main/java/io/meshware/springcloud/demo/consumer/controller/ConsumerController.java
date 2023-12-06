package io.meshware.springcloud.demo.consumer.controller;

import io.meshware.cache.api.RedisCache;
import io.meshware.springcloud.demo.consumer.entity.MoneyPO;
import io.meshware.springcloud.demo.consumer.repository.MoneyBaseQueryRepository;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    // private final FooService fooService;

    private final RedisTemplate<String, String> redisTemplate;

    private final MoneyBaseQueryRepository moneyBaseQueryRepository;

    @Autowired(required=false)
    private RedisCache redisCache;

    @Autowired
    public ConsumerController(RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate, MoneyBaseQueryRepository moneyBaseQueryRepository) {
        this.restTemplate = restTemplate;
        // this.fooService = fooService;
        this.redisTemplate = redisTemplate;
        this.moneyBaseQueryRepository = moneyBaseQueryRepository;
    }

    @GetMapping("/echo/{str}")
    public Map<String, Object> echo(@PathVariable String str) {
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromRestTemplate", restTemplate.getForObject("http://sc-provider/echo/" + str, String.class));
        // result.put("resultFromFeignClient", fooService.echo(str));
        return result;
    }

    @GetMapping("/addToDb/{id}")
    public Map<String, Object> addToDb(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        Optional<MoneyPO> moneyPOOptional = moneyBaseQueryRepository.findById(Integer.valueOf(id));
        if (moneyPOOptional.isPresent()) {
            result.put("old", moneyPOOptional.get());
        } else {
            MoneyPO moneyPO = new MoneyPO();
            moneyPO.setId(Integer.valueOf(id));
            moneyPO.setMoney(100L);
            moneyPO.setName("name-" + id);
            moneyPO.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
            moneyPO.setIsDeleted((byte) 0);
            MoneyPO m = moneyBaseQueryRepository.save(moneyPO);
            result.put("new", m);
        }
        return result;
    }

    @GetMapping("/getFromDb/{id}")
    public Map<String, Object> getFromDb(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        Optional<MoneyPO> moneyPOOptional = moneyBaseQueryRepository.findById(Integer.valueOf(id));
        if (moneyPOOptional.isPresent()) {
            result.put("resultFromDb", moneyPOOptional.get());
            // String valueFromCache = redisTemplate.opsForValue().get(id);
            // if (StringUtils.isEmpty(valueFromCache)) {
            //     valueFromCache = moneyPOOptional.get().getName();
            //     redisTemplate.opsForValue().set(id, moneyPOOptional.get().getName(), Duration.ofSeconds(20));
            // }
            // result.put("resultFromCache", valueFromCache);
            redisCache.setex(id, 20, moneyPOOptional.get().getName());
        }
        return result;
    }

    @GetMapping("/getFromCache/{id}")
    public Map<String, Object> getFromCache(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("resultFromCache", redisCache.get(id));
        return result;
    }

    /**
     * health check.
     *
     * @return health check info
     */
    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "ok";
    }
}
