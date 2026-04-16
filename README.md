# API de Gestión de Franquicias

API REST para administrar franquicias, sucursales y productos. Desarrollada con **Spring Boot 4.0.5**, **Java 21**, **Spring WebFlux** (reactivo) y **R2DBC + MySQL** siguiendo los principios de **Clean Architecture (Hexagonal / Ports & Adapters)**.

---

## Requisitos previos

Asegúrate de tener instalado en tu máquina local:

| Herramienta | Versión mínima | Notas |
|---|---|---|
| **JDK** | 21 | Requerido para compilar y ejecutar |
| **Maven** | No necesario | El proyecto incluye el wrapper (`mvnw`) |
| **MySQL Server** | 8.x | Escuchando en `localhost:3306` (no requerido si usas Docker) |
| **Docker / Docker Compose** | 24.x / v2 | Opcional — para levantar app + BD contenerizadas |
| **Git** | cualquiera | Para clonar el repositorio |

> Puedes verificar tu versión de Java con: `java -version`

---

## 1. Clonar el proyecto

```bash
git clone <URL_DEL_REPOSITORIO>
cd prueba-back-end
```

---

## 2. Crear la base de datos

Conéctate a tu instancia de MySQL local (por consola, MySQL Workbench, DBeaver, etc.) y ejecuta:

```sql
CREATE DATABASE franchise_db;
```

> No es necesario crear las tablas manualmente. La aplicación ejecuta automáticamente el archivo `src/main/resources/schema.sql` al iniciar y crea las tres tablas (`franchise`, `branch`, `product`).

---

## 3. Configurar credenciales

Abre el archivo `src/main/resources/application.properties` y ajusta el usuario y contraseña según tu instalación de MySQL:

```properties
spring.r2dbc.url=r2dbc:mysql://localhost:3306/franchise_db
spring.r2dbc.username=root
spring.r2dbc.password=TU_CONTRASEÑA
```

> ⚠️ Si tu MySQL no tiene contraseña, deja el valor vacío: `spring.r2dbc.password=`

---

## 4. Ejecutar la aplicación

Hay dos formas: ejecutar localmente con Maven, o levantar todo (app + MySQL) con Docker Compose.

### Opción A — Ejecución local con Maven

**Windows**
```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS**
```bash
./mvnw spring-boot:run
```

### Opción B — Docker Compose (recomendado, no requiere MySQL local)

Levanta la base de datos y la aplicación juntas:
```bash
docker compose up --build
```

Para detenerlas:
```bash
docker compose down
```

Para borrar también el volumen de datos de MySQL:
```bash
docker compose down -v
```

> Con Docker Compose no necesitas crear la BD ni modificar `application.properties`. Los valores de conexión se inyectan como variables de entorno (`SPRING_R2DBC_*`) al contenedor de la app, y MySQL arranca con la base `franchise_db` ya creada.

La aplicación levanta en **http://localhost:8080**.

Verás en la consola:
```
Netty started on port 8080
Started PruebaBackEndApplication
```

---

## 5. Probar los endpoints

Usa **Postman**, **Insomnia** o `curl`. Todos los requests deben incluir el header:
```
Content-Type: application/json
```

### Endpoints disponibles

| Método | URL | Descripción |
|---|---|---|
| `POST` | `/api/v1/franchises` | Crear franquicia |
| `PUT` | `/api/v1/franchises/{id}/name` | Actualizar nombre de franquicia |
| `POST` | `/api/v1/franchises/{franchiseId}/branches` | Agregar sucursal a franquicia |
| `GET` | `/api/v1/franchises/{franchiseId}/top-stock-products` | Producto con más stock por sucursal |
| `PUT` | `/api/v1/branches/{id}/name` | Actualizar nombre de sucursal |
| `POST` | `/api/v1/branches/{branchId}/products` | Agregar producto a sucursal |
| `DELETE` | `/api/v1/branches/{branchId}/products/{productId}` | Eliminar producto de sucursal |
| `PATCH` | `/api/v1/products/{productId}/stock` | Actualizar stock de un producto |
| `PUT` | `/api/v1/products/{id}/name` | Actualizar nombre de producto |

### Flujo de prueba recomendado

```bash
# 1. Crear una franquicia
curl -X POST http://localhost:8080/api/v1/franchises \
  -H "Content-Type: application/json" \
  -d '{"name": "Franquicia Norte"}'

# 2. Agregar una sucursal a la franquicia (usa el id retornado en el paso 1)
curl -X POST http://localhost:8080/api/v1/franchises/1/branches \
  -H "Content-Type: application/json" \
  -d '{"name": "Sucursal Centro"}'

# 3. Agregar productos a la sucursal
curl -X POST http://localhost:8080/api/v1/branches/1/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Producto A", "stock": 50}'

curl -X POST http://localhost:8080/api/v1/branches/1/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Producto B", "stock": 80}'

# 4. Consultar el producto con más stock por sucursal
curl http://localhost:8080/api/v1/franchises/1/top-stock-products

# 5. Actualizar stock de un producto
curl -X PATCH http://localhost:8080/api/v1/products/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock": 200}'

# 6. Actualizar nombre de franquicia / sucursal / producto
curl -X PUT http://localhost:8080/api/v1/franchises/1/name \
  -H "Content-Type: application/json" \
  -d '{"name": "Franquicia Actualizada"}'

# 7. Eliminar un producto
curl -X DELETE http://localhost:8080/api/v1/branches/1/products/1
```

---

## Comandos útiles

```bash
# Compilar y empaquetar el JAR
mvnw.cmd clean package -DskipTests

# Ejecutar pruebas unitarias
mvnw.cmd test

# Ejecutar una sola clase de prueba
mvnw.cmd -Dtest=NombreClase test

# Ejecutar el JAR generado
java -jar target/prueba-back-end-0.0.1-SNAPSHOT.jar
```

---

## Pruebas unitarias

El proyecto incluye pruebas unitarias sobre los **9 casos de uso** en `src/test/java/.../application/usecase/`, cubriendo tanto el camino feliz como los escenarios de error (`NotFoundException`, validación de reglas de dominio).

**Stack de testing:**
- **JUnit 5** — framework de pruebas
- **Mockito** — mocks para los puertos de salida (incluido en `spring-boot-starter-test`)
- **Reactor Test (`StepVerifier`)** — verificación de flujos reactivos `Mono` / `Flux`
- **AssertJ** — aserciones fluidas (incluido en `spring-boot-starter-test`)

Los tests son **puros de unidad**: no levantan el contexto de Spring ni requieren MySQL. Se aíslan mediante mocks de los puertos definidos en `domain/port/out/`.

```bash
# Ejecutar toda la suite
mvnw.cmd test

# Ejecutar una sola clase
mvnw.cmd -Dtest=CreateFranchiseUseCaseTest test
```

---

## Docker

El proyecto incluye `Dockerfile` (build multi-stage con Maven → JRE 21) y `docker-compose.yml` que orquesta la aplicación junto con MySQL 8.

### Construir la imagen de la aplicación

```bash
docker build -t franchise-api:latest .
```

### Levantar el stack completo (app + MySQL)

```bash
docker compose up --build    # primera vez o tras cambios
docker compose up            # subsecuentes
docker compose down          # detener
docker compose down -v       # detener y borrar volumen de BD
```

**Variables de entorno definidas en `docker-compose.yml`:**

| Variable | Valor | Descripción |
|---|---|---|
| `SPRING_R2DBC_URL` | `r2dbc:mysql://mysql:3306/franchise_db` | Apunta al contenedor de MySQL |
| `SPRING_R2DBC_USERNAME` | `root` | Usuario de la BD |
| `SPRING_R2DBC_PASSWORD` | `root` | Contraseña de la BD |

El contenedor `mysql` define un `healthcheck` con `mysqladmin ping`, y el contenedor `app` espera (`depends_on.condition: service_healthy`) a que MySQL esté listo antes de arrancar.

---

## Arquitectura

El proyecto sigue **Clean Architecture / Hexagonal** con tres capas:

```
com.darguin.prueba_back_end
├── domain/                     ← Modelos y puertos (sin dependencias de framework)
│   ├── model/                  Franchise, Branch, Product
│   └── port/out/               Interfaces de repositorio
│
├── application/                ← Casos de uso + DTOs (depende solo del dominio)
│   ├── dto/
│   ├── exception/              NotFoundException
│   └── usecase/                9 casos de uso
│
└── infrastructure/             ← Adaptadores, controladores, configuración
    ├── adapter/in/web/         Controladores REST (WebFlux)
    ├── adapter/out/persistence/ Entidades R2DBC, repositorios, adaptadores
    ├── config/                 BeanConfig (wiring de casos de uso)
    └── exception/              GlobalExceptionHandler (ProblemDetail RFC 7807)
```

**Regla de dependencia:** `domain ← application ← infrastructure`.

Los casos de uso son clases Java planas (no `@Service`), instanciados como `@Bean` en `BeanConfig.java` para mantener la capa de aplicación libre de dependencias de Spring.

### Stack

- **Spring WebFlux** — todos los endpoints son reactivos (`Mono<T>` / `Flux<T>`)
- **R2DBC + io.asyncer:r2dbc-mysql** — acceso no bloqueante a MySQL
- **Jakarta Bean Validation** — validación de DTOs de request
- **Spring ProblemDetail (RFC 7807)** — respuestas estandarizadas de error

---

## Estructura de la base de datos

El archivo `schema.sql` crea las siguientes tablas con relaciones:

```
franchise (id, name)
    └── branch (id, name, franchise_id)
            └── product (id, name, stock, branch_id)
```

---

## Solución de problemas comunes

| Error | Causa | Solución |
|---|---|---|
| `Access denied for user 'root'@'localhost'` | Contraseña incorrecta | Actualizar `spring.r2dbc.password` en `application.properties` |
| `Unknown database 'franchise_db'` | Base de datos no creada | Ejecutar `CREATE DATABASE franchise_db;` en MySQL |
| `Connection refused` | MySQL no está corriendo | Iniciar el servicio MySQL |
| Puerto 8080 ocupado | Otra aplicación lo usa | Cambiar con `server.port=8081` en `application.properties` |
| `docker compose up` falla porque el puerto 3306 está ocupado | Ya tienes MySQL local corriendo | Detén MySQL local o cambia el mapeo a `"3307:3306"` en `docker-compose.yml` |

---

## Respuestas de error

La API devuelve errores en formato **RFC 7807 (ProblemDetail)**:

**404 — Entidad no encontrada**
```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "Franquicia no encontrada: 999"
}
```

**400 — Error de validación**
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "El nombre de la franquicia no puede estar vacío"
}
```
