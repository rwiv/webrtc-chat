cd ..
docker compose -f .\docker\docker-compose-nginx-test.yml --env-file .\docker\secret\.env up
pause
