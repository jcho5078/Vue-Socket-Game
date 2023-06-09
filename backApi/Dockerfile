FROM adoptopenjdk/openjdk11
CMD ["./mvnw", "clean", "package"]
# path for docker-compose
ARG JAR_FILE_PATH=backApi/target/*.jar
ADD ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]