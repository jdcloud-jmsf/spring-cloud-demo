# 关于限流策略
## 检查项
- 引入相关jar包，如工程采用consul则需要确认工程中引入了：
  ```xml
  <dependency>
    <groupId>com.jdcloud.jmsf</groupId>
    <artifactId>spring-cloud-jmsf-sgc-consul</artifactId>
  </dependency>
  ```
- 确认配置文件中（*.yml 或 *.properties）中`jmsf.meta`下，`app`,`service`,`namespace`,`mesh-group`等填写正确。
- 关于控制台配置
  - `全局限流`是实例级别的全局限流，也就是统计访问某个POD的所有请求并进行限流。
  - `标签限流`即统计符合条件的请求，进行限流，如进行URL限流配置如下：
    ![image-20230717101307031](https://cdn.jsdelivr.net/gh/chenzhiguo/tuchuang@master/uPic-2023-07/image-20230717101307031-1689559987892.png)

## 压测
> 关于性能对比，不代表绝对性能，压测POD4C8G单实例
- 不开启限流
  ![image-20230717101718997](https://cdn.jsdelivr.net/gh/chenzhiguo/tuchuang@master/uPic-2023-07/image-20230717101718997-1689560239125.png)
- 开启压测，限流1000/s
  ![image-20230717101725723](https://cdn.jsdelivr.net/gh/chenzhiguo/tuchuang@master/uPic-2023-07/image-20230717101725723-1689560245816.png)