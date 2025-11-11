# Étape 1 : image de base
FROM eclipse-temurin:17-jdk

# Étape 2 : dossier de travail dans le conteneur
WORKDIR /app

# Étape 3 : copier le fichier JAR généré dans le conteneur
COPY target/Menu_Plat-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : exposer le port
EXPOSE 8082

# Étape 5 : lancer l’application
ENTRYPOINT ["java", "-jar", "app.jar"]
