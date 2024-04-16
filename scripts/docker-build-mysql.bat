cd ..

docker rmi chat-db
docker build -t chat-db:latest -f ./docker/Dockerfile-mysql .
pause
