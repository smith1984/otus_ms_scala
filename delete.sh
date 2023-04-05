kubectl delete -f ./k8s/istio/auth.yaml
kubectl delete -n otus-ms-hw-apigw -f ./k8s/istio/routes.yaml
kubectl delete -f ./k8s/istio/istio.yaml
istioctl operator remove -y

kubectl delete -n otus-ms-hw-apigw -f ./k8s/auth_service/service.yaml
kubectl delete -n otus-ms-hw-apigw -f ./k8s/auth_service/deployment.yaml
kubectl delete -n otus-ms-hw-apigw -f ./k8s/user_service/service.yaml
kubectl delete -n otus-ms-hw-apigw -f ./k8s/user_service/deployment.yaml
kubectl delete secret secrets-application -n otus-ms-hw-apigw
kubectl delete cm config-application -n otus-ms-hw-apigw
kubectl delete -n otus-ms-hw-apigw -f ./helm/infra/job.yaml
kubectl delete cm config-migration-properties -n otus-ms-hw-apigw
kubectl delete cm config-migration-changelog -n otus-ms-hw-apigw

helm delete postgresql -n otus-ms-hw-apigw

kubectl config set-context --current --namespace=default

kubectl delete -f ./k8s/namespaces.yaml