## Getting Started

### Build

```shell
./gradlew build -x check --parallel
```

### Run

```shell
java -jar ./build/libs/clover-chat-server-0.0.1-SNAPSHOT.jar
```

- 브라우저에서 http://localhost:8080/graphiql 로 접속해 graphql 테스트 가능
    - 서버를 `dev` 모드로 실행했다면 test용 mock data가 db에 존재할 것

