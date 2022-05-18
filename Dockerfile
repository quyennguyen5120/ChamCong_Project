FROM openjdk:11-jdk
WORKDIR jar
COPY target/TodoAPI-0.0.1-SNAPSHOT.jar /app-jar/TodoAPI.jar
EXPOSE 8080
CMD ["java", "-jar", "/app-jar/TodoAPI.jar"]