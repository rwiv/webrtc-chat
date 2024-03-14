docker rm clover-chat-server
docker rmi ghcr.io/jongho445/clover-chat-server

docker-compose -f docker-compose-prod.yml up