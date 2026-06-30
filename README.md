# TimeTrack - Sistema de Gestión de Asistencia

TimeTrack es un ecosistema moderno basado en una arquitectura de microservicios, diseñado para la gestión integral de recursos humanos y el control preciso de asistencia de los empleados. 

Este repositorio documenta la arquitectura, las convenciones de desarrollo colaborativo y la infraestructura DevOps del proyecto, construido con **Java y Spring Boot**.

---

## 1. Arquitectura del Proyecto y Ecosistema

### 1.1 Estructura CSR (Controller-Service-Repository)
Cada microservicio mantiene una separación de responsabilidades estricta:
* `controller/`: Capa de entrada (API REST) que maneja peticiones HTTP y respuestas enriquecidas.
* `service/`: Capa de lógica de negocio y validaciones.
* `repository/`: Capa de persistencia para operaciones CRUD con la base de datos.
* `model/`: Entidades de datos mapeadas con JPA.

### 1.2 Ecosistema de Microservicios
El sistema se compone de 11 microservicios independientes:
1. **`gateway`**: Enrutador principal y punto de entrada único.
2. **`auth`**: Gestión de autenticación, login y tokens.
3. **`manag`**: Administración central de usuarios, empresas y roles.
4. **`sucursales`**: Gestión de sedes físicas y sus ubicaciones.
5. **`location`**: Validación matemática de geocercas y perímetros.
6. **`attendance`**: Motor principal de registro de asistencias.
7. **`biometric`**: Verificación de identidad facial y dactilar.
8. **`schedule`**: Asignación de turnos y horarios laborales.
9. **`metrics`**: Generación de reportes y consolidado de inasistencias.
10. **`notif`**: Envío de comprobantes por correo electrónico.
11. **`maintenance`**: Bitácora de auditoría de eventos del sistema.

### 1.3 Comunicación Interna (Spring Cloud OpenFeign)
Los microservicios se comunican de forma síncrona y transparente. El flujo principal recae en `attendance`, que actúa como orquestador: antes de registrar un marcaje, consulta a `manag` para validar al empleado y a `location` para verificar mediante la fórmula de Haversine que las coordenadas GPS estén dentro de la sucursal asignada.

---

## 2. Estandarización y Calidad del Código

Para elevar el proyecto a estándares profesionales, se implementaron de forma transversal:
* **Flyway:** Automatización y versionado estricto de las migraciones de bases de datos.
* **Logback:** Trazabilidad completa y registro estructurado de eventos/errores en consola.
* **Spring HATEOAS (Assemblers):** Respuestas de API enriquecidas con hipervínculos (`_links`), creando una arquitectura autodescubrible para facilitar su consumo en el frontend.

---

## 3. Estrategia de Pruebas (Testing)

Se implementó una rigurosa cobertura de pruebas utilizando **JUnit 5, MockMvc y Mockito**.
* **Slice Testing (`@WebMvcTest`):** Se aislaron las capas de los controladores de todos los microservicios, permitiendo probar las APIs sin levantar bases de datos.
* **Mocks (`@MockBean`):** Se simularon escenarios de éxito (HTTP 200/201) y error (HTTP 400/401/404) inyectando datos ficticios, validando con exactitud los formatos JSON y las respuestas de negocio.

---

## 4. Convenciones de Desarrollo Colaborativo

### 4.1 Estrategia de Ramificación
Adoptamos un modelo de trabajo flexible y dinámico (*Feature Branching*). En lugar de usar una estructura rígida, fuimos creando ramas a medida que surgían nuevas tareas, funcionalidades o correcciones. Esto nos permitió trabajar en paralelo manteniendo el repositorio organizado bajo el siguiente formato:

| Tipo de Rama | Prefijo | Ejemplo |
| :--- | :--- | :--- |
| **Código Estable** | `main` | `main` |
| **Integración** | `develop` | `develop` |
| **Nuevas Funcionalidades** | `feature/` | `feature/api-endpoints` |
| **Correcciones** | `hotfix/` o `fix/` | `hotfix/connection-timeout` |

### 4.2 Estándar de Commits (Semantic Commits)
* **`feat:`** Una nueva característica (Ej: `feat: implement login controller`).
* **`fix:`** Corrección de un error o bug (Ej: `fix: correct timezone issue`).
* **`docs:`** Cambios en la documentación.

### 4.3 Flujo de Revisión y Merge
1. **Pull Requests (PR):** Prohibido el push directo a `main` o `develop`.
2. **Revisión:** Aprobación obligatoria (Approve) por otro miembro del equipo.
3. **Merge:** Ejecutado solo si las pruebas automáticas de CI pasan con éxito. Se realizaron diversos Merges con sus respectivos Pull Requests, integrando features a `develop`, hotfixes a `main` y un merge final consolidado hacia `main`.

---

## 5. Integración Continua y Seguridad (CI/CD)

El pipeline automatizado en GitHub Actions (`.github/workflows/ci.yml`) se ejecuta en cada PR hacia `main` y `develop`:
* **Validación:** Compilación en entorno aislado con Java 25 (`mvnw clean compile`).
* **Testing:** Ejecución de pruebas unitarias (`mvn clean test`).
* **Cobertura (JaCoCo):** Generación de reportes para medir el % de código evaluado.
* **Seguridad (Snyk):** Escaneo de dependencias en busca de vulnerabilidades antes del empaquetado (requiere el secreto `SNYK_TOKEN` en GitHub).
* **Construcción Automática:** Creación de la imagen Docker final mediante Docker Buildx.

---

## 6. Contenerización y Despliegue (Docker)

### 6.1 Multi-stage Build (Dockerfile)
* **Capa Builder:** Usa una imagen pesada con JDK y Maven para compilar y empaquetar el código.
* **Capa Runtime:** Extrae solo el artefacto final (`.jar`) a una imagen ligera basada en Alpine Linux (JRE), reduciendo drásticamente el peso y la superficie de ataque.

### 6.2 Despliegue Local (Docker Compose)
Se utiliza `docker-compose.yml` para levantar la arquitectura completa en una red virtual, garantizando su mantenibilidad, estabilidad (reinicio automático en caso de caída) y escalabilidad.
* **Construir imagen:** `docker build -t timetrack-app:latest .`
* **Iniciar entorno:** `docker compose up -d`
* **Detener entorno:** `docker compose down`

---

## 7. Documentación de API (Postman Collection)

A continuación se detalla la estructura para consumir los microservicios. (*Nota: Para peticiones POST/PUT, los cuerpos deben enviarse en formato JSON, excepto en Biometría que utiliza Query Params*).

### 7.1 Attendance (Asistencia)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/attendance/clock-in` | Registrar entrada |
| **POST** | `/api/attendance/clock-out` | Registrar salida |
| **POST** | `/api/attendance/break-start` | Iniciar descanso |
| **POST** | `/api/attendance/break-end` | Finalizar descanso |
| **GET** | `/api/attendance` | Obtener todos los registros |
| **GET** | `/api/attendance/{id}` | Obtener registro por ID |
| **GET** | `/api/attendance/history/{userId}` | Historial de un empleado |
| **PUT** | `/api/attendance/{id}` | Actualizar un registro |
| **DELETE** | `/api/attendance/{id}` | Eliminar un registro |

### 7.2 Auth (Autenticación)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/auth/users` | Registrar nuevo usuario |
| **POST** | `/api/auth/login` | Login (Devuelve el Token) |
| **POST** | `/api/auth/logout` | Logout (Requiere header `Authorization`) |
| **POST** | `/api/auth/validate-token` | Validar token actual |
| **GET** | `/api/auth/users` | Listar todos los usuarios |
| **GET** | `/api/auth/users/{id}` | Obtener usuario por ID |

### 7.3 Biometric (Biometría)
| Método | Endpoint | Configuración |
| :--- | :--- | :--- |
| **POST** | `/api/biometrics/register-face` | Params: `usuarioId`, `vectorFacial` |
| **POST** | `/api/biometrics/register-fingerprint`| Params: `usuarioId`, `huellaDactilar` |
| **POST** | `/api/biometrics/verify-face` | Params: `usuarioId`, `vectorFacial` |
| **POST** | `/api/biometrics/verify-fingerprint` | Params: `usuarioId`, `huellaDactilar` |
| **GET** | `/api/biometrics` | Sin parámetros |
| **GET** | `/api/biometrics/{id}` | Path variable `id` |
| **GET** | `/api/biometrics/user/{usuarioId}` | Path variable `usuarioId` |
| **DELETE** | `/api/biometrics/{id}` | Path variable `id` |

### 7.4 Location (Geocercas)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/location/geofence` | Crear/Actualizar geocerca |
| **POST** | `/api/location/validate` | Validar si un usuario está en rango |
| **GET** | `/api/location/geofence` | Listar todas las geocercas |
| **GET** | `/api/location/geofence/{id}` | Obtener geocerca por ID |
| **PUT** | `/api/location/geofence/{id}` | Actualizar geocerca existente |
| **DELETE** | `/api/location/geofence/{id}` | Eliminar geocerca |

### 7.5 Maintenance (Auditoría de Logs)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/audit/event` | Registrar un nuevo evento de log |
| **GET** | `/api/audit/logs` | Obtener todos los logs registrados |
| **GET** | `/api/audit/logs/{id}` | Obtener un log específico por ID |
| **PUT** | `/api/audit/logs/{id}` | Actualizar un registro de log |
| **DELETE** | `/api/audit/logs/{id}` | Eliminar un registro de log |

### 7.6 Manag (Gestión de Usuarios y Roles)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/users` | Registrar nuevo usuario central |
| **GET** | `/api/users` | Listar todos los usuarios |
| **GET** | `/api/users/{userId}` | Buscar usuario por ID |
| **GET** | `/api/users/role/{roleName}` | Buscar usuarios por nombre de rol |
| **PUT** | `/api/users/{userId}` | Actualizar datos de usuario |
| **DELETE** | `/api/users/{userId}` | Eliminar usuario |

### 7.7 Metrics (Métricas y Reportes)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/reports/monthly-summary/{userId}`| Resumen mensual de asistencias |
| **GET** | `/api/reports/daily-absences` | Lista de ausencias registradas hoy |
| **POST** | `/api/reports/export` | Generar un nuevo reporte (ExportRequest) |
| **GET** | `/api/reports/export` | Listar historial de exportaciones |
| **GET** | `/api/reports/export/{id}` | Buscar registro de exportación por ID |
| **PUT** | `/api/reports/export/{id}` | Actualizar registro de exportación |
| **DELETE** | `/api/reports/export/{id}` | Eliminar registro de exportación |

### 7.8 Notif (Notificaciones)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/notifications/send` | Envía comprobante al correo y guarda log |
| **GET** | `/api/notifications` | Obtiene el historial general |
| **GET** | `/api/notifications/{id}` | Obtiene una notificación específica |
| **PUT** | `/api/notifications/{id}` | Actualiza un log de notificación |
| **DELETE** | `/api/notifications/{id}` | Elimina un registro de log |

### 7.9 Schedule (Horarios y Turnos)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/schedules` | Asignar un nuevo turno/horario |
| **GET** | `/api/schedules` | Listar todos los horarios |
| **GET** | `/api/schedules/{scheduleId}` | Obtener un horario por ID |
| **GET** | `/api/schedules/user/{userId}` | Listar turnos asignados a un empleado |
| **PUT** | `/api/schedules/{scheduleId}` | Actualizar detalles del horario |
| **DELETE** | `/api/schedules/{scheduleId}` | Eliminar un horario asignado |

### 7.10 Sucursales (Gestión de Sedes)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/api/branches` | Registrar una nueva sucursal |
| **GET** | `/api/branches` | Listar todas las sucursales |
| **GET** | `/api/branches/{branchId}` | Obtener sucursal por ID |
| **PUT** | `/api/branches/{branchId}` | Actualizar datos de la sucursal |
| **DELETE** | `/api/branches/{branchId}` | Eliminar una sucursal |

---
*Documentación generada para el equipo de desarrollo TimeTrack.*
