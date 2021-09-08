FROM openjdk:11-jre-slim
WORKDIR usr/src
COPY ./target/orderservice-1.0.0.jar orderservice-1.0.0.jar
ENTRYPOINT ["java","-jar","orderservice-1.0.0.jar"]
EXPOSE 8880