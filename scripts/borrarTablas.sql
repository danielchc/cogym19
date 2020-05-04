--Borro o check que comproba que hai actividades
ALTER TABLE actividade  DROP CONSTRAINT comprobar_libre;

--Borramos os triggers
DROP TRIGGER IF EXISTS insertarActividadesCurso ON realizarcurso CASCADE;
DROP TRIGGER IF EXISTS crear_secuencia_area ON instalacion CASCADE;
DROP TRIGGER IF EXISTS engadir_secuencia_area ON area CASCADE;
DROP TRIGGER IF EXISTS crear_secuencia_material ON tipoMaterial CASCADE;
DROP TRIGGER IF EXISTS engadir_secuencia_material ON material CASCADE;

--Borramos as funcions
DROP FUNCTION IF EXISTS comprobarProfesorLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,pprofesor VARCHAR(20)) ;
DROP FUNCTION IF EXISTS comprobarAreaLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,parea INT,pinstalacion INT);
DROP FUNCTION IF EXISTS insertarActividades();
DROP FUNCTION IF EXISTS crearSecuenciaArea();
DROP FUNCTION IF EXISTS engadirSecuenciaArea();
DROP FUNCTION IF EXISTS crearSecuenciaMaterial();
DROP FUNCTION IF EXISTS engadirSecuenciaMaterial();

--Borramos as vistas
DROP VIEW IF EXISTS vistapersoal;
DROP VIEW IF EXISTS vistasocio;
--Borramos as taboas
DROP TABLE IF EXISTS estarCapacitado;
DROP TABLE IF EXISTS enviarMensaxe;
DROP TABLE IF EXISTS realizarCurso;
DROP TABLE IF EXISTS rexistroFisioloxico;
DROP TABLE IF EXISTS realizarActividade;
DROP TABLE IF EXISTS incidenciaArea;
DROP TABLE IF EXISTS incidenciaMaterial;
DROP TABLE IF EXISTS actividade;
DROP TABLE IF EXISTS curso;
DROP TABLE IF EXISTS tipoActividade;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS tipoMaterial;
DROP TABLE IF EXISTS area;
DROP TABLE IF EXISTS instalacion;
DROP TABLE IF EXISTS persoafisica;
DROP TABLE IF EXISTS persoal;
DROP TABLE IF EXISTS socio;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS tarifa;

--Creamos un procedemento para borrar secuencias creadas polos triggers
CREATE OR REPLACE FUNCTION borrarSecuencias() RETURNS void AS $$
	DECLARE
		tr RECORD;
	BEGIN
		FOR tr IN
			SELECT * FROM pg_class WHERE relkind = 'S' AND (relname LIKE 'secuencia\_material\_%' OR relname LIKE 'secuencia\_area\_%')
		LOOP
			RAISE NOTICE 'Borrar secuencia: %','DROP SEQUENCE IF EXISTS ' || tr.relname;
			EXECUTE 'DROP SEQUENCE IF EXISTS ' || tr.relname;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;
--Executamos o procedemento para borralas
DO $$ BEGIN
    PERFORM borrarSecuencias();
END $$;
--Borramos o procedemento
DROP FUNCTION IF EXISTS borrarSecuencias();