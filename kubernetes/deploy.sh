#!/bin/bash

# 0. start minikube
echo "Starting minikube..."
minikube start

# 1. Create the namespace
echo "Creating namespace..."
kubectl apply -f kubernetes/namespace.yaml

# 2. Deploy the infrastructure (PostgreSQL and Kafka)
echo "Deploying pg-insa and kafka..."
kubectl apply -f kubernetes/pg-insa.yaml
kubectl apply -f kubernetes/kafka.yaml

# 3. Wait for PostgreSQL and Kafka to be ready
echo "Waiting for pg-insa and kafka to be ready..."
kubectl wait --for=condition=ready pod -l app=pg-insa -n architecture-eda --timeout=300s
kubectl wait --for=condition=ready pod -l app=kafka -n architecture-eda --timeout=300s

# 4. Deploy the backend services
echo "Deploying integration and ws services..."
kubectl apply -f kubernetes/ws-services-template.yaml
kubectl apply -f kubernetes/integration-services.yaml

# 5. Deploy the frontend
echo "Deploying frontend service..."
kubectl apply -f kubernetes/front-consult.yaml

# 6. Access the frontend service
echo "Accessing the frontend service..."

kubectl wait --for=condition=ready pod -l app=front-consult -n architecture-eda --timeout=300s
minikube service front-consult -n architecture-eda