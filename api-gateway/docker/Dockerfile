FROM openjdk:17-oracle

WORKDIR /app
COPY api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar
EXPOSE 8765

ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]