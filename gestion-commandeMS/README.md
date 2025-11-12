# 🍽️ Gestion de Commandes - Restaurant

Application Spring Boot pour la gestion des commandes d'un restaurant avec architecture hexagonale (Clean Architecture).

## 📋 Technologies

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (en mémoire)
- **Maven**

## 🏗️ Architecture

L'application suit une architecture propre avec séparation des couches :

```
src/main/java/tn/esprit/foodjoy/commande/gestioncommande/
├── domain/                    # Couche domaine (entités, modèles)
│   └── model/
│       ├── Commande.java
│       ├── LigneCommande.java
│       ├── StatutCommande.java (enum)
│       └── TypeCommande.java (enum)
├── application/               # Couche application (services, DTOs, mappers)
│   ├── dto/
│   ├── mapper/
│   └── service/
├── infrastructure/            # Couche infrastructure (repositories, config)
│   ├── repository/
│   └── config/
└── web/                       # Couche web (contrôleurs, exceptions)
    ├── controller/
    └── exception/
```

## 🚀 Démarrage

1. **Compiler le projet :**
   ```bash
   mvn clean compile
   ```

2. **Lancer l'application :**
   ```bash
   mvn spring-boot:run
   ```

3. **Accéder à l'application :**
   - API REST : http://localhost:8081/api/commandes
   - Console H2 : http://localhost:8081/h2-console
     - URL JDBC: `jdbc:h2:mem:gestion_commande_db`
     - Username: `sa`
     - Password: (vide)

## 📚 API REST

### Base URLs
- **Commandes** : `http://localhost:8081/api/commandes`
- **Lignes de Commande** : `http://localhost:8081/api/lignes-commande`

### Endpoints

#### 1. CRUD Commandes

##### Créer une commande
```http
POST /api/commandes
Content-Type: application/json

{
  "typeCommande": "SUR_PLACE",
  "employeId": 1,
  "tableId": 5,
  "lignesCommande": [
    {
      "platId": 101,
      "quantite": 2,
      "prixUnitaire": 15.50,
      "commentaire": "Bien cuit"
    },
    {
      "platId": 102,
      "quantite": 1,
      "prixUnitaire": 8.00
    }
  ]
}
```

##### Obtenir toutes les commandes
```http
GET /api/commandes
```

##### Obtenir une commande par ID
```http
GET /api/commandes/{id}
```

##### Obtenir une commande par numéro
```http
GET /api/commandes/numero/{numeroCommande}
```

##### Mettre à jour une commande
```http
PUT /api/commandes/{id}
Content-Type: application/json

{
  "typeCommande": "À_EMPORTER",
  "employeId": 2,
  "tableId": null,
  "lignesCommande": [
    {
      "platId": 103,
      "quantite": 3,
      "prixUnitaire": 12.00,
      "commentaire": "Sans oignons"
    }
  ]
}
```

##### Supprimer une commande
```http
DELETE /api/commandes/{id}
```

#### 2. Méthodes Avancées

##### Changer le statut d'une commande
```http
PATCH /api/commandes/{id}/statut
Content-Type: application/json

{
  "nouveauStatut": "PREPARATION"
}
```

**Statuts disponibles :** `EN_ATTENTE`, `PREPARATION`, `PRETE`, `SERVIE`, `ANNULEE`

##### Calculer le total d'une commande
```http
GET /api/commandes/{id}/total
```

**Réponse :**
```json
{
  "commandeId": 1,
  "total": 39.00
}
```

#### 3. Recherches

##### Par statut
```http
GET /api/commandes/statut/{statut}
```

##### Par type
```http
GET /api/commandes/type/{type}
```

**Types disponibles :** `SUR_PLACE`, `À_EMPORTER`, `LIVRAISON`

##### Par employé
```http
GET /api/commandes/employe/{employeId}
```

##### Par table
```http
GET /api/commandes/table/{tableId}
```

#### 4. Gestion des Lignes de Commande (via Commande)

##### Obtenir les lignes d'une commande
```http
GET /api/commandes/{commandeId}/lignes
```

##### Ajouter une ligne à une commande
```http
POST /api/commandes/{commandeId}/lignes
Content-Type: application/json

{
  "platId": 101,
  "quantite": 2,
  "prixUnitaire": 15.50,
  "commentaire": "Bien cuit"
}
```

##### Mettre à jour une ligne de commande
```http
PUT /api/commandes/{commandeId}/lignes/{ligneId}
Content-Type: application/json

{
  "platId": 101,
  "quantite": 3,
  "prixUnitaire": 15.50,
  "commentaire": "Très bien cuit"
}
```

##### Supprimer une ligne de commande
```http
DELETE /api/commandes/{commandeId}/lignes/{ligneId}
```

##### Supprimer toutes les lignes d'une commande
```http
DELETE /api/commandes/{commandeId}/lignes
```

#### 5. CRUD Lignes de Commande (Endpoints Indépendants)

##### Créer une ligne de commande
```http
POST /api/lignes-commande/commande/{commandeId}
Content-Type: application/json

{
  "platId": 101,
  "quantite": 2,
  "prixUnitaire": 15.50,
  "commentaire": "Bien cuit"
}
```

##### Obtenir toutes les lignes de commande
```http
GET /api/lignes-commande
```

##### Obtenir une ligne de commande par ID
```http
GET /api/lignes-commande/{id}
```

##### Obtenir les lignes d'une commande
```http
GET /api/lignes-commande/commande/{commandeId}
```

##### Obtenir les lignes par plat
```http
GET /api/lignes-commande/plat/{platId}
```

##### Mettre à jour une ligne de commande
```http
PUT /api/lignes-commande/{id}
Content-Type: application/json

{
  "platId": 101,
  "quantite": 3,
  "prixUnitaire": 15.50,
  "commentaire": "Très bien cuit"
}
```

##### Supprimer une ligne de commande
```http
DELETE /api/lignes-commande/{id}
```

##### Supprimer toutes les lignes d'une commande
```http
DELETE /api/lignes-commande/commande/{commandeId}
```

## 📊 Modèle de Données

### Commande
- `id` : Long (auto-généré)
- `numeroCommande` : String (unique)
- `statut` : StatutCommande (enum)
- `dateCreation` : LocalDateTime
- `typeCommande` : TypeCommande (enum)
- `employeId` : Long
- `tableId` : Long
- `lignesCommande` : List<LigneCommande>

### LigneCommande
- `id` : Long (auto-généré)
- `platId` : Long
- `quantite` : Integer
- `prixUnitaire` : Double
- `commentaire` : String (optionnel)
- `commande` : Commande (relation ManyToOne)

## 🔧 Configuration

### application.properties
- **Port** : 8081
- **Base de données** : H2 en mémoire
- **DDL** : create-drop (recréation à chaque démarrage)
- **Console H2** : activée sur `/h2-console`

## 📦 Données d'Exemple

L'application initialise automatiquement 5 commandes d'exemple au démarrage :
- Commande 1 : Sur place - EN_ATTENTE (Table 5)
- Commande 2 : À emporter - PREPARATION
- Commande 3 : Livraison - PRETE
- Commande 4 : Sur place - SERVIE (Table 10)
- Commande 5 : À emporter - EN_ATTENTE

## 🧪 Tests

Pour tester l'API, vous pouvez utiliser :
- **Postman**
- **curl**
- **Thunder Client** (extension VS Code)
- **H2 Console** pour vérifier les données

### Exemple avec curl

```bash
# Obtenir toutes les commandes
curl http://localhost:8081/api/commandes

# Créer une commande
curl -X POST http://localhost:8081/api/commandes \
  -H "Content-Type: application/json" \
  -d '{
    "typeCommande": "SUR_PLACE",
    "employeId": 1,
    "tableId": 5,
    "lignesCommande": [
      {
        "platId": 101,
        "quantite": 2,
        "prixUnitaire": 15.50,
        "commentaire": "Bien cuit"
      }
    ]
  }'

# Changer le statut
curl -X PATCH http://localhost:8081/api/commandes/1/statut \
  -H "Content-Type: application/json" \
  -d '{"nouveauStatut": "PREPARATION"}'

# Calculer le total
curl http://localhost:8081/api/commandes/1/total

# Obtenir les lignes d'une commande
curl http://localhost:8081/api/commandes/1/lignes

# Ajouter une ligne à une commande
curl -X POST http://localhost:8081/api/commandes/1/lignes \
  -H "Content-Type: application/json" \
  -d '{
    "platId": 110,
    "quantite": 1,
    "prixUnitaire": 12.00,
    "commentaire": "Sans oignons"
  }'

# Obtenir toutes les lignes de commande
curl http://localhost:8081/api/lignes-commande

# Mettre à jour une ligne de commande
curl -X PUT http://localhost:8081/api/lignes-commande/1 \
  -H "Content-Type: application/json" \
  -d '{
    "platId": 101,
    "quantite": 4,
    "prixUnitaire": 15.50,
    "commentaire": "Modifié"
  }'

# Supprimer une ligne de commande
curl -X DELETE http://localhost:8081/api/lignes-commande/1
```

## 📝 Notes

- Le numéro de commande est généré automatiquement au format : `CMD-{UUID}-{date}`
- Les relations JPA sont gérées avec cascade (suppression en cascade des lignes de commande)
- La validation des données est effectuée avec Bean Validation
- La gestion des erreurs est centralisée avec `GlobalExceptionHandler`

## 🎯 Fonctionnalités

### Commandes
✅ CRUD complet pour les commandes  
✅ Changement de statut d'une commande  
✅ Calcul du total d'une commande  
✅ Recherches par statut, type, employé, table  
✅ Gestion des lignes de commande via endpoints dédiés  

### Lignes de Commande
✅ CRUD complet pour les lignes de commande  
✅ Gestion indépendante des lignes de commande  
✅ Recherche par commande ou par plat  
✅ Gestion via endpoints de commande ou endpoints indépendants  

### Général
✅ Validation des données  
✅ Gestion des erreurs centralisée  
✅ Données d'exemple au démarrage  
✅ Architecture hexagonale propre  

## 📄 Licence

Ce projet est un exemple d'application Spring Boot pour la gestion des commandes d'un restaurant.

