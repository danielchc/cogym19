package baseDatos;

import baseDatos.dao.DAOTarifas;
import baseDatos.dao.DAOUsuarios;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class FachadaBD {
    Connection conexion;
    DAOUsuarios daoUsuarios;
    DAOTarifas daoTarifas;

    public FachadaBD() throws IOException, SQLException {
        Properties configuracion = new Properties();
        FileInputStream prop;
        prop = new FileInputStream("baseDatos.properties");
        configuracion.load(prop);
        prop.close();
        Properties usuario = new Properties();
        usuario.setProperty("user", configuracion.getProperty("usuario"));
        usuario.setProperty("password", configuracion.getProperty("clave"));
        String con=String.format("jdbc:%s://%s:%s/%s", configuracion.getProperty("gestor"),configuracion.getProperty("servidor"),configuracion.getProperty("puerto"),configuracion.getProperty("baseDatos"));
        this.conexion= DriverManager.getConnection(con,usuario);
        this.daoUsuarios=new DAOUsuarios(this.conexion);
        this.daoTarifas=new DAOTarifas(this.conexion);
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAOTarifas getDaoTarifas() {
        return daoTarifas;
    }

    public void setDaoTarifas(DAOTarifas daoTarifas) {
        this.daoTarifas = daoTarifas;
    }
}
