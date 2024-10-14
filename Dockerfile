# Usa la imagen de Java 17 como base
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo de construcción de Gradle
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./

# Copia el código fuente de la aplicación
COPY src ./src

# Da permisos de ejecución al script de Gradle
RUN chmod +x gradlew

# Construye el proyecto
RUN ./gradlew build -x test

# Exponer el puerto en el que la aplicación estará escuchando
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "build/libs/mutant-detection-api-0.0.1-SNAPSHOT.jar"]


