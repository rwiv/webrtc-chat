docker build -t clover-chat-server .

docker tag clover-chat-server ghcr.io/jongho445/clover-chat-server
docker push ghcr.io/jongho445/clover-chat-server

docker rmi ghcr.io/jongho445/clover-chat-server
docker rmi clover-chat-server

pause