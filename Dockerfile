FROM openjdk:17-jdk-slim-buster

EXPOSE 8081

COPY target/CloudStorage-0.0.1-SNAPSHOT.jar backend.jar

CMD ["java", "-jar", "/backend.jar" ]

