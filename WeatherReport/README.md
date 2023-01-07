# Générateur de météo et de citations
Bienvenue sur le Générateur de météo et de citations!

Cette application vous permet de connaître la météo actuelle d'un lieu spécifique,de générer une citation aléatoire,
d'afficher une image de fond correspondante et de partager les images par e-mail.

L'application est construite en utilisant:
- Java et JavaFX et utilise la bibliothèque Jakarta Mail pour la fonctionnalité share.
- Lombok pour la génération de code.
- Google Gson pour l'analyse de données JSON.

Ce projet a été créé à l'aide de l'SDK Java 17 et utilise le multithreading pour améliorer les performances.

# Table des matières
- [Vue d'ensemble](#Vue d'ensemble)
- [Installation](#Installation)
- [Utilisation](#Utilisation)
- [Mises à jour actuelles](#Mises à jour actuelles)
- [Crédits](#Crédits)


# Vue d'ensemble
Le Générateur de météo et de citations est une application simple et facile à utiliser qui vous permet de connaître la météo actuelle d'un lieu spécifique, de générer une citation aléatoire, d'afficher une image de fond correspondante et de partager les images par e-mail. Il utilise l'API OpenWeatherMap pour les données météorologiques, l'API Unsplash pour les images de fond, l'API ZenQuotes pour la génération de citations, la bibliothèque Jakarta Mail pour la fonctionnalité e-mail, Lombok pour la génération de code et Google Gson pour l'analyse de données JSON.
# Installation
Pour utiliser le Générateur de météo et de citations, suivez ces étapes:

1. Clonez le répertoire:
    ```
    git clone https://github.com/YOUR-USERNAME/YOUR-REPOSITORY
    ```
2. Obtenez des clés API pour l'API OpenWeatherMap et l'API Unsplash.
3. Allez dans le fichier appelé .env dans le répertoire racine du projet et ajoutez les lignes suivantes, en remplaçant YOUR-API-KEY-HERE par vos clés API réelles:
    ```
   API_OPEN_WEATHER = [YOUR-API-KEY-HERE]
   APIKEY_UNSPLASH = [YOUR-API-KEY-HERE]
   ```
4. Si vous utilisez Maven pour gérer les dépendances, ajoutez les dépendances suivantes à votre fichier pom.xml:
    ```xml
   <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>19</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>19</version>
        </dependency>
        <dependency>
            <groupId>org.controlsfx</groupId>
            <artifactId>controlsfx</artifactId>
            <version>11.1.2</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-swing</artifactId>
            <version>19</version>
        </dependency>
        <dependency>
            <groupId>com.dlsc.formsfx</groupId>
            <artifactId>formsfx-core</artifactId>
            <version>11.5.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.openjfx</groupId>
                    <artifactId>*</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.kordamp.ikonli</groupId>
            <artifactId>ikonli-javafx</artifactId>
            <version>12.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.kordamp.bootstrapfx</groupId>
            <artifactId>bootstrapfx-core</artifactId>
            <version>0.4.0</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10</version>
        </dependency>
        <dependency>
            <groupId>io.github.cdimascio</groupId>
            <artifactId>dotenv-java</artifactId>
            <version>2.3.1</version>
        </dependency>
        <dependency>
            <groupId>com.sun.mail</groupId>
            <artifactId>jakarta.mail</artifactId>
            <version>2.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-web</artifactId>
            <version>19</version>
        </dependency>
    </dependencies>
   ```
# Utilisation
# Mises à jour actuelles
# Crédits