FROM maven:3.9-eclipse-temurin-21 AS builder

COPY src /usr/app/src
COPY pom.xml /usr/app

RUN mvn -f /usr/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ARG SERVICE_NAME

COPY --from=builder /usr/app/target/${SERVICE_NAME}-*.jar /usr/app/app.jar

ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]