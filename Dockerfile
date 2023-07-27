FROM maven:3.8.3-jdk-8 AS build

# Copy the source code to the container
WORKDIR /Trimmy
COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:8-jre-slim-bullseye

# Set the working directory inside the container
WORKDIR /Trimmy

# Copy the built JAR file from the previous stage to the container
COPY --from=build /Trimmy/target/Trimmy-1.0.0-SNAPSHOT.jar Trimmy.jar

# Install required packages for running MongoDB
RUN apt-get update && apt-get install -y gnupg wget
RUN wget -qO - https://www.mongodb.org/static/pgp/server-5.0.asc | apt-key add -
RUN echo "deb http://repo.mongodb.org/apt/debian bullseye/mongodb-org/5.0 main" | tee /etc/apt/sources.list.d/mongodb-org-5.0.list
RUN apt-get update && apt-get install -y mongodb-org
RUN apt-get install -y redis-server

# Expose the ports (Spring Boot application, MongoDB and Reddison)
EXPOSE 8081
EXPOSE 27017
EXPOSE 6379

# Copy the configuration files into the container
COPY application.properties .
COPY application-dev.yml .
COPY counter.txt .

# Command to run the Spring Boot application and MongoDB when the container starts
CMD ["java", "-jar", "Trimmy.jar", "--spring.config.location=application.properties,application-dev.yml"]
