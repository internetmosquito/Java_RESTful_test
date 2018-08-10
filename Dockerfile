FROM openjdk:8-jdk-alpine
EXPOSE 8080
EXPOSE 9010
RUN mkdir -p /app/
ADD build/libs/java-restful-test-0.1.0.jar /app/java-restful-test-0.1.0.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/java-restful-test-0.1.0.jar"]
ENTRYPOINT ["java", \
    "-Dcom.sun.management.jmxremote", \
    "-Dcom.sun.management.jmxremote.port=9010", \
    "-Dcom.sun.management.jmxremote.local.only=false", \
    "-Dcom.sun.management.jmxremote.authenticate=false", \
    "-Dcom.sun.management.jmxremote.ssl=false",\
    "-Dspring.data.mongodb.uri=mongodb://mongo_db:27017/users",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-jar","/app/java-restful-test-0.1.0.jar"]