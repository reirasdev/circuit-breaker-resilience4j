FROM openjdk:11-jdk-slim
ADD target/localidade-microservice-0.0.1-SNAPSHOT.jar localidade-microservice-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/localidade-microservice-0.0.1-SNAPSHOT.jar"]