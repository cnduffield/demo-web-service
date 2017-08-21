FROM openjdk:8-jdk

ADD target/keystone*.jar app.jar
RUN sh -c 'touch /app.jar'  
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/micros", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
