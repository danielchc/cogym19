package centrodeportivo.baseDatos.dao;

import centrodeportivo.aplicacion.obxectos.Incidencia;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOIncidencias extends  AbstractDAO{

    public DAOIncidencias(Connection conexion) {
        super(conexion);
    }

    public void insertarIncidencia(Incidencia incidencia) throws SQLException {
        Connection con= this.getConexion();
        PreparedStatement stmIncidencia=null;
        if(incidencia.getTipoIncidencia()== TipoIncidencia.Area){
            stmIncidencia=con.prepareStatement("INSERT INTO incidenciasAreas(usuario,descricion,area,instalacion)  VALUES (?,?,?,?);");
            stmIncidencia.setInt(3,incidencia.getArea());
            stmIncidencia.setInt(4,incidencia.getInstalacion());
        }else{
            stmIncidencia=con.prepareStatement("INSERT INTO incidenciasMateriais(usuario,descricion,material)  VALUES (?,?,?);");
            stmIncidencia.setInt(3,incidencia.getMaterial());
        }
        stmIncidencia.setString(1,incidencia.getUsuario());
        stmIncidencia.setString(2,incidencia.getDescricion());
        System.out.println(stmIncidencia);
        stmIncidencia.executeUpdate();
        con.commit();
    }
}
