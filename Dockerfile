# Build stage
FROM maven:4.0.0-openjdk-17-slim AS build
# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the project files to the container
COPY pom.xml ./
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-alpine
# Set the working directory
WORKDIR /app

# Copy JSP files to the appropriate directory
COPY src/main/webapp/views /usr/local/tomcat/webapps/views


# Copy the built JAR file from the previous stage to the container
COPY target/*.jar app.jar

# Set the command to run the application
CMD ["java", "-jar", "app.jar"]

# Expose the port
EXPOSE 9595
