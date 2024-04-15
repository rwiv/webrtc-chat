## Run in DEV

### Build

```shell
./gradlew build -x check --parallel
```

### Run

```shell
java -jar ./build/libs/clover-chat-server-0.0.1-SNAPSHOT.jar
```


## Deploy Process

1. vultr registry 생성
2. secret 폴더를 채운 뒤, local에서 `docker-build-mysql.bat`, `docker-build-nginx.bat`, `docker-build-server.bat`로 docker images build
3. `docker-push.bat <registry_name>`로 images push
4. vultr instance 생성 후, 내부에서 `docker-compose-prod-temp.yml` 실행

