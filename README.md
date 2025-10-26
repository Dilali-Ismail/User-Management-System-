# README - Configuration Manuelle d'une Application Spring

## Introduction
Ce projet a été réalisé dans un but pédagogique afin de maîtriser les concepts fondamentaux du framework Spring. Contrairement à une approche moderne avec Spring Boot qui repose sur l'auto-configuration, ce projet a été entièrement configuré manuellement en Java.

Ce document détaille, étape par étape, la configuration de chaque module principal :
- Spring Core pour l'inversion de contrôle
- Spring Data JPA pour la persistance des données
- Spring MVC pour la couche web

---

## Technologies
- **Langage** : Java 17
- **Framework** : Spring 6 (Core, MVC, Data JPA)
- **Persistance** : Hibernate 6, PostgreSQL
- **Serveur d'application** : Apache Tomcat 10
- **Conteneurisation** : Docker, Docker Compose

---

## Lancement du Projet
L'environnement est entièrement conteneurisé. La seule dépendance est Docker.

1. Clonez le projet.  
2. Ouvrez un terminal à la racine et exécutez :

```bash
docker-compose up --build -d
```

L'application sera accessible sur **http://localhost:8080**.

---

# Description Détaillée des Étapes de Configuration

Cette section explique comment les trois piliers de l'application Spring ont été mis en place manuellement.

---

## 1. Configuration de Spring Core (IoC, Beans, Injection)

Le cœur de Spring est son conteneur d'Inversion de Contrôle (IoC), qui gère le cycle de vie des objets (appelés *beans*) et leurs dépendances. La configuration a été réalisée exclusivement en Java.

**Fichiers clés** :
- `PersistenceConfig.java`
- `WebConfig.java`

### Étapes de configuration

#### Définition des Classes de Configuration (@Configuration)
Les classes Java comme `PersistenceConfig` et `WebConfig` ont été annotées avec `@Configuration`. Cette annotation indique à Spring que ces classes contiennent des définitions de beans.

#### Déclaration Explicite des Beans (@Bean)
Chaque méthode annotée `@Bean` représente un bean créé et géré par Spring.

Exemple :
- `dataSource()` dans `PersistenceConfig.java` crée un bean DataSource
- Spring intercepte la méthode et enregistre son résultat dans le conteneur IoC

#### Détection Automatique des Composants (@ComponentScan)
L’annotation `@ComponentScan` indique à Spring où trouver les classes annotées `@Service`, `@Repository`, `@Controller`, etc.

Exemples :
- Dans `PersistenceConfig` : scan du package `org.userManagement.service`
- Dans `WebConfig` : scan du package `org.userManagement.controller`

#### Injection de Dépendances (@Autowired)
L’annotation `@Autowired` permet à Spring de fournir automatiquement les dépendances nécessaires.

Exemple :
- Le constructeur de `UserServiceImpl` demande un `UserRepository`
- Spring injecte automatiquement le bean correspondant

---

## 2. Configuration de Spring Data JPA (Persistance, Transactions)

La couche JPA a été configurée avec Hibernate comme implémentation, et gérée entièrement par Spring.

**Fichier clé** : `PersistenceConfig.java`

### Étapes principales

#### Source de Données (DataSource)
Un bean `dataSource` configuré à partir des valeurs définies dans `application.properties`.  
Injection via `@Value`.

#### EntityManagerFactory
Déclaré via `LocalContainerEntityManagerFactoryBean` avec :
- Le `dataSource`
- Le scan des entités dans *org.userManagement.model*
- `HibernateJpaVendorAdapter` comme fournisseur JPA

#### Gestionnaire de Transactions
Un `JpaTransactionManager` est configuré pour gérer `commit` et `rollback`.

#### Activation de Spring Data JPA
`@EnableJpaRepositories` sur `PersistenceConfig` :
- Scan du package `org.userManagement.repository`
- Création automatique des implémentations de `JpaRepository`

#### Gestion Déclarative des Transactions
- `@EnableTransactionManagement` active les transactions Spring
- `@Transactional` sur `UserServiceImpl`

---

## 3. Configuration de Spring MVC (Contrôleurs, DispatcherServlet)

La configuration du framework web a été faite entièrement sans fichier `web.xml`.

**Fichiers clés** :
- `WebAppInitializer.java`
- `WebConfig.java`

### Étapes principales

#### Initialisation du Contexte Spring (WebAppInitializer)
La classe implémente `WebApplicationInitializer`.

Elle crée :

a. **Contexte Racine**
- via `AnnotationConfigWebApplicationContext`
- Enregistre `PersistenceConfig.class`

b. **Contexte Web**
- Enregistre `WebConfig.class`

c. **DispatcherServlet**
- Créé manuellement
- Enregistré auprès de Tomcat
- Mappé sur **/** pour intercepter toutes les requêtes

#### Configuration MVC (WebConfig)
- `@EnableWebMvc` pour activer Spring MVC
- `@ComponentScan("org.userManagement.controller")` pour détecter `UserController`

#### Contrôleur Web (@RestController)
`UserController` utilise :
- `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.
- Définition des endpoints REST
- Réponses souvent au format **JSON**

---

Fin du README
