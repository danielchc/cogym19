package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;

import java.sql.Connection;

/**
 * @author Manuel Bendaña
 * @author Helena Castro
 * @author Víctor Barreiro
 * Clase que servirá de pai para todos os DAO que se creen na aplicación, de maneira semellante ao que ocorría na
 * biblioteca. Facémolo para agrupar eses atributos comúns.
 */
public abstract class AbstractDAO {
    /**
     * Atributos comúns a todos os DAO: referencia á fachada da parte de aplicación e a conexión coa BD.
     */
    private FachadaAplicacion fachadaAplicacion;
    private Connection conexion;

    /**
     * Constructor da clase DAO abstracta:
     *
     * @param conexion          A conexión coa base de datos
     * @param fachadaAplicacion A referencia da fachada da parte de aplicación
     */
    public AbstractDAO(Connection conexion, FachadaAplicacion fachadaAplicacion) {
        //Asignamos os parámetros pasados a cada atributo:
        this.conexion = conexion;
        this.fachadaAplicacion = fachadaAplicacion;
    }

    /**
     * Getter da conexión
     *
     * @return A conexión coa base de datos que está almacenada nesta clase.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Setter da conexión
     *
     * @param conexion A conexión que se lle quere asignar ao atributo correspondente.
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Getter da fachada de aplicación
     *
     * @return A referencia á fachada de aplicación
     */
    public FachadaAplicacion getFachadaAplicacion() {
        return fachadaAplicacion;
    }

    /**
     * Setter da fachada de aplicación
     *
     * @param fachadaAplicacion A referencia á fachada de aplicación que se quere asignar.
     */
    public void setFachadaAplicacion(FachadaAplicacion fachadaAplicacion) {
        this.fachadaAplicacion = fachadaAplicacion;
    }
}
