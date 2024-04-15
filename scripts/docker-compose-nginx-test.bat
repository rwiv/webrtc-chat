cd ..
docker compose -f .\docker\docker-compose-nginx-test.yml --env-file .\secret\.env up
pause
