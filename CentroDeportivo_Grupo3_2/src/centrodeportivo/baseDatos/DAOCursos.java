package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCursos extends AbstractDAO{
    public DAOCursos (Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public void rexistrarCurso(Curso curso) throws ExcepcionBD {
        //Esta é quizais unha das consultas máis complexas, dado que temos que levar a cabo diferentes tarefas:
        //Temos que rexistrar o curso.
        //Temos que rexistrar todas as súas actividades.
        //Temos que unir as actividades ao curso.
        PreparedStatement stmCursos = null;
        PreparedStatement stmActividades = null;
        ResultSet rsCursos;
        Connection con;

        //Recuperamos a conexión:
        con = super.getConexion();

        //Comezamos coa parte de SQL.
        try{
            //Intentaremos, en primeira instancia, rexistrar o curso:
            stmCursos = con.prepareStatement("INSERT INTO curso (nome, descricion, prezo) " +
                    "VALUES (?, ?, ?) ");
            //Completamos a consulta:
            stmCursos.setString(1, curso.getNome());
            stmCursos.setString(2, curso.getDescricion());
            stmCursos.setFloat(3, curso.getPrezo());

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

            //O seguinte paso é insertar todas as actividades creadas:
            //Como a operación é a misma, para só cambiar os parámetros variables dentro do bucle preparamos o statement fóra:
            stmActividades = con.prepareStatement("INSERT INTO actividade" +
                    " (data, area, instalacion, tipoactividade, curso, profesor, nome, duracion)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for(Actividade actividade: curso.getActividades()){
                //O que temos que facer é, para cada unha das actividades, ila insertando na base de datos e enlazalas ao curso.
                //Cambiamos os campos:
                stmActividades.setTimestamp(1, actividade.getData());
                stmActividades.setInt(2, actividade.getArea().getCodArea());
                stmActividades.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
                stmActividades.setInt(4, actividade.getTipoActividade().getCodTipoActividade());
                stmActividades.setInt(5, curso.getCodCurso());
                stmActividades.setString(6, actividade.getProfesor().getLogin());
                stmActividades.setString(7, actividade.getNome());
                stmActividades.setFloat(8, actividade.getDuracion());

                //Executamos a actualización:
                stmActividades.executeUpdate();
            }

            //Se logramos acadar este punto, teremos toda a actualización feita: facemos o commit:
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
        ArrayList<Curso> resultado = null;
        return resultado;
    }
}
