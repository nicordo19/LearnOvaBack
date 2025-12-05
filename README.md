# LearnOvaBack
LearnOva est une plateforme d’apprentissage et de partage de connaissance  par la vidéo. Elle permet aux utilisateurs d’accéder facilement à des cours vidéo organisés par catégories, et aux formateurs de publier leurs contenus en toute simplicité.

### 1 ) Docker Backend :

Pour dockeriser mon backend j’ai du utiliser ces commandes 
qui me permettre :

installer éclipse 

- FROM eclipse-temurin:17-jdk

De donner un nom a mon fichier que je vais utiliser 

- WORKDIR /app

De renouer mon fichier qui compile tout mon projet 

- COPY target/*.jar app.jar

de lancer l’ensamble des modification avec docker 

- CMD ["java", "-jar", "app.jar"]

Commande utilisée : pour builder mon image Docker 

```bash
mvn clean package
docker build -t backend-image

```

### Lancement du container backend :

Commande :

```bash
docker run -p 8080:8080 backend-image

```
