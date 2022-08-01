FROM openjdk:17-ea-jdk-alpine3.13

WORKDIR /app

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

COPY ../.mvn .mvn
COPY ../mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY ../src ./src

CMD ["./mvnw", "spring-boot:run"]



