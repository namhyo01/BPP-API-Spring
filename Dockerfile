# FROM --platform=linux/amd64 gradle:7.6-jdk17 AS build
# LABEL authors="kimnamhyo"

# ENV APP_HOME=/app
# WORKDIR $APP_HOME
# COPY build.gradle $APP_HOME
# COPY settings.gradle $APP_HOME
# COPY gradlew $APP_HOME
# COPY gradle $APP_HOME/gradle
# RUN ["chmod","+x","gradlew"]
# RUN ./gradlew build ||  return 0
# COPY src $APP_HOME/src
# RUN ["./gradlew", "clean", "bootjar","-Pprofile=dev"]

FROM --platform=linux/amd64 openjdk:17-alpine

ENV APP_HOME=/app
WORKDIR $APP_HOME

ARG JASYPT_SECRET=${JASYPT_SECRET}
ENV JASYPT_SECRET=${JASYPT_SECRET}
ARG CONFIG_IMPORT=${CONFIG_IMPORT}
ENV CONFIG_IMPORT=${CONFIG_IMPORT}
ARG PROFILE_ACTIVE=${PROFILE_ACTIVE}
ENV PROFILE_ACTIVE=${PROFILE_ACTIVE}
# JAR파일
# ARG JAR_FILE=$APP_HOME/build/libs/*.jar
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE_ACTIVE}","-Djasypt.encryptor.password=${JASYPT_SECRET}","-Dspring.config.import=${CONFIG_IMPORT}", "-jar","app.jar"]