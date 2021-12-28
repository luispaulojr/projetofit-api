#################
# Build stage
#################
FROM maven:3.8.3-openjdk-16 AS build
WORKDIR /src
COPY . .
RUN mvn -e clean package -DskipTests

#################
# Package stage
#################
FROM openjdk:16-jdk-slim AS base 
WORKDIR /app
EXPOSE 80

COPY --from=build /src/target/*.jar ./app.jar
RUN bash -c "touch ./app.jar"

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]