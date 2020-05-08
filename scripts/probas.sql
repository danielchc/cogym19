ALTER TABLE actividade  DROP CONSTRAINT comprobar_libre;


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

ALTER TABLE actividade ADD CONSTRAINT comprobar_libre CHECK (comprobarAreaLibre(dataactividade,duracion,area,instalacion) AND comprobarProfesorLibre(dataactividade,duracion,profesor));



CREATE FUNCTION crearSecuenciaArea() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
  execute format('create sequence secuencia_area_%s', NEW.id);
  return NEW;
end
$$;


CREATE FUNCTION engadirSecuenciaArea() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
  NEW.seq := nextval('secuencia_area_' || NEW.instalacion);
  RETURN NEW;
end
$$;


CREATE TRIGGER crear_secuencia_area AFTER INSERT ON instalacion FOR EACH ROW EXECUTE PROCEDURE crearSecuenciaArea();
CREATE TRIGGER engadir_secuencia_area BEFORE INSERT ON area FOR EACH ROW EXECUTE PROCEDURE engadirSecuenciaArea();






