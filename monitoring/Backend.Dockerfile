FROM eclipse-temurin:21-jdk

# Zona horaria Lima
ENV TZ=America/Lima
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

# BackendDockerfile: LÍNEA CORREGIDA
COPY target/pixelplayback-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
