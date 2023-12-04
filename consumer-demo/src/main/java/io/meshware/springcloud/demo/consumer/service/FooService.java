// package io.meshware.springcloud.demo.consumer.service;
//
// import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
//
// /**
//  * FooService
//  *
//  * @author Zhiguo.Chen
//  * @version 20210702
//  */
// @FeignClient("sc-provider")
// @LoadBalancerClient("a")
// public interface FooService {
//
//     @GetMapping(value = "/echo/{str}")
//     String echo(@PathVariable("str") String str);
//
// }
