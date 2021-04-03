# The springboot-demo application

Simple spring-boot application for running in a Raspberry Pi kubernetes cluster. 

This application is based on the spring.io guide: 
[Spring Boot Kubernetes](https://spring.io/guides/gs/spring-boot-kubernetes/).
However, this application would not run on Raspberry pi microk8s kubernetes cluster as is. 
Raspberry pi requires images built for armv7/arm64 architecture (otherwise the image will 
cause "user format exec" error). 

Since the Raspberry pi 4 needs docker image built specifically for the arm64 platform. This 
proof-of-concept application describes mechanisms that can be used to create cross platform 
docker containers that can run in Raspberry pi kubernetes cluster as well as in other environments.

The default image built using ./mvnw spring-boot:build-image can't be utilized for cross platform 
builds, therefore the docker buildx multi-architecture build mechanism is utilized. 
The docker files are built based on the information provided in the spring guide: 
[Spring Boot Docker](https://spring.io/guides/gs/spring-boot-docker/) 

To integrate into the github CI pipeline, following marketplace tool is utilized:
- [Build and Push Docker Image](https://github.com/marketplace/actions/build-and-push-docker-images)

To link github and docker hub, a token was generated on the docker hub. This token was then provisioned 
as a github secret to help build and push images to docker hub. 

Github action based on these secrets was created for triggering the build and push of the docker 
image to the hub.docker.com [container registry](https://hub.docker.com/r/iconsent/springboot-demo).

The image is published as iconsent/springboot-demo:latest docker image in docker hub.

# The application.properties file in kubernetes

There are multiple ways to set the properties in the springboot application running in the 
kubernetes cluster:

1. Default application.properties file in the main/resource folder
2. Environment variables
3. ConfigMap as environment variables
4. ConfigMap as file

See the deployment files 1-4 in the kubernetes folder
## Environment variables

    spec:
       template:
           spec:
               containers:
               - env:
                    - name: CUSTOM_PROPERTY
                      value: "from environment in yaml"

## ConfigMap as environment variables

    spec:
       template:
           spec:
               containers:
               - env:
               - name: CUSTOM_PROPERTY
                   valueFrom:
                       configMapKeyRef:
                       name: springboot-demo-configmap
                       key: custom.property

## ConfigMap as a file

    spec:
        template:
            spec:
                containers:
                    volumeMounts:
                    - name: application-config
                      mountPath: "/deployments/config"
                      readOnly: true
                volumes:
                - name: application-config
                  configMap:
                    name: spring-app-config
                    items:
                    - key: application.properties
                      path: application.properties

# Local testing

The application reads few properties from the application.properties file and
returns them. Once the application is running use curl to obtain the values:

    docker build . -t iconsent/springboot-demo
    docker run -p 8080:8080 iconsent/springboot-demo
    curl http://localhost:8080/properties


