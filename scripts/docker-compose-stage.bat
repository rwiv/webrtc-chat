cd ..
docker compose -f .\docker\docker-compose-stage.yml --env-file .\secret\.env up
pause
