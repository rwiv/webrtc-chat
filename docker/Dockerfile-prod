# gui build
FROM node:21.7-alpine as nodejs
WORKDIR /app
COPY . .
WORKDIR /app/gui
RUN npm i -g pnpm
RUN pnpm i
RUN pnpm build:prod

# spring build
FROM gradle:8.7.0-jdk21-alpine as grapdle
WORKDIR /app
COPY . .
COPY --from=nodejs /app/gui/dist /app/src/main/resources/dist
COPY ./docker/secret/dev-keystore.p12 /app/src/main/resources/ssl/dev-keystore.p12
RUN gradle build -x check --parallel

# main
FROM openjdk:23-slim
WORKDIR /app
COPY --from=grapdle /app/build/libs/clover-chat-server-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
