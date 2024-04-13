# gui build
FROM node:21.7-alpine as nodeBuilder
WORKDIR /app
RUN git clone https://github.com/rwiv/clover-chat-server.git ./
WORKDIR /app/gui

# spring build
FROM gradle:8.7.0-jdk21-alpine as grapdleBuilder
WORKDIR /app
RUN git clone https://github.com/rwiv/clover-chat-server.git ./
RUN gradle build -x check --parallel

# main
FROM openjdk:23-slim
WORKDIR /app
COPY --from=grapdleBuilder /app/build/libs/clover-chat-server-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
