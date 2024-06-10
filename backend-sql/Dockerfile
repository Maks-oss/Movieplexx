# Use the official OpenJDK image as base
FROM gradle:8.7.0-jdk17 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build -i --stacktrace -x bootJar

FROM gradle:8.7.0-jdk17 AS build

# Set the working directory in the container
#COPY --chown=gradle:gradle . /home/gradle/src
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

# Create a new stage for the runtime image
FROM openjdk:17-jdk-slim
# Set the working directory in the container

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the default command to run the application
CMD ["java", "-jar", "app.jar"]

