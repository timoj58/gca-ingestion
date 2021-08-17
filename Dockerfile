FROM openjdk:11-jdk
RUN groupadd  -r gca && useradd -r gca-user -g gca

USER gca-user:gca
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
