FROM openjdk:8
MAINTAINER Nikita Shaldenkov <shaldnikita2@yandex.ru>

ADD ./build/libs/backend-0.0.1.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/backend-0.0.1.jar"]

EXPOSE 8080