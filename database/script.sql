drop database if exists tecnostore;

create database tecnostore;

use tecnostore;

create table marca(
    id int auto_increment primary key,
    nombre varchar(50)
);

create table celular(
    id int auto_increment primary key,
    id_marca int not null,
    modelo varchar (50) not null,
    stock int not null,
    sistemaOperativo enum ('IOS','ANDROID') not null,
    gama enum('ALTA','MEDIA','BAJA') not null,
    precio double not null,
    foreign key (id_marca) references marca(id)
);

create table persona(
    id int auto_increment primary key,
    nombre varchar(50)not null,
    identificacion varchar(50) not null,
    correo varchar(50) not null,
    telefono varchar(50) not null
)

create table cliente(
    id_persona int auto_increment primary key,
    foreign key (id_persona) references persona(id)
);


create table venta(
    id int auto_increment primary key,
    id_cliente int not null,
    fecha date not null,
    total double not null,
    foreign key(id_cliente) references cliente(id)
);

create table detalle_venta(
    id int auto_increment primary key,
    id_venta int not null,
    id_celular int not null,
    cantidad int not null,
    subtotal double not null,
    foreign key (id_venta) references venta(id),
    foreign key (id_celular) references celular(id)
);

/*
DATOS DE PRUEBA

*Tabla marca
INSERT INTO marca (nombre) VALUES
('Samsung'),
('Apple'),
('Xiaomi'),
('Motorola'),
('Nokia');


* Tabla celular
INSERT INTO celular (id_marca, modelo, stock, sistemaOperativo, gama, precio) VALUES
(1, 'Galaxy S23', 10, 'ANDROID', 'ALTA', 4200.00),
(2, 'iPhone 14', 8, 'IOS', 'ALTA', 5500.00),
(3, 'Redmi Note 12', 20, 'ANDROID', 'MEDIA', 1800.00),
(4, 'Moto G52', 15, 'ANDROID', 'MEDIA', 1600.00),
(5, 'C21', 25, 'ANDROID', 'BAJA', 900.00);

*Tabla persona
INSERT INTO persona (nombre, identificacion, correo, telefono) VALUES
('Juan Pérez', '0102030405', 'juan.perez@gmail.com', '0991234567'),
('María Gómez', '0607080910', 'maria.gomez@gmail.com', '0987654321'),
('Carlos López', '1112131415', 'carlos.lopez@gmail.com', '0974561238');


*Tabla cliente
INSERT INTO cliente (id_persona) VALUES
(1),
(2),
(3);


*Tabla venta
INSERT INTO venta (id_cliente, fecha, total) VALUES
(1, '2025-01-10', 4200.00),
(2, '2025-01-12', 3600.00),
(3, '2025-01-15', 900.00);


*Tabla detalle_venta
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(1, 1, 1, 4200.00),
(2, 3, 2, 3600.00),
(3, 5, 1, 900.00);

*/

/*
CONSULTAS PARA VERIFICAR

--Muestra todos los celulares con sus respectivos atributos
SELECT 
    c.id,
    m.nombre AS marca,
    c.modelo,
    c.stock,
    c.sistemaOperativo,
    c.gama,
    c.precio
FROM celular c
JOIN marca m ON c.id_marca = m.id;

SHOW COLUMNS FROM celular;


*/