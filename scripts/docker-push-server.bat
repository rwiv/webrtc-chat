docker tag chat-server:latest ewr.vultrcr.com/%1/chat-server:latest
docker push ewr.vultrcr.com/%1/chat-server:latest
docker rmi ewr.vultrcr.com/%1/chat-server:latest

pause
