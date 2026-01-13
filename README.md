# Architecture Orientée Services : TP Kubernetes
PAGNON Alexis<br>
SANCHEZ Adam<br>
16/01/2026

# Docker
## Build les containers

Pour construire les images Docker, utilisez le script "build.sh" situé à la racine du projet :
```
bash ./build.sh
```

Ou utiliser docker compose directement :
```
docker-compose build
```

## Lancer les containers

Pour lancer les containers, utilisez le script "run.sh" situé à la racine du projet :
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

Le service web pourra être accédé à l'adresse :
```
http://localhost:8080/
```

## Arrêter les containers

Pour arrêter les containers, utilisez le script "stop.sh" situé à la racine du projet :
```
bash ./stop.sh
```

## Push les containers sur DockerHub

Pour pousser les images Docker sur DockerHub, utilisez le script "push.sh" situé à la racine du projet, en vous assurant d'avoir configuré votre registre DockerHub dans le script :
```
bash ./push.sh
```

> [!WARNING]
> Les images Docker doivent être préalablement construites avant de pouvoir être poussées sur DockerHub.



# Kubernetes
## Déploiement & Lancement des services

Pour déployer les services sur un cluster Kubernetes, utilisez le script "deploy.sh" situé dans le dossier "kubernetes".<br>
Chaque service sera déployé dans un namespace créé.<br>
Ce script lance également minikube, et ouvre le service web dans le navigateur par défaut :
```
bash ./kubernetes/deploy.sh
```

## Suppression des services
Pour supprimer les services, utilisez le script "undeploy.sh" situé dans le dossier "kubernetes" :
```
bash ./kubernetes/undeploy.sh
```

> [!NOTE]
> Ce script ferme également minikube.


> [!WARNING]
> Le script "undeploy.sh" supprime le cluster minikube. Les données des volumes seront également supprimées.