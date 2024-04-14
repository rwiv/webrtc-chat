cd ..
docker compose -f .\docker\docker-compose-dev-nginx.yml --env-file .\docker\secret\.env up
pause
