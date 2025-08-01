-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    biografia TEXT,
    avatar_url TEXT
);

-- Tabla de libros únicos de la base Libralia
CREATE TABLE IF NOT EXISTS libros_libralia (
    id BIGSERIAL PRIMARY KEY,
    google_id VARCHAR(255) UNIQUE NOT NULL,
    titulo VARCHAR(255),
    autor VARCHAR(255),
    descripcion TEXT,
    url_portada TEXT,
    categoria VARCHAR(100),
    fecha_publicacion DATE
);

-- Tabla de libros personalizados de cada usuario
CREATE TABLE IF NOT EXISTS libros_personales (
    id BIGSERIAL PRIMARY KEY,
    estado VARCHAR(50),
    calificacion INT,
    resena TEXT,
    usuario_id BIGINT REFERENCES usuarios(id),
    libro_libralia_id BIGINT REFERENCES libros_libralia(id)
);
