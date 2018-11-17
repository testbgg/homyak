FROM openjdk:8-jre-alpine

MAINTAINER bgdevs

ADD app.jar /app/
WORKDIR /app

CMD ["java", "-jar", "/app/app.jar"]

EXPOSE 8080