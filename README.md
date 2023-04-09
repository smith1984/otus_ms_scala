# OTUS Microservices (HW APIGW)

## Зависимости

Для выполнения задания использовались следующие зависимости:

- [Minikube 1.29.0](https://github.com/kubernetes/minikube/releases/tag/v1.29.0)
- [Kubectl 0.26.1](https://github.com/kubernetes/kubectl/releases/tag/v0.26.1)
- [Istioctl 1.17.0](https://github.com/istio/istio/releases/tag/1.17.0)
- [Heml 3.3.4](https://github.com/helm/helm/releases/tag/v3.3.4)


## Описание
Реализация идемпотентности для метода создания заказа в API.

Используемый паттерн - "Ключ идемпотентности".

Для реализации идемпотентности взаимодействие между клиентом и сервером происходит с передачей клиента ключа идемпотентности.
Сервер проверяет наличие заказа с таким ключом, если такого заказа не было, то он сохраняется и передаётся номер заказа.
В противном случае возникает ошибка дублирования заказа.

## Разворачивание инфраструктуры и сервисов
Выполнить
> sh ./install.sh

## Тестирование
> newman run ./postman_test/order_service.postman_collection.json

## Удаление инфраструктуры и сервисов
> sh ./delete.sh