FROM gradle:alpine
COPY . /app
WORKDIR /app
RUN GRADLE_USER_HOME=/app/.gradle gradle bootRepackage

FROM openjdk:8-jre-slim
EXPOSE 7001
#install Spring Boot artifact
COPY --from=0 /app/build/libs/springstarter-0.0.1-SNAPSHOT.jar /spring-starter.jar
ENTRYPOINT ["java","-Xms500m","-Xmx500m","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-starter.jar"]
