FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/BlackjackAPI-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]