# Twitter
Un clon de twitter con java como proyectos universitario
creacion de bases de datos 

CREATE DATABASE red_social;
USE red_social;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE publicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    contenido VARCHAR(300) NOT NULL,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publicacion_id INT NOT NULL,
    usuario_id INT NOT NULL,
    contenido VARCHAR(300) NOT NULL,
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (publicacion_id) REFERENCES publicaciones(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE likes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publicacion_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha_like TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (publicacion_id) REFERENCES publicaciones(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    UNIQUE KEY unique_like (publicacion_id, usuario_id)
);

CREATE TABLE seguidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    seguidor_id INT NOT NULL,
    seguido_id INT NOT NULL,
    fecha_seguimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seguidor_id) REFERENCES usuarios(id),
    FOREIGN KEY (seguido_id) REFERENCES usuarios(id),
    UNIQUE KEY unique_seguimiento (seguidor_id, seguido_id)
);

select * from usuarios;

INSERT INTO usuarios (nombre, usuario, contrasena, fecha_registro) 
VALUES ('admin', 'prueba', '1234', '2024-05-22 14:00:00');

INSERT INTO publicaciones (usuario_id, contenido)
 VALUES (1, 'Este es un tuit de ejemplo');

INSERT INTO comentarios (publicacion_id, usuario_id, contenido) 
VALUES (1, 1, 'Este es un comentario de ejemplo');

INSERT INTO likes (publicacion_id, usuario_id) VALUES (1, 1);
INSERT INTO seguidores (seguidor_id, seguido_id) VALUES (1, 1);

-- VER PUBLICACIONES DE LOS SEGUIDORES
SELECT p.* 
FROM publicaciones p 
JOIN seguidores s ON p.usuario_id = s.seguido_id 
WHERE s.seguidor_id = 1 , usuarios
ORDER BY p.fecha_publicacion DESC;

select * from publicaciones;
