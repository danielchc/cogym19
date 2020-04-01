package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.Incidencia;
import centrodeportivo.aplicacion.obxectos.area.Material;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;
import centrodeportivo.baseDatos.AbstractDAO;

import java.sql.*;
import java.util.ArrayList;

public final class DAOIncidencias extends AbstractDAO {
    public DAOIncidencias(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected void insertarIncidencia(Incidencia incidencia) {
        PreparedStatement stmIncidencia=null;
        try {
            if (incidencia.getTipoIncidencia() == TipoIncidencia.Area) {
                stmIncidencia = super.getConexion().prepareStatement("INSERT INTO incidenciasAreas(usuario,descricion,area,instalacion)  VALUES (?,?,?,?);");
                stmIncidencia.setInt(3, incidencia.getArea().getCodArea());
                stmIncidencia.setInt(4, incidencia.getArea().getInstalacion().getCodInstalacion());
            } else {
                stmIncidencia = super.getConexion().prepareStatement("INSERT INTO incidenciasMateriais(usuario,descricion,material)  VALUES (?,?,?);");
                stmIncidencia.setInt(3, incidencia.getMaterial().getCodMaterial());
            }
            stmIncidencia.setString(1, incidencia.getUsuario().getLogin());
            stmIncidencia.setString(2, incidencia.getDescricion());
            System.out.println(stmIncidencia);
            stmIncidencia.executeUpdate();
            super.getConexion().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmIncidencia.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected ArrayList<Incidencia> listarIncidencia() {
        PreparedStatement stmIncidencia = null;
        ArrayList<Incidencia> incidencias=new ArrayList<Incidencia>();
        ResultSet rsIncidencias;
        try {
            stmIncidencia = super.getConexion().prepareStatement("SELECT *,ar.nome AS nombreArea,it.nome AS nombreInstalacion,ia.descricion AS descricionIncidencia,ar.descricion AS descricionArea FROM incidenciasAreas AS ia JOIN areas AS ar ON ia.area=ar.codArea JOIN instalacions AS it  ON ar.instalacion=it.codInstalacion");
            rsIncidencias = stmIncidencia.executeQuery();
            Area area;
            Instalacion instalacion;
            while (rsIncidencias.next()) {
                instalacion = new Instalacion(
                        rsIncidencias.getInt("codInstalacion"),
                        rsIncidencias.getString("nombreInstalacion"),
                        rsIncidencias.getString("numTelefono"),
                        rsIncidencias.getString("direccion")
                );
                area = new Area(
                        rsIncidencias.getInt("codArea"),
                        instalacion,
                        rsIncidencias.getString("nombreArea"),
                        rsIncidencias.getString("descricionArea"),
                        rsIncidencias.getInt("aforoMaximo"),
                        rsIncidencias.getDate("dataBaixa")
                );
                incidencias.add(new Incidencia(
                        TipoIncidencia.Area,
                        rsIncidencias.getInt("numero"),
                        new Usuario(rsIncidencias.getString("usuario")),
                        rsIncidencias.getString("descricionIncidencia"),
                        area
                ));
            }
            stmIncidencia = super.getConexion().prepareStatement("SELECT *,im.descricion AS descricionIncidencia FROM incidenciasmateriais AS im JOIN materiais AS ma ON im.material=ma.codMaterial;");
            rsIncidencias = stmIncidencia.executeQuery();
            Material material;
            while (rsIncidencias.next()) {
                material = new Material(
                        rsIncidencias.getInt("numero"),
                        new Area(rsIncidencias.getInt("area"), new Instalacion(rsIncidencias.getInt("instalacion"))),
                        rsIncidencias.getString("nome"),
                        rsIncidencias.getDate("dataCompra"),
                        rsIncidencias.getDouble("prezoCompra")
                );
                incidencias.add(new Incidencia(
                        TipoIncidencia.Material,
                        rsIncidencias.getInt("numero"),
                        new Usuario(rsIncidencias.getString("usuario")),
                        rsIncidencias.getString("descricionIncidencia"),
                        material
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmIncidencia.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return incidencias;
    }

    protected void resolverIncidencia(Incidencia incidencia) {
        PreparedStatement stmIncidencia=null;
        try {
            if (incidencia.getTipoIncidencia() == TipoIncidencia.Area) {
                stmIncidencia = super.getConexion().prepareStatement("UPDATE incidenciasAreas SET comentarioResolucion=?, dataResolucion=NOW(), custoReparacion=?  WHERE numero=?;");
                stmIncidencia.setString(1, incidencia.getComentarioResolucion());
                stmIncidencia.setDouble(2, incidencia.getCustoReparacion());
                stmIncidencia.setInt(3, incidencia.getNumero());
                System.out.println(stmIncidencia);
                stmIncidencia.executeUpdate();
            } else {
                stmIncidencia = super.getConexion().prepareStatement("UPDATE incidenciasMateriais SET comentarioResolucion=?, dataResolucion=NOW(), custoReparacion=?  WHERE numero=?;");
                stmIncidencia.setString(1, incidencia.getComentarioResolucion());
                stmIncidencia.setDouble(2, incidencia.getCustoReparacion());
                stmIncidencia.setInt(3, incidencia.getNumero());
                stmIncidencia.executeUpdate();
            }
            super.getConexion().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                stmIncidencia.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

}
