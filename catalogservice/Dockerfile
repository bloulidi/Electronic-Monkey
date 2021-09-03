FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/catalogservice-1.0.0.jar /usr/src/catalogservice-1.0.0.jar
ENTRYPOINT ["java","-jar","catalogservice-1.0.0.jar"]
