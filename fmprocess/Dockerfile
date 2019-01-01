FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG fmprocess-0.0.1-SNAPSHOT.jar
COPY target/fmprocess-0.0.1-SNAPSHOT.jar fmprocess.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fmprocess.jar"]