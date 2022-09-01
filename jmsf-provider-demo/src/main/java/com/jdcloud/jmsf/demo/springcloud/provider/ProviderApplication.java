package com.jdcloud.jmsf.demo.springcloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provider Demo Application
 *
 * @author Zhiguo.Chen
 */
@SpringBootApplication(scanBasePackages = {"com.jdcloud.jmsf.demo.springcloud.provider"})
public class ProviderApplication {

    public static void main(String[] args) {
        // 注意：下面的这些环境变量再生产环境理论上是不需要的，JMSF会自动注入这些环境变量，也就是打生产包时需要注释掉。
        // 因为开发项目时，本期启动时不会被自动注入所以需要通过下方代码模拟设置环境变量
        // System.setProperty("JMESH_SERVICE_NAME", "CustomProviderService");
        // System.setProperty("JMESH_REGISTRY_TOKEN", "6fca9ebf-ab1d-8023-9348-e6e12649968f");

        // 需同JMSF控制台显示的命名空间一致
        System.setProperty("JMESH_SERVICE_NAMESPACE", "default");
        // 需同JMSF控制台创建的服务名一致
        System.setProperty("JMESH_SERVICE_APP", "jmsf-provider-demo");
        System.setProperty("JMESH_SERVICE_ID", "jmsf-provider-demo");
        System.setProperty("JMESH_SERVICE_VERSION", "v1.0");
        System.setProperty("JMESH_SERVICE_GROUP", "group1");
        // 需同JMSF控制台显示的K8s集群一致
        System.setProperty("JMESH_SERVICE_CLUSTER", "cluster-dev");
        // 注册中心地址 注意：如果使用K8s Service或者F5等负载均衡，需要运维人员配置负载均衡策略为IP保持，否则可能会在心跳检测时输出异常信息
        System.setProperty("JMESH_REGISTRY_HOST", "127.0.0.1");
        // 注册中心端口
        System.setProperty("JMESH_REGISTRY_PORT", "8500");
        // 需同JMSF控制台显示的网格名称一致
        System.setProperty("JMESH_MESH_GROUP", "mesh01");
        System.setProperty("JMESH_SERVICE_DEPLOYMENT", "dep1");
        System.setProperty("JMESH_ZONE", "zone1");
        SpringApplication.run(ProviderApplication.class, args);
    }

}
