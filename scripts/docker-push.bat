docker tag chat-server:latest ewr.vultrcr.com/fjdndkv/chat-server:latest
docker push ewr.vultrcr.com/fjdndkv/chat-server:latest

docker rmi chat-server:latest
docker rmi ewr.vultrcr.com/fjdndkv/chat-server:latest

pause
