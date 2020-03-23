SELECT * FROM cursos;

SELECT * FROM actividades;

INSERT INTO actividades VALUES ('2020-05-12 19:09',1,1,1,1,'barjita69','Pochedad',23.0);

INSERT INTO actividades VALUES ('2020-05-15 19:09',1,1,1,1,'barjita69','Pochedad',23.0);
SELECT * FROM actividades;



create or replace function insertarActividades() returns trigger as $$
DECLARE
tr record;
BEGIN
    FOR tr IN
		SELECT login FROM usuarios
	LOOP
		INSERT INTO realizarActividades(tr.dataActividade,tr.area,tr.instalacion,tr.)
	END LOOP;
END;
$$ LANGUAGE plpgsql

