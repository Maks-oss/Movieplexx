# the base image
FROM amazoncorretto:17

# the JAR file path
ARG JAR_FILE=build/libs/*.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} backend.jar

CMD apt-get update -y
#EXPOSE 8080
# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/backend.jar"]
