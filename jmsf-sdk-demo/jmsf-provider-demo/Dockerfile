FROM openjdk:11-jre
ENV TZ Asia/Shanghai
MAINTAINER Zhiguo.Chen<admin@xxx.com>
USER root

# 临时模拟，正式环境需去掉
#ENV JMESH_SERVICE_APP=jmsf-provider-demo

WORKDIR /export/servers/
COPY ./target/deploy-package/ /export/servers/
#RUN /bin/bash -c 'for f in /export/servers/*; do if [ ${f##*.} == 'zip' ];then unzip -q -d /export/servers/ $f; fi; done'
#RUN chmod +x /export/servers/bin/*.sh

VOLUME ["/export/servers/logs", "/export/servers/config"]
EXPOSE 8080
#ENTRYPOINT ["/export/servers/bin/startup.sh"]
#ENTRYPOINT ["java","-jar","-Dloader.path=config", "/export/servers/jmsf-provider.jar","--spring.config.location=/export/servers/config/"]
ENTRYPOINT ["/bin/bash", "-c", "java ${SGM_OPTS} -jar -Dloader.path=config /export/servers/jmsf-provider.jar --spring.config.location=/export/servers/config/"]
