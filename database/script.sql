drop database if exists tecnostore;

create database tecnostore;

use tecnostore;


create table celular(
    id int auto_increment primarey key,
    marca varchar (50) not null,
    modelo varchar (50) not null,
    stock int not null,
    sistemaOperativo varchar (50) not null,
    gama varchar(50) not null,
    precio double not null
);

create table cliente(
    id int auto_increment primarey key,
    nombre varchar(50) not null,
    identificacion varchar(50) not null,
    correo varchar(50) not null,
    telefono varchar(50) not null
);

create table venta(
    id int auto_increment primarey key,
    id_cliente int not null,
    fecha date not null,
    total double not null,
    
);