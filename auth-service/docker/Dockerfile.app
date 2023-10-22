FROM openjdk:17-oracle

WORKDIR /app
COPY auth-service-0.0.1-SNAPSHOT.jar /app/auth-service.jar
EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/app/auth-service.jar"]