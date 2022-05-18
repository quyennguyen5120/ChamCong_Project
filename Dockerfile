FROM openjdk:11-jdk
WORKDIR jar
COPY target/spring-rest-api-0.0.1-SNAPSHOT.jar /app-jar/spring-rest-api.jar
EXPOSE 8080
CMD ["java", "-jar", "/app-jar/spring-rest-api.jar"]