FROM openjdk:11-jre-slim
WORKDIR usr/src
COPY ./target/eureka-service-1.0.0.jar eureka-service-1.0.0.jar
ENTRYPOINT ["java","-jar","eureka-service-1.0.0.jar"]
EXPOSE 8761