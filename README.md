# spring-cloud-demo
Spring Cloud Demo for JMSF.

## 模块介绍
> 项目中集成了生成docker镜像的插件，可使用
- `jmsf-provider-demo` 采用JMSF服务治理SDK的provider应用示例。
- `jmsf-consumer-demo` 采用JMSF服务治理SDK的consumer应用示例。
- `provider-demo` 原生Spring Cloud Provider应用示例。
- `consumer-demo` 原生Spring Cloud Consumer应用示例。
- `nacos-provider-demo` 原生Spring Cloud Provider应用示例（采用Nacos注册中心）。
- `nacos-consumer-demo` 原生Spring Cloud Consumer应用示例（采用Nacos注册中心）。
- `feign-eureka-provider-demo` 原生Spring Cloud Provider应用示例（采用Eureka注册中心）。
- `feign-eureka-consumer-demo` 原生Spring Cloud Consumer应用示例（采用Eureka注册中心）。
- `kubernetes`文件夹下，为快速部署上述服务至K8s的描述文件。

## 使用介绍

### 构建及推送镜像
根目录的`Makefile`文件可用与一键式编译项目，构建，推送镜像甚至部署服务等操作，详细请了解其中的相关命令。如`image-xx`相关命令为构建镜像，`push-xx`相关命令为推送镜像。
根目录`pom.xml`中定义了几组`profiles`，用于项目打包时，动态引入使用哪种注册中心？哪种服务治理中心？哪种运行模式？以下介绍选择哪个`profile`所起到的作用。注意：**同类profile只能选择一个！**

| 注册中心`profile`选择项 | 作用                                                   |
|------------------| ------------------------------------------------------ |
| registry-consul  | 项目引入consul相关jar包，服务注册至consul              |
| registry-nacos   | 项目引入nacos相关jar包，服务注册至nacos                |
| registry-eureka  | 项目引入eureka相关jar包，服务注册至eureka，不支持dubbo |

| 服务治理中心`profile`选择项 | 作用                                 |
| --------------------------- | ------------------------------------ |
| sgc-consul                  | 对接consul的kv存储并获取服务治理策略 |
| sgc-k8s                     | 对接k8s集群服务治理策略资源          |
| sgc-strancer                | 对接自研服务治理策略订阅分发服务     |

| Web类型`profile`选择项 | 作用                                                         |
|-------------------| ------------------------------------------------------------ |
| web-type-servlet  | 常见运行模式，基于servlet的http server，如tomcat。Http请求使用RestTemplate，Feign |
| web-type-reactive | Spring Gateway的netty server，对WebFlux技术更友好，Http请求使用WebClient |

### 部署至K8s

`deploy-xx`相关命令为通过yml文件部署至k8s集群，默认部署至本地`kubectl`命令默认所管理的目标集群。如想指定集群可以修改命令添加指定集群的`kubeconfig`文件配置。`undeploy-xx`相关命令为删除部署。

## 注意事项
如果使用jmsf微服务治理平台，请重点参照`jmsf-consumer-demo`和`jmsf-provider-demo`两个示例。
推荐的项目引入方式及包如下：
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>com.jdcloud.jmsf</groupId>
            <artifactId>spring-cloud-jmsf-dependencies</artifactId>
            <version>2.1.8</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
<dependencies>
    <dependency>
        <groupId>com.jdcloud.jmsf</groupId>
        <artifactId>spring-cloud-starter-jmsf</artifactId>
    </dependency>
    
    <!-- 注意：还需要引入上面注册中心，服务治理中心对应的依赖，例如项目采用consul，则需要添加registry-consul，sgc-consul对应的profile中所引用的依赖包 -->
    <dependency>
        <groupId>com.jdcloud.jmsf</groupId>
        <artifactId>spring-cloud-jmsf-registry-consul</artifactId>
    </dependency>
    <dependency>
        <groupId>com.jdcloud.jmsf</groupId>
        <artifactId>spring-cloud-jmsf-config-consul</artifactId>
    </dependency>
    <dependency>
        <groupId>com.jdcloud.jmsf</groupId>
        <artifactId>spring-cloud-jmsf-sgc-consul</artifactId>
    </dependency>
</dependencies>

```

## JMSF介绍

### Spring Cloud版本配套关系说明
JMSF目前支持 Spring Cloud 最新版本。Spring Cloud 、Spring Boot 及 JMSF SDK 版本之间的关系如下表所示。

| Spring Cloud | Spring Boot |
| ------------ | ----------- |
| 2020.0.x     | 2.5.x       |
| 2021.0.x     | 2.7.x       |

| SDK 版本号 | 新增特性                                                     |
|---------| ------------------------------------------------------------ |
| 1.0.0   | 支持基于Consul的服务注册与发现，服务限流，熔断，服务路由，服务鉴权等功能。 |
| 1.1.0   | 融合MeshWare服务治理SDK。                                    |
| 1.2.0   | 加入Nacos注册中心支持模块。                                  |
| 1.4.x   | 适配Spring Cloud 2020.0.x与Spring Boot 2.5.x版本。           |
| 2.1.x   | 适配Spring Cloud 2021.0.x与Spring Boot 2.7.x版本。           |