# =========================
# Etape 1 : Build Maven
# =========================
FROM maven:3.9.12-eclipse-temurin-17-alpine AS build

WORKDIR /build

# Copier les fichiers nécessaires au build
COPY pom.xml .
COPY src ./src

# Compiler l'application
RUN mvn clean package -DskipTests

# =========================
# Etape 2 : Runtime
# =========================
FROM eclipse-temurin:17-jre-alpine

LABEL author="TONDEUR Herve"
LABEL email="tondeur.herve@yahoo.fr"


RUN addgroup -S insa && adduser -S -G insa -u 1000 insa

RUN mkdir -p /opt/app && chown -R insa:insa /opt/app

#Répertoire de l'application
WORKDIR /opt/app

#Copier le jar généré par Maven
COPY --from=build /build/target/ws_services_template-0.0.1.jar ws_services_template-0.0.1.jar

#expose le pot 8800  port le service DEMO et SETUP
EXPOSE 8800

#connecter le user insa
USER insa

#gerer l'entrypoint
ENTRYPOINT [ "java","-jar", "/opt/app/ws_services_template-0.0.1.jar" ]
