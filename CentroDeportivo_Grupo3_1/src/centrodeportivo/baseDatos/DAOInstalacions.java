package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DAOInstalacions extends AbstractDAO {

    public DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }




}
