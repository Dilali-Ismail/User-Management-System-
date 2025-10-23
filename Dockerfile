# --- Étape 1: La phase de Build ---
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# --- Étape 2: La phase d'Exécution ---
FROM tomcat:10.1-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*

# =============================================================
#                  LA CORRECTION EST ICI
# On copie le bon fichier : userManagement.war
# =============================================================
COPY --from=build /app/target/userManagement.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]