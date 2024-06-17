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
git clone <URL_DEL_REPOSITORIO>
cd seguroapp

### Compilar el Proyecto
mvn clean install

### Ejecutar las pruebas
mvn test