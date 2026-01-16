# Architecture Orientée Services : TP Kubernetes
PAGNON Alexis<br>
SANCHEZ Adam<br>
16/01/2026
***
# Description du projet
Nous avons développé ce projet dans le cadre de notre enseignement "Architecture Orientée Services" à l'INSA Hauts-de-France. Il a pour but de mettre en place une architecture orientée services en utilisant Docker et Kubernetes. Il comprend plusieurs services interconnectés, chacun déployé dans son propre conteneur Docker, et orchestrés à l'aide de Kubernetes.<br>

Vous trouverez également dans ce répertoire le [rapport du projet](./Rapport_TP_Kubernetes_PAGNON_Alexis_SANCHEZ_Adam.pdf), contenant également les réponses aux premières questions posées sur minikube dans le sujet.<br>

# Structure du projet
Le projet est structuré de la manière suivante :
- `kubernetes/` : Contient les fichiers manifestes nécessaires pour déployer les services sur un cluster Kubernetes.
- `front-consult/` : Contient le code source du service front-end pour la consultation des étudiants.
- `integration_services/` : Contient le code source du service liant le bus Kafka et la base de données.
- `pg-insa/` : Contient le script SQL pour créer et initialiser la base de données PostgreSQL.
- `ws_services_template/` : Contient le code source du service fournissant une API REST et qui écrit les requêtes sur le bus Kafka.


# Docker
## Build les containers

Pour construire les images Docker, utilisez le script [build.sh](./build.sh) situé à la racine du projet :
```
bash ./build.sh
```

Ou utiliser docker compose directement :
```
docker-compose build
```

## Lancer les containers

Pour lancer les containers, utilisez le script [run.sh](./run.sh) situé à la racine du projet :
```
bash ./run.sh
```

> [!WARNING]
> Les images Docker doivent être préalablement construites avant d'être lancées.

> [!NOTE]
> Les images Docker peuvent être construites et lancées en une seule commande en utilisant le script "build_and_run.sh" situé à la racine du projet :
> ```
> bash ./build_and_run.sh
> ```

Le service web pourra être accédé à l'[adresse](http://localhost:8080/) :
```
http://localhost:8080/
```

## Arrêter les containers

Pour arrêter les containers, utilisez le script [stop.sh](./stop.sh) situé à la racine du projet :
```
bash ./stop.sh
```

## Push les containers sur DockerHub

Pour pousser les images Docker sur DockerHub, utilisez le script [push.sh](./push.sh) situé à la racine du projet, en vous assurant d'avoir configuré votre registre DockerHub dans le script :
```
bash ./push.sh
```

> [!WARNING]
> Les images Docker doivent être préalablement construites avant de pouvoir être poussées sur DockerHub.



# Kubernetes
## Déploiement & Lancement des services

Pour déployer les services sur un cluster Kubernetes, utilisez le script [deploy.sh](./kubernetes/deploy.sh) situé dans le dossier [kubernetes](./kubernetes).<br>
Chaque service sera déployé dans un namespace créé.<br>
Ce script lance également minikube, et ouvre le service web dans le navigateur par défaut :
```
bash ./kubernetes/deploy.sh
```

## Suppression des services
Pour supprimer les services, utilisez le script [undeploy.sh](./kubernetes/undeploy.sh) situé dans le dossier [kubernetes](./kubernetes) :
```
bash ./kubernetes/undeploy.sh
```

> [!NOTE]
> Ce script ferme également minikube.


> [!WARNING]
> Le script "undeploy.sh" supprime le cluster minikube. Les données des volumes seront également supprimées.