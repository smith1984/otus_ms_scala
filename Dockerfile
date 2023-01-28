#Build stage
FROM sbtscala/scala-sbt:graalvm-ce-22.3.0-b2-java17_1.8.2_2.13.10 AS build

WORKDIR /ServerApplication
ADD . /ServerApplication

RUN sbt assembly

#Final stage
FROM openjdk:17-alpine

WORKDIR /app
COPY --from=build /ServerApplication/target/scala-2.13/otus_ms_scala-assembly-0.0.3.jar /app/otus_ms_scala.jar

ENTRYPOINT ["java", "-jar", "otus_ms_scala.jar"]