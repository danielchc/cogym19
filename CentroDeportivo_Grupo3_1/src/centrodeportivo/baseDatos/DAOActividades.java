package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.actividades.TipoActividade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class DAOActividades extends AbstractDAO {

    /**
     * @param conexion Conexión coa base de datos
     * @param fachadaAplicacion fachada da aplicación
     */
    protected DAOActividades(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        super(conexion,fachadaAplicacion);
    }

    protected ArrayList<TipoActividade> listarTipoActividades(){
        PreparedStatement stmActividade = null;
        ArrayList<TipoActividade> tipoActividades=new ArrayList<TipoActividade>();
        ResultSet rsActividade;
        try {
            stmActividade = super.getConexion().prepareStatement("SELECT * FROM tipoactividade;");
            rsActividade = stmActividade.executeQuery();
            while (rsActividade.next()) {
                tipoActividades.add(new TipoActividade(
                        rsActividade.getInt("codtipoactividade"),
                        rsActividade.getString("nome"),
                        rsActividade.getString("descricion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmActividade.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return tipoActividades;
    }

}
