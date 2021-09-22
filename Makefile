version=1.0.0-SNAPSHOT

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
	mvn k8s:build -f ./consumer-demo/pom.xml

image-sc-provider:
	mvn k8s:build -f ./provider-demo/pom.xml

image-jmsf-consumer:
	mvn k8s:build -f ./jmsf-consumer-demo/pom.xml

image-jmsf-provider:
	mvn k8s:build -f ./jmsf-provider-demo/pom.xml

docker-login:
	#docker login jdcloud-cn-north-1.jcr.service.jdcloud.com   -u jdcloud -p xd8JEsbMe8iNAjR1
	docker login tpaas-registry-itg.jdcloud.com -u 'jrwangwei3' -p 'jrwangwei3'

push-sc-consumer:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-consumer:1.0.0-SNAPSHOT

push-sc-provider:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-provider:1.0.0-SNAPSHOT

push-jmsf-consumer:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-jmsf-consumer:1.0.0-SNAPSHOT

push-jmsf-provider:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-jmsf-provider:1.0.0-SNAPSHOT

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