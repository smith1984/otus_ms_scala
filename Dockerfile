#Build stage
FROM hseeberger/scala-sbt:eclipse-temurin-11.0.14.1_1.6.2_2.13.8 AS build

WORKDIR /ServerApplication
ADD . /ServerApplication

RUN sbt assembly

#Final stage
FROM openjdk:17-alpine

WORKDIR /app
COPY --from=build /ServerApplication/target/scala-2.13/otus_ms_scala-assembly-0.0.4.jar /app/otus_ms_scala.jar
COPY k8s/app.conf /app
COPY k8s/secrets.conf /app

ENTRYPOINT ["java", "-jar", "otus_ms_scala.jar"]