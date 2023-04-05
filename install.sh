kubectl apply -f ./k8s/namespaces.yaml

kubectl config set-context --current --namespace=otus-ms-hw-apigw

helm install postgresql bitnami/postgresql -n otus-ms-hw-apigw -f ./helm/infra/values.yaml
kubectl create cm config-migration-changelog -n otus-ms-hw-apigw --from-file=./helm/infra/changelog.yaml
kubectl create cm config-migration-properties -n otus-ms-hw-apigw --from-file=./helm/infra/liquibase.properties

sleep 10
kubectl apply -n otus-ms-hw-apigw -f ./helm/infra/job.yaml
sleep 10

kubectl create cm config-application -n otus-ms-hw-apigw --from-file=./k8s/user_service/app.conf
kubectl create secret generic secrets-application -n otus-ms-hw-apigw --from-file=./k8s/user_service/secrets.conf

kubectl apply -n otus-ms-hw-apigw -f ./k8s/user_service/deployment.yaml
kubectl apply -n otus-ms-hw-apigw -f ./k8s/user_service/service.yaml
kubectl apply -n otus-ms-hw-apigw -f ./k8s/auth_service/deployment.yaml
kubectl apply -n otus-ms-hw-apigw -f ./k8s/auth_service/service.yaml

istioctl operator init --watchedNamespaces istio-system --operatorNamespace istio-operator
kubectl apply -f ./k8s/istio/istio.yaml
kubectl apply -n otus-ms-hw-apigw -f ./k8s/istio/routes.yaml
kubectl apply -f ./k8s/istio/auth.yaml

