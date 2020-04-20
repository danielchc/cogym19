ALTER TABLE actividade  DROP CONSTRAINT comprobar_libre;
DROP TRIGGER IF EXISTS insertarActividadesCurso ON realizarcurso CASCADE;
DROP TRIGGER IF EXISTS crear_secuencia_area ON instalacion CASCADE;
DROP TRIGGER IF EXISTS engadir_secuencia_area ON area CASCADE;
DROP FUNCTION IF EXISTS comprobarProfesorLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,pprofesor VARCHAR(20)) ;
DROP FUNCTION IF EXISTS comprobarAreaLibre(pdataActividade TIMESTAMP,pduracion DECIMAL,parea INT,pinstalacion INT);
DROP FUNCTION IF EXISTS insertarActividades();
DROP FUNCTION IF EXISTS crearSecuenciaArea();
DROP FUNCTION IF EXISTS engadirSecuenciaArea();
DROP VIEW IF EXISTS vistapersoal;
DROP VIEW IF EXISTS vistasocio;
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
