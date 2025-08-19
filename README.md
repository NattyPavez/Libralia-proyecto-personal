## 📚 Libralia - Proyecto Personal de Desarrollo Backend

Libralia es una biblioteca virtual social que he desarrollado como parte de mi formación como Backend Developer. El proyecto muestra mi evolución técnica desde Java puro hasta Spring Boot, JPA y consumo de APIs externas.

Actualmente incluye endpoints REST organizados profesionalmente con controladores, servicios y DTOs, permitiendo construir perfiles, buscar libros, gestionar bibliotecas y editar reseñas. Cada avance refleja mi proceso de aprendizaje en desarrollo backend.

## 🚀 Evolución del Proyecto

**Libralia** ha ido evolucionando paso a paso en paralelo a mi formación en backend, acompañando mi ruta de aprendizaje dentro del programa **Oracle Next Education (Alura Latam)**. Ha sido una forma concreta de aplicar cada contenido aprendido en un proyecto real, desde los primeros pasos en Java hasta el desarrollo de una API profesional con Spring Boot.

### 1️⃣ Inicio en Java puro (POO)
- Creación inicial de clases `Usuario` y `LibroPersonal`.
- Manejo de listas, colecciones, condicionales y lógica de navegación.
- Simulación de búsquedas sin conexión real a datos.

### 2️⃣ Integración de APIs externas (Google Books API)
- Consumo de APIs REST usando `HttpClient`.
- Conversión de datos JSON a DTOs mediante `Jackson`.
- Construcción dinámica de libros a partir de los resultados de búsqueda.
- Tratamiento de campos opcionales, listas de autores y fechas de publicación.

### 3️⃣ Transición a Spring Boot y Arquitectura Profesional
- Migración del proyecto completo a Spring Boot.
- Uso de `@Component` y `@Autowired` para gestión de dependencias.
- Separación de capas: controlador, modelo, servicio, DTOs, repositorios.

### 4️⃣ Persistencia de Datos con PostgreSQL y Spring Data JPA
- Modelado de entidades con `@Entity` y relaciones bidireccionales `@OneToMany` / `@ManyToOne`.
- Configuración de la base de datos vía `application.properties`.
- Creación automática de tablas mediante Hibernate.
- Repositorios personalizados con `JpaRepository` y consultas con `@Query`.

### 5️⃣ Implementación de funcionalidades avanzadas
- Agregado de estados de lectura (`LEIDO`, `POR_LEER`, `LEYENDO`) para clasificar los libros.
- Calificación y reseñas personalizadas para libros leídos.
- Consulta de reseñas y calificaciones dentro del perfil del usuario.
- Cálculo y visualización del **Top 5 libros mejor evaluados**.

### 6️⃣ Gestión de flujos de usuario realistas
- Simulación de experiencia de usuario desde consola con menús dinámicos.
- Validación de entradas y verificación de duplicados al agregar libros.
- Modularización progresiva de métodos para mantener el código limpio y mantenible.

---

### 7️⃣ 🆕 Últimos avances: API REST profesional con Spring Boot 3
- Desarrollo completo de un **CRUD** real para la biblioteca del usuario.
- Endpoints `GET`, `POST`, `PUT` y `DELETE` con validaciones `@Valid`, `@NotBlank`, `@Size`, etc.
- Uso de `@RestControllerAdvice` para manejo global de errores.
- DTOs separados para entrada y salida de datos (resumen, edición, eliminación).
- **Migraciones Flyway** para versionado estructurado de la base de datos.
- Aplicación de **paginación y ordenamiento** en búsquedas y mantenimiento de libros.
- Edición de reseñas y calificaciones directamente desde el endpoint por título.
- Endpoint para **eliminar libros** de cualquier biblioteca del usuario.
- Reorganización del `UsuarioController` en rutas limpias y semánticas siguiendo buenas prácticas RESTful.

---

  ### 🔐 Seguridad y Autenticación
Ahora Libralia cuenta con **Spring Security + JWT** para proteger el acceso a sus funcionalidades:  
- Registro de usuarios.  
- Login y generación de token JWT.  
- Validación automática del token en cada request.  
- Acceso restringido a endpoints según autenticación.

---
### 🧭 Documentación, pruebas y build para distribución (Nuevo)
Esta sección cubre las últimas implementaciones relacionadas con la documentación, las pruebas y el empaquetado del proyecto para su distribución.

📝 **Documentación con Swagger**
Se ha integrado la documentación viva con **Swagger** gracias a la librería **springdoc-openapi** (v2.8.9). Ahora puedes:

- Acceder a la interfaz de usuario de **Swagger UI** en: `http://localhost:8081/swagger-ui/index.html`
- Obtener el archivo **Spec JSON** de la API en: `http://localhost:8081/v3/api-docs`
- Autorizar tus peticiones: en Swagger UI, haz clic en **Authorize** y pega tu **JWT** con el prefijo `Bearer` (ej.: `Bearer eyJhbGciOi...`).

  **Nota de seguridad**: Los endpoints de Swagger ( `/v3/api-docs/**`, `/swagger-ui.html`, `/swagger-ui/**`) han sido liberados de la seguridad para permitir el acceso público.
---
### 📦 Cómo compilar y ejecutar el proyecto

1. **Requisitos previos**

Asegúrate de tener instalados:
- **Git**: Para clonar el repositorio.
- **Java Development Kit (JDK) 17 o 21**: Para ejecutar la aplicación.
- **Apache Maven**: Para compilar el proyecto (el `mvnw` wrapper lo descarga por ti si no lo tienes).

2. **Clonar el repositorio**
- Abre tu terminal y clona el proyecto desde GitHub:
`git clone https://github.com/NattyPavez/Libralia-proyecto-personal.git`

- Luego, navega al directorio del proyecto:
`cd Libralia-proyecto-personal`

3. **Compilar el proyecto**
- Ahora, compila la aplicación en un archivo ejecutable JAR. Este comando también saltará las pruebas para una compilación más rápida.
`./mvnw -DskipTests clean package`

- Este proceso creará el archivo `libralia-0.0.1-SNAPSHOT.jar` dentro de la carpeta target/.

4. **Ejecutar la aplicación**
- Puedes ejecutar el proyecto con dos perfiles de configuración: `dev` (H2 en memoria) o `prod` (PostgreSQL).

    **Opción A:** Ejecutar con perfil `dev` (¡Recomendado para pruebas rápidas!)
  
    - Este perfil usa una base de datos H2 en memoria, por lo que no necesitas configurar nada.
            `java -jar target/libralia-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev`

    **Opción B:** Ejecutar con perfil `prod` (PostgreSQL)

    - Para usar este perfil, asegúrate de tener una base de datos PostgreSQL configurada y de exportar las variables de     entorno con tus credenciales.

      - **PowerShell (Windows)**

      `$env:LIBRALIA_DB_HOST="localhost"
$env:LIBRALIA_DB_NAME="libralia"
$env:LIBRALIA_DB_USER="postgres"
$env:LIBRALIA_DB_PASSWORD="<tu_password_real>"
$env:LIBRALIA_DB_PORT="5432"
java -jar target\libralia-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`

      - **Bash (Linux/macOS)**

      `export LIBRALIA_DB_HOST=localhost
export LIBRALIA_DB_NAME=libralia
export LIBRALIA_DB_USER=postgres
export LIBRALIA_DB_PASSWORD=<tu_password_real>
export LIBRALIA_DB_PORT=5432
java -jar target/libralia-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`

5. **Probar la API con Swagger**

- Una vez que la aplicación esté en ejecución, abre tu navegador y visita el siguiente enlace para ver la documentación de la API y probar sus endpoints:
`http://localhost:8081/swagger-ui/index.html`

---

### 📌 Endpoints actuales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **POST** | `/login` | Inicia sesión y obtiene token JWT. |
| **POST** | `/usuarios` | Crea un nuevo usuario. |
| **GET** | `/usuarios` | Lista todos los usuarios registrados. |
| **GET** | `/usuarios/{username}` | Devuelve los datos públicos del usuario. |
| **GET** | `/usuarios/{username}/biblioteca` | Devuelve avatar y todas las bibliotecas del usuario. |
| **GET** | `/usuarios/{username}/biblioteca/leidos` | Lista libros leídos. |
| **GET** | `/usuarios/{username}/biblioteca/leyendo` | Lista libros en lectura actual. |
| **GET** | `/usuarios/{username}/biblioteca/por-leer` | Lista libros por leer. |
| **GET** | `/usuarios/{username}/biblioteca/leidos/resenas` | Lista reseñas de libros leídos. |
| **POST** | `/biblioteca/agregar` | Agrega un libro a la biblioteca del usuario. |
| **PUT** | `/usuarios/{username}/biblioteca/leidos/editar-resena` | Edita la reseña y calificación personal de un libro leído por usuario. |
| **DELETE** | `/usuarios/{username}/biblioteca/eliminar-libro` | Elimina un libro de cualquier biblioteca del usuario. |
| **POST** | `/libros/buscar` | Busca libros en Google Books API. |
| **GET** | `/libros-db` | Lista libros registrados en base de datos. |


---

## 🛠️ Otras tecnologías utilizadas

- **Java 21 – Lenguaje principal del backend.
- **Spring Boot 3** – Framework para desarrollo de aplicaciones web modernas.
- **Spring Data JPA** – Abstracción para trabajar con bases de datos relacionales.
- **PostgreSQL** – Sistema de gestión de base de datos utilizado en producción.
- **Hibernate ORM** – Mapeo objeto-relacional para persistencia automática.
- **Jackson** – Serialización y deserialización de objetos JSON.
- **Google Books API** – Fuente externa de datos para libros.
- **IntelliJ IDEA** – Entorno de desarrollo integrado (IDE).
- **Git & GitHub** – Control de versiones y colaboración en el repositorio.
- **Flyway** – Herramienta de migración y versionado de base de datos.
- **Bean Validation (Jakarta)** – Validaciones con anotaciones `@Valid`, `@NotBlank`, `@Size`, etc.
- **Insomnia** – Pruebas de endpoints REST.
- **ChatGPT** – Asistencia para documentación, lógica y planificación de mejoras.
- **Swagger / OpenAPI** con `springdoc-openapi-starter-webmvc-ui` **2.8.9** - para documentación y pruebas.
- **Maven Wrapper (mvnw)** - para builds reproducibles sin requerir Maven global.
- **Spring Boot Maven Plugin** - para repackage del JAR ejecutable.

## 📌 Estado Actual

✅ Proyecto consolidado en arquitectura **Spring Boot 3**  
✅ Integración completa con base de datos **PostgreSQL**  
✅ Capacidad de búsqueda y selección de libros vía **Google Books API**  
✅ Creación y gestión de usuarios con perfiles personalizados  
✅ Clasificación de libros por estado de lectura: *Leyendo*, *Por leer*, *Leídos*  
✅ Agregado de **reseñas** y **calificaciones personales**  
✅ Endpoints REST funcionando para exponer la biblioteca y perfil de cada usuario  
✅ Edición y eliminación de libros desde el perfil  
✅ Implementación de **DTOs**, paginación y buenas prácticas en la arquitectura  
✅ Autenticación y autorización de usuarios (registro, login, token JWT)  
✅ Protección de endpoints privados con **Spring Security**  
✅ Validaciones robustas con mensajes personalizados  
✅ Optimización de consultas en base de datos  
✅ API asegurada con autenticación para proteger los datos del usuario  
✅ Documentación auto-generada con Swagger y disponible en `/swagger-ui/index.html`.
✅ Build reproducible con `./mvnw -DskipTests clean package` y ejecución por `JAR`.
✅ Perfiles `dev` (H2) y `prod` (PostgreSQL) con configuración vía variables de entorno.
✅ Swagger liberado en seguridad para facilitar pruebas (endpoints protegidos siguen con `JWT`).
✅ API lista para ejecutar en cualquier equipo con Java 21.

---

## 🚀 Próximas mejoras

- Refactor de lógica para mayor desacoplamiento y escalabilidad  
- Implementación de roles y permisos (ADMIN / USER)  
- Inicio del desarrollo del **frontend** de Libralia  
- Publicación en entorno cloud  
- Documentación para facilitar el consumo de la API  
- Implementación de tests automatizados   


## 🎬 Pruebas y demo

🧪 Pruebas realizadas en **Insomnia** para validar endpoints de Libralia:

🎥 [**Puedes ver el video demo aquí!**](https://www.youtube.com/watch?v=SUDqsni0l2I)

![Captura insomnia para README](https://github.com/user-attachments/assets/f86ac599-c2c8-4ddb-8253-6cfb1eb4d8ab)



## 📬 Contacto

- 📧 **Correo:** [nattypavez@gmail.com](nattypavez@gmail.com)  
- 🔗 **LinkedIn:** [Mi perfil profesional](https://www.linkedin.com/in/natalia-pavez-programacion/)  
- 💻 **Repositorio GitHub:** [Libralia - proyecto personal](https://github.com/NattyPavez/Libralia-proyecto-personal)

---

> 📝 **Nota:** Este proyecto sigue vivo y en constante evolución, en paralelo a mi formación como Backend Developer.  
> Cada curso representa un nuevo módulo que refuerza la arquitectura y amplía las funcionalidades de Libralia.
