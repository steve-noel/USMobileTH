# Stage 1: Build the application
FROM maven:3.9.7-eclipse-temurin-22-alpine AS build

# Set the working directory
WORKDIR .

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR .

# Copy the JAR file from the build stage
COPY --from=build /target/UsMobileTH-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
