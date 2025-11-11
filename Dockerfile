# Étape 1 : Utiliser une image Java 17 officielle
FROM eclipse-temurin:17-jdk-alpine

# Étape 2 : Ajouter le JAR dans l'image Docker
ARG JAR_FILE=target/Gestion-du-stock-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Étape 3 : Exposer le port que ton application utilise
EXPOSE 8095

# Étape 4 : Lancer l'application
ENTRYPOINT ["java","-jar","/app.jar"]
