package centrodeportivo.baseDatos.dao;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.Incidencia;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOIncidencias extends  AbstractDAO{

    public DAOIncidencias(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    public void insertarIncidencia(Incidencia incidencia) throws SQLException {
        Connection con= this.getConexion();
        PreparedStatement stmIncidencia=null;
        if(incidencia.getTipoIncidencia()== TipoIncidencia.Area){
            stmIncidencia=con.prepareStatement("INSERT INTO incidenciasAreas(usuario,descricion,area,instalacion)  VALUES (?,?,?,?);");
            stmIncidencia.setInt(3,incidencia.getArea().getCodArea());
            stmIncidencia.setInt(4,incidencia.getArea().getInstalacion().getCodInstalacion());
        }else{
            stmIncidencia=con.prepareStatement("INSERT INTO incidenciasMateriais(usuario,descricion,material)  VALUES (?,?,?);");
            stmIncidencia.setInt(3,incidencia.getMaterial().getCodMaterial());
        }
        stmIncidencia.setString(1,incidencia.getUsuario().getLogin());
        stmIncidencia.setString(2,incidencia.getDescricion());
        System.out.println(stmIncidencia);
        stmIncidencia.executeUpdate();
        con.commit();
    }

    public void listarIncidencia() throws SQLException {
        PreparedStatement stmIncidencia = null;
        ArrayList<Incidencia> incidencias=new ArrayList<Incidencia>();
        ResultSet rsIncidencias;
        Connection con=super.getConexion();
        stmIncidencia = con.prepareStatement("SELECT *,ar.nome AS nombreArea,it.nome AS nombreInstalacion,ia.descricion AS descricionIncidencia,ar.descricion AS descricionArea FROM incidenciasAreas AS ia JOIN areas AS ar ON ia.area=ar.codArea JOIN instalacions AS it  ON ar.instalacion=it.codInstalacion");
        rsIncidencias = stmIncidencia.executeQuery();
        Area area;
        Instalacion instalacion;
        while(rsIncidencias.next()){
            instalacion=new Instalacion(
                    rsIncidencias.getInt("codInstalacion"),
                    rsIncidencias.getString("nombreInstalacion"),
                    rsIncidencias.getString("numTelefono"),
                    rsIncidencias.getString("direccion")
            );
            area=new Area(
                    rsIncidencias.getInt("codArea"),
                    instalacion,
                    rsIncidencias.getString("nombreArea"),
                    rsIncidencias.getString("descricionArea"),
                    rsIncidencias.getInt("aforoMaximo"),
                    rsIncidencias.getDate("dataBaixa")
            );
            incidencias.add(new Incidencia(
                    TipoIncidencia.Area,
                    new Usuario(rsIncidencias.getString("usuario")),
                    rsIncidencias.getString("descricionIncidencia"),
                    area
            ));
            System.out.println(incidencias);
        }
    }
}
