# Use OpenJDK 17 image
FROM openjdk:17

# Copy the built jar file
COPY build/libs/*.jar app.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]
