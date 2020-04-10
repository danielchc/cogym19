package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.obxectos.incidencias.Incidencia;
import centrodeportivo.aplicacion.obxectos.tipos.TipoIncidencia;
import centrodeportivo.baseDatos.FachadaBD;
import centrodeportivo.gui.FachadaGUI;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class XestionIncidencias {
    private FachadaGUI fachadaGUI;
    private FachadaBD fachadaBD;

    public XestionIncidencias(FachadaGUI fachadaGUI,FachadaBD fachadaBD) {
        this.fachadaGUI=fachadaGUI;
        this.fachadaBD=fachadaBD;
    }

    public void insertarIncidencia(Incidencia incidencia)  {
        fachadaBD.insertarIncidencia(incidencia);
    }

    public ArrayList<Incidencia> listarIncidencias() {
        return fachadaBD.listarIncidencias("",TipoIncidencia.Todos);
    }

    public ArrayList<Incidencia> listarIncidencias(String descripcion, TipoIncidencia tipoIncidencia) {
        return fachadaBD.listarIncidencias(descripcion,tipoIncidencia);
    }

    public void resolverIncidencia(Incidencia incidencia){
        fachadaBD.resolverIncidencia(incidencia);
    }
    
}
