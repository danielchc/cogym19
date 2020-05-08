--Con esta función compróbase a condicion de que un curso non supere o aforo máximo.
--Suporemos que o aforo máximo é o mínimo dos aforos máximos das áreas nas que teñen lugar as actividades do curso.
CREATE OR REPLACE FUNCTION comprobarAforoMaximoCurso(codCurso INT) RETURNS boolean AS
$func$
	--Pasamos o código do curso e comprobamos se o mínimo dos aforos máximos supera ao número de participantes.
	--Se é así, poderemos insertar un novo participante, se non, non se poderá
	SELECT MIN(aforomaximo)>(SELECT COUNT(*) FROM realizarcurso WHERE curso=codCurso) AS podese
	FROM actividade AS ac JOIN area AS ar ON ac.area=ar.codarea AND ac.instalacion=ar.instalacion 
	WHERE ac.curso=codCurso;
$func$ LANGUAGE sql STABLE;

--Na taboa de realización de cursos engadimos a restricción de aforo máximo:
ALTER TABLE realizarcurso ADD CONSTRAINT comprobarAforoCursoMaximo CHECK (comprobarAforoMaximoCurso(curso));
