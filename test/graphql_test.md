## account

```graphql
mutation CreateAccount($creation: AccountCreation!) {
    createAccount(creation: $creation) {
        id
        role
        username
        password
        nickname
    }
}
```

```json
{
  "creation": {
    "role": "ADMIN",
    "username": "test1",
    "password": "1234",
    "nickname": "hello"
  }
}
```
