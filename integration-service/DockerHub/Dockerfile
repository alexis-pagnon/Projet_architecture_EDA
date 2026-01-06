FROM eclipse-temurin:17-jre-alpine

LABEL author="PAGNON Alexis"
LABEL email="alexis.pagnon@uphf.fr"

RUN addgroup -S insa && adduser -S -G insa -u 1000 insa

RUN mkdir -p /opt/app && chown -R insa:insa /opt/app

#Répertoire de l'application
WORKDIR /opt/app

#copier le fat jar (nom basé sur artifactId dans pom.xml)
COPY integration-service-0.0.1-SNAPSHOT.jar /opt/app/app.jar

#connecter le user insa
USER insa

#gerer l'entrypoint
ENTRYPOINT [ "java", "-jar", "/opt/app/app.jar" ]
