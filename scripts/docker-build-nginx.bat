cd ..

docker rmi chat-nginx
docker build -t chat-nginx:latest -f ./docker/Dockerfile-nginx .
pause
