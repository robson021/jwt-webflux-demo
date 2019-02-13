#!/usr/bin/env bash

docker stop postgres
docker rm postgres
docker-compose up -d

sleep 6

docker cp ./tables_postgres.sql  postgres:/docker-entrypoint-initdb.d/create_schema.sql
docker exec -it postgres psql -h localhost -U postgres -p 5432 -a -q -f docker-entrypoint-initdb.d/create_schema.sql

docker ps -a
