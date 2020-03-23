SELECT * FROM cursos;

SELECT * FROM actividades;

INSERT INTO actividades VALUES ('2020-05-12 19:09',1,1,1,1,'barjita69','Pochedad',23.0);

INSERT INTO actividades VALUES ('2020-05-15 19:09',1,1,1,1,'barjita69','Pochedad',23.0);
SELECT * FROM actividades;



CREATE OR REPLACE FUNCTION insertarActividades() RETURNS TRIGGER AS $$
	DECLARE
		tr RECORD;
	BEGIN
		FOR tr IN
			SELECT * FROM actividades WHERE curso=NEW.curso
		LOOP
			INSERT INTO realizarActividades(dataActividade,area,instalacion,usuario) VALUES(tr.dataActividade,tr.area,tr.instalacion,NEW.usuario);
		END LOOP;
		RETURN NEW;
END;
$$ LANGUAGE plpgsql

CREATE TRIGGER insertarActividadesCurso AFTER INSERT ON realizarcursos FOR EACH ROW EXECUTE PROCEDURE insertarActividades();


select * from cursos;

select * from realizarCursos;
DELETE FROM realizarCursos;



select * from realizarActividades;

SELECT * FROM cursos;
SELECT * FROM actividades;

INSERT INTO realizarCursos VALUES(1,'david_pocho62')
