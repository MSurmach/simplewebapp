FROM openjdk:8-jdk-alpine
ARG JAR=/target/simplewebapp.jar
COPY ${JAR} simplewebapp.jar
ENTRYPOINT ["java","-jar","/simplewebapp.jar"]