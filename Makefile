version=1.0.0-SNAPSHOT

#all: clean build image-consumer image-provider docker-login push-consumer push-provider undeploy-consumer undeploy-provider deploy-provider deploy-consumer

all-push: clean build image-consumer image-provider docker-login push-consumer push-provider

all-consumer: clean build image-consumer docker-login push-consumer undeploy-consumer deploy-consumer

all-provider: clean build image-provider docker-login push-provider undeploy-provider deploy-provider

clean:
	mvn clean -f ./pom.xml

build:
	mvn package -f ./pom.xml -DskipTests=true package -P artifactory,test

image-consumer:
	mvn k8s:build -f ./consumer-demo/pom.xml

image-provider:
	mvn k8s:build -f ./provider-demo/pom.xml

image-jmsf-consumer:
	mvn k8s:build -f ./jmsf-consumer-demo/pom.xml

image-jmsf-provider:
	mvn k8s:build -f ./jmsf-provider-demo/pom.xml

docker-login:
	#docker login jdcloud-cn-north-1.jcr.service.jdcloud.com   -u jdcloud -p xd8JEsbMe8iNAjR1
	docker login tpaas-registry-itg.jdcloud.com -u 'jrwangwei3' -p 'jrwangwei3'

push-consumer:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-consumer:1.0.0-SNAPSHOT

push-provider:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-provider:1.0.0-SNAPSHOT

push-jmsf-consumer:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-jmsf-consumer:1.0.0-SNAPSHOT

push-jmsf-provider:
	docker push tpaas-registry-itg.jdcloud.com/jmsf/sc-jmsf-provider:1.0.0-SNAPSHOT

undeploy-consumer:
	kubectl delete -f ./kubernetes/sc-consumer.yml

undeploy-provider:
	kubectl delete -f ./kubernetes/sc-consumer.yml

deploy-consumer:
	kubectl apply -f ./kubernetes/sc-consumer.yml

deploy-provider:
	kubectl apply -f ./kubernetes/sc-provider.yml

undeploy-jmsf-consumer:
	kubectl delete -f ./kubernetes/jmsf-consumer.yml

undeploy-jmsf-provider:
	kubectl delete -f ./kubernetes/jmsf-consumer.yml

deploy-jmsf-consumer:
	kubectl apply -f ./kubernetes/jmsf-consumer.yml

deploy-jmsf-provider:
	kubectl apply -f ./kubernetes/jmsf-provider.yml