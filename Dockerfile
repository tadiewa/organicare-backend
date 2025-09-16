# ----------- Build Stage -----------
FROM openjdk:21-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# ----------- Run Stage -----------
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/organicare-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9033
ENTRYPOINT ["java", "-jar", "app.jar"]
