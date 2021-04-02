# The springboot-demo application

Simple spring-boot application for running in a kubernetes cluster. This application is based on the 
spring.io guide at https://spring.io/guides/gs/spring-boot-kubernetes/ . However this application would not 
run on raspberry pi microk8s kubernetes cluster. 

The raspberry pi needs docker image built for the arm64 platform. This POC describes mechanisms that 
can be used to create cross platform docker containers that can run in raspberry pi kubernetes 
cluster as well as in other environments.

The default spring-boot:build-image can't be utilized for cross platform builds. The docker buildx 
multi-architecture build mechanism is utilized. The docker files are built based on the information 
provided in the spring guide at: https://spring.io/guides/gs/spring-boot-docker/ 

To integrate into the github CI pipeline, following marketplace tool is utilized.
https://github.com/marketplace/actions/build-and-push-docker-images

To link github and docker hub, secret was created in github (settings) and also a 
token was generated on the docker hub. This token was provisioned on the github secrets to help build 
and push images. Github action was created for trigerring the build and push of the docker image to
hub.docker.com.


# initial build steps



