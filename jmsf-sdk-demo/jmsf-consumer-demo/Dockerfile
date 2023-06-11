FROM openjdk:11-jre
ENV TZ Asia/Shanghai
MAINTAINER Zhiguo.Chen<admin@xxx.com>
USER root

# 临时模拟，正式环境需去掉
#ENV JMESH_SERVICE_APP=jmsf-consumer-demo
#ENV SGM_OPTS="-javaagent:agent1.jar -javaagent:agent2.jar"

WORKDIR /export/servers/
COPY ./target/deploy-package/ /export/servers/
#RUN /bin/bash -c 'for f in /export/servers/*; do if [ ${f##*.} == 'zip' ];then unzip -q -d /export/servers/ $f; fi; done'
#RUN chmod +x /export/servers/bin/*.sh

#RUN curl -s "http://sgm-server.jdd-paas.svc.cluster.loacl:8042/local/probe/java/script?app=jmsf-consumer-demo" -o /tmp/sgm_java.sh && source /tmp/sgm_java.sh
#RUN export SGM_OPTS="-javaagent:agent1.jar -javaagent:agent2.jar"
VOLUME ["/export/servers/logs", "/export/servers/config"]
EXPOSE 8081
#ENTRYPOINT ["/export/servers/bin/startup.sh"]
#jvm默认dns缓存永不过时，建议开发者在程序启动时设置缓存的过期时间：JAVA_OPTS=“-Dsun.net.inetaddr.ttl=3 -Dsun.net.inetaddr.negative.ttl=1”
#ENTRYPOINT ["java","-jar","-Dloader.path=config", "/export/servers/jmsf-consumer.jar", "--spring.config.location=/export/servers/config/"]
ENTRYPOINT ["/bin/bash", "-c", "java ${SGM_OPTS} -jar -Dloader.path=config /export/servers/jmsf-consumer.jar --spring.config.location=/export/servers/config/"]
