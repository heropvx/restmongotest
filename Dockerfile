FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/rest-mongo-test-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENV mongouri mongodb://mongo:27017/testDB
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=${mongouri}","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]