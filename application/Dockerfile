FROM openjdk:8-jdk-alpine
MAINTAINER Mishnev Nikita <fatheroctober@gmail.com>
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/application-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} url-shortener.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/url-shortener.jar"]