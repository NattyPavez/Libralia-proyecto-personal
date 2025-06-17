# 📚 Libralia - Proyecto Personal de Desarrollo Backend

**Libralia** es un proyecto de biblioteca virtual social, desarrollado como parte de mi proceso formativo como Backend Developer. Este proyecto refleja todo el camino técnico que he recorrido, desde mis primeras prácticas en Java puro hasta la integración completa de bases de datos, APIs externas, Spring Boot y persistencia de datos profesional con JPA.

---

## 🚀 Evolución del Proyecto

 **Libralia** ha ido evolucionando paso a paso, en paralelo a mi formación en backend, acompañado mi ruta de aprendizaje dentro del programa **Oracle Next Education (Alura Latam)**, permitiéndome aplicar en un proyecto real cada uno de los contenidos vistos en los cursos. Desde los primeros pasos en Programación Orientada a Objetos hasta persistencia de datos avanzada.


### 1️⃣ **Inicio en Java puro (POO)**
- Creación inicial de clases `Usuario` y `LibroPersonal`.
- Manejo de listas, colecciones, condicionales y lógica de navegación.
- Simulación de búsquedas sin conexión real a datos.

### 2️⃣ **Integración de APIs externas (Google Books API)**
- Consumo de APIs REST usando `HttpClient`.
- Conversión de datos JSON a DTOs mediante Jackson.
- Construcción dinámica de libros a partir de los resultados de búsqueda.
- Tratamiento de campos opcionales, listas de autores y fechas de publicación.

### 3️⃣ **Transición a Spring Boot y Arquitectura Profesional**
- Migración del proyecto completo a Spring Boot.
- Uso de `@Component` y `@Autowired` para gestionar dependencias.
- Separación de capas: API, modelo, servicio, DTOs, repositorios.

### 4️⃣ **Persistencia de Datos con PostgreSQL y Spring Data JPA**
- Modelado de las entidades `Usuario` y `LibroPersonal` con `@Entity`.
- Relación bidireccional `@OneToMany` y `@ManyToOne`.
- Configuración de la base de datos en `application.properties`.
- Creación automática de las tablas vía Hibernate.
- Repositorios personalizados usando `JpaRepository` y consultas `@Query`.

### 5️⃣ **Implementación de funcionalidades avanzadas**
- Agregado de **estados de lectura** (`LEIDO`, `POR_LEER`, `LEYENDO`) para clasificar los libros.
- Creación de lógica personalizada para:
  - Calificar libros leídos.
  - Agregar reseñas personales.
  - Consultar las calificaciones y reseñas dentro del perfil del usuario.
  - Mostrar un ranking de Top 5 libros mejor evaluados por los usuarios.

### 6️⃣ **Gestión de flujos de usuario realistas**
- Simulación de experiencia de usuario desde consola.
- Menús dinámicos y validación de entradas.
- Verificación de duplicados al agregar libros.
- Modularización progresiva de métodos para mantener el código limpio y mantenible.

---

## 🛠️ Tecnologías utilizadas

- Java 24
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Hibernate ORM
- Jackson (serialización/deserialización de JSON)
- API pública de Google Books
- IDE: IntelliJ IDEA
- Git y GitHub para control de versiones
- Asistente ChatGPT

---

## 🌐 Estado Actual

✅ Proyecto consolidado en arquitectura Spring Boot  
✅ Totalmente integrado a base de datos PostgreSQL  
✅ Capacidad de búsqueda y registro de libros vía API  
✅ Agregación de calificaciones y reseñas personalizadas  
✅ Gestión de usuarios y perfiles de lectura  
✅ Preparado para escalar hacia una App Social de lectores

---

## 🎯 Próximas mejoras (Fase 2)

- Refactor de duplicación de libros (lógica avanzada de validación)
- Implementación de DTOs para simplificar las respuestas de búsqueda
- Creación de endpoints REST para convertir Libralia en una API
- Desarrollo de frontend y conexión a interfaz gráfica
- Preparación de ambiente cloud para publicación en servidores

---

## 🎥 Video demo

📹 [**Ver Video**](https://drive.google.com/file/d/1xBNWcXpd3AmqworiMguwKbBDj8sCZBmI/view?usp=sharing)

---

## 📬 Contacto

📩 **Correo:** nattypavez@gmail.com
🔗 **LinkedIn:** [Mi perfil profesional](https://www.linkedin.com/in/natalia-pavez-programacion/)  
🔗 **Repositorio GitHub:** [Libralia - proyecto personal](https://github.com/NattyPavez/Libralia-proyecto-personal)

---

📝 **Nota:** Este proyecto sigue vivo y en constante evolución a medida que avanzo en los cursos y la especialización backend.

