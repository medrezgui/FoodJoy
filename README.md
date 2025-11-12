# FoodJoy Microservices Platform

Plateforme de gestion de restaurant basée sur une architecture microservices Spring Boot.  
Elle couvre la gestion des menus et plats, la prise de commandes, la facturation, le stock, les réservations, les tables et le personnel.  
Les services communiquent à la fois en REST via un API Gateway et de manière asynchrone via RabbitMQ.

---

## 🧱 Architecture

| Couche | Modules / Rôle |
| --- | --- |
| **Infrastructure partagée** | - `ConfigServer` (Spring Cloud Config – port `8888`) <br> - `EurekaServer` (Service Discovery – port `8761`) <br> - `ApiGateway` (Spring Cloud Gateway – port `8087`) <br> - RabbitMQ (événements inter-services – port `5672`) |
| **Services métier** | - `Menu_PlatMS` (`8082`) : menus & plats, publie les plats vers RabbitMQ <br> - `gestion-commandeMS` (`8081`) : prise de commandes, consomme les plats pour alimenter un cache interne <br> - `ReservationMS` (`8083`) : gestion des réservations clients <br> - `facture` (`8084`) : facturation & génération de factures <br> - `TableMS` (`8085`) : gestion des tables du restaurant <br> - `stock` (`8095`) : suivi des ingrédients et alertes stock <br> - `Employee` (`8088`) : gestion du personnel |

Les configurations par défaut (ports, bases H2, entêtes RabbitMQ…) sont centralisées dans `ConfigServer/src/main/resources/config`.

---

## 🛠️ Prérequis

- **JDK 17**
- **Maven 3.9+** (ou `mvnw` fourni par chaque service)
- **RabbitMQ** (local ou docker – port 5672 / management 15672)
- Git, un IDE Java, et (optionnel) Docker pour simplifier le lancement de RabbitMQ

### Lancer RabbitMQ en Docker

```bash
docker run -it --rm \
  -p 5672:5672 \
  -p 15672:15672 \
  --name foodjoy-rabbit \
  rabbitmq:3-management
```

> Interface de management : http://localhost:15672 (login `guest` / `guest`)

---

## 🚀 Démarrage rapide

Lancez chaque microservice dans un terminal distinct (ou utilisez votre IDE).

1. **Config Server**  
   ```bash
   cd ConfigServer
   mvn spring-boot:run
   ```

2. **Eureka Server**  
   ```bash
   cd ../EurekaServer
   mvn spring-boot:run
   ```

3. **API Gateway**  
   ```bash
   cd ../ApiGateway
   mvn spring-boot:run
   ```

4. **Services métier** (ordre recommandé)  
   - Menu & Plats : `Menu_PlatMS`  
   - Commandes : `gestion-commandeMS`  
   - Factures : `facture`  
   - Réservations : `ReservationMS`  
   - Tables : `TableMS`  
   - Stock : `stock`  
   - Employés : `Employee`

   ```bash
   cd ../Menu_PlatMS
   mvn spring-boot:run
   # Reproduire dans chaque dossier de service
   ```

5. Vérifiez le registre Eureka : http://localhost:8761  
   Tous les services doivent apparaître une fois démarrés.

---

## 📂 Structure du projet

```
FoodJoy/
├── ApiGateway/                 # Spring Cloud Gateway (exposition REST unique)
├── ConfigServer/               # Configurations centralisées
├── EurekaServer/               # Registre Eureka
├── Employee/                   # Gestion du personnel
├── Menu_PlatMS/                # Menus & plats (+ publication RabbitMQ)
├── gestion-commandeMS/         # Commandes & cache de plats (consommation RabbitMQ)
├── facture/                    # Facturation
├── ReservationMS/              # Réservations clients
├── TableMS/                    # Gestion des tables
├── stock/                      # Inventaire & alertes
└── postman_collection_gestion_commande.json
```

Chaque microservice suit une organisation en couches (domain, application, infrastructure, web) et expose ses propres endpoints (souvent documentés via Swagger ou dans les README internes).

---

## 🔗 Communication inter-services

- **REST (synchrones)** via le Gateway (`http://localhost:8087`) ou directement service ⇄ service selon les besoins.
- **RabbitMQ (asynchrone)** :
  - `Menu_PlatMS` publie les plats sur l’exchange `menuplat.exchange` (routing `menuplat.routingkey`).
  - `gestion-commandeMS` consomme la queue `menuplat.queue` et alimente son cache `plat_cache`.
  - D’autres services (stock, facture, etc.) peuvent publier/consommer leurs propres messages.

---

## 💾 Bases de données

| Service | Type / Mode | URL H2 | Console |
| --- | --- | --- | --- |
| gestion-commandeMS | H2 mémoire | `jdbc:h2:mem:gestion_commande_db` | `http://localhost:8081/h2-console` |
| Menu_PlatMS | H2 fichier | `jdbc:h2:file:./Database/Data/Menu` | `http://localhost:8082/h2` |
| ReservationMS | H2 fichier | `jdbc:h2:file:./data/reservationdb` | `http://localhost:8083/h2` |
| facture | H2 fichier | `jdbc:h2:file:./data/facturedb` | `http://localhost:8084/h2` |
| TableMS | H2 fichier | `jdbc:h2:file:./data/TableMS` | `http://localhost:8085/h2-console` |
| stock | H2 fichier | `jdbc:h2:~/testdb` | `http://localhost:8095/h2` |
| Employee | H2 fichier | `jdbc:h2:file:./Database/Data/Employee` | `http://localhost:8088/h2` |

> Les identifiants sont généralement `sa` / mot de passe vide (voir les fichiers `application.properties` de chaque service).

---

## 🧪 Tests & outils

- **Postman** : importer `gestion-commandeMS/postman_collection_gestion_commande.json` pour tester toutes les routes du service de commandes.
- **Swagger / SpringDoc** : certains services exposent la doc via `/swagger-ui.html` (ex : Menu_PlatMS).
- **H2 Console** : accessible service par service (voir tableau).
- **cURL / HTTPie** : pour des requêtes rapides en CLI.

---

## ✅ Bonnes pratiques & développement

- Utiliser `mvn clean install` à la racine pour vérifier que tous les modules compilent.
- Chaque service peut être lancé via `mvn spring-boot:run` ou depuis l’IDE (classe `*Application`).
- Penser à mettre à jour les configurations partagées dans `ConfigServer/src/main/resources/config` et redémarrer les services dépendants.
- Le Gateway peut centraliser toute la sécurité, le rate-limiting et les règles de routage.
- Les services sensibles au changement (Menu ↔ Commandes) reposent sur RabbitMQ : assurez-vous que l’instance est disponible avant de démarrer ces MS.

---

## 📚 Ressources utiles

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [RabbitMQ Docs](https://www.rabbitmq.com/documentation.html)
- [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/reference/html/)

---

## 📝 Licence

Projet pédagogique FoodJoy – architecture microservices Spring Boot.  
Utilisation libre pour démonstration, formation ou évolution interne.


