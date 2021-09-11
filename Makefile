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

image-jmesh-consumer:
	mvn k8s:build -f ./jmesh-consumer-demo/pom.xml

image-jmesh-provider:
	mvn k8s:build -f ./jmesh-provider-demo/pom.xml

docker-login:
	docker login tpaas-registry-itg.jdcloud.com -u 'jrwangwei3' -p 'jrwangwei3'

push-consumer:
	docker push tpaas-registry-itg.jdcloud.com/jmesh/sc-consumer:1.0.0-SNAPSHOT

push-provider:
	docker push tpaas-registry-itg.jdcloud.com/jmesh/sc-provider:1.0.0-SNAPSHOT

push-jmesh-consumer:
	docker push tpaas-registry-itg.jdcloud.com/jmesh/sc-jmesh-consumer:1.0.0-SNAPSHOT

push-jmesh-provider:
	docker push tpaas-registry-itg.jdcloud.com/jmesh/sc-jmesh-provider:1.0.0-SNAPSHOT

undeploy-consumer:
	kubectl delete -f ./kubernetes/sc-consumer.yml

undeploy-provider:
	kubectl delete -f ./kubernetes/sc-consumer.yml

deploy-consumer:
	kubectl apply -f ./kubernetes/sc-consumer.yml

deploy-provider:
	kubectl apply -f ./kubernetes/sc-provider.yml

undeploy-jmesh-consumer:
	kubectl delete -f ./kubernetes/jmesh-consumer.yml

undeploy-jmesh-provider:
	kubectl delete -f ./kubernetes/jmesh-consumer.yml

deploy-jmesh-consumer:
	kubectl apply -f ./kubernetes/jmesh-consumer.yml

deploy-jmesh-provider:
	kubectl apply -f ./kubernetes/jmesh-provider.yml