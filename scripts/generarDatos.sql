INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) values ('Pochísima',5,15.0,3.0);
INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) values ('Pocha',10,20.0,3.0);
INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) values ('Básica',25,35.0,2.0);
INSERT INTO tarifas (nome,maxActividades,precioBase,precioExtra) values ('Premium',30,45.0,1.0);

INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('cachoMono12','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Daniel Chenel','616123123','12345671X','daniel@gmail.com','ES2100000000000000000000');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('helenanita','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Helena Castro','616123123','12345672X','helena@gmail.es','ES2100000000000000000001');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('david_pocho62','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','David Carracedo','616123123','12345673X','david@gmail.com','ES2100000000000000000002');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('manolo-el-del-bombo','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Manuel Bendaña','616123123','12345674X','manu@hotmail.com','ES2100000000000000000003');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('victor122213','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Victor Barreiro','616123123','12345675X','victor@outlook.com','ES2100000000000000000004');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('barjita69','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Javier Barja','616123123','12345676X','mail@gmail.com','ES2100000000000000000005');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('doralaexploradora','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Dora','616123123','12345677X','mail@gmail.es','ES2100000000000000000006');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('esclavoCITIUS','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Nicolas','616123123','12345678X','mail@gmail.com','ES2100000000000000000007');
INSERT INTO usuarios (login,contrasinal,nome,numTelefono,DNI,correoElectronico,IBAN) values ('arbolitos69','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Maria Jose','616123123','12345679X','mail@hotmail.com','ES2100000000000000000008');

INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values ('cachoMono12','1998-01-23','Atrancado de neuronas, cóstalle',1);
INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values ('helenanita','2000-11-07',null,1);
INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values ('david_pocho62','2000-04-24','Atrancado de neuronas, cóstalle',2);
INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values ('manolo-el-del-bombo','2000-01-21',null,3);
INSERT INTO socios (login,dataNacemento,dificultades,tarifa) values ('victor122213','2000-01-01','Dificultades de visión',4);

INSERT INTO persoal (login,dataIncorporacion,NUSS) values ('barjita69','2010-01-01','12345678912');
INSERT INTO persoal (login,dataIncorporacion,NUSS) values ('doralaexploradora','2010-01-01','12345678913');
INSERT INTO persoal (login,dataIncorporacion,NUSS) values ('esclavoCITIUS','2010-01-01','12345678914');
INSERT INTO persoal (login,dataIncorporacion,NUSS) values ('arbolitos69','2010-01-01','12345678915');

INSERT INTO profesores (login) values ('barjita69');
INSERT INTO profesores (login) values ('doralaexploradora');
INSERT INTO profesores (login) values ('arbolitos69');

INSERT INTO instalacions (nome,numTelefono,direccion) values ('Casa do David','981999888','Rúa dos Soles, 21, BD');
INSERT INTO instalacions (nome,numTelefono,direccion) values ('Casa do Mono','981999889','Rúa das Margaritas, 2, 1º');
INSERT INTO instalacions (nome,numTelefono,direccion) values ('ETSE','981999810','Campus Vida, 33, SN');

INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (1,'Habitación do Davis','Área recreativa para actividades personales',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (1,'Baño do Davis','Área de pensamento para relajación personal',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (1,'Pistas de baloncesto','Área para a práctica de baloncesto',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (2,'Habitación do Mono','Área de locura máxima',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (2,'Horta','Área de disfrute das galiñas',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (2,'Cociña','Área de alimentación e saúde',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (3,'A3','Aula de diversión',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (3,'Solpor','Zona de alimentación',20);
INSERT INTO areas (instalacion,nome,descricion,aforoMaximo) values (3,'Zona de cortejo','Zona de enamorasión',20);

INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (1,1,'Prensa de pernas',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (2,1,'Bicicleta estática',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (3,1,'Bicicleta estática',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (4,2,'Prensa de pernas',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (5,2,'Mancuerna','2020-02-14',null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (6,2,'Balón de fútbol Adidas',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (7,3,'Balón de fútbol Nike',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (8,3,'Comba',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (9,3,'Elíptica',null,1200);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (9,3,'Cinta de correr',null,null);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (9,3,'Cinta de correr H12','2019-07-03',560);
INSERT INTO materiais (area,instalacion,nome,dataCompra,prezoCompra) values (9,3,'Cinta de correr',null,null);

INSERT INTO tipoActividades (nome,descricion) values ('Poda de árboles binarios', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Cumbiotes', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Zumba', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Yoga', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Fútbol', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Baloncesto', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Spinning', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Natación', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Atletismo', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Alterofilia', 'Lorem ipsum dolor sit amet, consectetur cras amet.');
INSERT INTO tipoActividades (nome,descricion) values ('Boxeo', 'Lorem ipsum dolor sit amet, consectetur cras amet.');

INSERT INTO cursos (nome, descricion, prezo) values ('Álgebra lineal','Lorem ipsum dolor sit amet, consectetur cras amet.',250);
INSERT INTO cursos (nome, descricion, prezo) values ('Curso zumba 2020','Lorem ipsum dolor sit amet, consectetur cras amet.',250);
INSERT INTO cursos (nome, descricion, prezo) values ('Fútbol 7 2019-2020','Lorem ipsum dolor sit amet, consectetur cras amet.',250);
INSERT INTO cursos (nome, descricion, prezo) values ('Curso aprendizaxe natación','Lorem ipsum dolor sit amet, consectetur cras amet.',250);

SELECT * FROM actividades;
INSERT INTO actividades VALUES('2020-01-22',1,1,1,1,'barjita69','Pochedad',35);
INSERT INTO actividades VALUES('2020-01-26',2,1,2,1,'doralaexploradora','Cumbiotes',35);

INSERT INTO realizarCursos VALUES(1,'david_pocho62');

SELECT * FROM realizarActividades;

INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),65.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),65.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),64.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),62.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),61.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),75.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),62.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),62.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),63.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),68.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),67.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),66.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),69.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),65.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),64.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),63.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),62.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),62.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),62.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),66.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),65.5,175,10.3,120,80,95,'comentario de proba');
INSERT INTO rexistroFisioloxicos VALUES ('david',NOW(),64.5,175,10.3,120,80,95,'comentario de proba');
