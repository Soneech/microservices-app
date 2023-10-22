FROM openjdk:17-oracle

WORKDIR /app
COPY soneech-shop-0.0.1-SNAPSHOT.jar /app/soneech-shop.jar
EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/app/soneech-shop.jar"]