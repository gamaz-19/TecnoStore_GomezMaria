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
    identificacion varchar(50) not null unique,
    correo varchar(50) not null,
    telefono varchar(50) not null
);

create table cliente(
    id_persona int primary key,
    foreign key (id_persona) references persona(id)
);


create table venta(
    id int auto_increment primary key,
    id_cliente int not null,
    fecha varchar(50) not null,
    total double not null,
    foreign key(id_cliente) references cliente(id_persona)
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
