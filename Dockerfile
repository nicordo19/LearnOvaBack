# =========================
# BUILD STAGE
# =========================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom en premier (cache dépendances)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code
COPY src ./src

# Compiler l'application
RUN mvn -DskipTests clean package

# =========================
# RUNTIME STAGE
# =========================
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

