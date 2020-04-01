package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;

import java.sql.Connection;

public abstract class AbstractDAO {
    private FachadaAplicacion fachadaAplicacion;
    private Connection conexion;

    public AbstractDAO(Connection conexion,FachadaAplicacion fachadaAplicacion){
        this.conexion=conexion;
        this.fachadaAplicacion=fachadaAplicacion;
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
