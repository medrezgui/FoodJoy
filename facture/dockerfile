FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
EXPOSE 8080
ADD target/Facture-0.0.1-SNAPSHOT.jar Facture.jar
ENTRYPOINT ["java", "-jar", "Facture.jar"]