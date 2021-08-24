FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/userservice-1.0.0.jar /usr/src/userservice-1.0.0.jar
ENTRYPOINT ["java","-jar","userservice-1.0.0.jar"]
