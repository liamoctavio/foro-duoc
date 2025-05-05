# Backend - Sistema de Foros

Este proyecto corresponde al backend del sistema de foros desarrollado en Spring Boot. Expone APIs REST para gestionar usuarios, temas, comentarios y funcionalidades de administración.

## 🛠️ Tecnologías utilizadas

- Java 21
- Spring Boot
- Maven Wrapper
- Oracle Cloud (Base de datos)
- Docker

---

## 🚀 Instrucciones para ejecutar

### 1. Compilar el proyecto

Desde la raíz del proyecto backend, ejecuta:

```bash
./mvnw clean package -DskipTests
cp target/demo1-0.0.1-SNAPSHOT.jar backend.jar
docker build -t mi-backend .
docker stop mi-backend && docker rm mi-backend
docker run -d -p 8080:8080 --name mi-backend mi-backend
```
