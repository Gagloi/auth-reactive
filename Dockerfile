FROM openjdk:17-alpine

WORKDIR /usr/src/app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean build

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "./build/libs/auth-reactive-0.0.1-SNAPSHOT.jar"]