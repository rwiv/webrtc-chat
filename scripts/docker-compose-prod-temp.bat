cd ..
docker compose -f .\docker\docker-compose-prod-temp.yml --env-file .\secret\.env up
pause
