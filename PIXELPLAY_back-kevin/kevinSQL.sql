use pixelplaydb;

INSERT INTO usuarios (nombre, apellido, correo, password, telefono, direccion,activo) 
VALUES ('Admin', 'Sistema', 'admin@pixelplay.com', '$2a$10$L6ytcZ05quJLP.4H49Ha4eSnJDIpghgpLZe0a3/v3aOHUzuQJg9ce', '999999999', 'PixelPlay HQ','');
-- 2. Obtener el ID del usuario recién creado
SET @admin_id = LAST_INSERT_ID();

-- 3. Crear rol ADMIN si no existe
INSERT IGNORE INTO roles (nombre) VALUES ('ROLE_ADMIN');

-- 4. Obtener el ID del rol ADMIN
SELECT id_rol INTO @rol_admin FROM roles WHERE nombre = 'ROLE_ADMIN';

-- 5. Asignar rol ADMIN al usuario
INSERT INTO usuario_roles (id_usuario, id_rol) 
VALUES (@admin_id, @rol_admin);


-- ============================================
-- SCRIPT DE INSERCIÓN DE PRODUCTOS
-- Fuente: Archivos TypeScript de Angular
-- Total: 25 productos
-- ============================================

-- Productos PC (6 productos)
INSERT INTO productos (nombre, descripcion, genero, precio, stock, plataforma, image_url, activo) VALUES
('Balatro', 'Un roguelike de póker adictivo', 'Roguelike', 14.99, 100, 'PC', '/balatro.png', true),
('Cuphead', 'Acción clásica de dibujos animados', 'Acción', 19.99, 80, 'PC', '/cuphead.png', true),
('Hollow Knight', 'Aventura metroidvania épica', 'Aventura', 14.99, 120, 'PC', '/hollowknight.png', true),
('Omori', 'RPG psicológico emocional', 'RPG', 19.99, 90, 'PC', '/Omori_cover.jpg', true),
('Hades', 'Roguelike de acción mitológico', 'Roguelike', 24.99, 110, 'PC', '/hades.jpg', true),
('Celeste', 'Plataformas desafiante e inspirador', 'Plataformas', 19.99, 95, 'PC', '/celeste.png', true);

-- Productos PS5 (6 productos)
INSERT INTO productos (nombre, descripcion, genero, precio, stock, plataforma, image_url, activo) VALUES
('Persona 5 Royal', 'JRPG estilizado de los Phantom Thieves', 'JRPG', 59.99, 75, 'PS5', '/persona5.jpg', true),
('The Evil Within', 'Horror de supervivencia intenso', 'Horror', 19.99, 60, 'PS5', '/evilwithin.jpg', true),
('Ghost of Tsushima', 'Aventura samurái en Japón feudal', 'Aventura', 49.99, 85, 'PS5', '/ghostoftsushima.jpg', true),
('Spider-Man 2', 'Nueva aventura del héroe arácnido', 'Acción', 69.99, 70, 'PS5', '/spiderman2.avif', true),
('Ratchet & Clank', 'Acción y plataformas interdimensional', 'Acción', 59.99, 65, 'PS5', '/ratchetandclank.jpg', true),
('Demon''s Souls', 'Remake del clásico souls-like', 'RPG', 69.99, 55, 'PS5', '/demonssouls.jpg', true);

-- Productos Xbox (6 productos)
INSERT INTO productos (nombre, descripcion, genero, precio, stock, plataforma, image_url, activo) VALUES
('Halo Infinite', 'La épica saga de Master Chief continúa', 'Shooter', 59.99, 80, 'Xbox', '/haloinfinite.jpg', true),
('Forza Horizon 5', 'Carreras de mundo abierto en México', 'Carreras', 59.99, 90, 'Xbox', '/forza5.jpg', true),
('Gears 5', 'Acción intensa contra la Horda', 'Acción', 39.99, 70, 'Xbox', '/gears5.jpg', true),
('Starfield', 'RPG espacial de Bethesda', 'RPG', 69.99, 65, 'Xbox', '/starfield.jpg', true),
('Sea of Thieves', 'Aventuras piratas multijugador', 'Aventura', 39.99, 85, 'Xbox', '/seaofthieves.jpg', true),
('Flight Simulator', 'Simulador de vuelo realista', 'Simulación', 59.99, 60, 'Xbox', '/flightsim.jpg', true);

-- Productos Nintendo Switch (6 productos)
INSERT INTO productos (nombre, descripcion, genero, precio, stock, plataforma, image_url, activo) VALUES
('Super Mario Odyssey', 'Aventura 3D del fontanero más famoso', 'Plataformas', 59.99, 100, 'Nintendo Switch', '/mario.jpg', true),
('The Legend of Zelda: BOTW', 'Mundo abierto épico de Hyrule', 'Aventura', 59.99, 95, 'Nintendo Switch', '/zelda.jpg', true),
('Animal Crossing', 'Vida relajante en tu propia isla', 'Simulación', 59.99, 110, 'Nintendo Switch', '/animalcrossing.jpg', true),
('Splatoon 3', 'Shooter colorido y competitivo', 'Shooter', 59.99, 80, 'Nintendo Switch', '/splatoon3.jpg', true),
('Kirby Star Allies', 'Plataformas adorables y divertidas', 'Plataformas', 49.99, 90, 'Nintendo Switch', '/kirby.jpg', true),
('Metroid Dread', 'Acción metroidvania intensa', 'Acción', 59.99, 75, 'Nintendo Switch', '/metroid.jpg', true);

-- Producto adicional de home.html (Red Dead Redemption 2)
-- Nota: Este juego aparece en el home pero no está en los arrays TypeScript
-- Lo agrego como multiplataforma o puedes asignarle una plataforma específica
INSERT INTO productos (nombre, descripcion, genero, precio, stock, plataforma, image_url, activo) VALUES
('Red Dead Redemption 2', 'Épica aventura del salvaje oeste', 'Acción', 59.99, 70, 'PS5', '/rdr2.jpg', true);

select*from usuarios;
select*from productos;
select*from pedidos;
SELECT * FROM detalle_pedido;