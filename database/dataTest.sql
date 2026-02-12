-- ============================================================
-- DATOS DE PRUEBA
-- ============================================================

-- Insertar marcas
INSERT INTO marca (nombre) VALUES
('Samsung'),
('Apple'),
('Xiaomi'),
('Motorola'),
('Nokia'),
('Realme'),
('Google'),
('Huawei'),
('OnePlus'),
('Oppo');

-- Insertar celulares (variedad de gamas, sistemas operativos y stock)
INSERT INTO celular (id_marca, modelo, stock, sistemaOperativo, gama, precio) VALUES
-- GAMA ALTA
(1, 'Galaxy S24 Ultra', 15, 'ANDROID', 'ALTA', 4500000.00),
(2, 'iPhone 15 Pro Max', 12, 'IOS', 'ALTA', 5500000.00),
(7, 'Pixel 8 Pro', 8, 'ANDROID', 'ALTA', 4000000.00),
(9, 'OnePlus 12 Pro', 10, 'ANDROID', 'ALTA', 3800000.00),
(2, 'iPhone 14 Pro', 6, 'IOS', 'ALTA', 4800000.00),

-- GAMA MEDIA
(3, 'Redmi Note 13 Pro', 25, 'ANDROID', 'MEDIA', 1800000.00),
(4, 'Moto G73', 20, 'ANDROID', 'MEDIA', 1600000.00),
(1, 'Galaxy A54', 30, 'ANDROID', 'MEDIA', 1200000.00),
(10, 'Reno 11', 15, 'ANDROID', 'MEDIA', 1500000.00),
(8, 'P60', 18, 'ANDROID', 'MEDIA', 1700000.00),
(3, 'Poco X6 Pro', 22, 'ANDROID', 'MEDIA', 1400000.00),

-- GAMA BAJA
(5, 'C21 Plus', 35, 'ANDROID', 'BAJA', 900000.00),
(6, 'C67', 3, 'ANDROID', 'BAJA', 450000.00),  -- Stock bajo para testing
(3, 'Redmi A3', 40, 'ANDROID', 'BAJA', 350000.00),
(4, 'Moto E13', 28, 'ANDROID', 'BAJA', 500000.00),
(1, 'Galaxy A15', 4, 'ANDROID', 'BAJA', 600000.00),  -- Stock bajo para testing
(10, 'A78', 2, 'ANDROID', 'BAJA', 380000.00);  -- Stock bajo para testing

-- Insertar personas (base para clientes)
INSERT INTO persona (nombre, identificacion, correo, telefono) VALUES
('Juan Pérez García', '1234567890', 'juan.perez@gmail.com', '3001234567'),
('María Gómez López', '0987654321', 'maria.gomez@hotmail.com', '3109876543'),
('Carlos Rodríguez Martínez', '1122334455', 'carlos.r@yahoo.com', '3201122334'),
('Ana Martínez Silva', '5544332211', 'ana.martinez@outlook.com', '3155443322'),
('Luis Hernández Torres', '6677889900', 'luis.hernandez@gmail.com', '3186677889'),
('Diana Castro Ruiz', '9988776655', 'diana.castro@gmail.com', '3009988776'),
('Pedro Ramírez González', '1357924680', 'pedro.ramirez@hotmail.com', '3151357924'),
('Laura Jiménez Morales', '2468013579', 'laura.jimenez@yahoo.com', '3202468013'),
('Andrés Vargas Díaz', '1029384756', 'andres.vargas@gmail.com', '3101029384'),
('Sofía Moreno Pérez', '5647382910', 'sofia.moreno@outlook.com', '3185647382');

-- Insertar clientes (asociados a personas)
INSERT INTO cliente (id_persona) VALUES 
(1), (2), (3), (4), (5), (6), (7), (8), (9), (10);

-- Insertar ventas (variedad de fechas y montos)
INSERT INTO venta (id_cliente, fecha, total) VALUES
-- Enero 2026
(1, '2026-01-05', 5355000.00),    -- Juan compra iPhone 15 Pro Max
(2, '2026-01-10', 3570000.00),    -- María compra Redmi Note 13 Pro x2
(3, '2026-01-15', 1071000.00),    -- Carlos compra Galaxy A15

-- Febrero 2026 (mes actual - más ventas para testing)
(4, '2026-02-01', 10710000.00),   -- Ana compra Galaxy S24 Ultra x2
(5, '2026-02-03', 6545000.00),    -- Luis compra iPhone 15 Pro Max + iPhone 14 Pro
(1, '2026-02-05', 4760000.00),    -- Juan compra Pixel 8 Pro
(6, '2026-02-07', 1428000.00),    -- Diana compra Galaxy A54
(7, '2026-02-08', 5355000.00),    -- Pedro compra iPhone 15 Pro Max
(2, '2026-02-09', 2142000.00),    -- María compra Redmi Note 13 Pro
(8, '2026-02-10', 4522000.00),    -- Laura compra OnePlus 12 Pro

-- Marzo 2026
(9, '2026-03-01', 1785000.00),    -- Andrés compra Moto G73
(10, '2026-03-05', 535500.00);    -- Sofía compra C21 Plus

-- Insertar detalles de ventas
-- Venta 1: Juan compra iPhone 15 Pro Max (Enero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(1, 2, 1, 5500000.00);

-- Venta 2: María compra Redmi Note 13 Pro x2 (Enero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(2, 6, 2, 3600000.00);

-- Venta 3: Carlos compra Galaxy A15 (Enero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(3, 16, 1, 900000.00);

-- Venta 4: Ana compra Galaxy S24 Ultra x2 (Febrero) - Venta grande
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(4, 1, 2, 9000000.00);

-- Venta 5: Luis compra iPhone 15 Pro Max + iPhone 14 Pro (Febrero) - Venta combinada
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(5, 2, 1, 5500000.00),
(5, 5, 1, 4800000.00);

-- Venta 6: Juan compra Pixel 8 Pro (Febrero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(6, 3, 1, 4000000.00);

-- Venta 7: Diana compra Galaxy A54 (Febrero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(7, 8, 1, 1200000.00);

-- Venta 8: Pedro compra iPhone 15 Pro Max (Febrero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(8, 2, 1, 5500000.00);

-- Venta 9: María compra Redmi Note 13 Pro (Febrero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(9, 6, 1, 1800000.00);

-- Venta 10: Laura compra OnePlus 12 Pro (Febrero)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(10, 4, 1, 3800000.00);

-- Venta 11: Andrés compra Moto G73 (Marzo)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(11, 7, 1, 1500000.00);

-- Venta 12: Sofía compra C21 Plus (Marzo)
INSERT INTO detalle_venta (id_venta, id_celular, cantidad, subtotal) VALUES
(12, 12, 1, 450000.00);