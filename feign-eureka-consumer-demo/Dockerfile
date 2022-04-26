FROM openjdk:11-jre
ENV TZ Asia/Shanghai
MAINTAINER Zhiguo.Chen<chenzhiguo@jd.com>
USER root

# 临时模拟，正式环境需去掉
#ENV JMESH_SERVICE_APP=jmsf-provider-demo

WORKDIR /export/servers/
COPY ./target/*.jar /export/servers/
EXPOSE 7211
ENTRYPOINT ["/bin/bash", "-c", "java -jar /export/servers/feign-eureka-consumer-demo-1.0.0-SNAPSHOT.jar"]
