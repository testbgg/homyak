FROM openjdk:8-jre-alpine

MAINTAINER bgdevs

ADD backend/build/libs/backend-0.0.1.jar /app/
WORKDIR /app

CMD ["java", "-jar", "/app/backend-0.0.1.jar"]

EXPOSE 8080