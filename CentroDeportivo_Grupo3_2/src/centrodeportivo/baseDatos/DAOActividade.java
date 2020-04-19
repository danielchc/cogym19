package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.actividades.Actividade;
import centrodeportivo.aplicacion.obxectos.area.Area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOActividade extends AbstractDAO {

    public DAOActividade (Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public void EngadiActividade(Actividade actividade) throws ExcepcionBD {
        PreparedStatement stmActivide = null;
        ResultSet rsActividade;
        Connection con;
        //Recuperamos a conexión coa base de datos.
        con = super.getConexion();

        //Preparamos a inserción:
        try {
            stmActivide = con.prepareStatement("INSERT INTO Actividade (dataactividade, area, instalacion, tipoactividade, curso, profesor, nome, duracion) " +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //Establecemos os valores:
            stmActivide.setTimestamp(1, actividade.getData());
            stmActivide.setInt(2, actividade.getArea().getCodArea());
            stmActivide.setInt(3, actividade.getArea().getInstalacion().getCodInstalacion());
            stmActivide.setInt(4, actividade.getTipoActividade().getCodTipoActividade());
            stmActivide.setInt(5, actividade.getCurso().getCodCurso());
            stmActivide.setString(6, actividade.getProfesor().getLogin());
            stmActivide.setString(7, actividade.getNome());
            stmActivide.setFloat(8, actividade.getDuracion());

            //Realizamos a actualización:
            stmActivide.executeUpdate();

            //Facemos commit:
            con.commit();

        } catch (SQLException e){
            //Lanzamos neste caso unha excepción cara a aplicación:
            throw new ExcepcionBD(con, e);
        } finally {
            //En calquera caso, téntase pechar os cursores.
            try {
                stmActivide.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

   /* public boolean
    {
        stmActivide = con.prepareStatement("SELECT codinstalacion " +
                " FROM instalacion " +
                " WHERE codinstalacion = ? ");

        stmActivide.setInt(1, area.getInstalacion().getCodInstalacion());

        //Facemos a consulta:
        rsActividade = stmActivide.executeQuery();
    }*/

}
