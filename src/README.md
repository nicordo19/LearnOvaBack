# RNCP – Backend

##  Description
Backend Java développé avec Spring Boot.
Il expose une API REST consommée par un frontend Angular.
La persistance des données est assurée par PostgreSQL via Hibernate (JPA).

```bash
```

Le projet est dockerisé (backend + base de données) et supporte deux environnements :

- DEV (développement)
- PROD (production)

---

##  Stack technique
 Prerequis :

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Docker & Docker Compose
- migration (Flyway)

---

##  Architecture globale

Frontend (Angular)
↓ -> communication HTTP (API REST)
↓ -> Backend (Spring Boot)
↓ -> couche ORM ( JPA / Hibernate)
  -> Base de données (PostgreSQL)


## Structure du projet 

L’architecture du backend suit une organisation en couches
permettant une séparation claire des responsabilités
et une meilleure maintenabilité du code

backend/
├── src/main/java
│   └── rncp/backend
│       ├── controller
│       │   └── UserController.java
│       │
│       ├── service
│       │   └── UserService.java
│       │
│       ├── repository
│       │   └── UserRepository.java
│       │
│       └── entity
│           ├── User.java
│           ├── Role.java
│           ├── Video.java
│           └── Comment.java
│           
├── src/main/resources
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-prod.properties
│
├── Dockerfile
└── README.md  
##  Configuration Spring & Environnements

### Fichiers de configuration
-  configuration commune ->  `application.properties`
-  DEV  -> `application-dev.properties`
-  PROD -> `application-prod.properties`

Le choix de l’environnement se fait via la variable : `SPRING_PROFILES_ACTIVE=dev | prod`

Cette variable est fournie par Docker


 ```bash 

```

Base de données & ORM
-
- Base de données : `PostgreSQL`
- ORM : `Hibernate (via Spring Data JPA)`
- Les entités Java représentent le schéma défini dans DrawSQL
   Comportement Hibernate
- (création / mise à jour automatique): DEV → `ddl-auto=update` 
- (aucune modification du schéma) : PROD → `ddl-auto=validate` 




Ne jamais utiliser update ou create en production.
Lancer le projet avec Docker (recommandé)

### DEV
- rofile Spring : dev
- Base : PostgreSQL Docker
- Hibernate en mode développement : `docker compose up -d`
- Accès: Backend : `http://localhost:8080`
- PostgreSQL : `localhost:5432`

### PROD
Profile Spring : prod
Hibernate en mode sécurisé :`docker compose -f docker-compose.prod.yml up -d`

### La base doit déjà contenir le schéma.
- Arrêter les conteneurs et suprime la base(dev uniquement )  :` docker compose down -v`
- Arrêter les conteneurs: `docker compose down`
- demarrer le container` = `docker compose up`
- Logs Docker : `docker compose logs backend`
- Logs en temps réel :` docker compose logs -f backend`

### Lancer le backend SANS Docker

DEV :  `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`

PROD :  `./mvnw spring-boot:run -Dspring-boot.run.profiles=prod `

## Base de données & Migrations

### Base de données
- SGBD : PostgreSQL
- Hébergée via Docker en environnement de développement
- Accès via Spring Data JPA (Hibernate)

### Initialisation en développement
Le schéma de la base est initialisé automatiquement au démarrage
des conteneurs Docker à partir des scripts SQL présents dans :

...developeur/ProjetRNCP/db/init-db/

### Évolution du schéma (migrations)
En environnement de production, les évolutions du schéma
sont gérées via des migrations SQL versionnées (Flyway).

Les scripts de migration seront placés dans :
src/main/resources/db/migration


### PostgreSQL doit être déjà disponible (local ou distant).

### Avec Maven Wrapper (recommandé) 

avec Maven installé : `mvn spring-boot:run`
 
`Avec profile` : `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

### Lancer avec Java (après build)
Construire le JAR: `./mvnw clean package`

Lancer le JAR : `java -jar target/backend-0.0.1-SNAPSHOT.jar`

Avec profile : `java -Dspring.profiles.active=dev -jar target/backend-0.0.1-SNAPSHOT.jar`

### Variables d’environnement utilisées:
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- SPRING_PROFILES_ACTIVE

## Sécurité & bonnes pratiques

- Aucune information sensible (mot de passe, URL de base, clés) n’est stockée en dur dans le code
- Les configurations sont gérées via des variables d’environnement
- Les profils Spring permettent d’adapter le comportement DEV / PROD
- Hibernate n’est jamais autorisé à modifier le schéma en production
- Les logs permettent de vérifier le profil actif au démarrage


Dans les logs, vérifier :

The following profiles are active

dev : Started BackendApplication


## À venir / Roadmap

- Mise en place des migrations de base de données avec Flyway
- Sécurisation de l’API (authentification, autorisation)
- Gestion des rôles et des permissions
- Documentation des endpoints REST
- Mise en place de tests unitaires et d’intégration
