FROM maven:3.8.8-eclipse-temurin-11 AS build
WORKDIR /app
# Instalación de Node 16.20.2 necesaria para Vaadin Flow v14
RUN curl -sL https://deb.nodesource.com/setup_16.x | bash - && \
    apt-get install -y nodejs=16.20.2-1nodesource1

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN rm -rf node_modules target package-lock.json webpack.generated.js tsconfig.json
RUN mvn clean package -Pproduction -DskipTests vaadin:build-frontend

FROM eclipse-temurin:11-jre-focal
WORKDIR /app
COPY --from=build /app/target/vaadin-cass-1.80.0-SNAPSHOT.jar app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app/app.jar"]