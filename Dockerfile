FROM openjdk:17-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package
RUN mv target/*.jar target/app.jar

EXPOSE 8080

CMD ["java", "-jar", "target/app.jar"]