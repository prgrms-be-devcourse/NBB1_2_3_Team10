# Use an official Gradle image to build the backend
FROM gradle:7.5.1-jdk11 AS build

# Set working directory
WORKDIR /app

# Copy and build the application
COPY . .
RUN gradle build -x test

# Use a lightweight JRE image for runtime
FROM openjdk:11-jre-slim
COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]

