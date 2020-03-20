CREATE TABLE tarifas(
	codTarifa SERIAL NOT NULL,
	nome VARCHAR(50) NOT NULL,
	maxActivades SMALLINT NOT NULL,
	precioBase DECIMAL NOT NULL,
	precioExtra DECIMAL NOT NULL,
	PRIMARY KEY (codTarifa)
);

CREATE TABLE usuarios(
	login 		VARCHAR(25) NOT NULL,
	contrasinal VARCHAR(50) NOT NULL,
	nome 		VARCHAR(200) NOT NULL,
	numTelefono CHAR(9) NOT NULL,
	DNI 		CHAR(9) NOT NULL UNIQUE,
	correoElectronico CHAR(9) NOT NULL,
	IBAN 		CHAR(24) NOT NULL,
	PRIMARY KEY (login)
);

CREATE TABLE socios(
	login 			VARCHAR(25) NOT NULL,
	dataNacemento 	DATE NOT NULL,
	dificultades 	VARCHAR(500),
	tarifa 			INT NOT NULL,
	PRIMARY KEY(login),
	FOREIGN KEY (login) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (tarifa) REFERENCES tarifas(codTarifa) 
	ON UPDATE CASCADE ON DELETE RESTRICT	
);

CREATE TABLE persoal(
	login VARCHAR(25) NOT NULL,
	dataIncorporacion DATE NOT NULL,
	NUSS	CHAR(15) NOT NULL,
	PRIMARY KEY(login),
	FOREIGN KEY (login) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE profesores(
	login 	VARCHAR(25) NOT NULL,
	PRIMARY KEY(login),
	FOREIGN KEY (login) REFERENCES persoal(login)
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE instalacions(
	codInstalacion 	SERIAL NOT NULL,
	nome 			VARCHAR(50) NOT NULL,
	numTelefono 	CHAR(9) NOT NULL,
	direccion 		VARCHAR(200) NOT NULL,
	PRIMARY KEY (codInstalacion)
);

CREATE TABLE areas(
	codArea 	SERIAL NOT NULL,
	instalacion INT NOT NULL,
	nome		VARCHAR(50),
	descricion 	VARCHAR(200) NOT NULL,
	aforoMaximo INT NOT NULL,
	PRIMARY KEY (codArea,instalacion),
	FOREIGN KEY (codArea) REFERENCES instalacions(codInstalacion) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE materiais(
	codMaterial 	SERIAL NOT NULL,
	area			INT NOT NULL,
	instalacion 	INT NOT NULL,
	nome			VARCHAR(50),
	dataCompra		DATE,
	prezoCompra 	DECIMAL,
	PRIMARY KEY (codMaterial),
	FOREIGN KEY (area,instalacion) REFERENCES areas(codArea,instalacion)
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE tipoActividades(
	codTipoActividade 	SERIAL,
	nome				VARCHAR(50),
	descricion			VARCHAR(200),
	PRIMARY KEY (codTipoActividade)
);

CREATE TABLE cursos(
	codCurso	SERIAL NOT NULL,
	nome		VARCHAR(50),
	descricion	VARCHAR(200),
	prezo		DECIMAL	NOT NULL,
	PRIMARY KEY (codCurso)
);
 
CREATE TABLE actividades(
	dataActividade 	TIMESTAMP NOT NULL,
	area 			INT NOT NULL,
	instalacion 	INT NOT NULL,
	tipoActividade 	INT NOT NULL,
	curso			INT,
	profesor		VARCHAR(25),
	nome 			VARCHAR(50) NOT NULL,
	duracion 		DECIMAL NOT NULL,
	PRIMARY KEY (dataActividade,area,instalacion),
	
	FOREIGN KEY (tipoActividade) REFERENCES tipoActividades(codTipoActividade)
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (curso) REFERENCES cursos(codCurso) 
	ON UPDATE CASCADE ON DELETE CASCADE,	
	FOREIGN KEY (area,instalacion) REFERENCES areas(codArea,instalacion) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (profesor) REFERENCES profesores(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE incidenciasMateriais(
	numero 			SERIAL NOT NULL,
	material		INT NOT NULL,
	usuario			VARCHAR(25) NOT NULL,
	descricion		VARCHAR(200) NOT NULL,
	dataFalla		DATE DEFAULT NOW(),
	dataResolucion 	DATE,
	custoReparacion DECIMAL,
	PRIMARY KEY(numero),
	FOREIGN KEY (usuario) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (material) REFERENCES materiais(codMaterial) 
	ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE incidenciasAreas(
	numero 		SERIAL NOT NULL,
	area		INT	NOT NULL,
	instalacion	INT NOT NULL,
	usuario		VARCHAR(25) NOT NULL,
	descricion	VARCHAR(200) NOT NULL,
	dataFalla	DATE DEFAULT NOW(),
	dataResolucion DATE,
	custoReparacion DECIMAL,
	PRIMARY KEY(numero),
	FOREIGN KEY (usuario) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (area,instalacion) REFERENCES areas(codArea,instalacion) 
	ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE realizarActividade(
	dataActividade 	TIMESTAMP NOT NULL,
	area 			INT NOT NULL,
	instalacion 	INT NOT NULL,
	usuario 		VARCHAR(25) NOT NULL,
	valoracion 		SMALLINT,
	PRIMARY KEY (dataActividade,area,instalacion,usuario) ,
	FOREIGN KEY (dataActividade,area,instalacion) REFERENCES actividades(dataActividade,area,instalacion)
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (usuario) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);
 
CREATE TABLE realizarCurso(
	curso		INT NOT NULL,
	usuario		VARCHAR(25),
	PRIMARY KEY (curso,usuario),
	FOREIGN KEY (curso) REFERENCES cursos(codCurso) 
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (usuario) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE enviarMensaxes(
	emisor		VARCHAR(25) NOT NULL,
	receptor	VARCHAR(25) NOT NULL,
	dataEnvio 	TIMESTAMP NOT NULL,
	contido 	VARCHAR(500) NOT NULL,
	PRIMARY KEY (emisor,receptor,dataEnvio),
	FOREIGN KEY (emisor) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (receptor) REFERENCES usuarios(login) 
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE estarCapacitado(
	tipoActividade 	INT NOT NULL, 
	profesor		VARCHAR(25) NOT NULL,
	FOREIGN KEY (profesor) REFERENCES profesores(login) 
	ON UPDATE CASCADE ON DELETE CASCADE,	
	FOREIGN KEY (tipoActividade) REFERENCES tipoActividades(codTipoActividade) 
	ON UPDATE CASCADE ON DELETE CASCADE
);

