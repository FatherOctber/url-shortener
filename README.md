# url-shortener
:fish: REST service for URL shortening 

## Features:
	- Shorten target url
	- Redirect to original url

## Technologies:
:point_right: Spring-Boot

:point_right: Spock Testing

:point_right: Redis

:point_right: Testcontainers

:point_right: Docker integration

## Run:
Use command "docker-compose build" to build docker images and "docker-compose up" for starting containers.
To get information about API see external-api/src/main/swagger/api.yaml.
For standalone running set your Redis connection properties in redisdb.properties.

## Docker:
Url-shortener image is available in docker hub (https://hub.docker.com/r/fatheroctober/test-projects/) with tag url-shortener_app. Use it with linkage to Redis image - see docker/docker-compose.

## Troubleshooting:
Restart your docker if you face with I/O error during integration test (with testcontainers) running.
