docker tag chat-db:latest ewr.vultrcr.com/%1/chat-db:latest
docker push ewr.vultrcr.com/%1/chat-db:latest
docker rmi ewr.vultrcr.com/%1/chat-db:latest

docker tag chat-nginx:latest ewr.vultrcr.com/%1/chat-nginx:latest
docker push ewr.vultrcr.com/%1/chat-nginx:latest
docker rmi ewr.vultrcr.com/%1/chat-nginx:latest

docker tag chat-server:latest ewr.vultrcr.com/%1/chat-server:latest
docker push ewr.vultrcr.com/%1/chat-server:latest
docker rmi ewr.vultrcr.com/%1/chat-server:latest

pause
