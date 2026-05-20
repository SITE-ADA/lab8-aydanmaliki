FROM eclipse-temurin:21-jdk

WORKDIR /workspace
COPY . .
RUN chmod +x gradlew && ./gradlew :course-service:bootJar --no-daemon

EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -jar $(ls course-service/build/libs/*.jar | head -n 1)"]
