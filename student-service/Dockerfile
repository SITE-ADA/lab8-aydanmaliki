FROM eclipse-temurin:21-jdk

WORKDIR /workspace
COPY . .
RUN chmod +x gradlew && ./gradlew :student-service:bootJar --no-daemon

EXPOSE 9090
ENTRYPOINT ["sh", "-c", "java -jar $(ls student-service/build/libs/*.jar | head -n 1)"]
