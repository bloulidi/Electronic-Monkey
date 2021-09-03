FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/api-gateway-service-1.0.0.jar /usr/src/api-gateway-service-1.0.0.jar
ENTRYPOINT ["java","-jar","api-gateway-service-1.0.0.jar"]
