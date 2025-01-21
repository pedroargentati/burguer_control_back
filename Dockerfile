FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=build /app/target/burguer-0.0.1-SNAPSHOT.jar /app/burguer.jar

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/burguer_control
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root

ENTRYPOINT ["java", "-jar", "/app/burguer.jar"]
