package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.actividades.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            for(Actividade actividade: curso.getActividades().values()){ //Usamos o método collection para o percorrido do HashMap.
                //O que temos que facer é, para cada unha das actividades, ila insertando na base de datos e enlazalas ao curso.
                //Cambiamos os campos:
                stmActividades.setDate(1, actividade.getData());
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

}
