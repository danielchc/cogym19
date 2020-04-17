SELECT 1 FROM actividade 
WHERE 
(
	'2029-10-02 10:00:00'>=dataactividade AND '2029-10-02 10:00:00' <(dataactividade + (duracion * interval '1 hour'))
	OR
	'2029-10-02 12:00:00'>dataactividade AND '2029-10-02 12:00:00' <=(dataactividade + (duracion * interval '1 hour'))
)
AND (area=1 AND instalacion=1)


SELECT 1 FROM actividade 
WHERE 
(
	'2019-10-02 10:00:00'>=dataactividade AND '2019-10-02 10:00:00' <(dataactividade + (duracion * interval '1 hour'))
	OR
	'2019-10-02 12:00:00'>dataactividade AND '2019-10-02 12:00:00' <=(dataactividade + (duracion * interval '1 hour'))
)
AND profesor='pocho'


CREATE OR REPLACE FUNCTION comprobarAreaLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,parea INT,pinstalacion INT) RETURNS boolean AS
$func$
SELECT EXISTS (
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
SELECT EXISTS (
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


SELECT comprobar('2019-10-02 9:00:00',0.5,1,1)

SELECT * FROM actividade;

