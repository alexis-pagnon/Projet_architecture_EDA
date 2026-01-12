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

LABEL author="PAGNON Alexis"
LABEL email="alexis.pagnon@uphf.fr"

# Créer un user non-root
RUN addgroup -S insa && adduser -S -G insa -u 1000 insa

WORKDIR /opt/app

# Copier le jar généré par Maven
COPY --from=build /build/target/integration_services-0.0.1.jar app.jar

# Droits utilisateur
RUN chown -R insa:insa /opt/app
USER insa

ENTRYPOINT ["java","-jar","/opt/app/app.jar"]