## GraphQL Testing

- 브라우저에서 http://localhost:8080/graphiql 로 접속해 graphql 테스트 가능
    - 서버를 `dev` 모드로 실행했다면 test용 mock data가 db에 존재할 것
    - `Headers` 탭에 `{ "Authorization": "admin" }`를 추가해야 query 가능
- graphql schema: [click](src/main/resources/schema)


## Misc

- graphql DateTime scalar는 `RFC-3339`
