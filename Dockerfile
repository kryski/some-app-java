FROM adoptopenjdk/openjdk11:alpine
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT java -Dspring.profiles.active=docker -jar app.jar