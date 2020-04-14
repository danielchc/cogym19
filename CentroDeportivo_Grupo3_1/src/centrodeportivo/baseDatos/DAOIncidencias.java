package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;
import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaArea;
import centrodeportivo.aplicacion.obxectos.incidencias.IncidenciaMaterial;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.aplicacion.obxectos.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public final class DAOIncidencias extends AbstractDAO {
    public DAOIncidencias(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    protected void insertarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        PreparedStatement stmIncidencia = null;
        try {
            if (incidencia instanceof IncidenciaArea) {
                IncidenciaArea incidenciaArea = (IncidenciaArea) incidencia;
                stmIncidencia = super.getConexion().prepareStatement("INSERT INTO incidenciaArea(usuario,descricion,area,instalacion)  VALUES (?,?,?,?);");
                stmIncidencia.setInt(3, incidenciaArea.getArea().getCodArea());
                stmIncidencia.setInt(4, incidenciaArea.getArea().getInstalacion().getCodInstalacion());
            } else if (incidencia instanceof IncidenciaMaterial) {
                IncidenciaMaterial incidenciaMaterial = (IncidenciaMaterial) incidencia;
                stmIncidencia = super.getConexion().prepareStatement("INSERT INTO incidenciaMaterial(usuario,descricion,material,tipomaterial)  VALUES (?,?,?,?);");
                stmIncidencia.setInt(3, incidenciaMaterial.getMaterial().getCodMaterial());
                stmIncidencia.setInt(4, incidenciaMaterial.getMaterial().getCodTipoMaterial());
            }
            stmIncidencia.setString(1, incidencia.getUsuario().getLogin());
            stmIncidencia.setString(2, incidencia.getDescricion());
            stmIncidencia.executeUpdate();
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmIncidencia.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

    protected ArrayList<Incidencia> listarIncidencias(String textoBuscar, TipoIncidencia tipoIncidencia) {
        PreparedStatement stmIncidencia = null;
        ArrayList<Incidencia> incidencias = new ArrayList<Incidencia>();
        ResultSet rsIncidencias;
        try {
            if (tipoIncidencia == TipoIncidencia.Area || tipoIncidencia == TipoIncidencia.Todos) {
                stmIncidencia = super.getConexion().prepareStatement(
                        "SELECT *,ar.nome AS nomeArea,ia.descricion AS descricionIncidencia,ar.descricion AS descricionArea FROM incidenciaArea AS ia JOIN area AS ar ON ia.area=ar.codArea " +
                                " WHERE ((LOWER(ar.nome) LIKE LOWER(?)) OR (LOWER(ia.descricion) LIKE LOWER(?)))");
                stmIncidencia.setString(1, "%"+textoBuscar+"%");
                stmIncidencia.setString(2, "%"+textoBuscar+"%");
                rsIncidencias = stmIncidencia.executeQuery();
                Area area;
                Instalacion instalacion;
                while (rsIncidencias.next()) {
                    instalacion = new Instalacion(
                            rsIncidencias.getInt("instalacion")
                    );
                    area = new Area(
                            rsIncidencias.getInt("codArea"),
                            instalacion,
                            rsIncidencias.getString("nomeArea"),
                            rsIncidencias.getString("descricionArea"),
                            rsIncidencias.getInt("aforoMaximo"),
                            rsIncidencias.getDate("dataBaixa")
                    );
                    incidencias.add(new IncidenciaArea(
                            rsIncidencias.getInt("numero"),
                            new Usuario(rsIncidencias.getString("usuario")),
                            rsIncidencias.getString("descricionIncidencia"),
                            rsIncidencias.getString("comentarioResolucion"),
                            rsIncidencias.getDate("dataFalla"),
                            rsIncidencias.getDate("dataResolucion"),
                            rsIncidencias.getFloat("custoReparacion"),
                            area
                    ));
                }
            }
            if (tipoIncidencia == TipoIncidencia.Material || tipoIncidencia == TipoIncidencia.Todos) {
                stmIncidencia = super.getConexion().prepareStatement("SELECT " +
                        "*,tm.nome AS tipoMaterialNome,im.descricion AS descricionIncidencia " +
                        "FROM incidenciamaterial AS im JOIN material AS ma ON im.material=ma.codMaterial JOIN tipomaterial AS tm ON tm.codtipomaterial=ma.tipomaterial " +
                        "WHERE ((LOWER(CONCAT_WS(' ',tm.nome,CAST( ma.codMaterial AS VARCHAR(5)))) LIKE LOWER(?) ) OR (LOWER(im.descricion) LIKE LOWER(?)))");
                stmIncidencia.setString(1, "%"+textoBuscar+"%");
                stmIncidencia.setString(2, "%"+textoBuscar+"%");
                rsIncidencias = stmIncidencia.executeQuery();
                Material material;
                while (rsIncidencias.next()) {
                    material = new Material(
                            rsIncidencias.getInt("material"),
                            rsIncidencias.getInt("tipomaterial"),
                            rsIncidencias.getString("tipoMaterialNome"),
                            new Area(
                                    rsIncidencias.getInt("area"),
                                    new Instalacion(rsIncidencias.getInt("instalacion"))
                            ),
                            rsIncidencias.getString("estado"),
                            rsIncidencias.getDate("dataCompra"),
                            rsIncidencias.getFloat("prezoCompra")
                    );
                    incidencias.add(new IncidenciaMaterial(
                            rsIncidencias.getInt("numero"),
                            new Usuario(rsIncidencias.getString("usuario")),
                            rsIncidencias.getString("descricionIncidencia"),
                            rsIncidencias.getString("comentarioResolucion"),
                            rsIncidencias.getDate("dataFalla"),
                            rsIncidencias.getDate("dataResolucion"),
                            rsIncidencias.getFloat("custoReparacion"),
                            material
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmIncidencia.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
        return incidencias;
    }

    protected void resolverIncidencia(Incidencia incidencia) throws ExcepcionBD {
        PreparedStatement stmIncidencia = null;
        try {
            if (incidencia instanceof IncidenciaArea) {
                stmIncidencia = super.getConexion().prepareStatement("UPDATE incidenciaArea SET comentarioResolucion=?, dataResolucion=NOW(), custoReparacion=?  WHERE numero=?;");
                stmIncidencia.setString(1, incidencia.getComentarioResolucion());
                stmIncidencia.setDouble(2, incidencia.getCustoReparacion());
                stmIncidencia.setInt(3, incidencia.getNumero());
                System.out.println(stmIncidencia);
                stmIncidencia.executeUpdate();
            } else if (incidencia instanceof IncidenciaMaterial){
                stmIncidencia = super.getConexion().prepareStatement("UPDATE incidenciaMaterial SET comentarioResolucion=?, dataResolucion=NOW(), custoReparacion=?  WHERE numero=?;");
                stmIncidencia.setString(1, incidencia.getComentarioResolucion());
                stmIncidencia.setDouble(2, incidencia.getCustoReparacion());
                stmIncidencia.setInt(3, incidencia.getNumero());
                stmIncidencia.executeUpdate();
            }
            super.getConexion().commit();
        } catch (SQLException e) {
            throw new ExcepcionBD(super.getConexion(), e);
        } finally {
            try {
                stmIncidencia.close();
            } catch (SQLException e) {
                System.out.println("Imposible pechar os cursores.");
            }
        }
    }

}
