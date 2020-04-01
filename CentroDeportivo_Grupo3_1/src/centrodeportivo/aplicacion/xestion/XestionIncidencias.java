package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.Incidencia;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

public class XestionIncidencias {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionIncidencias(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }

    public void insertarIncidencia(Incidencia incidencia) throws SQLException {
        fachadaBD.insertarIncidencia(incidencia);
    }

    public ArrayList<Incidencia> listarIncidencia() throws SQLException  {
        return fachadaBD.listarIncidencia();
    }

    public void resolverIncidencia(Incidencia incidencia) throws SQLException{
        fachadaBD.resolverIncidencia(incidencia);
    }
    
}
