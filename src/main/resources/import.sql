INSERT INTO regiones (id, nombre) VALUES (1, 'Miranda');
INSERT INTO pais (id, nombre) VALUES (1, 'Venezuela');

INSERT INTO registros (pais_id, region_id, nombre, apellido, tipo_documento, numero_documento, email, pass, terminos) VALUES(1, 1, 'dayana', 'sanchez', '2', '269624035', 'dayanalily22@gmail.com', '12345', true );


INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, tipo_documento, numero_documento, terminos, foto) 
VALUES (1, 'dayanalily2234@gmail.com', '$2a$10$t2DCLCYLA9f3J32LkTwftOJPB7LeC6qZ9xW3.jm2d8kgX04Uxv656', 1, 'dayana', 'sanchez', 1, '34567878787', true , '');
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, tipo_documento, numero_documento, terminos, foto) 
VALUES (2, 'dayanalilyeee2@gmail.com', '$2a$10$MyBX0BbD7DfHi6XuawQikulCpWUnupd9CYdT3STtF7Ki/wII1vZCC', 1, 'dayanaadmin', 'sanchez', 1,'269624056', true , '');
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, tipo_documento, numero_documento, terminos, foto ) 
VALUES (3, 'ayanalilyrr22@gmail.com', '$2a$10$4jSA6bXlkNAlQLauH5QcXeYLprfT1ZXesT2xt13yrZVhHtbHDr2Ee', 1,'dayanados', 'sanchez', 1, '269624035', true , '');



INSERT INTO roles (id, nombre) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, nombre) VALUES (2, 'ROLE_ADMIN');




INSERT INTO usuario_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuario_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuario_roles (usuario_id, role_id) VALUES (2,1);

