package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;

import java.sql.Connection;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class AbstractDAO {

    /**
     * Atributos
     */
    private FachadaAplicacion fachadaAplicacion;
    private Connection conexion;


    /**
     * @param conexion Conexión coa base de datos
     * @param fachadaAplicacion fachada da aplicación
     */
    public AbstractDAO(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        this.conexion = conexion;
        this.fachadaAplicacion = fachadaAplicacion;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }
}
