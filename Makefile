version=1.0.0

#all: clean build image-sc-consumer image-sc-provider docker-login push-sc-consumer push-sc-provider undeploy-consumer undeploy-provider deploy-provider deploy-consumer

all-sc-push: clean build image-sc-consumer image-sc-provider docker-login push-sc-consumer push-sc-provider

all-jmsf-push: clean build image-jmsf-consumer image-jmsf-provider docker-login push-jmsf-consumer push-jmsf-provider

all-consumer: clean build image-sc-consumer docker-login push-sc-consumer undeploy-consumer deploy-consumer

all-provider: clean build image-sc-provider docker-login push-sc-provider undeploy-provider deploy-provider

clean:
	mvn clean -f ./pom.xml

build:
	mvn package -f ./pom.xml -DskipTests=true package -P artifactory,test

image-sc-consumer:
	#mvn k8s:build -f ./consumer-demo/pom.xml
	docker build --platform linux/amd64 -t hurdes/sc-consumer:$(version) ./consumer-demo

image-sc-provider:
	#mvn k8s:build -f ./provider-demo/pom.xml
	docker build --platform linux/amd64 -t hurdes/sc-provider:$(version) ./provider-demo

image-jmsf-consumer:
	#mvn k8s:build -f ./jmsf-consumer-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/mesh/sc-jmsf-consumer:$(version) ./jmsf-consumer-demo

image-jmsf-provider:
	#mvn k8s:build -f ./jmsf-provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/mesh/sc-jmsf-provider:$(version) ./jmsf-provider-demo

image-nacos-provider:
	#mvn k8s:build -f ./jmsf-provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/mesh/sc-nacos-provider:$(version) ./nacos-provider-demo

image-nacos-consumer:
	#mvn k8s:build -f ./jmsf-provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/mesh/sc-nacos-consumer:$(version) ./nacos-consumer-demo

docker-login:
	docker login registry.xxx.com -u 'zhangsan' -p 'zhangsan'

push-sc-consumer:
	docker push hurdes/sc-consumer:$(version)

push-sc-provider:
	docker push hurdes/sc-provider:$(version)

push-jmsf-consumer:
	docker push hub.jdcloud.com/mesh/sc-jmsf-consumer:$(version)

push-jmsf-provider:
	docker push hub.jdcloud.com/mesh/sc-jmsf-provider:$(version)

push-nacos-provider:
	docker push hub.jdcloud.com/mesh/sc-nacos-provider:$(version)

push-nacos-consumer:
	docker push hub.jdcloud.com/mesh/sc-nacos-consumer:$(version)

undeploy-sc-consumer:
	kubectl delete -f ./kubernetes/sc-consumer.yml

undeploy-sc-provider:
	kubectl delete -f ./kubernetes/sc-consumer.yml

deploy-sc-consumer:
	kubectl apply -f ./kubernetes/sc-consumer.yml

deploy-sc-provider:
	kubectl apply -f ./kubernetes/sc-provider.yml

undeploy-jmsf-consumer:
	kubectl delete -f ./kubernetes/jmsf-consumer.yml

undeploy-jmsf-provider:
	kubectl delete -f ./kubernetes/jmsf-consumer.yml

deploy-jmsf-consumer:
	kubectl apply -f ./kubernetes/jmsf-consumer.yml

deploy-jmsf-provider:
	kubectl apply -f ./kubernetes/jmsf-provider.yml