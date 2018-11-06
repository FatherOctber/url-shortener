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

## Troubleshooting:
Restart your docker if you face with I/O error during integration test (with testcontainers) running.
