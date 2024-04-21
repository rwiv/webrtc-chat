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

1. secret 폴더 채우기
2. VCR 생성
    - VCR의 location은 `New York (NJ)`으로 설정해야 함
3. local 환경에서 아래 scripts를 통해 docker images build
   - `docker-build-mysql.bat`
   - `docker-build-nginx.bat`
   - `docker-build-server.bat`
4. `docker login ...`으로 생성한 VCR에 login
5. `docker-push-all.bat <registry_name>`으로 images push
6. `terraform-init.bat` → `terraform-apply.bat`을 통해 terraform으로 vultr instance 생성 후 server deploy
7. domain name에 맵핑된 ip를 vultr instance public ip로 변경
