FROM springboot:2.5.2-jdk11

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

COPY src/main/resources/application.properties /app/application.properties

COPY src/main/resources/db/changelog/db.changelog-master.yaml /app/db/changelog/db.changelog-master.yaml

WORKDIR /app

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=12345
ENV SPRING_LIQUIBASE_CHANGE_LOG=classpath:/db/changelog/db.changelog-master.yaml
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

EXPOSE 8080

CMD ["java","-jar","/app/app.jar"]