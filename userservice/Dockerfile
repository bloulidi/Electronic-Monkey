FROM openjdk:11-jre-slim
WORKDIR usr/src
COPY ./target/userservice-1.0.0.jar userservice-1.0.0.jar
ENTRYPOINT ["java","-jar","userservice-1.0.0.jar"]
EXPOSE 8090