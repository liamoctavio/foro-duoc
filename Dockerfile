FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY backend.jar app.jar
COPY wallet /wallet
ENV TNS_ADMIN=/wallet
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
