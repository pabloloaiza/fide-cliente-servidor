-- Base de datos del proyecto final. Ejecutar una sola vez antes de arrancar el servidor.
DROP DATABASE IF EXISTS pos;
CREATE DATABASE pos CHARACTER SET utf8mb4;
USE pos;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    salario DOUBLE NOT NULL DEFAULT 0,
    horario VARCHAR(50) NOT NULL DEFAULT '',
    puntos_acumulados DOUBLE NOT NULL DEFAULT 0
);

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    precio DOUBLE NOT NULL,
    tipo VARCHAR(10) NOT NULL,             -- ARTICULO o COMBO
    descripcion TEXT,
    disponible BOOLEAN NOT NULL DEFAULT TRUE
);

-- Relacion muchos a muchos: que articulos componen cada combo
CREATE TABLE combo_articulos (
    id_combo INT NOT NULL,
    id_articulo INT NOT NULL,
    PRIMARY KEY (id_combo, id_articulo),
    FOREIGN KEY (id_combo) REFERENCES productos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_articulo) REFERENCES productos(id) ON DELETE CASCADE
);

CREATE TABLE ordenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    nombre_cliente VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha DATETIME NOT NULL,
    total DOUBLE NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES usuarios(id)
);

-- Cada linea es un producto dentro de una orden. Guardo el nombre y el precio
-- del momento de la compra para que la factura no cambie si luego editan el producto.
CREATE TABLE orden_detalle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL,
    id_producto INT NOT NULL,
    nombre_producto VARCHAR(80) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    FOREIGN KEY (id_orden) REFERENCES ordenes(id) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

CREATE TABLE facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL UNIQUE,
    nombre_cliente VARCHAR(50) NOT NULL,
    subtotal DOUBLE NOT NULL,
    impuesto DOUBLE NOT NULL,
    total DOUBLE NOT NULL,
    fecha DATETIME NOT NULL,
    FOREIGN KEY (id_orden) REFERENCES ordenes(id) ON DELETE CASCADE
);
