cd ..

docker rmi chat-server
docker build -t chat-server:latest -f ./docker/Dockerfile-server .
pause
