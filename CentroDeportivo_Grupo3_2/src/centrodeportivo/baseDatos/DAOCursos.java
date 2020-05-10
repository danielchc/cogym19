package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.usuarios.Persoal;
import centrodeportivo.aplicacion.obxectos.usuarios.Socio;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que conterá todos os métodos DAO relacionados na súa maioría con xestións dos cursos.
 */
public final class DAOCursos extends AbstractDAO {

    /**
     * Constructor do DAO de cursos
     *
     * @param conexion          Referencia á conexión coa base de datos.
     * @param fachadaAplicacion Referencia á fachada da parte de aplicación.
     */
    public DAOCursos(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        // Asignaremos estes atributos no constructor da clase pai ao que chamamos:
        super(conexion, fachadaAplicacion);
    }


    /**
     * Método que nos permite introducir os datos dun novo curso na base de datos.
     *
     * @param curso O curso a insertar
     * @throws ExcepcionBD Excepción asociada a problemas que puideron ocorrer na base de datos.
     */
    public void rexistrarCurso(Curso curso) throws ExcepcionBD {
        // Rexistraremos unicamente datos propios do curso (da súa táboa).
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Comezamos coa parte de SQL.
        try {
            // Intentaremos, en primeira instancia, rexistrar o curso:
            stmCursos = con.prepareStatement("INSERT INTO curso (nome, descricion, prezo, aberto) " +
                    "VALUES (?, ?, ?, ?) ");
            // Completamos a consulta:
            stmCursos.setString(1, curso.getNome());
            stmCursos.setString(2, curso.getDescricion());
            stmCursos.setFloat(3, curso.getPrezo());
            stmCursos.setBoolean(4, false);

            // Realizamos entón a actualización sobre a base de datos:
            stmCursos.executeUpdate();

            // O seguinte paso será recuperar o id do curso creado (usamos o nome, que é CK):
            stmCursos = con.prepareStatement("SELECT codCurso FROM curso WHERE nome = ?");
            // Completamos:
            stmCursos.setString(1, curso.getNome());

            // Realizamos a consulta:
            rsCursos = stmCursos.executeQuery();

            // Comprobamos se hai resultado: tería que haber un: o código do curso insertado.
            if (rsCursos.next()) {
                curso.setCodCurso(rsCursos.getInt("codCurso"));
            }

            // Se logramos acadar este punto, teremos toda a actualización feita: facemos o commit.
            con.commit();

        } catch (SQLException e) {
            // Lanzaremos a nosa propia excepción dende este punto:
            // Aquí farase o rollback se é necesario.
            throw new ExcepcionBD(con, e);
        } finally {
            // Peche dos statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que nos permite realizar modificacións na información xeral dun curso determinado.
     *
     * @param curso O curso do que se quere modificar a información, cos datos modificados.
     * @throws ExcepcionBD Excepción asociada a problemas producidos coa base de datos.
     */
    public void modificarCurso(Curso curso) throws ExcepcionBD {
        // Neste método simplemente recollemos a modificación de datos principais do curso.
        // As actividades poderán ser modificadas nun punto diferente.
        PreparedStatement stmCursos = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        try {
            // Intentamos levar a cabo a actualización: modificación do curso:
            stmCursos = con.prepareStatement("UPDATE curso " +
                    " SET nome = ?, " +
                    "     descricion = ?, " +
                    "     prezo = ? " +
                    " WHERE codCurso = ? ");

            // Completamos a sentenza anterior cos '?':
            stmCursos.setString(1, curso.getNome());
            stmCursos.setString(2, curso.getDescricion());
            stmCursos.setFloat(3, curso.getPrezo());
            stmCursos.setInt(4, curso.getCodCurso());

            // Realizamos a actualización:
            stmCursos.executeUpdate();

            // Unha vez feita, teremos rematado. Facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a excepción que se obteña:
            throw new ExcepcionBD(con, e);
        } finally {
            // Pechamos os statement.
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que nos permite levar a cabo a activación dun curso.
     *
     * @param curso Os datos do curso que se quere activar.
     * @throws ExcepcionBD Excepción asociada a problemas producidos na base de datos.
     */
    public void abrirCurso(Curso curso) throws ExcepcionBD {
        // Introduciremos que o curso está aberto para que a xente poida comezar a rexistrarse nel:
        PreparedStatement stmCursos = null;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos levar a cabo a apertura do curso:
        try {
            // Facemos un update, poñendo a condición de apertura a true:
            stmCursos = con.prepareStatement("UPDATE curso" +
                    " SET aberto = ?" +
                    " WHERE codcurso = ?");
            // Establecemos os valores dos campos con '?':
            stmCursos.setBoolean(1, true);
            stmCursos.setInt(2, curso.getCodCurso());

            // Inténtase realizar a actualización:
            stmCursos.executeUpdate();

            // Rematado isto, podemos poñer o commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos a nosa propia excepción:
            throw new ExcepcionBD(con, e);
        } finally {
            // Intentamos pechar o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que nos permite cancelar un curso, e polo tanto borrar a súa información da base de datos.
     *
     * @param curso   O curso que se quere borrar.
     * @param mensaxe A mensaxe que se envía aos participantes polo borrado.
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante o borrado na base de datos.
     */
    public void cancelarCurso(Curso curso, Mensaxe mensaxe) throws ExcepcionBD {
        // Haberá que realizar neste caso varias tarefas á vez, pero xa se realizarán por ter cascade:
        // Hai que borrar o curso e con el borraranse actividades e participantes.
        // O que faremos tamén será neste caso un aviso automático de que o curso se cancelou.

        PreparedStatement stmCursos = null;
        PreparedStatement stmUsuarios = null;
        PreparedStatement stmMensaxes = null;
        ResultSet rsUsuarios;
        Connection con;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos levar a cabo o borrado:
        try {
            // Para comezar, recolleremos todos os participantes que neste momento estaban apuntados no curso:
            stmUsuarios = con.prepareStatement("SELECT usuario FROM realizarcurso WHERE curso = ?");
            stmUsuarios.setInt(1, curso.getCodCurso());
            // Executamos a consulta sobre a base de datos:
            rsUsuarios = stmUsuarios.executeQuery();

            // Feito isto, agora levamos a cabo o borrado como normal:
            stmCursos = con.prepareStatement("DELETE FROM curso WHERE codCurso = ?");
            stmCursos.setInt(1, curso.getCodCurso());

            // Executamos a actualización: borraranse curso, actividades e participacións.
            stmCursos.executeUpdate();

            // O seguinte paso é procesar a consulta dos usuarios. Facémola antes dado que o curso agora estará borrado
            // e non poderíamos coñecer os seus participantes.
            stmMensaxes = con.prepareStatement("INSERT INTO enviarmensaxe (emisor, receptor, contido)" +
                    "VALUES(?, ?, ?)");
            // Cambiamos os campos variables:
            stmMensaxes.setString(1, mensaxe.getEmisor().getLogin());
            stmMensaxes.setString(3, mensaxe.getContido());

            // Entón agora imos procesando ese resultado:
            while (rsUsuarios.next()) {
                stmMensaxes.setString(2, rsUsuarios.getString("usuario"));
                // Agora realizamos o envío:
                stmMensaxes.executeUpdate();
            }

            // Feito isto, facemos o commit: teremos feita a actualización sobre a base de datos que queríamos:
            // o curso borrouse e todos os participantes foron informados.
            con.commit();

        } catch (SQLException e) {
            // Lanzaremos unha excepción propia:
            throw new ExcepcionBD(con, e);
        } finally {
            // Pecharemos os statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    /**
     * Método que nos permite consultar os cursos que hai almacenados na base de datos.
     *
     * @param curso Curso polo que se realiza a busca.
     * @return Se curso vale null, devolveranse todos os cursos, noutro caso, filtraranse polo nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursos(Curso curso) {
        // Esta é a consulta que se usará dende a parte de persoal:
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        ArrayList<Curso> resultado = new ArrayList<>();
        String consulta = "";

        // Recuperamos a conexión:
        Connection con = super.getConexion();

        // Intentamos levar a cabo a consulta dos cursos. O resultado que se vai a ofrecer combina diferentes cuestións.
        try {
            // A búsqueda que poderá facer o persoal non ten sentido que inclúa campos como número de actividades ou un rango de prezos.
            // No noso caso centrarémonos en buscar simplemente por un campo, o nome do curso.
            consulta = "SELECT c.codcurso, c.nome, c.aberto," +
                    " count(distinct dataactividade) as numactividades, DATE(min(a.dataactividade)) as datainicio, sum(a.duracion) as duracion," +
                    " DATE(max(a.dataactividade)) as datafin " +
                    " FROM curso as c LEFT JOIN actividade as a " +
                    "   ON (c.codcurso = a.curso)";

            // Pode ser que non pasemos curso (o pasemos como null) ou que pasemos algo.
            // Se non pasamos ningún curso, non engadimos o filtro de busca, se non si:
            if (curso != null) {
                consulta += " WHERE lower(c.nome) like lower(?) ";
            }

            // Agrupamos tamén polo código do curso:
            consulta += "  GROUP BY c.codcurso";

            // Preparamos entón o statement de cursos para levar a cabo a consulta:
            stmCursos = con.prepareStatement(consulta);

            // Completamos a consulta (se procede):
            if (curso != null) {
                stmCursos.setString(1, "%" + curso.getNome() + "%");
            }

            // Intentamos levala a cabo:
            rsCursos = stmCursos.executeQuery();

            // Unha vez feita a consulta, tentamos recuperar os resultados:
            while (rsCursos.next()) {
                // Imos creando instancias de cursos cos datos recuperados:
                resultado.add(new Curso(rsCursos.getInt("codcurso"), rsCursos.getString("nome"),
                        rsCursos.getBoolean("aberto"), rsCursos.getFloat("duracion"),
                        rsCursos.getInt("numactividades"), rsCursos.getDate("datainicio"),
                        rsCursos.getDate("datafin")));
            }
            // Rematado isto, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Tentamos facer rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos os cursos consultados:
        return resultado;
    }

    /**
     * Método que nos permite recuperar datos máis concretos dun curso. Non só datos contidos na táboa de cursos,
     * máis información aínda.
     *
     * @param curso Información do curso do que se queren recuperar os datos (o atributo importante é o código).
     * @return Datos completos do curso procurado.
     */
    public Curso recuperarDatosCurso(Curso curso) {
        // Neste método recuperaremos todas as actividades do curso, os datos da consulta e os participantes:
        PreparedStatement stmCursos = null;
        PreparedStatement stmSocios = null;
        PreparedStatement stmActividades = null;
        ResultSet rsCursos;
        ResultSet rsActividades;
        ResultSet rsSocios;
        Connection con;
        Curso resultado = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Comezaremos recuperando todos os datos do curso:
        try {
            stmCursos = con.prepareStatement("SELECT c.codcurso, c.nome, c.descricion, c.prezo, c.aberto," +
                    " DATE(min(a.dataactividade)) as datainicio, DATE(max(a.dataactividade)) as datafin" +
                    " FROM curso as c LEFT JOIN actividade as a " +
                    "   ON (c.codcurso = a.curso)" +
                    " WHERE c.codcurso = ?" +
                    " GROUP BY c.codcurso");  // En principio non sería preciso o group by, porque só temos un curso, pero inda así reflexamos que agrupamos as actividades.


            // Completamos a consulta.
            stmCursos.setInt(1, curso.getCodCurso());

            // Facemos a consulta:
            rsCursos = stmCursos.executeQuery();

            // Debería haber un resultado, nese caso será o curso que usaremos:
            if (rsCursos.next()) {
                resultado = new Curso(rsCursos.getInt("codcurso"),
                        rsCursos.getString("nome"),
                        rsCursos.getString("descricion"),
                        rsCursos.getFloat("prezo"),
                        rsCursos.getBoolean("aberto"),
                        rsCursos.getDate("dataInicio"),
                        rsCursos.getDate("dataFin"));

                // Tendo o curso creado, procuraremos as súas actividades
                stmActividades = con.prepareStatement(
                        "SELECT " +
                                "ac.dataactividade, " +
                                "ac.tipoactividade, " +
                                "ac.area, " +
                                " ac.instalacion, " +
                                "ac.profesor, " +
                                "ac.nome as nomeactividade, " +
                                "ac.duracion, " +
                                "ar.nome as nomearea, " +
                                "inst.nome as nomeInstalacion" +
                                " FROM actividade as ac, area as ar, instalacion as inst" +
                                " WHERE " +
                                "ac.area = ar.codarea " +
                                "and ac.instalacion = ar.instalacion " +
                                "and ar.instalacion=inst.codInstalacion " +
                                "and curso = ? ");

                // Completamos a consulta:
                stmActividades.setInt(1, resultado.getCodCurso());

                // Executamos a consulta:
                rsActividades = stmActividades.executeQuery();

                // O seguinte paso é procesar o resultado:
                while (rsActividades.next()) {
                    // Imos engadindo as actividades unha a unha, de momento non nos interesa recuperar moita máis información sobre a actividade.
                    resultado.getActividades().add(new Actividade(rsActividades.getTimestamp("dataactividade"),
                            rsActividades.getString("nomeactividade"),
                            rsActividades.getFloat("duracion"),
                            new Area(rsActividades.getInt("area"),
                                    new Instalacion(rsActividades.getInt("instalacion"), rsActividades.getString("nomeInstalacion")),
                                    rsActividades.getString("nomearea")),
                            new TipoActividade(rsActividades.getInt("tipoactividade")),
                            resultado,
                            new Persoal(rsActividades.getString("profesor"))));
                }

                // Feito isto, vamos a consultar os socios participantes:
                stmSocios = con.prepareStatement("SELECT vs.login, vs.nome, vs.dificultades, " +
                        " date_part('year', age(vs.datanacemento)) as idade, vs.numtelefono, vs.correoelectronico" +
                        " FROM vistasocio as vs JOIN realizarcurso as rc" +
                        " ON (vs.login = rc.usuario)" +
                        " WHERE rc.curso = ?");

                // Completamos a consulta:
                stmSocios.setInt(1, resultado.getCodCurso());

                // Realizámola:
                rsSocios = stmSocios.executeQuery();

                // Procesamos o resultado:
                while (rsSocios.next()) {
                    // Imos engadindo os participantes:
                    resultado.getParticipantes().add(new Socio(rsSocios.getString("login"),
                            rsSocios.getString("nome"),
                            rsSocios.getString("dificultades"),
                            rsSocios.getInt("idade"),
                            rsSocios.getString("numtelefono"),
                            rsSocios.getString("correoelectronico")));
                }

                // Con isto teremos buscado o necesario sobre o curso, polo que temos rematada a consulta.
                con.commit();
            }
        } catch (SQLException e) {
            // En caso de excepción, imprimiríamos o stack trace e faríamos o rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Tentamos pechar os statements:
            try {
                if (stmActividades != null) {
                    stmActividades.close();
                }
                if (stmSocios != null) {
                    stmSocios.close();
                }
                // O de cursos é o único que sabemos certamente que non é null.
                stmCursos.close();
            } catch (SQLException e) {
                // Imprimiríamos o stack trace se houbo problemas.
                e.printStackTrace();
            }
        }
        // Devolvemos o resultado:
        return resultado; // Pode que sexa null, se a consulta non puido efectuarse.
    }

    /**
     * Método que nos permite recuperar información suficiente do curso como para elaborar o informe que ofrecer ao
     * usuario que o consulta.
     *
     * @param curso Información do curso do que se queren recuperar os datos para o informe.
     * @return Datos completos do curso, incluíndo información adicional necesaria para a elaboración do informe.
     */
    public Curso informeCurso(Curso curso) {
        // Neste método recuperaranse os datos que xa recuperamos en recuperarDatosCurso, pero inda máis:
        // Dadas as diferenzas co outro, decidín manter este por separado.
        PreparedStatement stmCursos = null;
        PreparedStatement stmActividades = null;
        PreparedStatement stmPersoal = null;
        PreparedStatement stmSocios = null;
        ResultSet rsCursos;
        ResultSet rsActividades;
        ResultSet rsPersoal;
        ResultSet rsSocios;
        Connection con;
        Curso resultado = null;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Imos ir agora paso por paso con todas as consultas que hai que ir levando a cabo:
        try {
            // Comezaremos por recuperar os datos basicos do propio curso. Agora, ademais, teremos que recuperar a data
            // de finalización e a media do curso, entre outros, o que leva á complexidade amosada:
            stmCursos = con.prepareStatement("SELECT c.codcurso, c.nome, c.descricion, c.prezo, c.aberto," +
                    " count(distinct dataactividade) as numactividades, DATE(min(a.dataactividade)) as datainicio, sum(a.duracion) as duracion," +
                    " DATE(max(a.dataactividade)) as datafin, " +
                    " count(distinct a.profesor) as numProfesores, " +
                    " avg(rA.valoracion) as valMediaCurso " +
                    " FROM curso as c, actividade as a " +
                    "   JOIN realizarActividade as rA USING (dataActividade, area, instalacion) " +
                    " WHERE c.codcurso = a.curso" +
                    "   and c.codcurso = ?" +
                    " GROUP BY c.codcurso");

            // Completamos a consulta cos ?
            stmCursos.setInt(1, curso.getCodCurso());

            // Executamos a consulta:
            rsCursos = stmCursos.executeQuery();

            // Procesamos o resultado:
            if (rsCursos.next()) {
                // Se hai resultado, poderemos seguir co resto de consultas.
                // En primeiro lugar, montamos o curso:
                resultado = new Curso(rsCursos.getInt("codcurso"),
                        rsCursos.getString("nome"),
                        rsCursos.getString("descricion"),
                        rsCursos.getFloat("prezo"),
                        rsCursos.getBoolean("aberto"),
                        rsCursos.getFloat("duracion"),
                        rsCursos.getInt("numactividades"),
                        rsCursos.getDate("datainicio"),
                        rsCursos.getDate("datafin"),
                        rsCursos.getInt("numprofesores"),
                        rsCursos.getFloat("valMediaCurso"));

                // A continuación, vamos a facer a busca dos datos das actividades, só que agora teremos en conta
                // tamén as valoracións realizadas.
                // Hai que, para contemplar isto, considerar outra táboa (realizaractividade) e agrupar por actividades.
                stmActividades = con.prepareStatement("SELECT ac.dataactividade, ac.tipoactividade, ac.area, " +
                        " ac.instalacion, ac.profesor, ac.nome as nomeactividade, ac.duracion, ar.nome as nomearea," +
                        " avg(rA.valoracion) as valMedia " +
                        " FROM actividade as ac NATURAL JOIN realizaractividade as ra, area as ar " +
                        " WHERE ac.area = ar.codarea and ac.instalacion = ar.instalacion" +
                        "  and curso = ? " +
                        " GROUP BY ac.area, ar.nome, ac.instalacion, ac.dataactividade");

                // Completamos a consulta:
                stmActividades.setInt(1, curso.getCodCurso());

                // Executamos a consulta:
                rsActividades = stmActividades.executeQuery();

                // Recuperamos os resultados:
                while (rsActividades.next()) {
                    // Imos engadindo actividade a actividade:
                    resultado.getActividades().add(new Actividade(rsActividades.getTimestamp("dataactividade"),
                            rsActividades.getString("nomeactividade"),
                            rsActividades.getFloat("duracion"),
                            new Area(rsActividades.getInt("area"),
                                    new Instalacion(rsActividades.getInt("instalacion")),
                                    rsActividades.getString("nomearea")),
                            new TipoActividade(rsActividades.getInt("tipoactividade")),
                            resultado,
                            new Persoal(rsActividades.getString("profesor")),
                            rsActividades.getFloat("valMedia")));
                }

                // Agora toca consultar os participantes, coma na outra consulta
                // A razón é que aproveitaremos esta consulta para refrescar tamén a información existente.
                stmSocios = con.prepareStatement("SELECT vs.login, vs.nome, vs.dificultades, " +
                        " date_part('year', age(vs.datanacemento)) as idade, vs.numtelefono, vs.correoelectronico" +
                        " FROM vistasocio as vs JOIN realizarcurso as rc" +
                        " ON (vs.login = rc.usuario)" +
                        " WHERE rc.curso = ?");

                // Completamos a consulta:
                stmSocios.setInt(1, curso.getCodCurso());

                // Realizamos a consulta:
                rsSocios = stmSocios.executeQuery();

                // Procesamos o resultado:
                while (rsSocios.next()) {
                    // Imos engadindo participante a participante:
                    resultado.getParticipantes().add(new Socio(rsSocios.getString("login"),
                            rsSocios.getString("nome"),
                            rsSocios.getString("dificultades"),
                            rsSocios.getInt("idade"),
                            rsSocios.getString("numtelefono"),
                            rsSocios.getString("correoelectronico")));
                }

                // Quédanos unha última consulta para recuperar ao persoal e as súas valoracións medias.
                stmPersoal = con.prepareStatement("SELECT vp.nome, vp.login, avg(ra.valoracion) as valoracion " +
                        " FROM vistapersoal as vp, actividade as ac NATURAL JOIN realizaractividade as ra" +
                        " WHERE vp.login = ac.profesor" +
                        "   and ac.curso = ?" +
                        " GROUP BY vp.login, vp.nome");

                // Completamos a consulta:
                stmPersoal.setInt(1, curso.getCodCurso());

                // Executamos a consulta:
                rsPersoal = stmPersoal.executeQuery();

                // Procesamos o resultado:
                while (rsPersoal.next()) {
                    resultado.getProfesores().add(new Persoal(rsPersoal.getString("login"),
                            rsPersoal.getString("nome"),
                            rsPersoal.getFloat("valoracion")));
                }

                // Rematado, facemos o commit:
                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Tentamos pechar os statements:
            try {
                if (stmActividades != null) {
                    stmActividades.close();
                }
                if (stmSocios != null) {
                    stmSocios.close();
                }
                if (stmPersoal != null) {
                    stmPersoal.close();
                }
                // O de cursos é o único que sabemos certamente que non é null.
                stmCursos.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Devolvemos o curso correspondente co recuperado:
        return resultado;
    }

    /**
     * Método que nos permite comprobar que non existe un curso rexistrado co mesmo nome.
     *
     * @param curso O curso que se quere validar.
     * @return True se non existe outro curso diferente que teña o mesmo nome ca este, False noutro caso.
     */
    public boolean comprobarExistencia(Curso curso) {
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;
        boolean resultado = false;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos facer a consulta:
        try {
            // Controlamos que o curso está creado ou non a través do código do curso, polo que a veces teremos que
            // incluílo e a veces non:
            String consulta = "SELECT * FROM curso" +
                    " WHERE lower(nome) = lower(?) ";

            // Introducimos o código do curso se non é null
            if (curso.getCodCurso() != null) {
                consulta += "   and codCurso != ? ";
            }

            // Preparamos o statement para a consulta:
            stmCursos = con.prepareStatement(consulta);

            // Completamos a consulta co campo do nome e do código do curso se é necesario:
            stmCursos.setString(1, curso.getNome());
            if (curso.getCodCurso() != null) {
                stmCursos.setInt(2, curso.getCodCurso());
            }
            // Realizamos a consulta.
            rsCursos = stmCursos.executeQuery();
            // Comprobamos se houbo resultados: se é así, existe un curso na base de datos (que non é o pasado) co mesmo nome.
            if (rsCursos.next()) {
                resultado = true;
            }

            // Feita a consulta, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Tentamos facer rollback en caso de excepción:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement de cursos:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o booleano:
        return resultado;
    }

    /**
     * Método que permite comprobar se un curso ten participantes.
     *
     * @param curso O curso para o que se quere validar dita información.
     * @return True se o curso ten participantes, False se non os ten.
     */
    public boolean tenParticipantes(Curso curso) {
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;
        // O resultado será un booleano que indicará se o curso ten participantes:
        boolean resultado = false;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos facer a consulta:
        try {
            // Buscamos se hai algún usuario realizando este curso:
            stmCursos = con.prepareStatement("SELECT * FROM realizarCurso WHERE curso = ? ");

            //C ompletamos a consulta co código do curso:
            stmCursos.setInt(1, curso.getCodCurso());

            // Realizamos a consulta:
            rsCursos = stmCursos.executeQuery();

            // Se hai resultados, o curso terá participantes:
            if (rsCursos.next()) {
                resultado = true;
            }

            // Completada a consulta, faise commit:
            con.commit();
        } catch (SQLException e) {
            // Tentamos facer rollback en caso de excepción:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o booleano para rematar:
        return resultado;
    }

    /**
     * Método que leva a cabo as comprobacións de se un curso está preparado para ser activado.
     *
     * @param curso O curso a activar.
     * @return True se o curso se pode activar, False en caso contrario.
     */
    public boolean listoParaActivar(Curso curso) {
        // Consultaremos se hai neste curso, como mínimo, dúas actividades, para poder abrilo ao público:
        // Ademais, comprobaremos se se cumpren as restriccións de data (que non comezara).
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;
        // Devolveremos un booleano como resultado:
        boolean resultado = false;

        // Recuperamos a conexión:
        con = super.getConexion();

        // Intentamos levar a cabo a consulta:
        try {
            // Buscamos o número de actividades que ten o curso preparadas:
            stmCursos = con.prepareStatement("SELECT count(*) as numAct, " +
                    " DATE(min(dataActividade)) > (current_date + interval '2 days') as inTime" +
                    " FROM actividade WHERE curso = ?");

            // Completamos a consulta:
            stmCursos.setInt(1, curso.getCodCurso());

            // Executamos consulta:
            rsCursos = stmCursos.executeQuery();

            // O seguinte paso é comprobar o resultado:
            if (rsCursos.next()) {
                if (rsCursos.getInt(1) >= 2 && rsCursos.getBoolean(2) == true) {
                    resultado = true;  // Devolveremos true se hai dúas actividades e inda non debería ter comezado o curso.
                }
            }

            // Feito isto, rematamos co commit:
            con.commit();
        } catch (SQLException e) {
            // Faríamos rollback e amosaríamos un erro:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado:
        return resultado;
    }

    /**
     * Método que nos permite consultar os cursos ós que un usuario se pode apuntar (contemplaranse todos aqueles cursos
     * que esten abertos, non esté apuntado e a maoires, que ainda non derán comezo). Contémplase a posibilidade de filtrar
     * os cursos polo nome dos mesmos:
     *
     * @param curso   Cursos que se empregará para o filtrado.
     * @param usuario Usuario polo que se realiza a busca.
     * @return Devolverase un ArrayList con todos os cursos nos que esta apuntado o usuario
     */
    public ArrayList<Curso> consultarCursosDisponhibles(Curso curso, Usuario usuario) {
        // Esta é a consulta que se usará dende a parte de socio:
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        ArrayList<Curso> resultado = new ArrayList<>();
        String consulta;

        // Recuperamos a conexión:
        Connection con = super.getConexion();

        // Intentamos levar a cabo a consulta dos cursos. O resultado que se vai a ofrecer combina diferentes cuestións.
        try {
            // A búsqueda que poderá facer o socio non ten sentido que inclúa campos como número de actividades ou un rango de prezos.
            // No noso caso centrarémonos en buscar simplemente por un campo, o nome do curso.
            consulta = "SELECT c.*, " +
                    "count(distinct dataactividade) as numactividades, DATE(min(a.dataactividade)) as datainicio, sum(a.duracion) as duracion, " +
                    "DATE(max(a.dataactividade)) as datafin " +
                    "FROM realizarcurso rc " +
                    "JOIN curso c on rc.curso = c.codcurso " +
                    "JOIN actividade a on c.codcurso = a.curso " +
                    "WHERE usuario != ? " +
                    "AND codcurso NOT IN (SELECT c2.codcurso FROM realizarcurso rc2 JOIN curso c2 on rc2.curso = c2.codcurso WHERE rc2.usuario = ? )" +
                    "AND a.dataactividade > now() ";


            // No caso de que pasemos o curso co nome:
            if (curso != null) {
                consulta += "AND lower(c.nome) like lower(?) ";
            }

            // Agrupamos tamén polo código do curso:
            consulta += "GROUP BY c.codcurso ";

            // Preparamos entón o statement de cursos para levar a cabo a consulta:
            stmCursos = con.prepareStatement(consulta);

            // Completamos a consulta:
            stmCursos.setString(1, usuario.getLogin());
            stmCursos.setString(2, usuario.getLogin());
            if (curso != null) {
                stmCursos.setString(3, "%" + curso.getNome() + "%");
            }

            // Intentamos levala a cabo:
            rsCursos = stmCursos.executeQuery();

            // Unha vez feita a consulta, xestionamos o resultado:
            while (rsCursos.next()) {
                // Imos creando instancias de cursos cos datos recuperados:
                resultado.add(new Curso(rsCursos.getInt("codcurso"), rsCursos.getString("nome"),
                        rsCursos.getBoolean("aberto"), rsCursos.getFloat("duracion"),
                        rsCursos.getInt("numactividades"), rsCursos.getDate("datainicio"),
                        rsCursos.getDate("datafin")));
            }
            // Rematado isto, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Intentase facer rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos os cursos consultados:
        return resultado;
    }

    /**
     * Método que nos permite consultar os cursos ós que está apuntado un usuario. Permitese unha busca filtrando polo
     * nome do curso.
     *
     * @param curso   Curso co que se filtra, no caso de que non sexa null, mediante o nome.
     * @param usuario Usuario co que se realiza a busca.
     * @return Devolverase un ArrayList con todos os cursos nos que esta apuntado o usuario e, se non é null,
     * coincidan co nome do curso pasado.
     */
    public ArrayList<Curso> consultarCursosUsuario(Curso curso, Usuario usuario) {
        // Esta é a consulta que se usará dende a parte de socio:
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        ArrayList<Curso> resultado = new ArrayList<>();
        String consulta;

        // Recuperamos a conexión:
        Connection con = super.getConexion();

        // Intentamos levar a cabo a consulta dos cursos. O resultado que se vai a ofrecer combina diferentes cuestións.
        try {
            // A búsqueda que poderá facer o socio non ten sentido que inclúa campos como número de actividades ou un rango de prezos.
            // No noso caso centrarémonos en buscar simplemente por un campo, o nome do curso.
            consulta = "SELECT c.*," +
                    "count(distinct dataactividade) as numactividades, DATE(min(a.dataactividade)) as datainicio, sum(a.duracion) as duracion, " +
                    "DATE(max(a.dataactividade)) as datafin " +
                    "FROM curso as c " +
                    "JOIN realizarcurso r " +
                    "on c.codcurso = r.curso " +
                    "JOIN actividade as a " +
                    "on c.codcurso = a.curso " +
                    "WHERE lower(c.nome) like lower(?) ";


            // No caso de que pasemos o curso co nome:
            if (usuario != null) {
                consulta += "AND usuario = ? ";
            }

            // Agrupamos tamén polo código do curso:
            consulta += "GROUP BY c.codcurso ";

            // Preparamos entón o statement de cursos para levar a cabo a consulta:
            stmCursos = con.prepareStatement(consulta);

            // Completamos a consulta:
            if (curso != null) {
                stmCursos.setString(1, "%" + curso.getNome() + "%");
            } else {
                stmCursos.setString(1, "%%");
            }
            if (usuario != null) {
                stmCursos.setString(2, usuario.getLogin());
            }

            // Intentamos levala a cabo:
            rsCursos = stmCursos.executeQuery();

            // Unha vez feita a consulta, xestionamos o resultado:
            while (rsCursos.next()) {
                // Imos creando instancias de cursos cos datos recuperados:
                resultado.add(new Curso(rsCursos.getInt("codcurso"), rsCursos.getString("nome"),
                        rsCursos.getBoolean("aberto"), rsCursos.getFloat("duracion"),
                        rsCursos.getInt("numactividades"), rsCursos.getDate("datainicio"),
                        rsCursos.getDate("datafin")));
            }
            // Rematado isto, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Intentase facer rollback:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos os cursos consultados:
        return resultado;
    }

    /**
     * Método que permite que un usuario se anote nun curso.
     *
     * @param curso   Curso ó que se vai a apuntar o usuario (o importante é o código).
     * @param usuario Usuario que se vai a apuntar ó curso (o importante é o login).
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta.
     */
    public void apuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmCurso = null;
        Connection con;

        // Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        // Preparamos a inserción:
        try {
            stmCurso = con.prepareStatement("INSERT INTO realizarcurso (curso, usuario) " +
                    " VALUES (?, ?)");
            // Establecemos os valores:
            stmCurso.setInt(1, curso.getCodCurso());
            stmCurso.setString(2, usuario.getLogin());

            // Realizamos a actualización:
            stmCurso.executeUpdate();

            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmCurso.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método que permite que un usuario se desapunte dun curso.
     *
     * @param curso   Curso ó que se vai a desapuntar o usuario (o importante é o código).
     * @param usuario Usuario que se vai a desapuntar ó curso (o importante é o login).
     * @throws ExcepcionBD Excepción asociada a problemas que poden ocorrer durante a consulta.
     */
    public void desapuntarseCurso(Curso curso, Usuario usuario) throws ExcepcionBD {
        PreparedStatement stmCurso = null;
        Connection con;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos o borrado:
        try {
            stmCurso = con.prepareStatement("DELETE FROM realizarcurso " +
                    "WHERE curso = ? and usuario = ? ");

            // Establecemos os valores:
            stmCurso.setInt(1, curso.getCodCurso());
            stmCurso.setString(2, usuario.getLogin());

            // Realizamos a actualización:
            stmCurso.executeUpdate();

            // Facemos commit:
            con.commit();
        } catch (SQLException e) {
            // Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            // En calquera caso, téntase pechar os cursores.
            try {
                stmCurso.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    /**
     * Método para comprobar que se un usuario esta apuntado nun curso.
     *
     * @param curso   Curso no que se quer comprobar se esta apuntado (o importante é o código).
     * @param usuario Usuario que se quer comprobar se esta apuntado (o importante é o login).
     */
    public boolean estarApuntado(Curso curso, Usuario usuario) {
        // Comprobamos que o usuario esta apuntado en dito curso.
        PreparedStatement stmCurso = null;
        ResultSet rsCurso;
        Connection con;
        boolean resultado = false;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmCurso = con.prepareStatement("SELECT curso, usuario " +
                    "FROM realizarcurso " +
                    "WHERE curso = ? and usuario = ? ");

            // Establecemos os valores:
            stmCurso.setInt(1, curso.getCodCurso());
            stmCurso.setString(2, usuario.getLogin());

            // Facemos a consulta:
            rsCurso = stmCurso.executeQuery();

            // Devolvemos true se obtemos algun resultado:
            resultado = rsCurso.next();

            // Feito isto, rematamos co commit:
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // En calquera caso, téntase pechar os cursores:
            try {
                stmCurso.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return resultado;
    }

    /**
     * Método que comproba que se o aforo do curso é o máximo.
     *
     * @param curso Curso no que se quer comprobar se o aforo e máximo.
     * @return Retorna true no caso de que o aforo non sexa o máximo, e false en caso contrario.
     */
    public boolean NonEMaximoAforo(Curso curso) {
        PreparedStatement stmCurso = null;
        ResultSet rsCurso;
        Connection con;
        // Devolveremos un booleano como resultado:
        boolean resultado = false;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Preparamos a consulta:
        try {
            stmCurso = con.prepareStatement("SELECT min(aforomaximo) " +
                    "FROM curso as c " +
                    "JOIN actividade as a " +
                    "ON c.codcurso = a.curso " +
                    "JOIN area ar " +
                    "ON a.area = ar.codarea AND a.instalacion = ar.instalacion " +
                    "WHERE c.codcurso = ? "
            );

            // Establecemos os valores:
            stmCurso.setInt(1, curso.getCodCurso());

            // Executamos a consulta:
            rsCurso = stmCurso.executeQuery();

            // Comprobamos o resultado obtido
            if (rsCurso.next()) {
                resultado = rsCurso.getInt(1) > curso.getParticipantes().size();
            }

            // Feito isto, rematamos co commit:
            con.commit();
        } catch (SQLException e) {
            // Imprimimos en caso de excepción o stack trace:
            e.printStackTrace();
            try {
                // Intentamos facer rollback:
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmCurso.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return resultado;
    }

    /**
     * Método para comprobar se un curso esta almaceado na base de datos.
     *
     * @param curso Curso que do que se quere comprobar a existencia.
     * @return Retorna true se o curso se atopa almaceado na base de datos e false en caso contrario.
     */
    public boolean isCurso(Curso curso) {
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;
        // Devolvemos un booleano como resultado:
        boolean resultado = false;

        // Recuperamos a conexión coa base de datos:
        con = super.getConexion();

        // Intentamos facer a consulta:
        try {
            // Comprobamos se existe un curso con ese código e ese nome:
            stmCursos = con.prepareStatement("SELECT * FROM curso " +
                    "WHERE lower(nome) = lower(?) " +
                    "AND codcurso = ?");


            // Completamos a consulta co campo do nome e do código do curso:
            stmCursos.setString(1, curso.getNome());
            stmCursos.setInt(2, curso.getCodCurso());

            // Realizamos a consulta:
            rsCursos = stmCursos.executeQuery();

            // Comprobamos se houbo resultados: se é así, existe o curso na base de datos:
            if (rsCursos.next()) {
                resultado = true;
            }

            // Feita a consulta, facemos o commit:
            con.commit();
        } catch (SQLException e) {
            // Tentamos facer rollback en caso de excepción:
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Pechamos o statement de cursos:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        // Devolvemos o resultado da consulta:
        return resultado;
    }
}
