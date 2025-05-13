# Use a minimal Java 17 runtime base image
FROM eclipse-temurin:17-jdk-alpine

# Set application JAR file name (replace with your actual file name if different)
ARG JAR_FILE=target/StoreManagement-0.0.1-SNAPSHOT.jar

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose the default Spring Boot port (adjust if you configured differently)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
