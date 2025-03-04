FROM openjdk:11-jdk-slim

MAINTAINER anonymous

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 12000

ENTRYPOINT ["java", "-jar", "app.jar"]