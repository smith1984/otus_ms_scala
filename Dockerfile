#Build stage
FROM hseeberger/scala-sbt:eclipse-temurin-11.0.14.1_1.6.2_2.13.8 AS build

WORKDIR /ServerApplication
ADD . /ServerApplication

RUN sbt assembly

#Final stage
FROM openjdk:17-alpine

WORKDIR /app
COPY --from=build /ServerApplication/target/scala-2.13/otus_ms_scala_srv_order-assembly-0.0.1.jar /app/otus_ms_scala_srv_order.jar

ENTRYPOINT ["java", "-jar", "otus_ms_scala_srv_order.jar"]