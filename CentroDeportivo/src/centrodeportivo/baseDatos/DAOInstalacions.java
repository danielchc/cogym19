package centrodeportivo.baseDatos;

import centrodeportivo.aplicacion.FachadaAplicacion;
import centrodeportivo.aplicacion.obxectos.area.Instalacion;

import java.sql.Connection;
import java.util.ArrayList;

public final class DAOInstalacions extends AbstractDAO {

    public DAOInstalacions(Connection conexion, FachadaAplicacion fachadaAplicacion){
        super(conexion, fachadaAplicacion);
    }

    public void darAltaInstalacion(Instalacion instalacion){

    }

    public void borrarInstalacion(Instalacion instalacion){

    }

    public void modificarInstalacion(Instalacion instalacion){

    }

    public ArrayList<Instalacion> listarInstalacions(){
        return new ArrayList<>();
    }
}
