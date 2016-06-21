# Spring Boot PoC

Este proyecto servirá de aprendizaje de todas las características de Spring Boot que vaya aprendiendo.

## Organización del repositorio

Los progresos que vaya realizando los iré clasificando en ramas. Cada rama se corresponderá con cada característica
de Spring Boot que vaya incluyendo.

### Ramas

#### Inicialización del proyecto

La rama llamada br_init contiene el código necesario para empezar a trabajar con una aplicación Spring Boot.

### Ejemplo API REST
 La rama br_rest_crud contiene un ejemplo CRUD de API REST con tests unitarios e integrados.

## Lenguajes

El proyecto está codificado en Java.

## Instalación

### Fork del repositorio

Fork el repositorio [Spring Boot PoC](https://github.com/ismaelcabanas/spring-boot-poc) de GitHub.  Clona el proyecto
a tu máquina local.

### Dependencias

El proyecto necesita que las siguientes dependencias estén instaladas en máquina local:

* Java Development Kit 8 o posterior
* Apache Maven 3 o posterior

## Ejecución

El proyecto utiliza [Maven](http://maven.apache.org/) para la construcción, empaquetado y test.
Los siguientes Maven goals son los que se utilizarán normalmente:

### spring-boot:run

El `spring-boot:run` Maven goal realiza los siguientes pasos:

* compila las clases Java al directorio /target
* copia todos los recursos al directorio /target
* arranca un servidor Tomcat embebido

Para ejecutar el `spring-boot:run` Maven goal, escribe el siguiente comando en una terminal en el directorio base
del proyecto.

```
mvn spring-boot:run
```

Pulsa `ctrl-C` para parar el servidor.

Este goal se usa para desarrollo local.  Utiliza el goal `package` para despliegue.

### test

El `test` Maven goal realia las siguientes tareas:

* compila las clases Java al directorio /target
* copia todos los recursos al directorio /target
* ejecuta los tests unitarios
* produce informes de tests unitarios

Para ejecutar el `test` Maven goal, escribe el siguiente comando en una terminal en el directorio base
del proyecto.

```
mvn clean test
```

### package

El `package` Maven goal realiza las siguientes tareas:

* compila las clases Java al directorio /target
* copia todos los recursos al directorio /target
* ejecuta los tests unitarios
* produce informes de tests unitarios
* genera un fichero JAR ejecutable en el directorio /target

El `package` Maven goal está diseñado para preparar la aplicación para distribuirla a algún servidor.  La aplicación
y todas sus dependencias se empaquetan en un único y ejecutable fichero JAR.

Para ejecutar el `package` goal,  escribe el siguiente comando en una terminal en el directorio base del proyecto.

```
mvn clean package
```

El artefacto de la aplicación se deja en el directorio /target y se le nombra utilizando el `artifactId` y `version`
del fichero pom.xml. Para ejecutar el fichero JAR utiliza el siguiente comando:

```
java -jar example-1.0.0.jar
```

