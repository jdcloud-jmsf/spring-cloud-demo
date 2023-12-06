FROM arm64v8/openjdk:11-jre
#FROM arm64v8/openjdk:11 # for ARM64
MAINTAINER Zhiguo.Chen<chenzhiguo@jd.com>
USER root

ENV TZ Asia/Shanghai
# 临时模拟，正式环境需去掉
#ENV JMESH_SERVICE_APP=jmsf-provider-demo

WORKDIR /export/servers/
COPY ./target/deploy-package/ /export/servers/
RUN /bin/bash -c 'for f in /export/servers/*; do if [ ${f##*.} == 'zip' ];then unzip -q -d /export/servers/ $f; fi; done'
RUN chmod +x /export/servers/bin/*.sh

VOLUME ["/export/servers/logs", "/export/servers/config"]
EXPOSE 8080
#ENTRYPOINT ["/export/servers/bin/startup.sh"]
ENTRYPOINT ["java","-javaagent:/export/servers/agent/skywalking-agent.jar","-jar","-Dloader.path=config","/export/servers/sc-consumer.jar","--spring.config.location=/export/servers/config/"]
