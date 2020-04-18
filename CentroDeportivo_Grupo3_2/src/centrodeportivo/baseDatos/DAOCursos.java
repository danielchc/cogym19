package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Mensaxe;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;

import java.sql.*;
import java.util.ArrayList;

public final class DAOCursos extends AbstractDAO{
    public DAOCursos (Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public void rexistrarCurso(Curso curso) throws ExcepcionBD {
        //Rexistraremos unicamente datos propios do curso (da súa táboa).
        PreparedStatement stmCursos = null;
        PreparedStatement stmActividades = null;
        ResultSet rsCursos;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Comezamos coa parte de SQL.
        try{
            //Intentaremos, en primeira instancia, rexistrar o curso:
            stmCursos = con.prepareStatement("INSERT INTO curso (nome, descricion, prezo, aberto) " +
                    "VALUES (?, ?, ?, ?) ");
            //Completamos a consulta:
            stmCursos.setString(1, curso.getNome());
            stmCursos.setString(2, curso.getDescricion());
            stmCursos.setFloat(3, curso.getPrezo());
            stmCursos.setBoolean(3, false);

            //Realizamos entón a actualización sobre a base de datos:
            stmCursos.executeUpdate();

            //O seguinte paso será recuperar o id do curso creado (usamos o nome, que é CK):
            stmCursos = con.prepareStatement("SELECT codCurso FROM curso WHERE nome = ?");
            //Completamos:
            stmCursos.setString(1, curso.getNome());

            //Realizamos a consulta:
            rsCursos = stmCursos.executeQuery();

            //Comprobamos se hai resultado: tería que haber un: o código do curso insertado.
            if(rsCursos.next()){
                curso.setCodCurso(rsCursos.getInt("codCurso"));
            }

            //Se logramos acadar este punto, teremos toda a actualización feita: facemos o commit.
            con.commit();

        } catch (SQLException e){
            //Lanzaremos a nosa propia excepción dende este punto:
            //Aquí farase o rollback se é necesario.
            throw new ExcepcionBD(con, e);
        } finally {
            //Peche dos statement:
            try {
                stmActividades.close();
                stmCursos.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public void modificarCurso(Curso curso) throws ExcepcionBD {
        //Neste método simplemente recollemos a modificación de datos principais do curso.
        //As actividades poderán ser modificadas nun punto diferente.
        PreparedStatement stmCursos = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        try{
            //Intentamos levar a cabo a actualización: modificación do curso:
            stmCursos = con.prepareStatement("UPDATE curso " +
                    " SET nome = ? " +
                    "     descricion = ? " +
                    "     prezo = ? " +
                    " WHERE codCurso = ? ");

            //Completamos a sentenza anterior cos ?:
            stmCursos.setString(1, curso.getNome());
            stmCursos.setString(2, curso.getDescricion());
            stmCursos.setFloat(3, curso.getPrezo());
            stmCursos.setInt(4, curso.getCodCurso());

            //Realizamos a actualización:
            stmCursos.executeUpdate();

            //Unha vez feita, teremos rematado. Facemos o commit:
            con.commit();
        } catch (SQLException e){
            //Lanzamos a excepción que se obteña:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pechamos os statement.
            try{
                stmCursos.close();
            } catch (SQLException e){
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    public void engadirActividade(Curso curso, Actividade actividade) throws ExcepcionBD {
        //Accederemos á táboa de actividades e inseriremos unha nova asociándoa ao curso correspondente.
        PreparedStatement stmActividades = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos facer a inserción da nova actividade
        try{
            //Preparamos a sentenza:
            stmActividades = con.prepareStatement("INSERT INTO actividade " +
                    "(data, area, instalacion, tipoactividade, curso, profesor, nome, duracion) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //Completamos todos os campos:
            stmActividades.setTimestamp(1, actividade.getData());
            stmActividades.setInt(2, actividade.getArea().getCodArea());
            stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActividades.setInt(4, actividade.getTipoActividade().getCodTipoActividade());
            stmActividades.setInt(5, curso.getCodCurso());
            stmActividades.setString(6, actividade.getProfesor().getLogin());
            stmActividades.setString(7, actividade.getNome());
            stmActividades.setFloat(8, actividade.getDuracion());

            //Realizamos a actualización sobre a base de datos:
            stmActividades.executeUpdate();

            //Unha vez feita, teremos rematado: facemos o commit.
            con.commit();
        } catch (SQLException e){
            //En caso de excepción SQL ao insertar, lanzaremos a nosa propia excepción cara arriba:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pechamos os statement:
            try {
                stmActividades.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public void abrirCurso(Curso curso) throws ExcepcionBD {
        //Introduciremos que o curso está aberto para que a xente poida comezar a rexistrarse nel:
        PreparedStatement stmCursos = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos levar a cabo a apertura do curso:
        try {
            stmCursos = con.prepareStatement("UPDATE curso" +
                    " SET aberto = ?" +
                    " WHERE codcurso = ?");
            //Establecemos os valores dos campos con ?
            stmCursos.setBoolean(1,true);
            stmCursos.setInt(2, curso.getCodCurso());

            //Intentase realizar a actualización:
            stmCursos.executeUpdate();

            //Rematado isto, faise o commit:
            con.commit();

        } catch(SQLException e){
            //Lanzamos a nosa propia excepción:
            throw new ExcepcionBD(con,e);
        } finally {
            //Intentamos pechar o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public void cancelarCurso(Curso curso) throws ExcepcionBD {
        //Haberá que realizar neste caso varias tarefas á vez, pero xa se realizarán por ter cascade:
        //Hai que borrar o curso e con el borraranse actividades e participantes.

        PreparedStatement stmCursos = null;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos levar a cabo o borrado:
        try{
            stmCursos = con.prepareStatement("DELETE FROM curso WHERE codCurso = ?");
            stmCursos.setInt(1, curso.getCodCurso());

            //Executamos a actualización: borraranse curso, actividades e participacións.
            stmCursos.executeUpdate();
        } catch (SQLException e){
            //Lanzaremos unha excepción propia:
            throw new ExcepcionBD(con, e);
        } finally {
            //Pecharemos os statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
    }

    public ArrayList<Curso> consultarCursos(Curso curso){
        //Esta é a consulta que se usará dende a parte de persoal:
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        ArrayList<Curso> resultado = new ArrayList<>();

        //Recuperamos a conexión:
        Connection con = super.getConexion();

        //Intentamos levar a cabo a consulta dos cursos. O resultado que se vai a ofrecer combina diferentes cuestións.
        try{
            //A búsqueda que poderá facer o persoal non ten sentido que inclúa campos como número de actividades ou un rango de prezos.
            //No noso caso centrarémonos en buscar simplemente por un campo, o nome do curso.
            stmCursos = con.prepareStatement("SELECT c.codcurso, c.nome, c.descricion, c.prezo, c.aberto, " +
                    "                                    count(*) as numactividades, min(a.dataactividade) as datainicio, sum(a.duracion) as duracion" +
                    " FROM curso as c, actividade as a" +
                    " WHERE c.codcurso = a.curso" +
                    "   and c.nome like ?" +
                    " GROUP BY c.codcurso");

            //Completamos a consulta:
            stmCursos.setString(1, "%" + curso.getNome() + "%");

            //Intentamos levala a cabo:
            rsCursos = stmCursos.executeQuery();

            //Unha vez feita a consulta, tentamos recuperar os resultados:
            while(rsCursos.next()){
                //Imos creando instancias de cursos cos datos recuperados:
                resultado.add(new Curso(rsCursos.getInt("codcurso"), rsCursos.getString("nome"),
                        rsCursos.getString("descricion"), rsCursos.getFloat("prezo"),
                        rsCursos.getBoolean("aberto"), rsCursos.getFloat("duracion"),
                        rsCursos.getInt("numactividades"), rsCursos.getTimestamp("datainicio")));
            }
            //Rematado isto, facemos o commit:
            con.commit();
        } catch (SQLException e){
            //Tentamos facer rollback:
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        } finally {
            //Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }

        return resultado;
    }

    public boolean comprobarExistencia(Curso curso){
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;
        boolean resultado = false;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos facer a consulta:
        try{
            stmCursos = con.prepareStatement("SELECT * FROM curso" +
                    " WHERE lower(nome) = lower(?) " + //Comprobamos que non coincida o nome estrictamente.
                    "   and codCurso != ? ");
            //Completamos a consulta co campo do código do curso:
            stmCursos.setInt(1, curso.getCodCurso());
            //Realizamos a consulta.
            rsCursos = stmCursos.executeQuery();
            //Comprobamos se houbo resultados: se é así, existe un curso na base de datos (que non é o pasado) co mesmo nome.
            if(rsCursos.next()){
                resultado = true;
            }

            //Feita a consulta, facemos o commit:
            con.commit();
        } catch (SQLException e){
            //Tentamos facer rollback en caso de excepción:
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        } finally {
            //Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        return resultado;
    }

    public boolean tenParticipantes(Curso curso){
        PreparedStatement stmCursos = null;
        ResultSet rsCursos;
        Connection con;
        boolean resultado = false;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Intentamos facer a consulta:
        try {
            //Buscamos se hai algún usuario realizando este curso:
            stmCursos = con.prepareStatement("SELECT * FROM realizarCurso WHERE codCurso = ? ");

            //Completamos a consulta co código do curso:
            stmCursos.setInt(1, curso.getCodCurso());

            //Realizamos a consulta:
            rsCursos = stmCursos.executeQuery();

            //Se hai resultados, o curso terá participantes:
            if(rsCursos.next()){
                resultado = true;
            }

            //Completada a consulta, faise commit:
            con.commit();
        } catch (SQLException e){
            //Tentamos facer rollback en caso de excepción:
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        } finally {
            //Pechamos o statement:
            try {
                stmCursos.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores");
            }
        }
        //Devolvemos o booleano:
        return resultado;
    }
}
