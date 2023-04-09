kubectl delete -n otus-ms-hw-idempotency -f ./k8s/order_service/ingress.yaml
helm delete -n otus-ms-hw-idempotency nginx

kubectl delete -n otus-ms-hw-idempotency -f ./k8s/order_service/service.yaml
kubectl delete -n otus-ms-hw-idempotency -f ./k8s/order_service/deployment.yaml
kubectl delete secret secrets-application -n otus-ms-hw-idempotency
kubectl delete cm config-application -n otus-ms-hw-idempotency

kubectl delete -f ./k8s/postgres/job.yaml
kubectl delete cm config-migration-properties
kubectl delete cm config-migration-changelog
helm delete postgresql
kubectl delete pvc --grace-period=0 --force data-postgresql-0

kubectl config set-context --current --namespace=default
kubectl delete -f ./k8s/namespaces.yaml
