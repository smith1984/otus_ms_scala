kubectl apply -f ./k8s/namespaces.yaml
kubectl config set-context --current --namespace=otus-ms-hw-idempotency

helm install postgresql bitnami/postgresql -f ./k8s/postgres/values.yaml
kubectl create cm config-migration-changelog --from-file=./k8s/postgres/changelog.yaml
kubectl create cm config-migration-properties --from-file=./k8s/postgres/liquibase.properties
sleep 10
kubectl apply -f ./k8s/postgres/job.yaml
sleep 10

kubectl create cm config-application -n otus-ms-hw-idempotency --from-file=./k8s/order_service/app.conf
kubectl create secret generic secrets-application -n otus-ms-hw-idempotency --from-file=./k8s/order_service/secrets.conf
kubectl apply -n otus-ms-hw-idempotency -f ./k8s/order_service/deployment.yaml
kubectl apply -n otus-ms-hw-idempotency -f ./k8s/order_service/service.yaml

helm install -n otus-ms-hw-idempotency nginx ingress-nginx/ingress-nginx -f ./k8s/nginx/nginx-ingress.yaml
sleep 15
kubectl apply -n otus-ms-hw-idempotency -f ./k8s/order_service/ingress.yaml