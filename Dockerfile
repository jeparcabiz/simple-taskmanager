FROM maven:3.9.7-eclipse-temurin-17 as builder
COPY . /tmp
WORKDIR /tmp
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim as app
VOLUME /tmp
COPY --from=builder /tmp/target/simple-taskmanager-1.0.0.jar simple-taskmanager.jar
ENTRYPOINT ["java","-jar","/simple-taskmanager.jar"]
