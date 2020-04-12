package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.Material;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;
import centrodeportivo.aplicacion.obxectos.area.Area;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class DAOInstalacions extends AbstractDAO {

    protected DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected HashMap<Area,ArrayList<Material>> listarAreas(){
        PreparedStatement stmAreas = null,stmMateriais=null;
        HashMap<Area,ArrayList<Material>> listaAreaMaterial=new HashMap<>();
        ResultSet rsAreas,rsMaterial;
        try {
            stmAreas = super.getConexion().prepareStatement("SELECT *,ar.nome AS nomeArea,ins.nome AS nomeInstalacion FROM area AS ar JOIN instalacion AS ins ON ar.instalacion=ins.codinstalacion WHERE (databaixa IS NULL) ORDER BY nomeArea;");
            rsAreas = stmAreas.executeQuery();
            Area area;
            while (rsAreas.next()) {
                ArrayList<Material> listaMaterial=new ArrayList<>();
                area=new Area(
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
                stmMateriais = super.getConexion().prepareStatement("SELECT * FROM material AS ma JOIN tipomaterial AS tp ON ma.tipomaterial=tp.codtipomaterial WHERE area=? AND instalacion=? ORDER BY tp.nome;");
                stmMateriais.setInt(1, area.getCodArea());
                stmMateriais.setInt(2, area.getInstalacion().getCodInstalacion());
                rsMaterial = stmMateriais.executeQuery();
                while (rsMaterial.next()) {
                    listaMaterial.add(new Material(
                            rsMaterial.getInt("codmaterial"),
                            rsMaterial.getInt("tipomaterial"),
                            rsMaterial.getString("nome"),
                            area,
                            rsMaterial.getString("estado"),
                            rsMaterial.getDate("datacompra"),
                            rsMaterial.getFloat("prezocompra")
                            )
                    );
                }
                listaAreaMaterial.put(area, listaMaterial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmAreas.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return listaAreaMaterial;
    }



}
