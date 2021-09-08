FROM openjdk:11-jre-slim
WORKDIR usr/src
COPY ./target/catalogservice-1.0.0.jar catalogservice-1.0.0.jar
ENTRYPOINT ["java","-jar","catalogservice-1.0.0.jar"]
EXPOSE 8800