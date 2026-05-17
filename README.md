# HealthSync — Backend

API REST desarrollada con **Spring Boot** para la gestión integral de salud personal. Forma parte del Proyecto Intermodular del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multimedia (DAM).

## Tecnologías

- Java 17
- Spring Boot 3.2.4
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL (Supabase)
- Maven 3.9

## Endpoints principales

- POST /api/v1/auth/register — Registrar usuario
- POST /api/v1/auth/login — Iniciar sesión
- POST /api/v1/parametros-vitales/registro — Registrar medición
- GET /api/v1/parametros-vitales/historial — Historial paginado
- GET /api/v1/citas — Listar citas médicas
- POST /api/v1/citas — Crear cita
- GET /api/v1/alertas — Listar alertas
- POST /api/v1/alertas — Crear alerta

## Ejecución

1. Clona el repositorio
2. Configura application.properties con tu base de datos Supabase
3. Ejecuta: mvn spring-boot:run
4. Swagger UI: http://localhost:8080/api/v1/swagger-ui.html

## Autor

**David Rodríguez Palomeque**
Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multimedia
CEFP-UCJC · Curso 2025-2026
