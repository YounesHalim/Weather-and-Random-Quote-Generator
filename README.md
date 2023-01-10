# Application de météo et générateur d'images inspirantes aléatoire

Cette application vous permet de connaître la météo actuelle d'un lieu spécifique,de générer une citation aléatoire,
d'afficher une image de fond correspondante et de partager les images par e-mail.

L'application est construite en utilisant:
- Java et JavaFX et utilise la bibliothèque Jakarta Mail pour la fonctionnalité share.
- Lombok pour la génération de code.
- Google Gson pour l'analyse de données JSON.

Ce projet a été créé à l'aide de l'SDK Java 17 et utilise le multithreading pour améliorer les performances.

# Table des matières
- [Vue d'ensemble](#Vue-d'ensemble)
- [Installation](#Installation)
- [Utilisation](#Utilisation)
- [Mises à jour actuelles](#Mises-à-jour-actuelles)
- [Crédits](#Crédits)


# Vue d'ensemble
Le Générateur de météo et de citations est une application simple et facile à utiliser qui vous permet de connaître la météo actuelle d'un lieu spécifique, de générer une citation aléatoire, d'afficher une image de fond correspondante et de partager les images par e-mail. Il utilise l'API OpenWeatherMap pour les données météorologiques, l'API Unsplash pour les images de fond, l'API ZenQuotes pour la génération de citations, la bibliothèque Jakarta Mail pour la fonctionnalité e-mail, Lombok pour la génération de code et Google Gson pour l'analyse de données JSON.
# Installation
Pour utiliser le Générateur de météo et de citations, suivez ces étapes:

1. Clonez le répertoire:
    ```
    git clone https://github.com/youneshalim/Weather-app
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
Pour connaître la météo d'un lieu spécifique, entrez la ville et le pays dans la barre de recherche et appuyez sur entrée. L'application affichera la météo actuelle pour l'emplacement sélectionné.

Pour générer une nouvelle citation, cliquez sur l'onglet "Quotes" puis sur le bouton "Generate". 
L'application affichera une citation aléatoire et une image de fond correspondante. Vous pouvez partager l'image actuelle par e-mail en cliquant sur le bouton "Share" ou enregistrer l'image sur votre appareil en cliquant sur le bouton "Save".


# Mises à jour actuelles
- Filtres d'image et résolution d'image améliorée
- Plus de variations d'image
- Possibilité pour l'utilisateur de sélectionner leur propre image

# Crédits

- Données météorologiques fournies par [**OpenWeatherMap**](https://openweathermap.org)
- Images de fond fournies par [**Unsplash**](https://unsplash.com)
- Citations générées à l'aide de l'API [**ZenQuotes**](https://zenquotes.io)
- Fonctionnalité e-mail fournie par la bibliothèque [**Jakarta Mail**](https://commons.apache.org/proper/commons-email/)
- Analyse de données JSON fournie par [**Google Gson**](https://github.com/google/gson)
- Génération de code fournie par [**Project Lombok**](https://projectlombok.org)
