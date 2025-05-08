FROM maven:3.9.9-amazoncorretto-17 AS build
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn clean package assembly:single -DskipTests

#run stage

FROM openjdk:17-alpine
ARG JAR_FILE=/build/target/*.jar
COPY --from=build /build/target/ServerLab6-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/docker-server/app.jar
ENTRYPOINT ["java", "-jar", "/opt/docker-server/app.jar"]