docker tag chat-db:latest ewr.vultrcr.com/%1/chat-db:latest
docker push ewr.vultrcr.com/%1/chat-db:latest
docker rmi ewr.vultrcr.com/%1/chat-db:latest

pause
