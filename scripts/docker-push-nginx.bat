docker tag chat-nginx:latest ewr.vultrcr.com/%1/chat-nginx:latest
docker push ewr.vultrcr.com/%1/chat-nginx:latest
docker rmi ewr.vultrcr.com/%1/chat-nginx:latest

pause
