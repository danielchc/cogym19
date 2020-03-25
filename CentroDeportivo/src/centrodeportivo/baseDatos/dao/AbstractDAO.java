package centrodeportivo.baseDatos.dao;

import java.sql.Connection;

public abstract class AbstractDAO {
    private Connection conexion;

    public AbstractDAO(Connection conexion){
        this.conexion=conexion;
    }
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
