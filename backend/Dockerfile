# Use the official OpenJDK image as base
FROM gradle:8.7.0-jdk17 AS build

# Set the working directory in the container
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar

# Create a new stage for the runtime image
FROM openjdk:17-jdk-slim
# Set the working directory in the container

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the default command to run the application
CMD ["java", "-jar", "app.jar"]

