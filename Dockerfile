FROM openjdk:17-slim

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install maven -y
RUN mvn clean install -DskipTests
RUN mv target/*.jar target/app.jar

EXPOSE 8080

CMD ["java", "-jar", "target/app.jar"]