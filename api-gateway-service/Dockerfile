FROM openjdk:11-jre-slim
WORKDIR usr/src
COPY ./target/api-gateway-service-1.0.0.jar api-gateway-service-1.0.0.jar
ENTRYPOINT ["java","-jar","api-gateway-service-1.0.0.jar"]
EXPOSE 8080