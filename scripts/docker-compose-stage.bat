cd ..
docker compose -f .\docker\docker-compose-stage.yml --env-file .\docker\secret\.env up
pause
