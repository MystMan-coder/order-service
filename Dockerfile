# Use Java 17 base image
FROM openjdk:17-jdk-slim

# App jar name
ARG JAR_FILE=target/order-service-0.0.1-SNAPSHOT.jar

# Copy jar into container
COPY ${JAR_FILE} app.jar

# Run application
ENTRYPOINT ["java","-jar","/app.jar"]