FROM openjdk:8-jdk-alpine
LABEL maintainer="gowdar.manjunath@outlook.com"
VOLUME /tmp
EXPOSE 8443
ARG JAR_FILE=target/ecomp-application-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} ecomp_application.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ecomp_application.jar"]