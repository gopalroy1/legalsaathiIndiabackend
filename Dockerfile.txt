# 1. Build the application
FROM maven:3.8.8-jdk-17 AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Run the application
FROM openjdk:17-jdk-slim
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
