FROM openjdk:17-jdk-slim
ARG AppVersion=0.0.1-SNAPSHOT
WORKDIR /app
COPY ./target/items-service-${AppVersion}.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Xmx50m -jar app.jar" ]