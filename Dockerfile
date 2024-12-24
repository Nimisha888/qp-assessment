# Dockerfile

# Base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file
COPY target/groceryStore-1.0.0.jar app.jar

# Create the database directory
RUN mkdir -p database

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
