package io.meshware.springcloud.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenzhiguo
 */
@SpringBootApplication(scanBasePackages = "io.meshware.springcloud.demo.provider.controller")
public class ProviderApplication {

    public static void main(String[] args) {
        // System.setProperty("JMESH_SERVICE_APP", "sc-provider-chen");
        // System.setProperty("JMESH_REGISTRY_HOST", "116.196.114.131");
        SpringApplication.run(ProviderApplication.class, args);
    }

}
