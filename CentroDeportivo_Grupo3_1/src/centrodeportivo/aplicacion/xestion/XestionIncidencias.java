package centrodeportivo.aplicacion.xestion;

import centrodeportivo.aplicacion.excepcions.ExcepcionBD;
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

    public XestionIncidencias(FachadaGUI fachadaGUI, FachadaBD fachadaBD) {
        this.fachadaGUI = fachadaGUI;
        this.fachadaBD = fachadaBD;
    }

    public void insertarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        fachadaBD.insertarIncidencia(incidencia);
    }

    public ArrayList<Incidencia> listarIncidencias() {
        return fachadaBD.listarIncidencias("", TipoIncidencia.Todos);
    }

    public ArrayList<Incidencia> listarIncidencias(String textoBuscar, TipoIncidencia tipoIncidencia) {
        return fachadaBD.listarIncidencias(textoBuscar, tipoIncidencia);
    }

    public void resolverIncidencia(Incidencia incidencia) throws ExcepcionBD {
        fachadaBD.resolverIncidencia(incidencia);
    }

    public Incidencia consultarIncidencia(Incidencia incidencia) throws ExcepcionBD {
        return fachadaBD.consultarIncidencia(incidencia);
    }

}
