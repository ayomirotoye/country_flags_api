# Use a suitable base image (e.g., OpenJDK 17 or later)
FROM openjdk:17-jdk-slim

# Copy the Gradle wrapper files (if you have them)
COPY gradlew gradlew
COPY gradle ./gradle

# Set the working directory inside the container
WORKDIR /app

# Copy the build files and build the application (using Gradle's build cache for speed)
COPY build.gradle settings.gradle ./
RUN ./gradlew assemble --no-daemon

# Create a thin JAR (recommended)
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy only the built JAR file from the previous stage
COPY --from=0 /app/build/libs/*.jar /app/app.jar

# Expose the port your application is listening on (usually 8080)
EXPOSE 8080

# Command to run when the container starts
CMD ["java", "-jar", "/app/app.jar"]