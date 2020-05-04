package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class DAOInstalacions extends AbstractDAO {

    /**
     * @param conexion          Conexión coa base de datos
     * @param fachadaAplicacion fachada da aplicación
     */
    protected DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion, fachadaAplicacion);
    }

    /**
     * Método para buscar todas as áreas dispoñibles.
     *
     * @return lista con todas as áreas.
     */
    protected ArrayList<Area> listarAreas() {
        PreparedStatement stmAreas = null;
        ArrayList<Area> listaAreas = new ArrayList<>();
        ResultSet rsAreas;
        try {
            stmAreas = super.getConexion().prepareStatement("SELECT *,ar.nome AS nomeArea,ins.nome AS nomeInstalacion FROM area AS ar JOIN instalacion AS ins ON ar.instalacion=ins.codinstalacion WHERE (databaixa IS NULL) ORDER BY nomeArea;");
            rsAreas = stmAreas.executeQuery();
            Area area;
            while (rsAreas.next()) {
                area = new Area(
                        rsAreas.getInt("codarea"),
                        new Instalacion(
                                rsAreas.getInt("codinstalacion"),
                                rsAreas.getString("nomeInstalacion"),
                                rsAreas.getString("numtelefono"),
                                rsAreas.getString("direccion")
                        ),
                        rsAreas.getString("nomeArea"),
                        rsAreas.getString("descricion"),
                        rsAreas.getInt("aforoMaximo"),
                        rsAreas.getDate("databaixa")
                );
                listaAreas.add(area);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmAreas.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return listaAreas;
    }

    /**
     * Método para listar todos os materiais dispoñibles.
     *
     * @return lista cos materiais.
     */
    protected ArrayList<Material> listarMateriais(Area area) {
        PreparedStatement stmMateriais = null;
        ArrayList<Material> listaMaterial = new ArrayList<>();
        ResultSet rsMaterial;
        try {
            stmMateriais = super.getConexion().prepareStatement("SELECT * FROM material AS ma JOIN tipomaterial AS tp ON ma.tipomaterial=tp.codtipomaterial WHERE area=? AND instalacion=? ORDER BY tp.nome;");
            stmMateriais.setInt(1, area.getCodArea());
            stmMateriais.setInt(2, area.getInstalacion().getCodInstalacion());
            rsMaterial = stmMateriais.executeQuery();
            while (rsMaterial.next()) {
                listaMaterial.add(new Material(
                                rsMaterial.getInt("codmaterial"),
                                rsMaterial.getInt("tipomaterial"),
                                rsMaterial.getString("nome"),
                                new Area(rsMaterial.getInt("area"), new Instalacion(rsMaterial.getInt("instalacion"))),
                                rsMaterial.getString("estado"),
                                rsMaterial.getDate("datacompra"),
                                rsMaterial.getFloat("prezocompra")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmMateriais.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return listaMaterial;
    }


}
