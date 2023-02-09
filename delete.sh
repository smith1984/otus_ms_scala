kubectl delete -f ./k8s
kubectl delete cm config-application
kubectl delete secret secrets-application

kubectl delete -f ./helm/infra/job.yaml
kubectl delete cm config-migration-changelog
kubectl delete cm config-migration-properties

helm delete postgresql
kubectl delete pvc --grace-period=0 --force data-postgresql-0