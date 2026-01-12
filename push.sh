#!/bin/bash
# Connexion
docker login

REGISTRY="alexispgn"

# Build
docker-compose build

# Tag
docker tag front-consult:latest $REGISTRY/front_consult:1.0
docker tag integration_services:latest $REGISTRY/integration_services:1.0
docker tag ws_services_template:latest $REGISTRY/ws_services_template:1.0
docker tag pg-insa:latest $REGISTRY/pg_insa:1.0
# Push
docker push $REGISTRY/front_consult:1.0
docker push $REGISTRY/integration_services:1.0
docker push $REGISTRY/ws_services_template:1.0
docker push $REGISTRY/pg_insa:1.0
