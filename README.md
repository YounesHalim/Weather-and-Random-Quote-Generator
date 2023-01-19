# Application de météo et générateur de citations aléatoires avec une image de fond assortie

Cette application vous permet de connaître la météo actuelle d'un lieu spécifique, de générer une citation aléatoire,
d'afficher une image de fond correspondante et de partager les images par e-mail.

L'application est construite en utilisant :
- Multithreading pour améliorer la performance.
- Java et JavaFX et utilise la bibliothèque Jakarta Mail pour la fonctionnalité share.
- Lombok pour la génération de code.
- Google Gson pour l'analyse de données JSON.
- Données météorologiques fournies par [**OpenWeatherMap**](https://openweathermap.org)
- Données images de fond fournies par [**Unsplash**](https://unsplash.com)
- Données citations générées à l'aide de l'API [**ZenQuotes**](https://zenquotes.io)


Ce projet a été créé à l'aide de le SDK Java 17 et utilise le multithreading pour améliorer les performances.

# Table des matières
- [Vue d'ensemble](#Vue-d'ensemble)
- [Installation](#Installation)
- [Utilisation](#Utilisation)
- [Mises à jour actuelles](#Mises-à-jour-actuelles)
- [Crédits](#Crédits)


# Vue d'ensemble
Cette application permet à l'utilisateur de consulter la météo de n'importe quelle ville du monde. En plus de cela, l'utilisateur peut également accéder à un générateur de citations qui génère une citation aléatoire accompagnée d'une image aléatoire générée à partir de deux API, en donnant une image en sortie. L'image peut aussi être partagée par e-mail avec plusieurs personnes. 
De plus, l'utilisateur peut maintenant ajouter des filtres à l'image générée. Dans les mises à jour futures, l'utilisateur pourra sélectionner la résolution de l'image et ajouter des filtres et la partager sur les réseaux sociaux.
# Installation
Pour utiliser le Générateur de météo et de citations, suivez ces étapes :

1. Clonez le répertoire :
    ```
    git clone https://github.com/youneshalim/Weather-app
    ```
2. Obtenez des clés API pour l'API OpenWeatherMap et l'API Unsplash.
3. Allez dans le fichier appelé .env dans le répertoire racine du projet et ajoutez les lignes suivantes, en remplaçant YOUR-API-KEY-HERE par vos clés API réelles :
    ```
   API_OPEN_WEATHER = [YOUR-API-KEY-HERE]
   APIKEY_UNSPLASH = [YOUR-API-KEY-HERE]
   EMAIL = your_email@gmail.com
   PASSWORD = your_email_application_password
   ```
4. Si vous utilisez Maven pour gérer les dépendances, ajoutez les dépendances suivantes à votre fichier pom.xml :
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


__Note__: <mark> Pour utiliser la fonctionnalité de partage par e-mail, vous devez ajouter votre email et votre mot de passe d'application dans le fichier .env, cela est mentionné dans la section d'installation. Le serveur SMTP de Jakarta a été programmé pour utiliser Gmail. Si vous souhaitez envoyer des e-mails via un autre fournisseur, vous devez mettre à jour cette section en conséquence.


Vous devez également vous rendre dans l'interface EmailProperties et mettre à jour les propriétés par défaut gmailProperties() { Properties props = new Properties(); props.put("mail.smtp.auth", "true"); props.put("mail.smtp.starttls.enable", "true"); props.put("mail.smtp.host", "smtp.gmail.com"); props.put("mail.smtp.port", "587"); return props;} ou utilisez la méthode setProperties pour ajouter une nouvelle propriété.

Mettre à jour directement la methode par défaut :
```java
public interface EmailProperties {
   default Properties gmailProperties() {
       // Update the default configuration with the desired one.
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.example.com");
      props.put("mail.smtp.port", "587");
      return props;
   }
}
```
Sinon, allez dans la classe EmailSenderService, et vous pouvez faire par exemple : 
```java
public class EmailSenderService implements EmailProperties {
    
    @Override
    public Properties setProperties() {
        Properties props = new Properties();
        // Update this section with the required configuration
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");
        return props;
    }
    // Then update this method. Example:
    @SneakyThrows
    public Message defaultSender(EmailProps email) {
       Properties myNewProps = setProperties(); 
       Message message = new MimeMessage(setSession(myNewProps)); // Set a new session with the new properties.  
       message.setFrom(new InternetAddress(Objects.requireNonNullElseGet(email, EmailProps::new).getEmail()));
       return message;
    }
}

```

# Fonctionnalités
L'application comporte maintenant une fonctionnalité permettant d'ajouter des filtres à l'image générée. Les utilisateurs peuvent sélectionner parmi une variété de filtres, tels que :
- Grayscale
- Inverted


Pour utiliser cette fonctionnalité, cliquez sur le bouton "Filters" dans l'interface Quotes, sélectionnez le filtre souhaité.

# Mises à jour actuelles
- Résolution d'image améliorée
- Plus de variations d'image
- Possibilité pour l'utilisateur de sélectionner leur propre image
- Ajout d'une option de partage direct (Direct Share) (partage direct sur les réseaux sociaux, par exemple)

# Crédits

- Données météorologiques fournies par [**OpenWeatherMap**](https://openweathermap.org)
- Images de fond fournies par [**Unsplash**](https://unsplash.com)
- Citations générées à l'aide de l'API [**ZenQuotes**](https://zenquotes.io)
- Fonctionnalité e-mail fournie par la bibliothèque [**Jakarta Mail**](https://commons.apache.org/proper/commons-email/)
- Analyse de données JSON fournie par [**Google Gson**](https://github.com/google/gson)
- Génération de code fournie par [**Project Lombok**](https://projectlombok.org)
