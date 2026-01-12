#!/bin/bash

# Supprimer les pods et services
echo "Undeploying all resources..."

kubectl delete -f kubernetes/front-consult.yaml --ignore-not-found
kubectl delete -f kubernetes/integration-services.yaml --ignore-not-found
kubectl delete -f kubernetes/ws-services-template.yaml --ignore-not-found
kubectl delete -f kubernetes/kafka.yaml --ignore-not-found
kubectl delete -f kubernetes/pg-insa.yaml --ignore-not-found

# Supprimer le namespace
echo "Undeploying namespace..."

kubectl delete -f kubernetes/namespace.yaml --ignore-not-found

echo "Undeploying completed !"

# Stop minikube
echo "Stopping minikube..."
minikube stop