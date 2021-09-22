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
        //模拟设置环境变量
        // System.setProperty("JMESH_SERVICE_NAME", "CustomProviderService");
        // System.setProperty("JMESH_REGISTRY_TOKEN", "6fca9ebf-ab1d-8023-9348-e6e12649968f");

        // System.setProperty("JMESH_SERVICE_NAMESPACE", "default");
        // System.setProperty("JMESH_SERVICE_APP", "jmsf-provider-demo");
        // System.setProperty("JMESH_SERVICE_VERSION", "v1.0");
        // System.setProperty("JMESH_SERVICE_GROUP", "group1");
        // System.setProperty("JMESH_SERVICE_CLUSTER", "cluster-dev");
        // System.setProperty("JMESH_REGISTRY_HOST", "127.0.0.1");
        // System.setProperty("JMESH_REGISTRY_PORT", "32279");
        // // System.setProperty("JMESH_REGISTRY_HOST", "consul.test.tpaas");
        // // System.setProperty("JMESH_REGISTRY_PORT", "80");
        // System.setProperty("JMESH_MESH_GROUP", "mesh01");
        // System.setProperty("JMESH_SERVICE_DEPLOYMENT", "dep1");
        // System.setProperty("JMESH_ZONE", "zone1");
        SpringApplication.run(ProviderApplication.class, args);
    }

}
