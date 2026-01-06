FROM eclipse-temurin:17-jre-alpine

LABEL author="TONDEUR Herve"
LABEL email="tondeur.herve@yahoo.fr"


RUN addgroup -S insa && adduser -S -G insa -u 1000 insa

RUN mkdir -p /opt/app && chown -R insa:insa /opt/app

#Répertoire de l'application
WORKDIR /opt/app

#copier le fat jar
COPY ws_services_template-0.0.1.jar /opt/app/

#expose le pot 8800  port le service DEMO et SETUP
EXPOSE 8800

#connecter le user insa
USER insa

#gerer l'entrypoint
ENTRYPOINT [ "java","-jar", "/opt/app/ws_services_template-0.0.1.jar" ]
