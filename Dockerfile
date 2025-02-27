FROM openjdk:17-jdk

WORKDIR /opt/service

COPY /target/*.jar app.jar


