helm install postgresql bitnami/postgresql -f ./helm/infra/values.yaml
kubectl create cm config-migration-changelog --from-file=./helm/infra/changelog.yaml
kubectl create cm config-migration-properties --from-file=./helm/infra/liquibase.properties

sleep 10
kubectl apply -f ./helm/infra/job.yaml
sleep 10

kubectl create cm config-application --from-file=./k8s/app.conf
kubectl create secret generic secrets-application --from-file=./k8s/secrets.conf

kubectl apply -f ./k8s