CREATE DATABASE IF NOT EXISTS detalles_con_amor CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE detalles_con_amor;
CREATE TABLE IF NOT EXISTS productos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(500) NULL,
  precio DECIMAL(12,2) NOT NULL CHECK (precio >= 0),
  stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO productos (nombre, descripcion, precio, stock)
SELECT 'Desayuno sorpresa clásico', 'Desayuno personalizado para una ocasión especial.', 45000.00, 8
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Desayuno sorpresa clásico');
