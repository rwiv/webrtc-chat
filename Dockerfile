FROM openjdk:17-ea-11-jdk-slim

WORKDIR /usr/src/app

COPY . .

#COPY ./build/libs/clover-chat-server-0.0.1-SNAPSHOT.jar .
#RUN ./gradlew build -x check --parallel

ENTRYPOINT [ "java", "-jar", "./build/libs/clover-chat-server-0.0.1-SNAPSHOT.jar" ]