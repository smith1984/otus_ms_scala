# OTUS Microservices (HW 2)

## Описание
Сервис реализован на Scala и предоставляет доступ к следующим эндпоинтам:
* `/version` возвращает `{"version": "0.0.3"}`
* `/health` возвращает `{"status": "OK"}`
* `/hello/<name>` возвращает строку `Hello <name> from hello-deployment-<pod>`

Ingress перенаправляет запросы:
* на путь `^/version/?$` в эндпоинт `/version`
* на путь `^/health/?$` в эндпоинт `/health`
* на путь `^/otusapp/<student name>/*` в эндпоинт `/hello/<student name>`

## Установка сервиса
> sh ./install.sh

## Проверка работоспособности
> newman run ./postman/hw2.collection.json

## Удаление сервиса
> sh ./delete.sh