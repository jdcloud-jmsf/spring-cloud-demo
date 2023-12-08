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

image-sc-consumer-amd:
	#mvn k8s:build -f ./consumer-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/jmsf/sc-consumer:$(version)-AMD64 ./consumer-demo

image-sc-consumer-arm:
	docker build --platform linux/arm64 -t hub.jdcloud.com/jmsf/sc-consumer:${version}-ARM64 -f ./consumer-demo/Dockerfile-ARM.dockerfile ./consumer-demo

image-sc-provider-amd:
	#mvn k8s:build -f ./provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/jmsf/sc-provider:$(version)-AMD64 ./provider-demo

image-sc-provider-arm:
	docker build --platform linux/arm64 -t hub.jdcloud.com/jmsf/sc-provider:${version}-ARM64 -f ./provider-demo/Dockerfile-ARM.dockerfile ./provider-demo

image-jmsf-consumer:
	#mvn k8s:build -f ./jmsf-consumer-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/jmsf/sc-jmsf-consumer:$(version) ./jmsf-consumer-demo

image-jmsf-provider:
	#mvn k8s:build -f ./jmsf-provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/jmsf/sc-jmsf-provider:$(version) ./jmsf-provider-demo

image-nacos-provider:
	#mvn k8s:build -f ./jmsf-provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/jmsf/sc-nacos-provider:$(version) ./nacos-provider-demo

image-nacos-consumer:
	#mvn k8s:build -f ./jmsf-provider-demo/pom.xml
	docker build --platform linux/amd64 -t hub.jdcloud.com/jmsf/sc-nacos-consumer:$(version) ./nacos-consumer-demo

docker-login:
	docker login registry.xxx.com -u 'zhangsan' -p 'zhangsan'

push-sc-consumer-amd:
	docker push hub.jdcloud.com/jmsf/sc-consumer:$(version)-AMD64

push-sc-consumer-arm:
	docker push hub.jdcloud.com/jmsf/sc-consumer:$(version)-ARM64

push-sc-provider-amd:
	docker push hub.jdcloud.com/jmsf/sc-provider:$(version)-AMD64

push-sc-provider-arm:
	docker push hub.jdcloud.com/jmsf/sc-provider:$(version)-ARM64

create-multi-arch-sc-consumer:
	docker manifest rm hub.jdcloud.com/jmsf/sc-consumer:$(version)
	docker manifest create hub.jdcloud.com/jmsf/sc-consumer:$(version) hub.jdcloud.com/jmsf/sc-consumer:$(version)-AMD64 hub.jdcloud.com/jmsf/sc-consumer:$(version)-ARM64
	docker manifest push hub.jdcloud.com/jmsf/sc-consumer:$(version)

build-multi-arch-sc-consumer: image-sc-consumer-amd image-sc-consumer-arm push-sc-consumer-amd push-sc-consumer-arm create-multi-arch-sc-consumer

create-multi-arch-sc-provider:
	docker manifest rm hub.jdcloud.com/jmsf/sc-provider:$(version)
	docker manifest create hub.jdcloud.com/jmsf/sc-provider:$(version) hub.jdcloud.com/jmsf/sc-provider:$(version)-AMD64 hub.jdcloud.com/jmsf/sc-provider:$(version)-ARM64
	docker manifest push hub.jdcloud.com/jmsf/sc-provider:$(version)

build-multi-arch-sc-provider: image-sc-provider-amd image-sc-provider-arm push-sc-provider-amd push-sc-provider-arm create-multi-arch-sc-provider

push-jmsf-consumer:
	docker push hub.jdcloud.com/jmsf/sc-jmsf-consumer:$(version)

push-jmsf-provider:
	docker push hub.jdcloud.com/jmsf/sc-jmsf-provider:$(version)

push-nacos-provider:
	docker push hub.jdcloud.com/jmsf/sc-nacos-provider:$(version)

push-nacos-consumer:
	docker push hub.jdcloud.com/jmsf/sc-nacos-consumer:$(version)

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