# OTUS Microservices (HW 3)

## Описание
Сервис реализован на Scala и предоставляет доступ к следующим эндпоинтам:
* GET /api/v1/user/{id} поиск пользователя по идентификатору
* POST /api/v1/user/ создание пользователя
* PUT /api/v1/user/{id} обновление пользователя
* DELETE /api/v1/user/{id} удаление пользователя

## Установка сервиса
> sh ./install.sh

## Проверка работоспособности
> newman run ./postman/hw3.collection.json

## Удаление сервиса
> sh ./delete.sh