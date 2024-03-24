## account

```graphql
query {
    accounts {
        id
        username
    }
}
```

```graphql
mutation CreateAccount($creation: AccountCreation!) {
    createAccount(creation: $creation) {
        id
        role
        username
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

## chatroom

```graphql
query {
    chatRooms {
        id
        title
        createDate
        createAccount {
            id
            username
            nickname
        }
    }
}

```

```graphql
mutation CreateChatRoom($creation: ChatRoomCreation!) {
    createChatRoom(creation: $creation) {
        id
        title
        createDate
        createAccount {
            id
            username
            nickname
        }
    }
}
```

```json
{
  "creation": {
    "createUserId": 1,
    "password": null,
    "title": "test chatroom",
    "type": "PUBLIC"
  }
}
```

```graphql
mutation DeleteChatRoom($chatRoomId: Long!) {
    deleteChatRoom(chatRoomId: $chatRoomId) {
        id
        title
        createDate
        createAccount {
            id
            username
            nickname
        }
    }
}
```

```json
{
  "chatRoomId": 6
}
```
