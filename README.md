# Proyecto BICEVIDA

## Descripción
Este proyecto contiene una serie de métodos para manejar información relacionada con clientes, cuentas y seguros en una aplicación de seguros. El objetivo es implementar métodos que realicen diversas operaciones sobre los datos y asegurar que funcionen correctamente mediante pruebas unitarias.

## Estructura del Proyecto
seguroapp/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/
│ │ │ └── marcos/
│ │ │ └── bicevida/
│ │ │ ├── app/
│ │ │ │ ├── Main.java
│ │ │ │ └── models/
│ │ │ │ ├── Cliente.java
│ │ │ │ ├── Cuenta.java
│ │ │ │ └── Seguro.java
│ ├── test/
│ │ ├── java/
│ │ │ └── com/
│ │ │ └── marcos/
│ │ │ └── bicevida/
│ │ │ ├── app/
│ │ │ │ └── MainTest.java
├── target/
├── pom.xml
├── README.md

## Requisitos
- Java 8 o superior
- Maven 3.6.3 o superior

## Instalación y Ejecución

### Clonar el Repositorio
git clone https://github.com/marcoszapatao/seguroapp.git
cd seguroapp

### Compilar el Proyecto
mvn clean compile

### Ejecutar las pruebas
mvn test

### Empaquetar el proyecto
mvn package

### Ejecutar el JAR
java -jar target/seguroapp-1.0-SNAPSHOT.jar