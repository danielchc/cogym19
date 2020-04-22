CREATE TABLE tarifa(
	codTarifa 		SERIAL NOT NULL,
	nome 			VARCHAR(50) NOT NULL UNIQUE,
	maxActividades 	SMALLINT CHECK(maxActividades>=0) NOT NULL ,
	precioBase 		DECIMAL NOT NULL,
	precioExtra 	DECIMAL NOT NULL,
	PRIMARY KEY (codTarifa)
);

CREATE TABLE usuario(
	login 				VARCHAR(25) NOT NULL,
	contrasinal 		CHAR(64),
	numTelefono 		CHAR(9),
	correoElectronico 	VARCHAR(200),
	IBAN 				CHAR(24),
	dataAlta			DATE NOT NULL DEFAULT NOW(),
	dataBaixa			DATE CHECK(dataBaixa>=dataAlta),
	PRIMARY KEY (login)
);

CREATE TABLE socio(
	login 			VARCHAR(25) NOT NULL,
	tarifa 			INT NOT NULL,
	PRIMARY KEY(login),
	FOREIGN KEY (login) REFERENCES usuario(login) 
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (tarifa) REFERENCES tarifa(codTarifa) 
		ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE persoal(
	login 				VARCHAR(25) NOT NULL,
	NUSS				CHAR(12) NOT NULL UNIQUE,
	profesorActivo		BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY(login),
	FOREIGN KEY (login) REFERENCES usuario(login) 
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE persoaFisica(
	DNI 				CHAR(9) NOT NULL,
	nome 				VARCHAR(200),
	dificultades 		VARCHAR(500),
	dataNacemento 		DATE CHECK ((NOW()- INTERVAL'16 years')>dataNacemento),
	usuarioSocio		VARCHAR(25),
	usuarioPersoal		VARCHAR(25),
	PRIMARY KEY (DNI),
	FOREIGN KEY (usuarioSocio) REFERENCES socio(login) 
			ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (usuarioPersoal) REFERENCES persoal(login) 
			ON UPDATE CASCADE ON DELETE RESTRICT		
);

CREATE TABLE instalacion(
	codInstalacion 	SERIAL NOT NULL,
	nome 			VARCHAR(50) NOT NULL UNIQUE,
	numTelefono 	CHAR(9) NOT NULL,
	direccion 		VARCHAR(200) NOT NULL,
	PRIMARY KEY (codInstalacion)
);

CREATE TABLE area(
	codArea 	INT NOT NULL,
	instalacion INT NOT NULL,
	nome		VARCHAR(50),
	descricion 	VARCHAR(200) NOT NULL,
	aforoMaximo INT NOT NULL CHECK (aforoMaximo>0),
	dataBaixa 	DATE,
	PRIMARY KEY (codArea,instalacion),
	FOREIGN KEY (instalacion) REFERENCES instalacion(codInstalacion) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	UNIQUE(instalacion,nome)
);

CREATE TABLE tipoMaterial(
	codTipoMaterial 	SERIAL NOT NULL, 			
	nome 				VARCHAR(100) NOT NULL,
	PRIMARY KEY(codTipoMaterial)
);

CREATE TABLE material(
	codMaterial 	INT NOT NULL,
	tipoMaterial	INT	NOT NULL,
	area			INT NOT NULL,
	instalacion 	INT NOT NULL,
	estado			VARCHAR(50) NOT NULL,
	dataCompra		DATE,
	prezoCompra 	DECIMAL	CHECK (prezoCompra>=0),
	PRIMARY KEY (codMaterial,tipoMaterial),
	FOREIGN KEY (area,instalacion) REFERENCES area(codArea,instalacion)
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (tipoMaterial) REFERENCES tipoMaterial(codTipoMaterial)
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE tipoActividade(
	codTipoActividade 	SERIAL NOT NULL,
	nome				VARCHAR(50) UNIQUE NOT NULL,
	descricion			VARCHAR(200),
	PRIMARY KEY (codTipoActividade)
);

CREATE TABLE curso(
	codCurso	SERIAL NOT NULL,
	nome		VARCHAR(50) UNIQUE,
	descricion	VARCHAR(200),
	prezo		DECIMAL	NOT NULL CHECK (prezo>=0),
	aberto		BOOLEAN NOT NULL DEFAULT false,
	PRIMARY KEY (codCurso)
);
 
CREATE TABLE actividade(
	dataActividade 	TIMESTAMP NOT NULL,
	area 			INT NOT NULL,
	instalacion 	INT NOT NULL,
	tipoActividade 	INT NOT NULL,
	curso			INT,
	profesor		VARCHAR(25),
	nome 			VARCHAR(50) NOT NULL,
	duracion 		DECIMAL NOT NULL CHECK (duracion>=0),
	PRIMARY KEY (dataActividade,area,instalacion),
	
	FOREIGN KEY (tipoActividade) REFERENCES tipoActividade(codTipoActividade)
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (curso) REFERENCES curso(codCurso) 
	ON UPDATE CASCADE ON DELETE CASCADE,	
	FOREIGN KEY (area,instalacion) REFERENCES area(codArea,instalacion) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (profesor) REFERENCES persoal(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE incidenciaMaterial(
	numero 			SERIAL NOT NULL,
	material		INT NOT NULL,
	tipoMaterial	INT NOT NULL,
	usuario			VARCHAR(25) NOT NULL,
	descricion		VARCHAR(500) NOT NULL,
	comentarioResolucion VARCHAR(500),
	dataFalla		DATE NOT NULL DEFAULT NOW(),
	dataResolucion 	DATE CHECK (dataResolucion>=dataFalla),
	custoReparacion DECIMAL CHECK (custoReparacion>=0),
	PRIMARY KEY(numero),
	FOREIGN KEY (usuario) REFERENCES usuario(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (material,tipoMaterial) REFERENCES material(codMaterial,tipoMaterial) 
	ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE incidenciaArea(
	numero 			SERIAL NOT NULL,
	area			INT	NOT NULL,
	instalacion		INT NOT NULL,
	usuario			VARCHAR(25) NOT NULL,
	descricion		VARCHAR(500) NOT NULL,
	comentarioResolucion VARCHAR(500),
	dataFalla		DATE DEFAULT NOW(),
	dataResolucion 	DATE CHECK (dataResolucion>=dataFalla),
	custoReparacion DECIMAL CHECK (custoReparacion>=0),
	PRIMARY KEY(numero),
	FOREIGN KEY (usuario) REFERENCES usuario(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY (area,instalacion) REFERENCES area(codArea,instalacion) 
	ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE rexistroFisioloxico(
	socio			VARCHAR(25) NOT NULL,
	dataMarca 		TIMESTAMP NOT NULL,
	peso			DECIMAL CHECK (peso>=0),
	altura			DECIMAL CHECK (altura>=0),
	bfp				DECIMAL CHECK (bfp>=0),
	tensionAlta		INT CHECK (tensionAlta>=0),
	tensionBaixa	INT CHECK (tensionBaixa>=0),
	ppm				INT CHECK (ppm>=0),
	comentario		VARCHAR(200),
	PRIMARY KEY (socio,dataMarca),
	FOREIGN KEY (socio) REFERENCES socio(login) 
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE realizarActividade(
	dataActividade 	TIMESTAMP NOT NULL,
	area 			INT NOT NULL,
	instalacion 	INT NOT NULL,
	usuario 		VARCHAR(25) NOT NULL,
	valoracion 		SMALLINT CHECK (valoracion>=0 AND valoracion<=5),
	PRIMARY KEY (dataActividade,area,instalacion,usuario),
	FOREIGN KEY (dataActividade,area,instalacion) REFERENCES actividade(dataActividade,area,instalacion)
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (usuario) REFERENCES usuario(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);
 
CREATE TABLE realizarCurso(
	curso		INT NOT NULL,
	usuario		VARCHAR(25) NOT NULL,
	PRIMARY KEY (curso,usuario),
	FOREIGN KEY (curso) REFERENCES curso(codCurso) 
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (usuario) REFERENCES usuario(login) 
	ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE enviarMensaxe(
	emisor		VARCHAR(25) NOT NULL,
	receptor	VARCHAR(25) NOT NULL,
	dataEnvio 	TIMESTAMP 	NOT NULL,
	contido 	VARCHAR(500) NOT NULL,
	lido		BOOLEAN	DEFAULT FALSE,
	PRIMARY KEY (emisor,receptor,dataEnvio),
	FOREIGN KEY (emisor) REFERENCES usuario(login) 
	ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (receptor) REFERENCES usuario(login) 
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE estarCapacitado(
	tipoActividade 	INT NOT NULL, 
	persoal		VARCHAR(25) NOT NULL,
	FOREIGN KEY (persoal) REFERENCES persoal(login) 
	ON UPDATE CASCADE ON DELETE CASCADE,	
	FOREIGN KEY (tipoActividade) REFERENCES tipoActividade(codTipoActividade) 
	ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE OR REPLACE VIEW vistapersoal AS
SELECT 
	us.login,
	pf.dni,
	pf.nome,
	pf.dificultades,
	pf.datanacemento,
	us.contrasinal,
	us.numtelefono,
	us.correoelectronico,
	us.iban,
	us.dataalta,
	us.databaixa,
	pe.nuss,
	pe.profesoractivo
FROM persoafisica pf
JOIN usuario us ON pf.usuariopersoal = us.login
JOIN persoal pe ON pe.login = us.login;


CREATE OR REPLACE VIEW  vistasocio AS 
	SELECT 
	us.login,
	contrasinal,
	dni,
	nome,
	tarifa,
	dificultades,
	datanacemento,
	iban,
	dataalta,
	databaixa,
	numtelefono,
	correoelectronico
FROM socio AS so 
JOIN persoaFisica AS pf ON so.login=pf.usuariosocio 
JOIN usuario AS us ON us.login=so.login;


CREATE OR REPLACE FUNCTION insertarActividades() RETURNS TRIGGER AS $$
	DECLARE
		tr RECORD;
	BEGIN
		FOR tr IN
			SELECT * FROM actividade WHERE curso=NEW.curso
		LOOP
			INSERT INTO realizarActividade(dataActividade,area,instalacion,usuario) VALUES(tr.dataActividade,tr.area,tr.instalacion,NEW.usuario);
		END LOOP;
		RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION comprobarAreaLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,parea INT,pinstalacion INT) RETURNS boolean AS
$func$
  SELECT NOT EXISTS (
   	SELECT 1 FROM actividade 
	WHERE 
	(
		pdataActividade>=dataactividade AND pdataActividade <(dataactividade + (duracion * interval '1 hour'))
		OR
		(pdataactividade + (pduracion * interval '1 hour'))>dataactividade AND (pdataactividade + (pduracion * interval '1 hour')) <=(dataactividade + (duracion * interval '1 hour'))
	)
	AND (area=parea AND instalacion=pinstalacion)
	)
$func$ LANGUAGE sql STABLE;


CREATE OR REPLACE FUNCTION comprobarProfesorLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,pprofesor VARCHAR(20)) RETURNS boolean AS
$func$
SELECT NOT EXISTS (
   	SELECT 1 FROM actividade 
	WHERE 
	(
		pdataActividade>=dataactividade AND pdataActividade <(dataactividade + (duracion * interval '1 hour'))
		OR
		(pdataactividade + (pduracion * interval '1 hour'))>dataactividade AND (pdataactividade + (pduracion * interval '1 hour')) <=(dataactividade + (duracion * interval '1 hour'))
	)
	AND (profesor=pprofesor)
)
$func$ LANGUAGE sql STABLE;


CREATE OR REPLACE  FUNCTION crearSecuenciaArea() RETURNS TRIGGER LANGUAGE plpgsql
AS $$
BEGIN
  EXECUTE format('create sequence secuencia_area_%s', NEW.codinstalacion);
  RETURN NEW;
END
$$;


CREATE OR REPLACE FUNCTION engadirSecuenciaArea() RETURNS TRIGGER LANGUAGE plpgsql
AS $$
BEGIN
  NEW.codArea := nextval('secuencia_area_' || NEW.instalacion);
  RETURN NEW;
END
$$;

CREATE OR REPLACE  FUNCTION crearSecuenciaMaterial() RETURNS TRIGGER LANGUAGE plpgsql
AS $$
BEGIN
  EXECUTE format('create sequence secuencia_material_%s', NEW.codTipoMaterial);
  RETURN NEW;
END
$$;


CREATE OR REPLACE FUNCTION engadirSecuenciaMaterial() RETURNS TRIGGER LANGUAGE plpgsql
AS $$
BEGIN
  NEW.codMaterial := nextval('secuencia_material_' || NEW.tipoMaterial);
  RETURN NEW;
END
$$;


CREATE TRIGGER crear_secuencia_area AFTER INSERT ON instalacion FOR EACH ROW EXECUTE PROCEDURE crearSecuenciaArea();
CREATE TRIGGER engadir_secuencia_area BEFORE INSERT ON area FOR EACH ROW EXECUTE PROCEDURE engadirSecuenciaArea();


CREATE TRIGGER crear_secuencia_material AFTER INSERT ON tipoMaterial FOR EACH ROW EXECUTE PROCEDURE crearSecuenciaMaterial();
CREATE TRIGGER engadir_secuencia_material BEFORE INSERT ON material FOR EACH ROW EXECUTE PROCEDURE engadirSecuenciaMaterial();


CREATE TRIGGER insertarActividadesCurso AFTER INSERT ON realizarcurso FOR EACH ROW EXECUTE PROCEDURE insertarActividades();

ALTER TABLE actividade ADD CONSTRAINT comprobar_libre CHECK (comprobarAreaLibre(dataactividade,duracion,area,instalacion) AND comprobarProfesorLibre(dataactividade,duracion,profesor));

