FROM openjdk:17-jdk-slim
ARG AppVersion=0.0.1-SNAPSHOT
WORKDIR /app
COPY ./target/items_k8s_service-${AppVersion}.jar app.jar
# maven build by profile
ENTRYPOINT [ "sh", "-c", "java -Dspring.profiles.active=default,dev -Xmx50m -jar app.jar" ]