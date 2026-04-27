FROM eclipse-temurin:24-jre

WORKDIR /app

COPY target/pipeline-demo-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-cp", "app.jar", "com.domchar.ProfilingDemo"]