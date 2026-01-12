#!/bin/bash
# 1. Créer le namespace
kubectl apply -f kubernetes/namespace.yaml

# 2. Déployer l'infrastructure (PostgreSQL et Kafka)
kubectl apply -f kubernetes/pg-insa.yaml
kubectl apply -f kubernetes/kafka.yaml

# 3. Attendre que PostgreSQL et Kafka soient prêts
kubectl wait --for=condition=ready pod -l app=pg-insa -n architecture-eda --timeout=300s
kubectl wait --for=condition=ready pod -l app=kafka -n architecture-eda --timeout=300s

# 4. Déployer les services backend
kubectl apply -f kubernetes/ws-services-template.yaml
kubectl apply -f kubernetes/integration-services.yaml

# 5. Déployer le frontend
kubectl apply -f kubernetes/front-consult.yaml