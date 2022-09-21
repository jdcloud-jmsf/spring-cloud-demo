# spring-cloud-demo
Spring Cloud Demo for JMSF.

## 模块介绍
> 项目中集成了生成docker镜像的插件，可使用
- `jmsf-provider-demo` 采用JMSF服务治理SDK的provider应用示例。
- `jmsf-consumer-demo` 采用JMSF服务治理SDK的consumer应用示例。
- `provider-demo` 原生Spring Cloud Provider应用示例。
- `consumer-demo` 原生Spring Cloud Consumer应用示例。
- `kubernetes`文件夹下，为快速部署上述服务至K8s的描述文件。

## 使用介绍

### 构建及推送镜像
根目录的`Makefile`文件可用与一键式编译项目，构建，推送镜像甚至部署服务等操作，详细请了解其中的相关命令。如`image-xx`相关命令为构建镜像，`push-xx`相关命令为推送镜像。

### 部署至K8s
`deploy-xx`相关命令为通过yml文件部署至k8s集群，默认部署至本地`kubectl`命令默认所管理的目标集群。如想指定集群可以修改命令添加指定集群的`kubeconfig`文件配置。`undeploy-xx`相关命令为删除部署。

## 注意事项
如果使用jmsf微服务治理平台，请重点参照`jmsf-consumer-demo`和`jmsf-provider-demo`两个示例。
引入包：
```xml
<dependency>
    <groupId>com.jdcloud.jmsf</groupId>
    <artifactId>spring-cloud-starter-jmsf</artifactId>
    <version>2.0.1</version>
</dependency>
```

## JMSF介绍

### Spring Cloud版本配套关系说明
JMSF目前支持 Spring Cloud 最新版本。Spring Cloud 、Spring Boot 及 JMSF SDK 版本之间的关系如下表所示。

| Spring Cloud | Spring Boot |
| ------------ | ----------- |
| 2020.0.x     | 2.5.x       |
| 2021.0.x     | 2.7.x       |

| SDK 版本号 | 新增特性                                                     |
| ---------- | ------------------------------------------------------------ |
| 1.0.0      | 支持基于Consul的服务注册与发现，服务限流，熔断，服务路由，服务鉴权等功能。 |
| 1.1.0      | 融合MeshWare服务治理SDK。                                    |
| 1.2.0      | 加入Nacos注册中心支持模块。                                  |
| 1.3.x      | 适配Spring Cloud 2020.0.x与Spring Boot 2.5.x版本。           |
| 2.0.x      | 适配Spring Cloud 2021.0.x与Spring Boot 2.7.x版本。           |