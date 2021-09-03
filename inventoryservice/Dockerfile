FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/inventoryservice-1.0.0.jar /usr/src/inventoryservice-1.0.0.jar
ENTRYPOINT ["java","-jar","inventoryservice-1.0.0.jar"]
