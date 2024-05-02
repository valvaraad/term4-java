FROM openjdk:21-jdk

WORKDIR /app

COPY target/labjava-1.0.jar app.jar

CMD ["java", "-jar", "app.jar"]

LABEL authors="valvaraad"