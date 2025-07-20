Profe lo unico que necesita es tener springboot y maven instalado, ademas, el iconico video del comercial de SAUL tuvo que ser removido... puesto que no el archivo era demasiado pesado para levantar el repositorio aca esta todo lo necesario para levantar la base de datos

USE bettercallsaul;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE usuarios_roles (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);
INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO usuarios (username, password) VALUES ('admin', 'admin123');
INSERT INTO usuarios (username, password) VALUES ('usuario', 'usuario123');
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 2);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 1);

UPDATE usuarios SET password = '$2a$10$nSp2Whl2GD09uy.r14FKI.TdnmLwoTNJEk8eGPPNfBiW5Gd1YPPYu' WHERE username = 'admin';
UPDATE usuarios SET password = '$2a$10$Lw2daDFQtl52bphkOKY2He/gkRv20qOPbks0D2kwaWP5JoxNyucg2' WHERE username = 'usuario';
UPDATE usuarios SET password = '$2a$10$qc2N/yHY.cdUD0nTeFe9zepNUXgP0prnracZ0ZV2CRZ0ULf5i4W5m' WHERE username = 'Prueb@Usuario';






CREATE TABLE Citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    nombreCliente VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    descripcion VARCHAR(50) NOT NULL
);

CREATE TABLE Productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL DEFAULT 0,
    imagen VARCHAR(255)
);

CREATE TABLE Carrito(
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    nombre_producto VARCHAR(100), 
    precio_unitario DOUBLE,    
    cantidad INT NOT NULL,
    precio_total DOUBLE,         

    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
        ON DELETE CASCADE #Si un producto se elimina, sus ítems en el carrito también.
        ON UPDATE CASCADE # Si cambia el ID del producto, se actualiza en el carrito.
);

# ACA SE INSERTAN PRODUCTOS EN LA TIENDA
INSERT INTO Productos (nombre, descripcion,precio,imagen)
VALUES 
("Termo", "Termo de acero inoxidable", 29.99, "/tomatodo.png"),
('Camiseta BetterCaulSaul', 'Camiseta 100% de algodon', 9.99, "/camiseta.png"),
('Funda de celular promocional', 'Funda protectora para celular', 5.99, "/caseCelular.png");

INSERT INTO Productos (nombre, descripcion, precio, imagen)
VALUES ('Taza Saul', 'Taza de saul', 5.99, '/tazaSaul.webp');

# ELIMINA LA TABLA CARRITO
DROP TABLE IF EXISTS Carrito;

# ELIMINA TODOS LOS PRODUCTOS
DELETE FROM productos ;

#ELIMINA TODO LO QUE ESTE EN EL CARRITO
DELETE FROM Carrito;

#muestra los productos
SELECT * FROM Productos;

#muestra el carrito y su contenido
SELECT * FROM Carrito;

#ELIMINA LA TABLA PRODUCTOS
DROP TABLE productos;

#ELIMINA LA TABLA CITAS
DROP TABLE citas;

#MUESTRA LAS CITAS
SELECT * FROM citas;

SELECT * FROM usuarios;

SELECT * FROM roles;

SELECT * FROM usuarios_roles;

#ELIMINA LAS CITAS
DELETE FROM citas;


IMPORTANTE : en app properties esta la informacion , en caso de levantar la base de datos debe señalar el usuario y contraseña del servidor MYSQL, este proyecto no fue hecho usando XAMPP, sino MYSQL Workbench
