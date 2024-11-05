# Use OpenJDK 17 image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar file
COPY build/libs/*.jar app.jar

# Set the entry point to run the application
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]
