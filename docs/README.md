# **Mutant Detection API**

Este proyecto implementa una API REST que permite analizar secuencias de ADN para determinar si una persona es un mutante o un humano, de acuerdo con las reglas establecidas por Magneto. El proyecto incluye un sistema escalable con pruebas automáticas, una base de datos para almacenar las secuencias verificadas y un endpoint adicional para estadísticas.

## **Descripción General**

### **Nivel 1**:

El programa implementa un algoritmo que analiza una matriz NxN de secuencias de ADN y detecta si el ADN corresponde a un mutante.

### **Nivel 2**:

Se ha creado una API REST con el endpoint `/mutant/`, que permite enviar secuencias de ADN mediante una petición HTTP POST en formato JSON. Si la secuencia corresponde a un mutante, la API devuelve un **HTTP 200-OK**. En caso contrario, devuelve **HTTP 403-Forbidden**.

-   **POST** `/mutant/`

    `{
      "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
    }`


### **Nivel 3**:

El proyecto está respaldado por una base de datos H2 en memoria para almacenar las secuencias verificadas, asegurando que cada secuencia sea almacenada una única vez. También se implementó un servicio `/stats` que devuelve estadísticas sobre los ADN verificados:

-   **GET** `/stats/`
    `{
      "count_mutant_dna": 40,
      "count_human_dna": 100,
      "ratio": 0.4
    }`


## **Instrucciones de Ejecución**

### **Requisitos Previos**

-   Java 17
-   Gradle
-   Docker (opcional para la ejecución en contenedores)

### **Clonar el Repositorio**
`git clone https://github.com/Lucas-Chavez/ProgramacionIII-Parcial-1.git
cd ProgramacionIII-Parcial-1`

### **Ejecución Local**

1.  **Compilar y ejecutar el proyecto:**

    `./gradlew bootRun`

2.  **Acceso a la API:**

    -   Acceder a la API en `http://localhost:8080`
    -   **Consola H2**: `http://localhost:8080/h2-console`
        -   JDBC URL: `jdbc:h2:mem:testdb`
        -   Usuario: `sa`
        -   Contraseña: (dejar vacío)

### **Ejecución en Docker**

1.  **Construir la imagen de Docker:**

    `docker build -t mutant-detection-api .`

2.  **Ejecutar el contenedor:**

    `docker run -p 8080:8080 mutant-detection-api`

3.  **Acceso a la API:**

    -   Acceder a la API en `http://localhost:8080`

### **Despliegue en la Nube (Render)**

La API está desplegada en la plataforma Render y está disponible en la siguiente URL:

-   **URL de la API en producción**: [https://programacioniii-parcial-1.onrender.com](https://programacioniii-parcial-1.onrender.com)

## **Pruebas Automáticas**

El proyecto incluye un conjunto de pruebas unitarias utilizando JUnit y Mockito. Las pruebas se pueden ejecutar con el siguiente comando:

`./gradlew test`

La cobertura del código supera el 80%, garantizando que las principales funcionalidades del sistema están cubiertas.

## **Documentación Completa**

Para obtener la documentación completa del proyecto, incluyendo la descripción del algoritmo, arquitectura, pruebas y otros detalles técnicos, puedes consultar el siguiente PDF:

[**Documentación del Proyecto**](https://github.com/Lucas-Chavez/ProgramacionIII-Parcial-1/raw/main/docs/documentacion-proyecto.pdf)

## **Formato PDF de la Documentación**

El proyecto también incluye un archivo en formato PDF con la descripción completa del sistema, conforme a los requisitos del Nivel 3.

## **Autor**

Este proyecto fue desarrollado por **Lucas Chávez**.

----------